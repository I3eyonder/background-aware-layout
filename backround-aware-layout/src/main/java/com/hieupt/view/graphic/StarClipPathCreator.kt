package com.hieupt.view.graphic

import android.graphics.Path
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by HieuPT on 10/20/2020.
 */
class StarClipPathCreator(
    val vertices: Int
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
        val vertices: Int = this.vertices * 2
        val alpha = (2 * Math.PI).toFloat() / vertices
        val radius = (if (height <= width) height else width) / 2
        val centerX = (left + right) / 2f
        val centerY = (top + bottom) / 2f

        val path = Path()
        for (i in vertices + 1 downTo 1) {
            val r = radius * (i % 2 + 1) / 2f
            val omega = alpha * i.toDouble()
            path.lineTo(
                (r * sin(omega)).toFloat() + centerX,
                (r * cos(omega)).toFloat() + centerY
            )
        }
        path.close()
        return path
    }
}