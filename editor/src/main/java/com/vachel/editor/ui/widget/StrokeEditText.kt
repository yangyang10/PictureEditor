package com.vachel.editor.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * Created by HHY on 2026/3/4 18:53
 * Desc: 输入框 文字描边
 **/
class StrokeEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

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

    fun setStrokeEnabledAndColor(enabled: Boolean,color: Int) {
        strokeColor = color
        strokePaint.color = color

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

        // 同步填充画笔的属性
        fillPaint.textSize = paint.textSize
        fillPaint.typeface = paint.typeface
        fillPaint.color = currentTextColor
        fillPaint.textScaleX = paint.textScaleX
        fillPaint.textSkewX = paint.textSkewX
        fillPaint.letterSpacing = paint.letterSpacing

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
            val x = layout.getLineLeft(i)

            // 绘制描边
            canvas.drawText(lineText, x, baseline, strokePaint)
        }

        // 再绘制所有文本的填充（在原位置）
        for (i in 0 until layout.lineCount) {
            val lineStart = layout.getLineStart(i)
            val lineEnd = layout.getLineEnd(i)
            val lineText = currentText.substring(lineStart, lineEnd)

            val baseline = layout.getLineBaseline(i).toFloat()
            val x = layout.getLineLeft(i)

            // 绘制填充
            canvas.drawText(lineText, x, baseline, fillPaint)
        }

        // 绘制光标、选择等（如果有）
        if (isFocused) {
            // 可以在这里绘制光标
            // 但为了简化，我们可以调用super.onDraw来绘制光标
            // 但需要先恢复部分状态
        }

        canvas.restore()

    }
}