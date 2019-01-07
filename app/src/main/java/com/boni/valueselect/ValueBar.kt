package com.boni.valueselect

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

@SuppressLint("PrivateResource")
class ValueBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private var maxValue = 100
    private var currentValue = 0

    private var barHeight: Int = 0
    private var circleRadius: Int = 0
    private var spaceAfterBar: Int = 0
    private var circleTextSize: Int = 0
    private var maxValueTextSize: Int = 0
    private var labelTextSize: Int = 0
    private var labelTextColor: Int = 0
    private var currentValueTextColor: Int = 0
    private var circleTextColor: Int = 0
    private var baseColor: Int = 0
    private var fillColor: Int = 0
    private var labelText: String? = null

    private var labelPaint: Paint
    private var maxValuePaint: Paint
    private var barBasePaint: Paint
    private var baseFillPaint: Paint
    private var circlePaint: Paint
    private var currentValuePaint: Paint

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ValueBar, 0, 0)

        with(typedArray) {
            barHeight = getDimensionPixelSize(R.styleable.ActionBar_height, 0)
            circleRadius = getDimensionPixelSize(R.styleable.ValueBar_circleRadius, 0)
            spaceAfterBar = getDimensionPixelSize(R.styleable.ValueBar_spaceAfterBar, 0)
            circleTextSize = getDimensionPixelSize(R.styleable.ValueBar_circleTextSize, 0)
            maxValueTextSize = getDimensionPixelSize(R.styleable.ValueBar_maxValueTextSize, 0)
            labelTextSize = getDimensionPixelSize(R.styleable.ValueBar_labelTextSize, 0)
            labelTextColor = getColor(R.styleable.ValueBar_labelTextColor, Color.BLACK)
            currentValueTextColor = getColor(R.styleable.ValueBar_maxValueTextColor, Color.BLACK)
            circleTextColor = getColor(R.styleable.ValueBar_circleTextColor, Color.BLACK)
            baseColor = getColor(R.styleable.ValueBar_baseColor, Color.BLACK)
            fillColor = getColor(R.styleable.ValueBar_fillColor, Color.BLACK)
            labelText = getString(R.styleable.ValueBar_labelText)
        }

        typedArray.recycle()

        labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = labelTextSize.toFloat()
            it.color = labelTextColor
            it.textAlign = Paint.Align.LEFT
            it.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        maxValuePaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = maxValueTextSize.toFloat()
            it.color = currentValueTextColor
            it.textAlign = Paint.Align.RIGHT
            it.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        barBasePaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.color = baseColor
        }

        baseFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.color = fillColor
        }

        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.color = fillColor
        }

        currentValuePaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = circleTextSize.toFloat()
            it.color = circleTextColor
            it.textAlign = Paint.Align.CENTER
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    /*
        Calling invalidate() tells Android that the state of the view has changed and needs to be redrawn.
        requestLayout means that the size of the view may have changed and needs to be remeasured, which could impact the entire layout.
     */
    fun setMaxValue(value: Int) {
        maxValue = value
        invalidate()
        requestLayout()
    }

    fun setValue(newValue: Int) {
        currentValue = when {
            newValue < 0 -> 0
            newValue > maxValue -> maxValue
            else -> newValue
        }

        invalidate()
    }

    private fun measureHeight(measureSpec: Int): Int {
        var size = (paddingTop + paddingBottom).toFloat()
        size += labelPaint.fontSpacing
        val maxValueTextSpacing = maxValuePaint.fontSpacing
        size += Math.max(maxValueTextSpacing.toInt(), Math.max(barHeight, circleRadius * 2))
        return resolveSizeAndState(size.toInt(), measureSpec, 0)
    }

    private fun measureWidth(measureSpec: Int): Int {
        var size = paddingLeft + paddingRight
        var bounds = Rect()

        labelText?.let {
            labelPaint.getTextBounds(it, 0, it.length, bounds)
        }

        size += bounds.width()

        bounds = Rect()

        maxValuePaint.getTextBounds(maxValue.toString(), 0, maxValue.toString().length, bounds)

        size += bounds.width()

        return resolveSizeAndState(size, measureSpec, 0)
    }
}