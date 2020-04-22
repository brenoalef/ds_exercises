package com.company.androidremotecalculator.ui.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocalViewModel extends ViewModel {

    private MutableLiveData<String> resultText;

    public LocalViewModel() {
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
        }
    }
}