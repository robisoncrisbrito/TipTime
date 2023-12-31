package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.tipResult.text = getString(R.string.tip_amount_20_00,  "" )

    }

    fun calculateTip(view: View) {
        val stringInTextField = binding.costOfService.text.toString()

        val cost = stringInTextField.toDoubleOrNull() ?: return

        val tipPercentage = when (binding.tipOption.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = cost * tipPercentage

        val roundUp = binding.roundUpSwitch.isChecked

        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount_20_00, formattedTip)

        binding.costOfService.setOnKeyListener(View.OnKeyListener { view, i, _ -> handleKeyEvent( view, i ) } )

    }

    private fun handleKeyEvent ( view : View, keyCode : Int) : Boolean {

        if ( keyCode == KeyEvent.KEYCODE_ENTER ) {
            val inputMethodManager = getSystemService( Context.INPUT_METHOD_SERVICE ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow( view.windowToken, 0 )
            return true
        }

        return false
    }
}