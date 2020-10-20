package com.hieupt.view.graphic

import android.graphics.Path
import android.graphics.RectF
import android.view.View

/**
 * Created by HieuPT on 10/20/2020.
 */
class BubbleClipPathCreator(
    val cornerRadius: Float,
    val arrowPosition: ArrowPosition,
    val arrowWidth: Float,
    val arrowHeight: Float
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
        return drawBubble(rectF, cornerRadius, cornerRadius, cornerRadius, cornerRadius)
    }

    private fun drawBubble(
        myRect: RectF,
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
        val spacingLeft = if (arrowPosition == ArrowPosition.LEFT) arrowHeight else 0f
        val spacingTop = if (arrowPosition == ArrowPosition.TOP) arrowHeight else 0f
        val spacingRight = if (arrowPosition == ArrowPosition.RIGHT) arrowHeight else 0f
        val spacingBottom = if (arrowPosition == ArrowPosition.BOTTOM) arrowHeight else 0f
        val left = spacingLeft + myRect.left
        val top = spacingTop + myRect.top
        val right = myRect.right - spacingRight
        val bottom = myRect.bottom - spacingBottom
        val centerX = myRect.centerX()
        path.moveTo(left + topLeftDiameterFinal / 2f, top)
        //LEFT, TOP
        if (arrowPosition == ArrowPosition.TOP) {
            path.lineTo(centerX - arrowWidth, top)
            path.lineTo(centerX, myRect.top)
            path.lineTo(centerX + arrowWidth, top)
        }
        path.lineTo(right - topRightDiameterFinal / 2f, top)
        path.quadTo(right, top, right, top + topRightDiameterFinal / 2)
        //RIGHT, TOP
        if (arrowPosition == ArrowPosition.RIGHT) {
            path.lineTo(right, bottom / 2f - arrowWidth)
            path.lineTo(myRect.right, bottom / 2f)
            path.lineTo(right, bottom / 2f + arrowWidth)
        }
        path.lineTo(right, bottom - bottomRightDiameterFinal / 2)
        path.quadTo(right, bottom, right - bottomRightDiameterFinal / 2, bottom)
        //RIGHT, BOTTOM
        if (arrowPosition == ArrowPosition.BOTTOM) {
            path.lineTo(centerX + arrowWidth, bottom)
            path.lineTo(centerX, myRect.bottom)
            path.lineTo(centerX - arrowWidth, bottom)
        }
        path.lineTo(left + bottomLeftDiameterFinal / 2, bottom)
        path.quadTo(left, bottom, left, bottom - bottomLeftDiameterFinal / 2)
        //LEFT, BOTTOM
        if (arrowPosition == ArrowPosition.LEFT) {
            path.lineTo(left, bottom / 2f + arrowWidth)
            path.lineTo(myRect.left, bottom / 2f)
            path.lineTo(left, bottom / 2f - arrowWidth)
        }
        path.lineTo(left, top + topLeftDiameterFinal / 2)
        path.quadTo(left, top, left + topLeftDiameterFinal / 2, top)
        path.close()
        return path
    }

    enum class ArrowPosition {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
}