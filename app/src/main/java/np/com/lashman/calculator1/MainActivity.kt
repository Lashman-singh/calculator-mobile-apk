package np.com.lashman.calculator1

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import np.com.lashman.calculator1.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var canAddOperation = false
    private var canAddDecimal = true
    private var operator: String? = null
    private var operand1: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Example usage
        binding.workingTV.text = ""
        binding.resultsTV.text = ""
    }

    fun numberAction(view: View) {
        if (view is Button) {
            binding.workingTV.append(view.text) // This should append the button text to workingTV
            canAddOperation = true // Set this to true so we can add an operation after a number
            if (view.text == ".") {
                canAddDecimal = false // Allow only one decimal point
            }
        }
    }


    fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            operand1 = binding.workingTV.text.toString()
            operator = view.text.toString()
            binding.workingTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun allClearAction(view: View) {
        binding.workingTV.text = ""
        binding.resultsTV.text = ""
        canAddOperation = false
        canAddDecimal = true
        operator = null
        operand1 = ""
    }

    fun backSpaceAction(view: View) {
        val length = binding.workingTV.text.length
        if (length > 0) {
            binding.workingTV.text = binding.workingTV.text.subSequence(0, length - 1)
        }
    }

    fun equalsAction(view: View) {
        if (operator != null) {
            val operand2 = binding.workingTV.text.toString().substringAfterLast(operator!!)
            try {
                val result = calculateResult(operand1, operand2, operator!!)
                binding.resultsTV.text = result.toString()
            } catch (e: Exception) {
                binding.resultsTV.text = "Error"
            }
        }
    }

    private fun calculateResult(operand1: String, operand2: String, operator: String): Double {
        val num1 = operand1.toDoubleOrNull() ?: 0.0
        val num2 = operand2.toDoubleOrNull() ?: 0.0
        return when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> if (num2 != 0.0) num1 / num2 else throw ArithmeticException("Cannot divide by zero")
            else -> 0.0
        }
    }
}
