package com.hieupt.view.graphic

import android.graphics.Path
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Created by HieuPT on 10/20/2020.
 */
class PolygonClipPathCreator(
    val numberOfSides: Int
) : IClipPathCreator {

    override fun createClipPath(
        view: View,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        width: Float,
        height: Float
    ): Path {
        val section = (2.0 * Math.PI / numberOfSides).toFloat()
        val polygonSize = min(width, height)
        val radius = polygonSize / 2
        val centerX = (left + right) / 2f
        val centerY = (top + bottom) / 2f

        val polygonPath = Path()
        polygonPath.moveTo(
            centerX + radius * cos(0.0).toFloat(), centerY + radius * sin(0.0)
                .toFloat()
        )

        for (i in 1 until numberOfSides) {
            polygonPath.lineTo(
                centerX + radius * cos(section * i.toDouble()).toFloat(),
                centerY + radius * sin(section * i.toDouble()).toFloat()
            )
        }

        polygonPath.close()
        return polygonPath
    }
}