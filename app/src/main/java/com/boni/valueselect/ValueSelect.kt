package com.boni.valueselect

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.value_select.view.*

class ValueSelect: RelativeLayout {

    private var maxValue = Integer.MAX_VALUE
    private var minValue = Integer.MIN_VALUE

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    private var root: View = View.inflate(context, R.layout.value_select, this)

    private var minusButton = root.findViewById<ImageView>(R.id.minusButton)
    private var plusButton = root.findViewById<ImageView>(R.id.plusButton)
    private var valueTextView = root.findViewById<EditText>(R.id.valueTextView)

    init {
        minusButton.setOnClickListener { decrementValue() }
        plusButton.setOnClickListener { incrementValue() }
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
            valueTextView.setText(currentValue + 1)
        }
    }

    private fun decrementValue() {
        val currentValue = valueTextView.text.toString().toInt()

        if(currentValue > minValue) {
            valueTextView.setText(currentValue - 1)
        }
    }
}