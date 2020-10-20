package com.hieupt.view.graphic

import android.graphics.Path
import android.view.View

/**
 * Created by HieuPT on 10/20/2020.
 */
fun interface IClipPathCreator {

    fun createClipPath(
        view: View,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        width: Float,
        height: Float
    ): Path
}