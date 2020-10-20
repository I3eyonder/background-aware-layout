package com.hieupt.view.graphic

import android.graphics.Path
import android.graphics.RectF
import android.view.View
import kotlin.math.min

/**
 * Created by HieuPT on 10/20/2020.
 */
class RoundRectClipPathCreator(
    val topLeftRadius: Float = 0f,
    val topRightRadius: Float = 0f,
    val bottomRightRadius: Float = 0f,
    val bottomLeftRadius: Float = 0f
) : IClipPathCreator {

    private val rectF = RectF()

    constructor(radius: Float = 0f) : this(radius, radius, radius, radius)

    override fun createClipPath(
        view: View,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        width: Float,
        height: Float
    ): Path {
        rectF.set(left, top, right, bottom)
        return generatePath(
            rectF,
            limitSize(topLeftRadius, width, height),
            limitSize(topRightRadius, width, height),
            limitSize(bottomRightRadius, width, height),
            limitSize(bottomLeftRadius, width, height)
        )
    }

    private fun generatePath(
        rect: RectF,
        topLeftRadius: Float,
        topRightRadius: Float,
        bottomRightRadius: Float,
        bottomLeftRadius: Float
    ): Path {
        val path = Path()
        val topLeftRadiusFinal = if (topLeftRadius < 0) 0f else topLeftRadius
        val topRightRadiusFinal = if (topRightRadius < 0) 0f else topRightRadius
        val bottomLeftRadiusFinal = if (bottomLeftRadius < 0) 0f else bottomLeftRadius
        val bottomRightRadiusFinal = if (bottomRightRadius < 0) 0f else bottomRightRadius
        path.moveTo(rect.left + topLeftRadiusFinal, rect.top)
        path.lineTo(rect.right - topRightRadiusFinal, rect.top)
        path.quadTo(rect.right, rect.top, rect.right, rect.top + topRightRadiusFinal)
        path.lineTo(rect.right, rect.bottom - bottomRightRadiusFinal)
        path.quadTo(rect.right, rect.bottom, rect.right - bottomRightRadiusFinal, rect.bottom)
        path.lineTo(rect.left + bottomLeftRadiusFinal, rect.bottom)
        path.quadTo(rect.left, rect.bottom, rect.left, rect.bottom - bottomLeftRadiusFinal)
        path.lineTo(rect.left, rect.top + topLeftRadiusFinal)
        path.quadTo(rect.left, rect.top, rect.left + topLeftRadiusFinal, rect.top)
        path.close()
        return path
    }

    private fun limitSize(from: Float, width: Float, height: Float): Float {
        return min(from, min(width, height))
    }
}