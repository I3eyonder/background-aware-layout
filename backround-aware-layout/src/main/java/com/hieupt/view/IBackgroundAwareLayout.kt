package com.hieupt.view

import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.drawable.Drawable
import com.hieupt.view.graphic.IClipPathCreator


/**
 * Created by HieuPT on 10/20/2020.
 */
interface IBackgroundAwareLayout {
    val clipPathEraser: Paint
    val backgroundEraser: Paint
    val tintPaint: Paint

    interface IBackgroundAwareLayoutParams {
        var backgroundAwareMode: BackgroundAwareMode
        var backgroundAware: Drawable?
        var backgroundAwareTint: ColorStateList?
        var backgroundAwarePathCreator: IClipPathCreator?
        var backgroundAwareScaleType: BackgroundAwareScaleType
    }
}