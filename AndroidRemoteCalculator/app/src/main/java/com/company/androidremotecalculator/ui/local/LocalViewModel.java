package com.company.androidremotecalculator.ui.local;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.company.androidremotecalculator.ui.rmi.RMIViewModel;

public class LocalViewModel extends AndroidViewModel {

    private MutableLiveData<String> resultText;

    public LocalViewModel(@NonNull Application application) {
        super(application);
        resultText = new MutableLiveData<>();
        resultText.setValue("");
    }

    LiveData<String> getResult() {
        return resultText;
    }

    void calc(int operation, double oper1, double oper2) {
        switch (operation) {
            case 1:
                // 1 - soma
                resultText.setValue(String.valueOf(oper1 + oper2));
                break;
            case 2:
                // 2 - subtração
                resultText.setValue(String.valueOf(oper1 - oper2));
                break;
            case 3:
                // 3 - multiplicação
                resultText.setValue(String.valueOf(oper1 * oper2));
                break;
            case 4:
                // 4 - divisão
                resultText.setValue(String.valueOf(oper1 / oper2));
                break;
            default:
                resultText.setValue("");
                Toast.makeText(getApplication().getApplicationContext(), "Não foi possível conectar-se ao servidor.", Toast.LENGTH_SHORT).show();
        }
    }
}