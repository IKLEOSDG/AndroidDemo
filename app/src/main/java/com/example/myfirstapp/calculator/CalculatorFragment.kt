package com.example.myfirstapp.calculator
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class CalculatorFragment : Fragment() {

  private lateinit var display: TextView
  private var currentOperation: Int? = null
  private var firstOperand: BigDecimal? = null
  private var secondOperand: BigDecimal? = null
  private var isOperationSelected = false
  private val MAX_DIGITS = 9
  //recycle部分声明
  private lateinit var recyclerView: RecyclerView
  private lateinit var historyAdapter: HistoryItemAdapter
  private val historyList = mutableListOf<String>()
  //基础操作
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.activity_calculator, container, false)
    //display代表着输入框
    display = view.findViewById(R.id.textViewResult)
    display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80f)
    //按钮表
    val buttons = listOf(
      R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4,
      R.id.bt_5, R.id.bt_6, R.id.bt_7, R.id.bt_8, R.id.bt_9,
      R.id.bt_point, R.id.bt_plus, R.id.bt_sub, R.id.bt_multi, R.id.bt_divide,
      R.id.bt_equal, R.id.bt_ac, R.id.bt_negate, R.id.bt_percent
    )

    buttons.forEach { id ->
      view.findViewById<Button>(id).setOnClickListener { handleButtonClick(it) }
    }
    //recycle事件
    recyclerView = view.findViewById(R.id.recycler_view)
    recyclerView.layoutManager = LinearLayoutManager(context)
    historyAdapter = HistoryItemAdapter(historyList)
    recyclerView.adapter = historyAdapter
    return view
  }
  //点击
  private fun handleButtonClick(view: View) {
    when (view.id) {
      R.id.bt_ac -> clearDisplay()
      R.id.bt_equal -> calculateResult()
      R.id.bt_plus, R.id.bt_sub, R.id.bt_multi, R.id.bt_divide -> selectOperation(view as Button)
      R.id.bt_negate -> negateNumber()
      R.id.bt_percent -> percentageNumber()
      else -> updateDisplay(view as Button)
    }
  }
  //清屏
  private fun clearDisplay() {
    display.text = "0"
    firstOperand = null
    secondOperand = null
    currentOperation = null
    isOperationSelected = false
    historyList.clear()
    historyAdapter.notifyDataSetChanged()
  }
  //+/-
  private fun negateNumber() {
    val value = display.text.toString().toBigDecimalOrNull()
    value?.let {
      val negatedValue = it.negate()
      display.text = negatedValue.stripTrailingZeros().toPlainString()
    }
  }
  //%
  private fun percentageNumber() {
    val value = display.text.toString().toBigDecimalOrNull()
    value?.let {
      val percentageValue = it.divide(BigDecimal("100"))
      display.text = percentageValue.stripTrailingZeros().toPlainString()
    }
  }
  //格式化数据
  private fun formatNumberWithCommas(number: String): String {
    return try {
      val parsedNumber = BigDecimal(number.replace(",", ""))
      DecimalFormat("#,###.########").format(parsedNumber)
    } catch (e: NumberFormatException) {
      number // 如果转换失败，返回原始字符串
    }
  }

  private fun addResultToHistory(first: BigDecimal, second: BigDecimal, result: BigDecimal) {
    val operationSymbol = getOperationSymbol(currentOperation!!)
    val historyRecord = "${first.stripTrailingZeros().toPlainString()} $operationSymbol ${second.stripTrailingZeros().toPlainString()} = ${result.stripTrailingZeros().toPlainString()}"
    historyList.add(historyRecord)
    historyAdapter.notifyItemInserted(historyList.size - 1)
    recyclerView.scrollToPosition(historyList.size - 1)
  }

  private fun getOperationSymbol(operationId: Int): String {
    return when (operationId) {
      R.id.bt_plus -> "+"
      R.id.bt_sub -> "-"
      R.id.bt_multi -> "x"
      R.id.bt_divide -> "÷"
      else -> ""
    }
  }
  // 字体大小定义
  private val fontSizes = mapOf(
    1 to 80f,
    2 to 80f,
    3 to 80f,
    4 to 80f,
    5 to 80f,
    6 to 75f,
    7 to 60f,
    8 to 55f,
    9 to 50f
  )
  private fun selectOperation(button: Button) {
    val displayText = display.text.toString().replace(",", "")
    firstOperand = displayText.toBigDecimalOrNull()
    currentOperation = button.id
    isOperationSelected = true
  }

  private fun updateDisplay(button: Button) {
    val buttonText = button.text.toString()

    // 特别处理小数点的逻辑
    if (buttonText == ".") {
      if (!display.text.contains(".")) {
        // 如果当前显示的文本不包含小数点
        display.text = if (isOperationSelected || display.text.toString() == "0") {
          "0."
        } else {
          display.text.toString() + "."
        }
        isOperationSelected = false
      }
      return
    }

    // 如果之前选择了操作符，且按下的不是小数点，则重置显示
    if (isOperationSelected) {
      display.text = ""
      isOperationSelected = false
    }

    // 检查数字长度（不包括小数点和逗号）
    val currentText = display.text.toString().replace(",", "")
    if (currentText.replace(".", "").length >= MAX_DIGITS) {
      return
    }

    val newText = currentText + buttonText
    display.text = formatNumberWithCommas(newText)
    adjustTextSize(display.text.toString())
  }

  private fun adjustTextSize(displayText: String) {
    val cleanText = displayText.replace(",", "").replace(".", "")
    val textSize = fontSizes[cleanText.length] ?: 80f//fontSizes
    // 映射中获取完了；相应的字体大小。如果映射中不存在对应长度的字体大小，那么就使用默认的大小 80f。
    display.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)//设置字体大小
  }
  private fun adjustTextSizeForResult(resultText: String) {
    val cleanText = resultText.replace(",", "").replace(".", "")
    val textSize = fontSizes.getOrElse(cleanText.length) { 34f } // 当字符超过9个时，默认大小为34
    display.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
  }

  //获取计算结果o pe w p
  private fun calculateResult() {
    // 确保 firstOperand 有效
    val operand1 = firstOperand?.toString()?.replace(",", "") ?: return

    // 从 display 获取 secondOperand，确保其有效
    val operand2 = display.text.toString().replace(",", "").toBigDecimalOrNull() ?: return

    if (operand1.isNotEmpty() && operand2 != null) {
      val first = BigDecimal(operand1)
      val second = operand2

      val result = when (currentOperation) {
        R.id.bt_plus -> first + second
        R.id.bt_sub -> first - second
        R.id.bt_multi -> first * second
        R.id.bt_divide -> if (second != BigDecimal.ZERO) {
          first.divide(second, 10, RoundingMode.HALF_UP)
        } else null
        else -> null
      }

      result?.let {
        val resultText = it.stripTrailingZeros().toPlainString()
        display.text = formatNumberWithCommas(resultText)
        adjustTextSizeForResult(resultText)
        display.text = formatNumberWithCommas(it.stripTrailingZeros().toPlainString())
        addResultToHistory(first, second, it)
      }
    }

    isOperationSelected = true
  }



}
