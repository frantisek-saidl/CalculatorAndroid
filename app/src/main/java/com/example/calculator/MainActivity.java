package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private double currentValue = 0;
    private String currentOperation = "";
    private boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeButtons();
    }

    // Initialize buttons and set onclick listeners
    private void initializeButtons() {
        textView = findViewById(R.id.textView);
        int[] buttonIds = {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
                R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
                R.id.button_plus, R.id.button_minus, R.id.button_multiply, R.id.button_divide,
                R.id.button_ac, R.id.button_equals, R.id.button_dot, R.id.button_factorial,
                R.id.button_power, R.id.button_root
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this::onButtonClick);
        }
    }

    // Handle button onclick events
    private void onButtonClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        try {
            switch (buttonText) {
                case "+":
                case "*":
                case "/":
                case "^":
                case "√":
                    handleOperator(buttonText);
                    break;
                case "-":
                    handleMinus();
                    break;
                case "=":
                    calculateResult();
                    break;
                case "AC":
                    resetCalculator();
                    break;
                case ".":
                    handleDecimal();
                    break;
                case "!":
                    calculateFactorial();
                    break;
                default:
                    handleNumberInput(buttonText);
                    break;
            }
        } catch (Exception e) {
            textView.setText("Error");
        }
    }

    // Handle operator input
    private void handleOperator(String operator) {
        String currentText = textView.getText().toString();

        if (!currentText.isEmpty() && !isOperator(currentText.charAt(currentText.length() - 1))) {
            currentValue = Double.parseDouble(currentText);
            currentOperation = operator;
            textView.append(" " + operator + " ");
            isNewInput = false;
        }
    }

    // Handle minus operator separately to allow negative numbers
    private void handleMinus() {
        String currentText = textView.getText().toString();

        if (currentText.isEmpty()) {
            // Allow "-" at the beginning (negative number)
            textView.append("-");
            isNewInput = false;
        } else {
            char lastChar = currentText.charAt(currentText.length() - 1);

            if (isOperator(lastChar)) {
                // Allow "-5" after an operator (e.g., "5 * -3")
                textView.append(" -");
            } else {
                // Treat as subtraction
                handleOperator("-");
            }
        }
    }

    // Calculate the result based on the current operation
    private void calculateResult() {
        String currentText = textView.getText().toString();

        if (currentText.isEmpty() || currentOperation.isEmpty() || isOperator(currentText.charAt(currentText.length() - 1))) {
            return;
        }

        String[] parts = currentText.split(" ");
        if (parts.length < 3) return;

        double newValue = Double.parseDouble(parts[2]);
        double result = 0;

        switch (currentOperation) {
            case "+":
                result = currentValue + newValue;
                break;
            case "-":
                result = currentValue - newValue;
                break;
            case "*":
                result = currentValue * newValue;
                break;
            case "/":
                if (newValue == 0) {
                    textView.setText("Cannot divide by zero");
                    return;
                }
                result = currentValue / newValue;
                break;
            case "^":
                result = Math.pow(currentValue, newValue);
                break;
            case "√":
                if (currentValue < 0) {
                    textView.setText("Error");
                    return;
                }
                result = Math.pow(currentValue, 1 / newValue);
                break;
        }

        if (Double.isInfinite(result)) {
            textView.setText("Overflow");
        } else {
            textView.setText(String.valueOf(result));
        }

        currentOperation = "";
        isNewInput = true;
    }

    // Reset the calculator to its initial state
    private void resetCalculator() {
        textView.setText("");
        currentValue = 0;
        currentOperation = "";
        isNewInput = true;
    }

    // Handle decimal point input
    private void handleDecimal() {
        String text = textView.getText().toString();
        if (!text.contains(".")) {
            textView.append(".");
        }
    }

    // Handle number input
    private void handleNumberInput(String number) {
        if (isNewInput) {
            textView.setText(number);
            isNewInput = false;
        } else {
            textView.append(number);
        }
    }

    // Calculate the factorial of the current number
    private void calculateFactorial() {
        String currentText = textView.getText().toString();

        if (currentText.isEmpty() || isOperator(currentText.charAt(currentText.length() - 1))) {
            return;
        }

        try {
            int value = Integer.parseInt(currentText);
            if (value < 0) {
                textView.setText("Error");
                return;
            }

            long factorial = 1;
            for (int i = 1; i <= value; i++) {
                if (factorial > Long.MAX_VALUE / i) {
                    textView.setText("Overflow");
                    return;
                }
                factorial *= i;
            }

            textView.setText(String.valueOf(factorial));
        } catch (Exception e) {
            textView.setText("Error");
        }
        isNewInput = true;
    }

    // Check if a character is an operator
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '√';
    }
}