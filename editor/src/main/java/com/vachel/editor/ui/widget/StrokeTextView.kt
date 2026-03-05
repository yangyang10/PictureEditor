package com.vachel.editor.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by HHY on 2026/3/5 15:05
 * Desc: 文字描边
 **/
class StrokeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var strokeColor = Color.RED
    private var strokeWidth = 12f
    private var strokeEnabled = true
    private val strokePaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val fillPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    init {
        // 初始化画笔
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = strokeWidth
        strokePaint.color = strokeColor

        fillPaint.style = Paint.Style.FILL
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
        strokePaint.color = color
        invalidate()
    }

    fun setStrokeWidth(width: Float) {
        strokeWidth = width
        strokePaint.strokeWidth = width
        invalidate()
    }

    fun setStrokeEnabled(enabled: Boolean) {
        strokeEnabled = enabled
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (!strokeEnabled || text.isNullOrEmpty()) {
            super.onDraw(canvas)
            return
        }

        val currentText = text.toString()
        val layout = layout

        // 同步描边画笔的属性与原画笔
        strokePaint.textSize = paint.textSize
        strokePaint.typeface = paint.typeface
        strokePaint.textScaleX = paint.textScaleX
        strokePaint.textSkewX = paint.textSkewX
        strokePaint.letterSpacing = paint.letterSpacing
        strokePaint.textAlign = paint.textAlign

        // 同步填充画笔的属性
        fillPaint.textSize = paint.textSize
        fillPaint.typeface = paint.typeface
        fillPaint.color = currentTextColor
        fillPaint.textScaleX = paint.textScaleX
        fillPaint.textSkewX = paint.textSkewX
        fillPaint.letterSpacing = paint.letterSpacing
        fillPaint.textAlign = paint.textAlign

        // 保存canvas状态
        canvas.save()

        // 计算绘制区域
        val scrollX = scrollX.toFloat()
        val scrollY = scrollY.toFloat()
        val paddingLeft = paddingLeft.toFloat()
        val paddingTop = paddingTop.toFloat()
        val paddingRight = paddingRight.toFloat()
        val paddingBottom = paddingBottom.toFloat()

        // 应用滚动和padding
        canvas.translate(paddingLeft - scrollX, paddingTop - scrollY)

        // 裁剪绘制区域
        canvas.clipRect(0f, 0f, width - paddingLeft - paddingRight + scrollX,
            height - paddingTop - paddingBottom + scrollY)

        // 先绘制所有文本的描边
        for (i in 0 until layout.lineCount) {
            val lineStart = layout.getLineStart(i)
            val lineEnd = layout.getLineEnd(i)
            val lineText = currentText.substring(lineStart, lineEnd)

            val baseline = layout.getLineBaseline(i).toFloat()
            val x = when (paint.textAlign) {
                Paint.Align.CENTER -> (width - paddingLeft - paddingRight) / 2f
                Paint.Align.RIGHT -> width - paddingLeft - paddingRight - layout.getLineRight(i) + layout.getLineLeft(i)
                else -> layout.getLineLeft(i) // Paint.Align.LEFT
            }

            // 绘制描边
            canvas.drawText(lineText, x, baseline, strokePaint)
        }

        // 再绘制所有文本的填充（在原位置）
        for (i in 0 until layout.lineCount) {
            val lineStart = layout.getLineStart(i)
            val lineEnd = layout.getLineEnd(i)
            val lineText = currentText.substring(lineStart, lineEnd)

            val baseline = layout.getLineBaseline(i).toFloat()
            val x = when (paint.textAlign) {
                Paint.Align.CENTER -> (width - paddingLeft - paddingRight) / 2f
                Paint.Align.RIGHT -> width - paddingLeft - paddingRight - layout.getLineRight(i) + layout.getLineLeft(i)
                else -> layout.getLineLeft(i) // Paint.Align.LEFT
            }

            // 绘制填充
            canvas.drawText(lineText, x, baseline, fillPaint)
        }

        canvas.restore()
    }
}