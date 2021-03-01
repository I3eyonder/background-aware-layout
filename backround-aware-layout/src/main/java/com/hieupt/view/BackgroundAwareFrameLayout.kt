package com.hieupt.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import com.hieupt.R
import com.hieupt.view.extensions.obtainStyledAttributes
import com.hieupt.view.extensions.onDrawAwareBackground
import com.hieupt.view.extensions.onMeasureAwareBackground
import com.hieupt.view.graphic.IClipPathCreator

/**
 * Created by HieuPT on 10/19/2020.
 */
class BackgroundAwareFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IBackgroundAwareLayout {

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

    override fun generateLayoutParams(attrs: AttributeSet?): FrameLayout.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams
    }

    override fun generateDefaultLayoutParams(): FrameLayout.LayoutParams {
        return LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
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

    class LayoutParams : FrameLayout.LayoutParams,
        IBackgroundAwareLayout.IBackgroundAwareLayoutParams {

        override var backgroundAwareMode: BackgroundAwareMode = BackgroundAwareMode.TINT
        override var backgroundAware: Drawable? = null
        override var backgroundAwareTint: ColorStateList? = null
        override var backgroundAwarePathCreator: IClipPathCreator? = null
        override var backgroundAwareScaleType: BackgroundAwareScaleType =
            BackgroundAwareScaleType.CENTER

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
            obtainStyledAttributes(c, attrs, R.styleable.BackgroundAwareFrameLayout_Layout)
        }

        constructor(source: LayoutParams) : super(source) {
            this.backgroundAware = source.backgroundAware
            this.backgroundAwareTint = source.backgroundAwareTint
            this.backgroundAwareMode = source.backgroundAwareMode
            this.backgroundAwarePathCreator = source.backgroundAwarePathCreator
            this.backgroundAwareScaleType = source.backgroundAwareScaleType
        }

        constructor(width: Int, height: Int) : super(width, height)
        constructor(width: Int, height: Int, gravity: Int) : super(width, height, gravity)
        constructor(source: ViewGroup.LayoutParams) : super(source)
        constructor(source: MarginLayoutParams) : super(source)
    }
}