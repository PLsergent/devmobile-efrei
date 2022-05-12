package fr.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Button> keys = new ArrayList();
    public Button equalKey;
    public TextView inputArea;
    public String operationText;
    public TextView outputArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputArea = findViewById(R.id.textViewInput);
        operationText = "";
        outputArea = findViewById(R.id.textViewOutput);

        addEqualKeyToLayout();
        setOnClickListenerOnKeys();
    }

    private void addEqualKeyToLayout(){
        equalKey = new Button(this);
        equalKey.setText("=");
        LinearLayout.LayoutParams buttonLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        equalKey.setLayoutParams(buttonLayoutParam);
        LinearLayout parentLayout = findViewById(R.id.lineatLayoutEqual);
        parentLayout.addView(equalKey);
        equalKey.setOnClickListener(this::onEqualPress);
    }

    private void setOnClickListenerOnKeys() {
        Integer[] ids = {R.id.keyOne, R.id.keyTwo, R.id.keyThree, R.id.keyFour, R.id.keyFive, R.id.keySix,
                R.id.keySeven, R.id.keyEight, R.id.keyNine, R.id.keyZero, R.id.keyPlus, R.id.keyDivision, R.id.keyPlus,
                R.id.keyMultiply, R.id.keyMinus};

        for (Integer id : ids) {
            keys.add(findViewById(id));
        }

        for (Button key : keys) {
            key.setOnClickListener(this::onKeyPress);
        }
    }

    private void onKeyPress(View view) {
        Button keyPressed = (Button) view;
        inputArea.append(keyPressed.getText());
        operationText += keyPressed.getText();

        outputArea.setText("");
    }


    private void onEqualPress(View view) {
        String[] operators = {"+", "-", "/", "*"};
        String[] arrayNumbers = operationText.split("[+-/*]");
        ArrayList<Double> numbers = new ArrayList<>();
        String[] arrayExpression = operationText.split("");
        ArrayList<String> arrayOperators = new ArrayList<>();

        for (String s : arrayExpression) {
            for (String operator : operators) {
                if (s.equals(operator)) {
                    arrayOperators.add(s);
                }
            }
        }

        if (arrayOperators.size() == 0) {
            arrayOperators.add("000");
        }
        // Test valid expression
        if (arrayOperators.size()-arrayNumbers.length > 0 ||
                arrayExpression[arrayExpression.length-1].equals(arrayOperators.get(arrayOperators.size()-1))) {

            outputArea.setText("Invalid operation!");
            return;
        }

        // convert numbers to int
        for (String numb: arrayNumbers) {
            if (numb.equals("")){
                numbers.add(0.0);
            } else {
                numbers.add((double) Integer.parseInt(numb));
            }
        }

        int nOp = 0;
        // first priority
        for (int i = 0; i < arrayOperators.size(); i++) {
            Double opNumber1;
            Double opNumber2;
            double result;

            if (arrayOperators.get(i).equals("*")) {
                System.out.println(numbers);
                System.out.println(arrayOperators);
                opNumber1 = numbers.get(i-nOp);
                opNumber2 = numbers.get(i+1-nOp);
                result = opNumber1 * opNumber2;
            } else if (arrayOperators.get(i).equals("/")) {
                opNumber1 = numbers.get(i-nOp);
                opNumber2 = numbers.get(i+1-nOp);
                result = opNumber1 / opNumber2;
            } else {
                continue;
            }
            numbers.remove(opNumber1);
            numbers.remove(opNumber2);
            numbers.add(i-nOp, result);
            nOp++;
        }

        ArrayList<String> arraySecondPriorityOperators = new ArrayList<>();
        for (String op : arrayOperators) {
            if (!op.equals("*") && !op.equals("/")) {
                arraySecondPriorityOperators.add(op);
            }
        }

        nOp = 0;
        // second priority
        for (int i = 0; i < arraySecondPriorityOperators.size(); i++) {
            Double opNumber1;
            Double opNumber2;
            double result;

            if (arraySecondPriorityOperators.get(i).equals("+")) {
                System.out.println(numbers);
                System.out.println(arraySecondPriorityOperators);
                System.out.println(numbers.get(0) + " " + numbers.get(1));
                opNumber1 = numbers.get(i-nOp);
                opNumber2 = numbers.get(i+1-nOp);
                result = opNumber1 + opNumber2;
            } else if (arraySecondPriorityOperators.get(i).equals("-")) {
                System.out.println(numbers);
                System.out.println(arraySecondPriorityOperators);
                opNumber1 = numbers.get(i-nOp);
                opNumber2 = numbers.get(i+1-nOp);
                result = opNumber1 - opNumber2;
            } else {
                continue;
            }
            numbers.remove(opNumber1);
            numbers.remove(opNumber2);
            numbers.add(i-nOp, result);
            nOp++;
        }

        outputArea.append(""+numbers.get(0));
        operationText = "";
        inputArea.setText("");
    }
}