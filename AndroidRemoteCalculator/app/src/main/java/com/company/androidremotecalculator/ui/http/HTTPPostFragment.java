package com.company.androidremotecalculator.ui.http;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.company.androidremotecalculator.R;

public class HTTPPostFragment extends Fragment {

    private HTTPPostViewModel httpPostViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        httpPostViewModel =
                ViewModelProviders.of(this).get(HTTPPostViewModel.class);
        View root = inflater.inflate(R.layout.fragment_http_post, container, false);

        final EditText oper1EditText = root.findViewById(R.id.editTextPostOPer1);
        final EditText oper2EditText = root.findViewById(R.id.editTextPostOPer2);

        // Atualiza o valor do resultado se for alterado no ViewModel
        final TextView resultTextView = root.findViewById(R.id.textViewPostResult);
        httpPostViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                resultTextView.setText(s);
            }
        });

        final Button sumButton = root.findViewById(R.id.buttonPostSum);
        final Button subButton = root.findViewById(R.id.buttonPostSub);
        final Button mulButton = root.findViewById(R.id.buttonPostMul);
        final Button divButton = root.findViewById(R.id.buttonPostDiv);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Checa se os dois números fora fornecidos e habilita/desabilita os botões de operação
                boolean enabled = !(TextUtils.isEmpty(oper1EditText.getText()) || TextUtils.isEmpty(oper2EditText.getText()));
                sumButton.setEnabled(enabled);
                subButton.setEnabled(enabled);
                mulButton.setEnabled(enabled);
                divButton.setEnabled(enabled);
            }
        };

        // Observa alterações nos valores dos operadores
        oper1EditText.addTextChangedListener(afterTextChangedListener);
        oper2EditText.addTextChangedListener(afterTextChangedListener);

        View.OnClickListener calcListenner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double oper1 = Double.parseDouble(oper1EditText.getText().toString());
                double oper2 = Double.parseDouble(oper2EditText.getText().toString());
                // Muda a operação de acordo com qual botão foi pressionado
                switch (v.getId()) {
                    case R.id.buttonPostSum:
                        httpPostViewModel.calc(1, oper1, oper2);
                        break;
                    case R.id.buttonPostSub:
                        httpPostViewModel.calc(2, oper1, oper2);
                        break;
                    case R.id.buttonPostMul:
                        httpPostViewModel.calc(3, oper1, oper2);
                        break;
                    case R.id.buttonPostDiv:
                        httpPostViewModel.calc(4, oper1, oper2);
                        break;
                }
            }
        };

        // Ação dos botões
        sumButton.setOnClickListener(calcListenner);
        subButton.setOnClickListener(calcListenner);
        mulButton.setOnClickListener(calcListenner);
        divButton.setOnClickListener(calcListenner);

        return root;
    }
}
