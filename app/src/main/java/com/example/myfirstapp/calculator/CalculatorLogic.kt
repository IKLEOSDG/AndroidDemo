package com.example.myfirstapp.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myfirstapp.R
import java.math.BigDecimal

class CalculatorLogic : Fragment() {

  private lateinit var display: TextView
  private var currentOperation: String? = null
  private var firstOperand: BigDecimal? = null
  private var secondOperand: BigDecimal? = null
  private var isOperationSelected = false
  // 当前计算器的状态
  private var currentState = CalculatorState.INIT
  //same
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.activity_calculator, container, false)

    display = view.findViewById(R.id.textViewResult)

    val buttons = listOf(
      R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4,
      R.id.bt_5, R.id.bt_6, R.id.bt_7, R.id.bt_8, R.id.bt_9,
      R.id.bt_point, R.id.bt_plus, R.id.bt_sub, R.id.bt_multi, R.id.bt_divide,
      R.id.bt_equal, R.id.bt_ac
    )

    buttons.forEach { id ->
      view.findViewById<Button>(id).setOnClickListener { handleButtonClick(it) }
    }

    return view
  }
  private fun handleButtonClick(view: View) {
    when (view.id) {
      R.id.bt_ac -> clearDisplay()
      R.id.bt_equal -> {
        calculateResult()
        currentState = CalculatorState.RESULT_DISPLAYED
      }
      R.id.bt_plus, R.id.bt_sub, R.id.bt_multi, R.id.bt_divide -> {
        selectOperation(view as Button)
        currentState = CalculatorState.OPERATION_SELECTED
      }
      else -> updateDisplay(view as Button)
    }
  }
  // clearDisplay 和 calculateResult
  private fun clearDisplay() {
    display.text = "0"
    firstOperand = null
    secondOperand = null
    currentOperation = null
    isOperationSelected = false
  }
  private fun calculateResult() {
    secondOperand = display.text.toString().toBigDecimalOrNull()
    if (firstOperand != null && secondOperand != null) {
      val result = when (currentOperation) {
        "+" -> firstOperand!! + secondOperand!!
        "-" -> firstOperand!! - secondOperand!!
        "x" -> firstOperand!! * secondOperand!!
        "÷" -> if (secondOperand != BigDecimal.ZERO) firstOperand!! / secondOperand!! else null
        else -> null
      }
      result?.let { display.text = it.stripTrailingZeros().toPlainString() }
    }
    isOperationSelected = true
  }
  private fun selectOperation(button: Button) {
    firstOperand = display.text.toString().toBigDecimalOrNull()
    currentOperation = button.text.toString()
    currentState = CalculatorState.WAITING_FOR_SECOND_OPERAND
  }

  private fun updateDisplay(button: Button) {
    when (currentState) {
      CalculatorState.INIT, CalculatorState.RESULT_DISPLAYED -> {
        display.text = button.text
        currentState = CalculatorState.WAITING_FOR_OPERAND
      }
      CalculatorState.WAITING_FOR_OPERAND, CalculatorState.WAITING_FOR_SECOND_OPERAND -> {
        display.append(button.text)
      }
      CalculatorState.OPERATION_SELECTED -> {
        display.text = button.text
        currentState = CalculatorState.WAITING_FOR_SECOND_OPERAND
      }
    }
  }

}
enum class CalculatorState {
  INIT, // 初始状态
  WAITING_FOR_OPERAND, // 等待输入操作数
  OPERATION_SELECTED, // 已选择操作符
  WAITING_FOR_SECOND_OPERAND, // 等待输入第二个操作数
  RESULT_DISPLAYED // 结果已显示
}