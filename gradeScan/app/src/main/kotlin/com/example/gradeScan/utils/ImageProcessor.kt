package com.example.gradeScan.utils

import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.util.*

object ImageProcessor {

    // Detects document corners, applies skew correction, and crops the image
    fun detectAndTransformDocument(bitmap: Bitmap): Bitmap? {
        // Convert the bitmap to OpenCV Mat
        val src = Mat()
        Utils.bitmapToMat(bitmap, src)

        // Convert image to grayscale
        val gray = Mat()
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY)

        // Apply Gaussian blur to reduce noise
        Imgproc.GaussianBlur(gray, gray, Size(5.0, 5.0), 0.0)

        // Use Canny edge detection to detect edges
        val edges = Mat()
        Imgproc.Canny(gray, edges, 75.0, 200.0)

        // Find contours
        val contours: List<MatOfPoint> = ArrayList()
        val hierarchy = Mat()
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE)

        // Sort contours by area and filter out the largest contour
        val sortedContours = contours.sortedByDescending { Imgproc.contourArea(it) }
        var documentContour: MatOfPoint2f? = null
        for (contour in sortedContours) {
            val contour2f = MatOfPoint2f(*contour.toArray())
            val peri = Imgproc.arcLength(contour2f, true)
            val approx = MatOfPoint2f()
            Imgproc.approxPolyDP(contour2f, approx, 0.02 * peri, true)
            if (approx.total() == 4L) {
                documentContour = approx
                break
            }
        }

        // If no contour with four corners was found, return null
        if (documentContour == null) {
            return null
        }

        // Define destination points for perspective transformation (A4 size)
        val targetWidth = 595.0
        val targetHeight = 842.0
        val destPoints = listOf(
            Point(0.0, 0.0),
            Point(targetWidth, 0.0),
            Point(targetWidth, targetHeight),
            Point(0.0, targetHeight)
        )

        // Order source points (document corners) to match destination points order
        val srcPoints = orderPoints(documentContour.toArray())
        val srcMat = MatOfPoint2f(*srcPoints)
        val destMat = MatOfPoint2f(*destPoints.toTypedArray())

        // Apply perspective transformation
        val perspectiveTransform = Imgproc.getPerspectiveTransform(srcMat, destMat)
        val output = Mat()
        Imgproc.warpPerspective(src, output, perspectiveTransform, Size(targetWidth, targetHeight))

        // Convert back to Bitmap
        val transformedBitmap = Bitmap.createBitmap(output.cols(), output.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(output, transformedBitmap)

        // Release Mat resources
        src.release()
        gray.release()
        edges.release()
        hierarchy.release()
        output.release()
        perspectiveTransform.release()

        return transformedBitmap
    }

    // Orders points in a consistent way: top-left, top-right, bottom-right, bottom-left
    private fun orderPoints(points: Array<Point>): Array<Point> {
        val sortedPoints = points.sortedWith(compareBy({ it.y }, { it.x })).toMutableList()
        val topTwo = sortedPoints.take(2).sortedBy { it.x }
        val bottomTwo = sortedPoints.takeLast(2).sortedByDescending { it.x }
        return arrayOf(topTwo[0], topTwo[1], bottomTwo[0], bottomTwo[1])
    }
}
