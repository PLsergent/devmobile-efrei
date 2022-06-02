package fr.android.calculatorasync;

import android.os.AsyncTask;
import android.widget.TextView;

public class CalculateAsyncTask extends AsyncTask<String, Integer, String> {
    private TextView outputArea;
    private CalculateFactory calculateFactory;

    public CalculateAsyncTask(TextView outputArea, CalculateFactory calculateFactory) {
        this.outputArea = outputArea;
        this.calculateFactory = calculateFactory;
    }

    @Override
    protected String doInBackground(String... operationTexts) {
        if (operationTexts.length != 1) {
            return "";
        } else {
            return calculateFactory.calculateOperation(operationTexts[0]);
        }
    }

    protected void onPostExecute(String result) {
        outputArea.append(result);
    }
}
