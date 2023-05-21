package com.example.ardino_administration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class VerificationActivity extends AppCompatActivity {

    EditText et_1 , et_2 , et_3 , et_4;
    Button btn_verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        et_1 = findViewById(R.id.verificationAct_ed1);
        et_2 = findViewById(R.id.verificationAct_ed2);
        et_3 = findViewById(R.id.verificationAct_ed3);
        et_4 = findViewById(R.id.verificationAct_ed4);
        btn_verification = findViewById(R.id.verificationAct_btn_verification);
        btn_verification.setEnabled(false);
        btn_verification.setTextColor(getResources().getColor(android.R.color.darker_gray , null));

        setUpOTPEditText();

        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext()  , MainActivity.class));
                finish();
            }
        });
    }


    public void setUpOTPEditText(){
        et_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    et_2.requestFocus();
                }
                setButtonEnable(et_1.getText().toString().isEmpty() ,et_2.getText().toString().isEmpty(),
                        et_3.getText().toString().isEmpty(), et_4.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    et_3.requestFocus();
                }
                setButtonEnable(et_1.getText().toString().isEmpty() ,et_2.getText().toString().isEmpty(),
                        et_3.getText().toString().isEmpty(), et_4.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    et_4.requestFocus();
                }
                setButtonEnable(et_1.getText().toString().isEmpty() ,et_2.getText().toString().isEmpty(),
                        et_3.getText().toString().isEmpty(), et_4.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    InputMethodManager mgr = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et_4.getWindowToken(), 0);
                }
                setButtonEnable(et_1.getText().toString().isEmpty() ,et_2.getText().toString().isEmpty(),
                        et_3.getText().toString().isEmpty(), et_4.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setButtonEnable(boolean a , boolean b , boolean c , boolean d){
        if (!a && !b && !c && !d) {
            btn_verification.setEnabled(true);
            btn_verification.setTextColor(getResources().getColor(R.color.white , null));
        }else {
            btn_verification.setEnabled(false);

        }
    }
}