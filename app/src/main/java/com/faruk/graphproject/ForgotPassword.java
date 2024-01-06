package com.faruk.graphproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPassword extends AppCompatActivity {

    private TextView feedbackSuccess, feedbackFail;
    private EditText editTxtPuk, yeniParola, parolaDogrula;
    private Button sifreDegis, geri;
    private HocaDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        feedbackSuccess = findViewById(R.id.for_pass_feedbackSuccess);
        feedbackFail = findViewById(R.id.for_pass_feedbackFail);
        editTxtPuk = findViewById(R.id.for_pass_puk);
        yeniParola = findViewById(R.id.for_pass_editTxtParola);
        parolaDogrula = findViewById(R.id.for_pass_editTxtParolaDogrula);
        sifreDegis = findViewById(R.id.for_pass_degistir);
        geri = findViewById(R.id.for_pass_geri);
        db = new HocaDBHelper(this);
    }

    public void geriOnClick(View v) {
        Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void degistirOnClick(View v) {
        String puk = editTxtPuk.getText().toString();
        String pass = yeniParola.getText().toString();
        int passLength = pass.length();
        String verifyPass = parolaDogrula.getText().toString();
        Boolean checkPuk = db.checkPuk(puk);
        if (passLength < 8) {
            feedbackFail.setVisibility(View.VISIBLE);
            feedbackSuccess.setVisibility(View.INVISIBLE);
            feedbackFail.setText("Parola 8 karakterden büyük olmalı!");
        }
        else {
            if (pass.equals(verifyPass)) {
                if (checkPuk == true) {
                    db.updatePassword(puk, pass);
                    feedbackSuccess.setVisibility(View.VISIBLE);
                    feedbackFail.setVisibility(View.INVISIBLE);
                    feedbackSuccess.setText("Parolanız başarıyla güncellenmiştir.");
                }
                else {
                    feedbackFail.setVisibility(View.VISIBLE);
                    feedbackSuccess.setVisibility(View.INVISIBLE);
                    feedbackFail.setText("Girdiğiniz anahtar değere sahip bir kullanıcı bulunmuyor!");
                }
            } else {
                feedbackFail.setVisibility(View.VISIBLE);
                feedbackFail.setText("Parolalar aynı değil!");
            }
        }
    }
}