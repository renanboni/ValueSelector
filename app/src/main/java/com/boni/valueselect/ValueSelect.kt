package com.boni.valueselect

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.*

@SuppressLint("ClickableViewAccessibility")
class ValueSelect @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
        RelativeLayout(context, attrs, defStyleAttr) {

    private var maxValue = Integer.MAX_VALUE
    private var minValue = Integer.MIN_VALUE

    private var root = View.inflate(context, R.layout.value_select, this)

    private var minusButton: ImageView
    private var plusButton: ImageView
    private var valueTextView: EditText

    private var plusButtonIsPressed = false
    private var minusButtonIsPressed = false

    companion object {
        const val REPEAT_INTERVAL_MS: Long = 100
    }

    init {
        minusButton = root.findViewById(R.id.minusButton)
        plusButton = root.findViewById(R.id.plusButton)
        valueTextView = root.findViewById(R.id.valueTextView)

        minusButton.setOnClickListener { decrementValue() }
        plusButton.setOnClickListener { incrementValue() }

        minusButton.setOnLongClickListener {
            minusButtonIsPressed = true
            handler.post(AutoDecrementer())
            true
        }

        plusButton.setOnLongClickListener {
            plusButtonIsPressed = true
            handler.post(AutoIncrementer())
            true
        }

        minusButton.setOnTouchListener { _ , event ->
            if((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)) {
                minusButtonIsPressed = false
            }
            false
        }

        plusButton.setOnTouchListener { _ , event ->
            if((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)) {
                plusButtonIsPressed = false
            }
            false
        }
    }

    fun getMinValue() = minValue

    fun getMaxValue() = maxValue

    fun setMaxValue(max: Int) {
        maxValue = max
    }

    fun setMinValue(min: Int) {
        minValue = min
    }

    fun getValue() = valueTextView.text.toString().toInt()

    fun setValue(newValue: Int) {
        var value = newValue

        if(newValue < minValue) {
            value = minValue
        } else if(newValue > maxValue) {
            value = maxValue
        }

        valueTextView.setText(value)
    }

    private fun incrementValue() {
        val currentValue = valueTextView.text.toString().toInt()

        if(currentValue < maxValue) {
            valueTextView.setText((currentValue + 1).toString())
        }
    }

    private fun decrementValue() {
        val currentValue = valueTextView.text.toString().toInt()

        if(currentValue > minValue) {
            valueTextView.setText((currentValue - 1).toString())
        }
    }

    private inner class AutoIncrementer: Runnable {
        override fun run() {
            if(plusButtonIsPressed) {
                incrementValue()
                handler.postDelayed(AutoIncrementer(), REPEAT_INTERVAL_MS)
            }
        }
    }

    private inner class AutoDecrementer: Runnable {
        override fun run() {
            if(minusButtonIsPressed) {
                decrementValue()
                handler.postDelayed(AutoDecrementer(), REPEAT_INTERVAL_MS)
            }
        }
    }
}