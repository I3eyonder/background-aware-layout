package com.hieupt.view.graphic

import android.graphics.Path
import android.graphics.PointF
import android.view.View

/**
 * Created by HieuPT on 10/20/2020.
 */
class TriangleClipPathCreator(
    val verticesAPercent: PointF,
    val verticesBPercent: PointF,
    val verticesCPercent: PointF
) : IClipPathCreator {

    constructor(
        verticesAPercentX: Float,
        verticesAPercentY: Float,
        verticesBPercentX: Float,
        verticesBPercentY: Float,
        verticesCPercentX: Float,
        verticesCPercentY: Float
    ) : this(
        PointF(verticesAPercentX, verticesAPercentY),
        PointF(verticesBPercentX, verticesBPercentY),
        PointF(verticesCPercentX, verticesCPercentY)
    )

    override fun createClipPath(
        view: View,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        width: Float,
        height: Float
    ): Path {
        return Path().apply {
            moveTo(left + verticesAPercent.x * width, top + verticesAPercent.y * height)
            lineTo(left + verticesBPercent.x * width, top + verticesBPercent.y * height)
            lineTo(left + verticesCPercent.x * width, top + verticesCPercent.y * height)
            close()
        }
    }
}