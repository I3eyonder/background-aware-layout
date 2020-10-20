package com.hieupt.view

import android.graphics.*
import android.view.View
import androidx.annotation.IdRes
import com.hieupt.view.graphic.IClipPathCreator

/**
 * Created by HieuPT on 10/20/2020.
 */
interface IBackgroundAwareLayout {

    val eraser: Paint

    val pathCreatorMap: HashMap<Int, IClipPathCreator>

    fun getView(): View

    fun setClipPathCreator(@IdRes viewId: Int, clipPathCreator: IClipPathCreator) {
        pathCreatorMap[viewId] = clipPathCreator
    }

    interface IBackgroundAwareLayoutParams {

        var isBackgroundAware: Boolean
    }
}

internal fun IBackgroundAwareLayout.eraseChildren(canvas: Canvas?, childrenView: List<View>) {
    childrenView.filter {
        (it.layoutParams as? IBackgroundAwareLayout.IBackgroundAwareLayoutParams)?.isBackgroundAware
            ?: false
    }.map {
        it to pathCreatorMap[it.id]?.createClipPath(
            it,
            it.left.toFloat(),
            it.top.toFloat(),
            it.right.toFloat(),
            it.bottom.toFloat(),
            it.width.toFloat(),
            it.height.toFloat()
        )
    }.forEach {
        it.second?.let { path ->
            canvas?.drawPath(path, eraser)
        } ?: kotlin.run {
            val view = it.first
            canvas?.drawRect(
                view.left.toFloat(), view.top.toFloat(),
                view.right.toFloat(), view.bottom.toFloat(),
                eraser
            )
        }
    }
}


internal fun IBackgroundAwareLayout.setupEraser() {
    eraser.apply {
        color = Color.TRANSPARENT
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        isAntiAlias = true
    }
    getView().setLayerType(View.LAYER_TYPE_HARDWARE, null)
}