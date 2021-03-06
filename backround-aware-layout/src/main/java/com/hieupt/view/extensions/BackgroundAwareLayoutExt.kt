package com.hieupt.view.extensions

import android.graphics.*
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.hieupt.view.BackgroundAwareMode
import com.hieupt.view.BackgroundAwareScaleType
import com.hieupt.view.IBackgroundAwareLayout
import kotlin.math.max
import kotlin.math.min

/**
 * Created by HieuPT on 3/3/2021.
 */
internal fun IBackgroundAwareLayout.onDrawAwareBackground(
    canvas: Canvas?,
    childrenView: List<View>
) {
    childrenView.filter {
        it.layoutParams is IBackgroundAwareLayout.IBackgroundAwareLayoutParams
    }.map {
        val lp = it.layoutParams as IBackgroundAwareLayout.IBackgroundAwareLayoutParams
        val clipPath = lp.backgroundAwarePathCreator?.createClipPath(
            it,
            it.left.toFloat(),
            it.top.toFloat(),
            it.right.toFloat(),
            it.bottom.toFloat(),
            it.width.toFloat(),
            it.height.toFloat()
        )
        Triple(it, lp, clipPath)
    }.forEach {
        val (view, lp, clipPath) = it
        val pickPaint = fun(eraserPaint: Paint): Paint {
            return if (lp.backgroundAwareMode == BackgroundAwareMode.CLEAR) {
                eraserPaint
            } else {
                tintPaint.apply {
                    lp.backgroundAwareTint?.defaultColor?.let { tintColor ->
                        this.color = tintColor
                        this.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
                    } ?: kotlin.run {
                        this.color = Color.BLACK
                        this.colorFilter = null
                    }
                }
            }
        }
        clipPath?.let { path ->
            canvas?.drawClipPath(path, pickPaint(clipPathEraser))
        } ?: kotlin.run {
            lp.backgroundAware?.let { background ->
                val rectF = RectF(
                    view.left.toFloat(),
                    view.top.toFloat(),
                    view.right.toFloat(),
                    view.bottom.toFloat()
                )
                val width = if (background.intrinsicWidth > 0) {
                    when (lp.backgroundAwareScaleType) {
                        BackgroundAwareScaleType.CENTER -> {
                            background.intrinsicWidth
                        }
                        BackgroundAwareScaleType.FIT_START,
                        BackgroundAwareScaleType.FIT_END,
                        BackgroundAwareScaleType.FIT_CENTER -> {
                            val bgHeight = if (background.intrinsicHeight > 0) {
                                background.intrinsicHeight
                            } else {
                                rectF.height().toInt()
                            }
                            val factor = min(
                                rectF.width() / background.intrinsicWidth,
                                rectF.height() / bgHeight
                            )
                            (background.intrinsicWidth * factor).toInt()
                        }
                        BackgroundAwareScaleType.FIT_XY -> {
                            rectF.width().toInt()
                        }
                    }
                } else {
                    rectF.width().toInt()
                }
                val height = if (background.intrinsicHeight > 0) {
                    when (lp.backgroundAwareScaleType) {
                        BackgroundAwareScaleType.CENTER -> {
                            background.intrinsicHeight
                        }
                        BackgroundAwareScaleType.FIT_START,
                        BackgroundAwareScaleType.FIT_END,
                        BackgroundAwareScaleType.FIT_CENTER -> {
                            val intrinsicWidth = if (background.intrinsicWidth > 0) {
                                background.intrinsicWidth
                            } else {
                                rectF.width().toInt()
                            }
                            val factor = min(
                                rectF.width() / intrinsicWidth,
                                rectF.height() / background.intrinsicHeight
                            )
                            (background.intrinsicHeight * factor).toInt()
                        }
                        BackgroundAwareScaleType.FIT_XY -> {
                            rectF.height().toInt()
                        }
                    }
                } else {
                    rectF.height().toInt()
                }
                if (width > 0 && height > 0) {
                    val bitmap = background.toBitmap(width, height)
                    when (lp.backgroundAwareScaleType) {
                        BackgroundAwareScaleType.FIT_START -> {
                            canvas?.drawBitmap(bitmap, 0f, 0f, pickPaint(backgroundEraser))
                        }
                        BackgroundAwareScaleType.FIT_END -> {
                            canvas?.drawBitmap(
                                bitmap,
                                rectF.width() - width,
                                0f,
                                pickPaint(backgroundEraser)
                            )
                        }
                        else -> {
                            canvas?.drawBitmap(
                                bitmap,
                                rectF.centerX() - width / 2,
                                rectF.centerY() - height / 2,
                                pickPaint(backgroundEraser)
                            )
                        }
                    }
                }
            } ?: kotlin.run {
                if (lp.backgroundAwareMode == BackgroundAwareMode.CLEAR) {
                    canvas?.drawRect(
                        view.left.toFloat(), view.top.toFloat(),
                        view.right.toFloat(), view.bottom.toFloat(),
                        clipPathEraser
                    )
                }
            }
        }
    }
}

internal fun IBackgroundAwareLayout.onMeasureAwareBackground(childrenView: List<View>) {
    childrenView.filter {
        it.layoutParams is IBackgroundAwareLayout.IBackgroundAwareLayoutParams
    }.forEach { child ->
        val lp = child.layoutParams as IBackgroundAwareLayout.IBackgroundAwareLayoutParams
        lp.backgroundAware?.let {
            child.minimumWidth = max(child.measuredWidth, it.minimumWidth)
            child.minimumHeight = max(child.minimumHeight, it.minimumHeight)
        }
    }
}