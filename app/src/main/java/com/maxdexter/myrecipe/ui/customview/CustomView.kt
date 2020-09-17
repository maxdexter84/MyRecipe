package com.maxdexter.myrecipe.ui.customview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.core.graphics.BitmapCompat
import com.bumptech.glide.request.target.BitmapImageViewTarget

class FaceView(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatImageView(context, attrs){


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var fillColor = Color.YELLOW
    private var borderColor = Color.BLACK
    private var borderWidth = 4.0f
    private var size = 320

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFaceBackground(canvas)

    }

    private fun drawFaceBackground(canvas: Canvas) {
        // 1
        paint.color = fillColor
        paint.style = Paint.Style.FILL

        // 2
        val radius = size / 2f

        // 3
        canvas.drawCircle(size / 2f, size / 2f, radius, paint)

        // 4
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth

        // 5
        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 1
        size = Math.min(measuredWidth, measuredHeight)
        // 2
        setMeasuredDimension(size, size)
    }


}