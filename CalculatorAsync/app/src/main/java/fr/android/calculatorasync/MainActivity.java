package fr.android.calculatorasync;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Button> keys = new ArrayList();
    public Button equalKey;
    public TextView inputArea;
    public String operationText;
    public TextView outputArea;
    public CalculateFactory calculateFactory;
    public CalculateAsyncTask calculateAsyncTaskOp; // AsyncTask method
    public Handler calculateHandler; // Handler method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputArea = findViewById(R.id.textViewInput);
        operationText = "";
        outputArea = findViewById(R.id.textViewOutput);

        addEqualKeyToLayout();
        setOnClickListenerOnKeys();
        calculateFactory = new CalculateFactory();
        calculateAsyncTaskOp = new CalculateAsyncTask(outputArea, calculateFactory); // AsyncTask
        calculateHandler = new Handler(); // Handler
    }

    private void addEqualKeyToLayout(){
        equalKey = new Button(this);
        equalKey.setText("=");
        LinearLayout.LayoutParams buttonLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        equalKey.setLayoutParams(buttonLayoutParam);
        LinearLayout parentLayout = findViewById(R.id.lineatLayoutEqual);
        parentLayout.addView(equalKey);

        // We can define here onEqualPressAsyncTask or onEqualPressHandler to switch between
        // the two implementation methods
        equalKey.setOnClickListener(this::onEqualPressHandler);
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


    private void onEqualPressAsyncTask(View view) {
        calculateAsyncTaskOp.execute(operationText);
        inputArea.setText("");
        operationText = "";
    }

    private void onEqualPressHandler(View view) {
        Runnable runnable = () -> {
            Looper.prepare();
            outputArea.append(calculateFactory.calculateOperation(operationText));
            inputArea.setText("");
            operationText = "";
        };
        new Thread(runnable).start();
    }
}