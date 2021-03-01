package com.hieupt.view.extensions

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

/**
 * Created by HieuPT on 3/3/2021.
 */
internal fun Canvas.drawClipPath(path: Path, paint: Paint) {
    drawPath(path, paint)
}