package com.hieupt.view.graphic

import android.graphics.Path
import android.view.View
import kotlin.math.tan

/**
 * Created by HieuPT on 10/20/2020.
 */
class DiagonalClipPathCreator(
    val diagonalAngle: Double,
    val diagonalDirection: DiagonalDirection,
    val diagonalPosition: DiagonalPosition
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
        val path = Path()
        val perpendicularHeight = (width * tan(Math.toRadians(diagonalAngle))).toFloat()
        val isDirectionLeft = diagonalDirection == DiagonalDirection.LEFT

        val innerLeft = left + view.paddingLeft.toFloat()
        val innerRight = right - view.paddingRight.toFloat()
        val innerTop = top + view.paddingTop.toFloat()
        val innerBottom = bottom - view.paddingBottom.toFloat()

        when (diagonalPosition) {
            DiagonalPosition.BOTTOM -> if (isDirectionLeft) {
                path.moveTo(innerLeft, innerTop)
                path.lineTo(innerRight, innerTop)
                path.lineTo(innerRight, innerBottom - perpendicularHeight)
                path.lineTo(innerLeft, innerBottom)
                path.close()
            } else {
                path.moveTo(innerRight, innerBottom)
                path.lineTo(innerLeft, innerBottom - perpendicularHeight)
                path.lineTo(innerLeft, innerTop)
                path.lineTo(innerRight, innerTop)
                path.close()
            }
            DiagonalPosition.TOP -> if (isDirectionLeft) {
                path.moveTo(innerRight, innerBottom)
                path.lineTo(innerRight, innerTop + perpendicularHeight)
                path.lineTo(innerLeft, innerTop)
                path.lineTo(innerLeft, innerBottom)
                path.close()
            } else {
                path.moveTo(innerRight, innerBottom)
                path.lineTo(innerRight, innerTop)
                path.lineTo(innerLeft, innerTop + perpendicularHeight)
                path.lineTo(innerLeft, innerBottom)
                path.close()
            }
            DiagonalPosition.RIGHT -> if (isDirectionLeft) {
                path.moveTo(innerLeft, innerTop)
                path.lineTo(innerRight, innerTop)
                path.lineTo(innerRight - perpendicularHeight, innerBottom)
                path.lineTo(innerLeft, innerBottom)
                path.close()
            } else {
                path.moveTo(innerLeft, innerTop)
                path.lineTo(innerRight - perpendicularHeight, innerTop)
                path.lineTo(innerRight, innerBottom)
                path.lineTo(innerLeft, innerBottom)
                path.close()
            }
            DiagonalPosition.LEFT -> if (isDirectionLeft) {
                path.moveTo(innerLeft + perpendicularHeight, innerTop)
                path.lineTo(innerRight, innerTop)
                path.lineTo(innerRight, innerBottom)
                path.lineTo(innerLeft, innerBottom)
                path.close()
            } else {
                path.moveTo(innerLeft, innerTop)
                path.lineTo(innerRight, innerTop)
                path.lineTo(innerRight, innerBottom)
                path.lineTo(innerLeft + perpendicularHeight, innerBottom)
                path.close()
            }
        }
        return path
    }

    enum class DiagonalDirection {
        LEFT,
        RIGHT
    }

    enum class DiagonalPosition {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
}