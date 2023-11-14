package io.strongapp.gymworkout.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import io.strongapp.gymworkout.R


class CustomNumericKeypad(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

	init {
		init(context)
	}

	private fun init(context: Context) {
		LayoutInflater.from(context).inflate(R.layout.custom_numeric_keypad, this, true)
		val one = findViewById<TextView>(R.id.number1)
		val two = findViewById<TextView>(R.id.number2)
		val three = findViewById<TextView>(R.id.number3)
		val four = findViewById<TextView>(R.id.number4)
		val five = findViewById<TextView>(R.id.number5)
		val six = findViewById<TextView>(R.id.number6)
		val seven = findViewById<TextView>(R.id.number7)
		val eight = findViewById<TextView>(R.id.number8)
		val nine = findViewById<TextView>(R.id.number9)
		val zero = findViewById<TextView>(R.id.number0)
		val zeroZero = findViewById<TextView>(R.id.number00)
		val dot = findViewById<TextView>(R.id.btnDot)
		val btnNext = findViewById<AppCompatTextView>(R.id.btnNext)
		val btnHide = findViewById<TextView>(R.id.btnHide)
		btnHide.setOnClickListener {
			hideKeyboard()
		}
		val btnDelete = findViewById<TextView>(R.id.btnDelete)
		btnDelete.setOnClickListener {
			onNumberKeyClickListener?.onNumberKeyClick(-1)
		}
		val buttons = listOf(one, two, three, four, five, six, seven, eight, nine, zero,zeroZero,btnNext,dot,btnHide,btnDelete)

		buttons.forEachIndexed { index, button ->
			button.setOnClickListener {
				onNumberKeyClickListener?.onNumberKeyClick(index)
			}
		}

	}

	var onNumberKeyClickListener: OnNumberKeyClickListener? = null

	private fun hideKeyboard() {
		val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(windowToken, 0)
	}
	interface OnNumberKeyClickListener {
		fun onNumberKeyClick(number: Int)
	}
}