package com.company.androidremotecalculator.ui.http;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class HTTPPostViewModel extends AndroidViewModel {

    private MutableLiveData<String> resultText;

    public HTTPPostViewModel(@NonNull Application application) {
        super(application);
        resultText = new MutableLiveData<>();
        resultText.setValue("");
    }

    LiveData<String> getResult() {
        return resultText;
    }

    void calc(int operation, double oper1, double oper2) {
        MyAsyncTask myAsyncTask = new MyAsyncTask(operation, oper1, oper2);
        myAsyncTask.execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, String> {
        int operation;
        double oper1, oper2;

        public MyAsyncTask(int operation, double oper1, double oper2) {
            this.operation = operation;
            this.oper1 = oper1;
            this.oper2 = oper2;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                URL url = new URL("https://double-nirvana-273602.appspot.com/?hl=pt-BR");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                //ENVIO DOS PARAMETROS
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write("oper1=" + oper1 + "&oper2=" + oper2 + "&operacao=" + operation);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = response.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplication().getApplicationContext(), "Não foi possível conectar-se ao servidor.", Toast.LENGTH_SHORT).show();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            resultText.setValue(result);
        }
    }
}