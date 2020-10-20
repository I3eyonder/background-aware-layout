package com.hieupt.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.use
import androidx.core.view.children
import com.hieupt.R
import com.hieupt.view.graphic.IClipPathCreator

/**
 * Created by HieuPT on 10/19/2020.
 */
class BackgroundAwareLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr), IBackgroundAwareLayout {

    override val pathCreatorMap = hashMapOf<Int, IClipPathCreator>()

    override val eraser: Paint = Paint()

    init {
        setupEraser()
    }

    override fun getView(): View = this

    override fun generateLayoutParams(attrs: AttributeSet?): LinearLayoutCompat.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams
    }

    override fun generateDefaultLayoutParams(): LinearLayoutCompat.LayoutParams? {
        return when (orientation) {
            HORIZONTAL -> {
                LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                )
            }
            VERTICAL -> {
                return LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                )
            }
            else -> null
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        eraseChildren(canvas, children.toList())
    }

    class LayoutParams : LinearLayoutCompat.LayoutParams,
        IBackgroundAwareLayout.IBackgroundAwareLayoutParams {

        override var isBackgroundAware = false

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
            c.obtainStyledAttributes(attrs, R.styleable.BackgroundAwareLinearLayout_Layout)
                .use {
                    isBackgroundAware = it.getBoolean(
                        R.styleable.BackgroundAwareLinearLayout_Layout_layout_backgroundAware,
                        false
                    )
                }
        }

        constructor(source: LayoutParams?) : super(source) {
            this.isBackgroundAware = source?.isBackgroundAware ?: false
        }

        constructor(width: Int, height: Int) : super(width, height)
        constructor(width: Int, height: Int, weight: Float) : super(width, height, weight)
        constructor(p: ViewGroup.LayoutParams?) : super(p)
        constructor(source: MarginLayoutParams?) : super(source)
    }
}