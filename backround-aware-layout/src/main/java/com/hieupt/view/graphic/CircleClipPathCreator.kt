package com.hieupt.view.graphic

import android.graphics.Path
import android.view.View
import kotlin.math.min

/**
 * Created by HieuPT on 10/20/2020.
 */
class CircleClipPathCreator(
    val radius: Float? = null
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
        val radius = this.radius ?: min(width / 2f, height / 2f)
        return Path().apply {
            addCircle(
                (left + right) / 2f,
                (top + bottom) / 2f,
                radius,
                Path.Direction.CW
            )
        }
    }
}