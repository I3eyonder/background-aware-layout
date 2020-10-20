package com.hieupt.view.graphic

import android.graphics.Path
import android.view.View

/**
 * Created by HieuPT on 10/21/2020.
 */
class CompositeClipPathCreator(
    vararg val clipPathCreators: IClipPathCreator
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
        val compositePath = Path()
        clipPathCreators.forEach {
            compositePath.addPath(it.createClipPath(view, left, top, right, bottom, width, height))
        }
        return compositePath
    }
}