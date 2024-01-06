package com.faruk.graphproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {

    private TextView txtFeedback, txtFeedbackFail;
    private EditText editTxtMail, editTxtParola, editTxtAd, editTxtSoyad, editTxtPuk;
    private Button btnGuncelle;
    private HocaDBHelper db;


    public void init() {
        txtFeedback = findViewById(R.id.activity_profile_txtFeedback);
        txtFeedbackFail = findViewById(R.id.activity_profile_txtFeedbackFail);
        editTxtMail = findViewById(R.id.activity_profile_editTxtMail);
        editTxtMail.setEnabled(false);
        editTxtParola = findViewById(R.id.activity_profile_editTxtParola);
        editTxtAd = findViewById(R.id.activity_profile_editTxtad);
        editTxtSoyad = findViewById(R.id.activity_profile_editTxtsoyad);
        editTxtPuk = findViewById(R.id.activity_profile_editTxtPuk);
        btnGuncelle = findViewById(R.id.activity_profile_guncelle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        init();
        db = new HocaDBHelper(this);
        editTxtMail.setText(db.getDataFromDatabase("mail"));
        editTxtParola.setText(db.getDataFromDatabase("parola"));
        editTxtAd.setText(db.getDataFromDatabase("ad"));
        editTxtSoyad.setText(db.getDataFromDatabase("soyad"));
        editTxtPuk.setText(db.getDataFromDatabase("puk"));
        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList updatedFields = new ArrayList();
                int parolaSize = editTxtParola.length();
                if ( parolaSize < 8) {
                    editTxtParola.setText(db.getDataFromDatabase("parola"));
                    txtFeedbackFail.setVisibility(View.VISIBLE);
                    txtFeedbackFail.setText("Parola alanı en az 8 karakter olmalıdır!");
                }
                if (editTxtParola.getText().toString().equalsIgnoreCase(db.getDataFromDatabase("parola"))) {

                } else {
                    db.updateUser("parola", editTxtParola.getText().toString());
                    updatedFields.add("parola");
                }
                if (editTxtAd.getText().toString().equalsIgnoreCase(db.getDataFromDatabase("ad"))) {

                } else {
                    db.updateUser("ad", editTxtAd.getText().toString());
                    updatedFields.add("ad");
                }
                if (editTxtSoyad.getText().toString().equalsIgnoreCase(db.getDataFromDatabase("soyad"))) {

                } else {
                    db.updateUser("soyad", editTxtSoyad.getText().toString());
                    updatedFields.add("soyad");
                }
                if (editTxtPuk.getText().toString().equalsIgnoreCase(db.getDataFromDatabase("puk"))) {

                } else {
                    db.updateUser("puk", editTxtPuk.getText().toString());
                    updatedFields.add("puk");
                }

                if (!updatedFields.isEmpty() && updatedFields.size() > 1) {
                    String alanlar = new String();
                    String ilkHarf, kalan;
                    for (int i = 0; i < updatedFields.toArray().length; i++) {
                        alanlar = updatedFields.toString();
                    }
                    txtFeedback.setVisibility(View.VISIBLE);
                    txtFeedbackFail.setVisibility(View.INVISIBLE);
                    txtFeedback.setBackgroundColor(getResources().getColor(R.color.feedBackColor));
                    alanlar = alanlar.replace("[", "");
                    alanlar = alanlar.replace("]", "");
                    ilkHarf = alanlar.substring(0, 1).toUpperCase();
                    kalan = alanlar.substring(1);
                    txtFeedback.setText(ilkHarf + kalan + " bilgileriniz başarıyla güncellenmiştir.");
                    System.out.println(alanlar);
                } else if (!updatedFields.isEmpty() && updatedFields.size() == 1) {
                    String alanlar = new String();
                    String ilkHarf, kalan;
                    alanlar = updatedFields.toString();
                    txtFeedback.setVisibility(View.VISIBLE);
                    txtFeedbackFail.setVisibility(View.INVISIBLE);
                    txtFeedback.setBackgroundColor(getResources().getColor(R.color.feedBackColor));
                    alanlar = alanlar.replace("[", "");
                    alanlar = alanlar.replace("]", "");
                    ilkHarf = alanlar.substring(0, 1).toUpperCase();
                    kalan = alanlar.substring(1);
                    txtFeedback.setText(ilkHarf + kalan + " bilginiz başarıyla güncellenmiştir.");
                    System.out.println(alanlar);
                } else {
                    txtFeedback.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfilActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);
    }

    public void ProfilActivitygeriOnClick(View v) {
        Intent intent = new Intent(ProfilActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);
    }
}