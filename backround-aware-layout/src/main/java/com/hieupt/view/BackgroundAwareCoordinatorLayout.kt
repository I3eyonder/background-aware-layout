package com.hieupt.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.use
import androidx.core.view.children
import com.hieupt.R
import com.hieupt.view.graphic.IClipPathCreator

/**
 * Created by HieuPT on 10/19/2020.
 */
class BackgroundAwareCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr), IBackgroundAwareLayout {

    override val pathCreatorMap = hashMapOf<Int, IClipPathCreator>()

    override val eraser: Paint = Paint()

    init {
        setupEraser()
    }

    override fun getView(): View = this

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
        eraseChildren(canvas, children.toList())
    }

    class LayoutParams : CoordinatorLayout.LayoutParams,
        IBackgroundAwareLayout.IBackgroundAwareLayoutParams {

        override var isBackgroundAware = false

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