package com.hieupt.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.content.res.use
import androidx.core.view.children
import com.hieupt.R
import com.hieupt.view.graphic.IClipPathCreator

/**
 * Created by HieuPT on 10/19/2020.
 */
class BackgroundAwareGridLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr), IBackgroundAwareLayout {

    override val pathCreatorMap = hashMapOf<Int, IClipPathCreator>()

    override val eraser: Paint = Paint()

    init {
        setupEraser()
    }

    override fun getView(): View = this

    override fun generateLayoutParams(attrs: AttributeSet?): GridLayout.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return if (p is LayoutParams) {
            super.checkLayoutParams(p)
        } else false
    }

    override fun generateDefaultLayoutParams(): GridLayout.LayoutParams {
        return LayoutParams()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        eraseChildren(canvas, children.toList())
    }

    class LayoutParams : GridLayout.LayoutParams,
        IBackgroundAwareLayout.IBackgroundAwareLayoutParams {

        override var isBackgroundAware = false

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
            c.obtainStyledAttributes(attrs, R.styleable.BackgroundAwareGridLayout_Layout)
                .use {
                    isBackgroundAware = it.getBoolean(
                        R.styleable.BackgroundAwareGridLayout_Layout_layout_backgroundAware,
                        false
                    )
                }
        }

        constructor(source: LayoutParams?) : super(source) {
            this.isBackgroundAware = source?.isBackgroundAware ?: false
        }

        constructor(rowSpec: Spec?, columnSpec: Spec?) : super(rowSpec, columnSpec)
        constructor() : super()
        constructor(params: ViewGroup.LayoutParams?) : super(params)
        constructor(params: MarginLayoutParams?) : super(params)
    }
}