package com.boni.valueselect

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
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
}