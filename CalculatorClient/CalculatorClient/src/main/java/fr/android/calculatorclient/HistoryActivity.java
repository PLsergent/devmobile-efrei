package fr.android.calculatorclient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {
    public TextView lastOperationView;
    public TextView lastResultView;
    private EditText inputUrl;
    public Button searchButton;
    public Button calculatorIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Bundle variables = getIntent().getExtras();

        lastOperationView = findViewById(R.id.lastOperationText);
        lastResultView = findViewById(R.id.lastResultText);
        searchButton = findViewById(R.id.search);
        inputUrl = findViewById(R.id.inputUrl);
        calculatorIntent = findViewById(R.id.calculatorIntent);

        if (variables != null) {
            lastResultView.setText(variables.getString("lastResult"));
            lastOperationView.setText(variables.getString("lastOperation"));
        }

        searchButton.setOnClickListener(this::openInBrowser);
        calculatorIntent.setOnClickListener(this::triggerIntent);
    }

    private void openInBrowser(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(inputUrl.getText().toString()));
        startActivity(browserIntent);
    }

    private void triggerIntent(View view) {
        Intent mainActivity = new Intent("hereismyapp");
        startActivity(mainActivity);
    }
}
