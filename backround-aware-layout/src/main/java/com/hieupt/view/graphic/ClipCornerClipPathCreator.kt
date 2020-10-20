package com.hieupt.view.graphic

import android.graphics.Path
import android.graphics.RectF
import android.view.View

/**
 * Created by HieuPT on 10/20/2020.
 */
class ClipCornerClipPathCreator(
    val topLeftCutSize: Float = 0f,
    val topRightCutSize: Float = 0f,
    val bottomRightCutSize: Float = 0f,
    val bottomLeftCutSize: Float = 0f
) : IClipPathCreator {

    private val rectF = RectF()

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
            topLeftCutSize,
            topRightCutSize,
            bottomRightCutSize,
            bottomLeftCutSize
        )
    }

    private fun generatePath(
        rect: RectF,
        topLeftDiameter: Float,
        topRightDiameter: Float,
        bottomRightDiameter: Float,
        bottomLeftDiameter: Float
    ): Path {
        val path = Path()
        val topLeftDiameterFinal = if (topLeftDiameter < 0) 0f else topLeftDiameter
        val topRightDiameterFinal = if (topRightDiameter < 0) 0f else topRightDiameter
        val bottomLeftDiameterFinal = if (bottomLeftDiameter < 0) 0f else bottomLeftDiameter
        val bottomRightDiameterFinal = if (bottomRightDiameter < 0) 0f else bottomRightDiameter
        path.moveTo(rect.left + topLeftDiameterFinal, rect.top)
        path.lineTo(rect.right - topRightDiameterFinal, rect.top)
        path.lineTo(rect.right, rect.top + topRightDiameterFinal)
        path.lineTo(rect.right, rect.bottom - bottomRightDiameterFinal)
        path.lineTo(rect.right - bottomRightDiameterFinal, rect.bottom)
        path.lineTo(rect.left + bottomLeftDiameterFinal, rect.bottom)
        path.lineTo(rect.left, rect.bottom - bottomLeftDiameterFinal)
        path.lineTo(rect.left, rect.top + topLeftDiameterFinal)
        path.lineTo(rect.left + topLeftDiameterFinal, rect.top)
        path.close()
        return path
    }
}