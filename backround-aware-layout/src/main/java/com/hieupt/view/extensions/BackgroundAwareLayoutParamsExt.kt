package com.hieupt.view.extensions

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.use
import com.hieupt.R
import com.hieupt.view.BackgroundAwareMode
import com.hieupt.view.BackgroundAwareScaleType
import com.hieupt.view.IBackgroundAwareLayout

/**
 * Created by HieuPT on 3/3/2021.
 */
internal fun IBackgroundAwareLayout.IBackgroundAwareLayoutParams.obtainStyledAttributes(
    context: Context,
    set: AttributeSet?,
    attrs: IntArray
) {
    context.obtainStyledAttributes(set, attrs).use {
        backgroundAware =
            it.getDrawable(R.styleable.BackgroundAwareGlobalAttr_layout_backgroundAware)
        backgroundAwareTint =
            it.getColorStateList(R.styleable.BackgroundAwareGlobalAttr_layout_backgroundAwareTint)
        backgroundAwareMode = if (it.getInt(
                R.styleable.BackgroundAwareGlobalAttr_layout_backgroundAwareMode,
                -1
            ) == BackgroundAwareMode.CLEAR.ordinal
        ) {
            BackgroundAwareMode.CLEAR
        } else {
            BackgroundAwareMode.TINT
        }
        val scaleTypeOrdinal =
            it.getInt(R.styleable.BackgroundAwareGlobalAttr_layout_backgroundAwareScaleType, -1)
        backgroundAwareScaleType =
            if (scaleTypeOrdinal in BackgroundAwareScaleType.values().indices) {
                BackgroundAwareScaleType.values()[scaleTypeOrdinal]
            } else {
                BackgroundAwareScaleType.CENTER
            }
    }
}