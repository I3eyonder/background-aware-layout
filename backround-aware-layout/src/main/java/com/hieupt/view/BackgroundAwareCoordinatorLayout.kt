package com.hieupt.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import com.hieupt.R

/**
 * Created by HieuPT on 10/19/2020.
 */
class BackgroundAwareCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private val eraser by lazy { Paint() }

    init {
        setupEraser()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): CoordinatorLayout.LayoutParams {
        return LayoutParams(context, attrs, super.generateLayoutParams(attrs))
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams && super.checkLayoutParams(p)
    }

    override fun generateDefaultLayoutParams(): CoordinatorLayout.LayoutParams {
        return LayoutParams(
            CoordinatorLayout.LayoutParams.MATCH_PARENT,
            CoordinatorLayout.LayoutParams.MATCH_PARENT
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

    class LayoutParams : CoordinatorLayout.LayoutParams {

        var isBackgroundAware = false

        constructor(c: Context, attrs: AttributeSet?, p: CoordinatorLayout.LayoutParams) : this(p) {
            c.obtainStyledAttributes(attrs, R.styleable.BackgroundAwareCoordinatorLayout_Layout)
                .use {
                    isBackgroundAware = it.getBoolean(
                        R.styleable.BackgroundAwareCoordinatorLayout_Layout_layout_backgroundAware,
                        false
                    )
                }
        }

        constructor(p: LayoutParams) : this(p as CoordinatorLayout.LayoutParams) {
            this.isBackgroundAware = p.isBackgroundAware
        }

        constructor(p: CoordinatorLayout.LayoutParams) : super(p) {
            this.gravity = p.gravity
            this.anchorId = p.anchorId
            this.anchorGravity = p.anchorGravity
            this.keyline = p.keyline
            this.insetEdge = p.insetEdge
            this.dodgeInsetEdges = p.dodgeInsetEdges
            this.behavior = p.behavior
        }

        constructor(width: Int, height: Int) : super(width, height)
        constructor(p: MarginLayoutParams?) : super(p)
        constructor(p: ViewGroup.LayoutParams?) : super(p)
    }
}