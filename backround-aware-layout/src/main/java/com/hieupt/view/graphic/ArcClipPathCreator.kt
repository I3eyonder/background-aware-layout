package com.hieupt.view.graphic

import android.graphics.Path
import android.view.View

/**
 * Created by HieuPT on 10/20/2020.
 */
class ArcClipPathCreator(
    val arcHeight: Float,
    val cropDirection: ArcCropDirection,
    val arcPosition: ArcPosition
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
        val isCropInside = cropDirection == ArcCropDirection.INSIDE
        when (arcPosition) {
            ArcPosition.BOTTOM -> if (isCropInside) {
                path.moveTo(left, top)
                path.lineTo(left, bottom)
                path.quadTo(
                    (left + right) / 2f,
                    bottom - 2 * arcHeight,
                    right,
                    bottom
                )
                path.lineTo(right, top)
                path.close()
            } else {
                path.moveTo(left, top)
                path.lineTo(left, bottom - arcHeight)
                path.quadTo(
                    (left + right) / 2f,
                    bottom + arcHeight,
                    right,
                    bottom - arcHeight
                )
                path.lineTo(right, top)
                path.close()
            }
            ArcPosition.TOP -> if (isCropInside) {
                path.moveTo(left, bottom)
                path.lineTo(left, top)
                path.quadTo(
                    (left + right) / 2f,
                    top + 2 * arcHeight,
                    right,
                    top
                )
                path.lineTo(right, bottom)
                path.close()
            } else {
                path.moveTo(left, top + arcHeight)
                path.quadTo(
                    (left + right) / 2f,
                    top - arcHeight,
                    right,
                    top + arcHeight
                )
                path.lineTo(right, bottom)
                path.lineTo(left, bottom)
                path.close()
            }
            ArcPosition.LEFT -> if (isCropInside) {
                path.moveTo(right, top)
                path.lineTo(left, top)
                path.quadTo(
                    left + arcHeight * 2f,
                    (top + bottom) / 2f,
                    left,
                    bottom
                )
                path.lineTo(right, bottom)
                path.close()
            } else {
                path.moveTo(right, top)
                path.lineTo(left + arcHeight, top)
                path.quadTo(
                    left - arcHeight,
                    (top + bottom) / 2f,
                    left + arcHeight,
                    bottom
                )
                path.lineTo(right, bottom)
                path.close()
            }
            ArcPosition.RIGHT -> if (isCropInside) {
                path.moveTo(left, top)
                path.lineTo(right, top)
                path.quadTo(
                    right - arcHeight * 2f,
                    (top + bottom) / 2f,
                    right,
                    bottom
                )
                path.lineTo(left, bottom)
                path.close()
            } else {
                path.moveTo(left, top)
                path.lineTo(right - arcHeight, top)
                path.quadTo(
                    right + arcHeight,
                    (top + bottom) / 2f,
                    right - arcHeight,
                    bottom
                )
                path.lineTo(left, bottom)
                path.close()
            }
        }
        return path
    }

    enum class ArcCropDirection {
        INSIDE,
        OUTSIDE
    }

    enum class ArcPosition {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
}