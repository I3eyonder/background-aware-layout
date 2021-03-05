package com.hieupt.backgroundawareconstraintlayoutdemo

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hieupt.view.BackgroundAwareMode
import com.hieupt.view.BackgroundAwareScaleType
import com.hieupt.view.extensions.backgroundAwareMode
import com.hieupt.view.extensions.backgroundAwarePathCreator
import com.hieupt.view.extensions.backgroundAwareScaleType
import com.hieupt.view.extensions.backgroundAwareTint
import com.hieupt.view.graphic.IClipPathCreator
import com.hieupt.view.graphic.StarClipPathCreator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initActions()
    }

    private fun initActions() {
        btnClear1.setOnClickListener(OnClearButtonClick(contentView1))
        btnClear2.setOnClickListener(OnClearButtonClick(contentView2))
        btnClear3.setOnClickListener(OnClearButtonClick(contentView3))
        btnClear4.setOnClickListener(OnClearButtonClick(contentView4))

        btnTint2.setOnClickListener(
            OnTintButtonClick(
                contentView2,
                ContextCompat.getColor(this, R.color.design_default_color_secondary)
            )
        )
        btnTint3.setOnClickListener(
            OnTintButtonClick(
                contentView3,
                ContextCompat.getColor(this, R.color.design_default_color_secondary)
            )
        )
        btnTint4.setOnClickListener(
            OnTintButtonClick(
                contentView4,
                ContextCompat.getColor(this, R.color.design_default_color_secondary)
            )
        )

        contentView4.setOnClickListener {
            val scaleTypeOrdinal = it.backgroundAwareScaleType.ordinal
            val scaleTypeSize = BackgroundAwareScaleType.values().size
            it.backgroundAwareScaleType =
                BackgroundAwareScaleType.values()[(scaleTypeOrdinal + 1) % scaleTypeSize]
        }
    }

    private fun initViews() {
        updateClearText(btnClear1, contentView1)
        updateClearText(btnClear2, contentView2)
        updateClearText(btnClear3, contentView3)
        updateClearText(btnClear4, contentView4)

        updateTintText(btnTint2, contentView2)
        updateTintText(btnTint3, contentView3)
        updateTintText(btnTint4, contentView4)

        contentView3.backgroundAwarePathCreator = StarClipPathCreator(5)
    }

    private fun updateClearText(textView: TextView, contentView: View) {
        if (contentView.backgroundAwareMode == BackgroundAwareMode.CLEAR) {
            textView.setText(R.string.clear_on)
        } else {
            textView.setText(R.string.clear_off)
        }
    }

    private fun updateTintText(textView: TextView, contentView: View) {
        if (contentView.backgroundAwareTint != null) {
            textView.setText(R.string.tint_on)
        } else {
            textView.setText(R.string.tint_off)
        }
    }

    private fun toggleClearMode(view: View) {
        if (view.backgroundAwareMode == BackgroundAwareMode.CLEAR) {
            view.backgroundAwareMode = BackgroundAwareMode.TINT
        } else {
            view.backgroundAwareMode = BackgroundAwareMode.CLEAR
        }
    }

    private fun toggleTintColor(view: View, tintColor: Int) {
        if (view.backgroundAwareTint != null) {
            view.backgroundAwareTint = null
        } else {
            view.backgroundAwareTint = ColorStateList.valueOf(tintColor)
        }
    }

    private inner class OnClearButtonClick(private val contentView: View) : View.OnClickListener {

        override fun onClick(v: View) {
            toggleClearMode(contentView)
            updateClearText(v as TextView, contentView)
        }
    }

    private inner class OnTintButtonClick(
        private val contentView: View,
        private val tintColor: Int
    ) :
        View.OnClickListener {

        override fun onClick(v: View) {
            toggleTintColor(contentView, tintColor)
            updateTintText(v as TextView, contentView)
        }
    }
}