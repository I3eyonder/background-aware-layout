package com.hieupt.view.extensions

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.View
import com.hieupt.view.BackgroundAwareMode
import com.hieupt.view.BackgroundAwareScaleType
import com.hieupt.view.IBackgroundAwareLayout
import com.hieupt.view.graphic.IClipPathCreator

/**
 * Created by HieuPT on 3/3/2021.
 */
var View.backgroundAware: Drawable?
    get() = (layoutParams as? IBackgroundAwareLayout.IBackgroundAwareLayoutParams)?.backgroundAware
    set(value) {
        val lp = layoutParams
        if (lp is IBackgroundAwareLayout.IBackgroundAwareLayoutParams) {
            invalidate()
            lp.backgroundAware = value
            layoutParams = lp
        }
    }

var View.backgroundAwareTint: ColorStateList?
    get() = (layoutParams as? IBackgroundAwareLayout.IBackgroundAwareLayoutParams)?.backgroundAwareTint
    set(value) {
        val lp = layoutParams
        if (lp is IBackgroundAwareLayout.IBackgroundAwareLayoutParams) {
            invalidate()
            lp.backgroundAwareTint = value
            layoutParams = lp
        }
    }

var View.backgroundAwareMode: BackgroundAwareMode
    get() = (layoutParams as? IBackgroundAwareLayout.IBackgroundAwareLayoutParams)?.backgroundAwareMode
        ?: BackgroundAwareMode.TINT
    set(value) {
        val lp = layoutParams
        if (lp is IBackgroundAwareLayout.IBackgroundAwareLayoutParams) {
            invalidate()
            lp.backgroundAwareMode = value
            layoutParams = lp
        }
    }

var View.backgroundAwarePathCreator: IClipPathCreator?
    get() = (layoutParams as? IBackgroundAwareLayout.IBackgroundAwareLayoutParams)?.backgroundAwarePathCreator
    set(value) {
        val lp = layoutParams
        if (lp is IBackgroundAwareLayout.IBackgroundAwareLayoutParams) {
            invalidate()
            lp.backgroundAwarePathCreator = value
            layoutParams = lp
        }
    }

var View.backgroundAwareScaleType: BackgroundAwareScaleType
    get() = (layoutParams as? IBackgroundAwareLayout.IBackgroundAwareLayoutParams)?.backgroundAwareScaleType
        ?: BackgroundAwareScaleType.CENTER
    set(value) {
        val lp = layoutParams
        if (lp is IBackgroundAwareLayout.IBackgroundAwareLayoutParams) {
            invalidate()
            lp.backgroundAwareScaleType = value
            layoutParams = lp
        }
    }
