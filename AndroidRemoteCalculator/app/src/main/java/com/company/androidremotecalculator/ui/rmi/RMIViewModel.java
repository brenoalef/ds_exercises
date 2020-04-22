package com.company.androidremotecalculator.ui.rmi;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.company.CalculatorInterface;

import java.io.IOException;

import lipermi.handler.CallHandler;
import lipermi.net.Client;

public class RMIViewModel extends AndroidViewModel {

    private MutableLiveData<String> resultText;

    public RMIViewModel(@NonNull Application application) {
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
        protected String doInBackground(Void... params) {
            CallHandler callHandler = new CallHandler();
            String remoteHost = "192.168.15.10";
            int portWasBinded = 9090;

            Client client;
            try {
                client = new Client(remoteHost, portWasBinded, callHandler);

                CalculatorInterface remoteObject =
                        (CalculatorInterface) client.getGlobal(CalculatorInterface.class);
                double result = remoteObject.calc(operation, oper1, oper2);
                System.out.println(result);
                return String.valueOf(result);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplication().getApplicationContext(), "Não foi possível conectar-se ao servidor.", Toast.LENGTH_SHORT).show();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            resultText.setValue(result);
        }
    }

}