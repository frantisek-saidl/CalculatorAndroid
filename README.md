
# Calculator App for Android (Java)

## 1. UI Design

The user interface (UI) is designed in the <a href="https://github.com/frantisek-saidl/CalculatorAndroid/blob/master/app/src/main/res/layout/activity_main.xml" >`activity_main.xml`</a> file using a combination of `LinearLayout` and `ConstraintLayout`. The layout structure is inspired by the standard calculator app on my phone.

### **Key Design Elements:**

-   **ConstraintLayout:** Used as the root layout to position elements dynamically.
-   **LinearLayout:** Utilized for the number and operation buttons.
-   **TextView:** Displays the entered numbers and results.
-   **Buttons:** Include digits (0-9), basic arithmetic operators (+, -, *, /), and additional functions such as factorial (`!`), power (`^`), square root (`√`), and an `AC` (clear) button.
- **Colors:** Defined in the <a href="https://github.com/frantisek-saidl/CalculatorAndroid/blob/master/app/src/main/res/values/colors.xml">`colors.xml`</a> file

----------

## 2. Button Layout and Structure

The buttons are arranged in a grid-like structure using multiple nested `LinearLayout` elements. Each button has:

-   `android:layout_width="match_parent"` to ensure proper scaling.
-   `android:layout_weight` for equal distribution of space.
-   `android:textColor="@color/black"` for visibility.
-   Different operators and functions assigned.

The equal (`=`) button stands out with a pink background to indicate its importance.

## 3. Logic (Java)
The <a href="https://github.com/frantisek-saidl/CalculatorAndroid/blob/master/app/src/main/java/com/example/calculator/MainActivity.java">`MainActivity.java`</a> contains the core logic for handling the calculator's operations:

- **Button Initialization:** Buttons are initialized and their `OnClickListener` is set for each button in the `initializeButtons()` method.
-  **Button Handling:**
    -   Each button's functionality is handled in `onButtonClick()`.
    -   Operators are appended to the `TextView`, and the current operation is tracked.
    -   When the `=` button is clicked, `calculateResult()` evaluates the expression.
    -   Special buttons like `AC` clear the input, and `!` calculates the factorial of the currently entered number, and `√` calculates the square root.
- **Mathematical Operations:**
    -   Basic arithmetic operations are handled using the `handleOperator()` method.
    -   Factorial and square root are handled by `calculateFactorial()` and `calculateRoot`, both with an error thrown for negative numbers.
-  **Error Handling:**
    -   The app provides error messages for invalid operations like division by zero, factorial of negative numbers, and invalid input sequences.
-  **Special Cases:**
    -   The minus operator (`-`) is handled separately to allow entering negative numbers or performing subtraction.
    -   Decimal input (`.`) is allowed only once in each number.
## 4. Issues
 - **Nth root:** 
	 - Most of the time i worked with just a square root which i handled separately using the Math library until i realized i can handle it similarly as the power like this: `result = Math.pow(currentValue, 1 / newValue)`
- **Subtracting negative numbers**
	- You can't subtract a negative number as the second number [6 - (-6)], because i prevent users from writing the minus twice in a row, so it just throws an error.
	- This issue could be easily resolved, but I just simply ran out of time to do it.
## 5. Conclusion
Overall I did not find this project very difficult, sure, there were some minor knowledge gaps I ran into while completing it, but I enjoyed it and feel kind of proud of my work. I feel like Java is one of the most easily understood languages and although we had it in the second year i feel like I learned more during this project than back then. Although I did'nt like Android studio at first glance, through time spent working in it I managed to decently understand it, and I just love how well documented it is.
