package fr.android.calculatorclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public String lastOperation;
    public static String lastResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastOperation = "";
        lastResult = "";

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

        equalKey.setOnClickListener(this::onEqualPressAsyncTask);
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
        CalculateAsyncTask calculateAsyncTaskOp = new CalculateAsyncTask(outputArea); // AsyncTask
        calculateAsyncTaskOp.execute(operationText);
        lastOperation = operationText;
        lastResult = outputArea.getText().toString();
        inputArea.setText("");
        operationText = "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_history:
                Intent intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("lastOperation", lastOperation);
                intent.putExtra("lastResult", lastResult);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}