package br.dev.com.camera.ui.imagem

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


/* Coloca a imagem em tamanho total */
class ProportionalImageView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = drawable
        if (d != null) {
            val w = MeasureSpec.getSize(widthMeasureSpec)
            val h = w * d.intrinsicHeight / d.intrinsicWidth
            setMeasuredDimension(w, h)
        } else super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

}