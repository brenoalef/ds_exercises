package com.company.androidremotecalculator.ui.local;

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

public class LocalFragment extends Fragment {

    private LocalViewModel localViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        localViewModel =
                ViewModelProviders.of(this).get(LocalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_local, container, false);

        final EditText oper1EditText = root.findViewById(R.id.editTextLocalOPer1);
        final EditText oper2EditText = root.findViewById(R.id.editTextLocalOPer2);

        final TextView resultTextView = root.findViewById(R.id.textViewLocalResult);
        localViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                resultTextView.setText(s);
            }
        });

        final Button sumButton = root.findViewById(R.id.buttonLocalSum);
        final Button subButton = root.findViewById(R.id.buttonLocalSub);
        final Button mulButton = root.findViewById(R.id.buttonLocalMul);
        final Button divButton = root.findViewById(R.id.buttonLocalDiv);

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
                boolean enabled = !(TextUtils.isEmpty(oper1EditText.getText()) || TextUtils.isEmpty(oper2EditText.getText()));
                sumButton.setEnabled(enabled);
                subButton.setEnabled(enabled);
                mulButton.setEnabled(enabled);
                divButton.setEnabled(enabled);
            }
        };

        oper1EditText.addTextChangedListener(afterTextChangedListener);
        oper2EditText.addTextChangedListener(afterTextChangedListener);

        View.OnClickListener calcListenner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double oper1 = Double.parseDouble(oper1EditText.getText().toString());
                double oper2 = Double.parseDouble(oper2EditText.getText().toString());
                switch (v.getId()) {
                    case R.id.buttonLocalSum:
                        localViewModel.calc(1, oper1, oper2);
                        break;
                    case R.id.buttonLocalSub:
                        localViewModel.calc(2, oper1, oper2);
                        break;
                    case R.id.buttonLocalMul:
                        localViewModel.calc(3, oper1, oper2);
                        break;
                    case R.id.buttonLocalDiv:
                        localViewModel.calc(4, oper1, oper2);
                        break;
                }
            }
        };

        sumButton.setOnClickListener(calcListenner);
        subButton.setOnClickListener(calcListenner);
        mulButton.setOnClickListener(calcListenner);
        divButton.setOnClickListener(calcListenner);

        return root;
    }
}
