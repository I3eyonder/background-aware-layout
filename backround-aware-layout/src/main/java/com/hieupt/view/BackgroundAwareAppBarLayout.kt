package com.hieupt.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import com.google.android.material.appbar.AppBarLayout
import com.hieupt.R

/**
 * Created by HieuPT on 10/19/2020.
 */
class BackgroundAwareAppBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppBarLayout(context, attrs, defStyleAttr) {

    private val eraser by lazy { Paint() }

    init {
        setupEraser()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): AppBarLayout.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams
    }

    override fun generateDefaultLayoutParams(): AppBarLayout.LayoutParams? {
        return LayoutParams(
            AppBarLayout.LayoutParams.MATCH_PARENT,
            AppBarLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        children.filter {
            (it.layoutParams as? LayoutParams)?.isBackgroundAware ?: false
        }.forEach {
            canvas?.drawRect(
                it.left.toFloat(), it.top.toFloat(),
                it.right.toFloat(), it.bottom.toFloat(),
                eraser
            )
        }
    }

    private fun setupEraser() {
        eraser.apply {
            color = ContextCompat.getColor(context, android.R.color.transparent)
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            isAntiAlias = true
        }
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
    }

    class LayoutParams : AppBarLayout.LayoutParams {

        var isBackgroundAware = false

        constructor(c: Context?, attrs: AttributeSet?) : super(c, attrs) {
            c?.obtainStyledAttributes(attrs, R.styleable.BackgroundAwareAppBarLayout_Layout)?.use {
                isBackgroundAware = it.getBoolean(
                    R.styleable.BackgroundAwareAppBarLayout_Layout_layout_backgroundAware,
                    false
                )
            }
        }

        constructor(source: LayoutParams) : super(source) {
            this.isBackgroundAware = source.isBackgroundAware
        }

        constructor(width: Int, height: Int) : super(width, height)
        constructor(width: Int, height: Int, weight: Float) : super(width, height, weight)
        constructor(p: ViewGroup.LayoutParams?) : super(p)
        constructor(source: MarginLayoutParams?) : super(source)
        constructor(source: LinearLayout.LayoutParams?) : super(source)
    }
}