package com.boni.valueselect

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(valuebar) {
            setMaxValue(100)
        }

        update.setOnClickListener {
            valuebar.setValue(valueSelect.getValue())
        }
    }
}
