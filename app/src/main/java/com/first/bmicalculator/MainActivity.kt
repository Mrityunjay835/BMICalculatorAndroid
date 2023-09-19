package com.first.bmicalculator

import android.content.Context
import android.hardware.input.InputManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var weightText:EditText
    private lateinit var heightText:EditText
    private lateinit var calcButton: Button
    private lateinit var resultIndex:TextView
    private lateinit var resultDescription:TextView
    private lateinit var info:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weightText = findViewById(R.id.etWeight)
        heightText =findViewById(R.id.etHeight)

        calcButton = findViewById(R.id.btnCalculate)

        resultIndex= findViewById(R.id.tvIndex)
        resultDescription = findViewById(R.id.tvResult)
        info = findViewById(R.id.tvInfo)

        calcButton.setOnClickListener {

            weightText.clearFocus()
            heightText.clearFocus()

            val imm : InputMethodManager  = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
            val weight = weightText.text.toString()
            val height = heightText.text.toString()
            if(validateInput(weight,height)) {
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))

                val bmi2Decimal = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2Decimal)
            }

        }

    }

    private fun validateInput(weight:String, height:String):Boolean{

        return when{
            weight.isNullOrEmpty()->{
                Toast.makeText(this, "Weight is empty", Toast.LENGTH_LONG).show()
                return false
            }
            height.isNullOrEmpty()->{
                Toast.makeText(this,"Height is empty",Toast.LENGTH_LONG).show()
                return false
            }
            else -> return true
        }
    }


    private fun displayResult(bmi : Float){
        resultIndex.text = bmi.toString()
        info.text = "(normal range is 18.5 ~ 24.9)"

        var resultText = ""
        var color =0

        when{
            bmi<18.50 -> {
                resultText = "Underweight"
                color = R.color.underWeight
            }
            bmi in 18.50..24.99 -> {
                resultText = "Healthy"
                color = R.color.normalWeight
            }
            bmi in 25.00..29.00 -> {
                resultText = "Overweight"
                color = R.color.overWeight
            }
            bmi > 29.99 -> {
                resultText = "Obese"
                color = R.color.obese
            }
        }

        resultDescription.setTextColor(ContextCompat.getColor(this,color))
        resultDescription.text = resultText


    }
}
