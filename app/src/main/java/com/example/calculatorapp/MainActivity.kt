package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculatorapp.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var lastNumber = false
    var stateError = false
    var lastDot = false
    private lateinit var expression: Expression


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigitClick(view: View) {
        if (stateError) {
            binding.workTv.text = (view as Button).text
            stateError = false
        } else {
            binding.workTv.append((view as Button).text)
        }
        lastNumber = true
        onEqual()
    }

    fun onOperatorClick(view: View) {

        if (!stateError && lastNumber) {
            binding.workTv.append((view as Button).text)
            lastDot = false
            lastNumber = false
            onEqual()
        }
    }

    fun onBackClick(view: View) {

        val lenght = binding.workTv.text.length
        if (lenght>=1)
            binding.workTv.text = binding.workTv.text.subSequence(0,lenght-1)

    }

    fun onClearClick(view: View) {

        binding.workTv.text = ""
        binding.resultTv.text = ""
        lastNumber = false
        lastDot = false
        stateError = false
    }

    fun onEqual() {

        if (lastNumber && !stateError) {

            val txt = binding.workTv.text.toString()
            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = result.toString()

            } catch (ex: ArithmeticException) {
                Log.e("evaluate error", ex.toString())
                binding.resultTv.text = "error"
                stateError = true
                lastNumber = false
            }
        }
    }

    fun onEqualClick(view: View) {

        onEqual()
        binding.workTv.text = binding.resultTv.text.toString()
    }

}