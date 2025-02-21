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
    private boolean isNewInput = true; // To reset input after a result

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

    private void onButtonClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        try {
            switch (buttonText) {
                case "+":
                case "-":
                case "*":
                case "/":
                case "^":
                    handleOperator(buttonText);
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
                case "√":
                    calculateSquareRoot();
                    break;
                default:
                    handleNumberInput(buttonText);
                    break;
            }
        } catch (Exception e) {
            textView.setText("Error");
        }
    }

    private void handleOperator(String operator) {
        if (!textView.getText().toString().isEmpty()) {
            currentValue = Double.parseDouble(textView.getText().toString());
            currentOperation = operator;
            isNewInput = true;
        }
    }

    private void calculateResult() {
        if (textView.getText().toString().isEmpty() || currentOperation.isEmpty()) {
            return;
        }

        double newValue = Double.parseDouble(textView.getText().toString());
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
                    textView.setText("Error");
                    return;
                }
                result = currentValue / newValue;
                break;
            case "^":
                result = Math.pow(currentValue, newValue);
                break;
        }

        // Check for overflow (if the result is too large)
        if (Double.isInfinite(result)) {
            textView.setText("Overflow");
        } else {
            textView.setText(String.valueOf(result));
        }

        currentOperation = "";
        isNewInput = true;
    }

    private void resetCalculator() {
        textView.setText("");
        currentValue = 0;
        currentOperation = "";
        isNewInput = true;
    }

    private void handleDecimal() {
        String text = textView.getText().toString();
        if (!text.contains(".")) {
            textView.append(".");
        }
    }

    private void handleNumberInput(String number) {
        if (isNewInput) {
            textView.setText(number);
            isNewInput = false;
        } else {
            textView.append(number);
        }
    }

    private void calculateFactorial() {
        if (textView.getText().toString().isEmpty()) {
            return;
        }

        try {
            int value = Integer.parseInt(textView.getText().toString());
            if (value < 0) {
                textView.setText("Error");
                return;
            }

            long factorial = 1;
            for (int i = 1; i <= value; i++) {
                factorial *= i;
                if (factorial > Long.MAX_VALUE) { // Prevent overflow
                    textView.setText("Overflow");
                    return;
                }
            }

            textView.setText(String.valueOf(factorial));
        } catch (Exception e) {
            textView.setText("Error");
        }
        isNewInput = true;
    }

    private void calculateSquareRoot() {
        if (textView.getText().toString().isEmpty()) {
            return;
        }

        double value = Double.parseDouble(textView.getText().toString());
        if (value < 0) {
            textView.setText("Error");
            return;
        }

        double result = Math.sqrt(value);

        // Check for overflow
        if (Double.isInfinite(result)) {
            textView.setText("Overflow");
        } else {
            textView.setText(String.valueOf(result));
        }

        isNewInput = true;
    }
}
