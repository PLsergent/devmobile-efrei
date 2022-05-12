package fr.android.eurodollar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class CurrencyConverter extends AppCompatActivity {

    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.text = (EditText) findViewById(R.id.editText1);
        // We are getting the component's object in the variable called "text"
    }

    public void convertClickHandler(View view){
        switch (view.getId()) {
            case R.id.button:
                RadioButton euroButton = (RadioButton) findViewById(R.id.radioButton);
                RadioButton dollarButton = (RadioButton) findViewById(R.id.radioButton2);
                if (text.getText().length() == 0) {
                    Toast.makeText(this, "Please enter a valid number",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                float inputValue = Float.parseFloat(text.getText().toString());
                if (euroButton.isChecked()) {
                    text.setText(String
                            .valueOf(convertDollarToEuro(inputValue)));
                    euroButton.setChecked(false);
                    dollarButton.setChecked(true);
                } else {
                    text.setText(String
                            .valueOf(convertEuroToDollar(inputValue)));
                    dollarButton.setChecked(false);
                    euroButton.setChecked(true);
                }
                break;
        }
    }

    // Convertir Dollar à Euro
    private float convertDollarToEuro(float dollar) {
        return (float) (dollar*0.92);
    }

    // Convertir Euro à Dollar
    private float convertEuroToDollar(float euro) {
        return (float) (euro*1.02);
    }
}