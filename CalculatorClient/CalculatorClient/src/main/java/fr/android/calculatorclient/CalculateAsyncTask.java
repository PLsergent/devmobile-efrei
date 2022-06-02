package fr.android.calculatorclient;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CalculateAsyncTask extends AsyncTask<String, Integer, String> {
    private TextView outputArea;

    public CalculateAsyncTask(TextView outputArea) {
        this.outputArea = outputArea;
    }

    @Override
    protected String doInBackground(String... operationTexts) {
        if (operationTexts.length != 1) {
            return "";
        } else {
            try {
                Socket clientSocket = new Socket("10.0.2.2", 9876);
                DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream());
                dOut.writeUTF(operationTexts[0]);
                System.out.println(operationTexts[0]);
                dOut.flush();

                DataInputStream dIn = new DataInputStream(clientSocket.getInputStream());
                String result = dIn.readUTF();

                dIn.close();
                dOut.close();
                clientSocket.close();

                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    protected void onPostExecute(String result) {
        outputArea.append(result);
        MainActivity.lastResult = result;
    }
}