package com.hieupt.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import com.hieupt.R

/**
 * Created by HieuPT on 10/19/2020.
 */
class BackgroundAwareMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    private val eraser by lazy { Paint() }

    init {
        setupEraser()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): ConstraintLayout.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams
    }

    override fun generateDefaultLayoutParams(): ConstraintLayout.LayoutParams {
        return LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
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

    class LayoutParams : ConstraintLayout.LayoutParams {

        var isBackgroundAware = false

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
            c.obtainStyledAttributes(attrs, R.styleable.BackgroundAwareMotionLayout_Layout)
                .use {
                    isBackgroundAware = it.getBoolean(
                        R.styleable.BackgroundAwareMotionLayout_Layout_layout_backgroundAware,
                        false
                    )
                }
        }

        constructor(source: LayoutParams) : super(source) {
            this.isBackgroundAware = source.isBackgroundAware
        }

        constructor(width: Int, height: Int) : super(width, height)
        constructor(source: ViewGroup.LayoutParams?) : super(source)

    }
}