package com.hieupt.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import com.hieupt.R
import com.hieupt.view.extensions.obtainStyledAttributes
import com.hieupt.view.extensions.onDrawAwareBackground
import com.hieupt.view.extensions.onMeasureAwareBackground
import com.hieupt.view.graphic.IClipPathCreator

/**
 * Created by HieuPT on 10/19/2020.
 */
class BackgroundAwareCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr), IBackgroundAwareLayout {

    override val clipPathEraser: Paint = Paint().apply {
        color = Color.TRANSPARENT
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        isAntiAlias = true
    }

    override val backgroundEraser: Paint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        isAntiAlias = true
    }

    override val tintPaint: Paint = Paint().apply {
        isAntiAlias = true
    }

    init {
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        onMeasureAwareBackground(children.toList())
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        onDrawAwareBackground(canvas, children.toList())
    }

    class LayoutParams : CoordinatorLayout.LayoutParams,
        IBackgroundAwareLayout.IBackgroundAwareLayoutParams {

        override var backgroundAwareMode: BackgroundAwareMode = BackgroundAwareMode.TINT
        override var backgroundAware: Drawable? = null
        override var backgroundAwareTint: ColorStateList? = null
        override var backgroundAwarePathCreator: IClipPathCreator? = null
        override var backgroundAwareScaleType: BackgroundAwareScaleType =
            BackgroundAwareScaleType.CENTER

        constructor(c: Context, attrs: AttributeSet?, p: CoordinatorLayout.LayoutParams) : this(p) {
            obtainStyledAttributes(c, attrs, R.styleable.BackgroundAwareCoordinatorLayout_Layout)
        }

        constructor(p: LayoutParams) : this(p as CoordinatorLayout.LayoutParams) {
            this.backgroundAware = p.backgroundAware
            this.backgroundAwareTint = p.backgroundAwareTint
            this.backgroundAwareMode = p.backgroundAwareMode
            this.backgroundAwarePathCreator = p.backgroundAwarePathCreator
            this.backgroundAwareScaleType = p.backgroundAwareScaleType
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