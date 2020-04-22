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

// Precisa extender AndroidViewModel pra ter acesso ao contexto
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
        // Precisa ser assíncrona por lidar com a rede
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
            try {
                // Conexão com o servidor remoto
                URL url = new URL("https://double-nirvana-273602.appspot.com/?hl=pt-BR");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                // Configurando limites de tempo
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                // Indica que o método HTTP utilizado deve ser o POST
                conn.setRequestMethod("POST");
                // Indica que serão realizadas envios e recebimentos
                conn.setDoInput(true);
                conn.setDoOutput(true);

                //ENVIO DOS PARAMETROS
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, StandardCharsets.UTF_8));
                // Operações: 1 (adição), 2 (Subtração), 3 (Multiplicação) e 4 (Divisão).
                writer.write("oper1=" + oper1 + "&oper2=" + oper2 + "&operacao=" + operation);
                // Envia a stream
                writer.flush();
                // Encerra a escrita
                writer.close();
                os.close();

                // Checa se o servidor retornou 200 ou se houve algum erro
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    // Se retornou 200, lê a resposta
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                    // StringBuilder lida com Strings cujo tamanho pode ser alterado
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            // Se o resultado é vazio, houve algum problema na chamada do método
            if (result.isEmpty()) {
                Toast.makeText(getApplication().getApplicationContext(), "Erro na comunicação com o servidor.", Toast.LENGTH_SHORT).show();
            }
            resultText.setValue(result);
        }
    }
}