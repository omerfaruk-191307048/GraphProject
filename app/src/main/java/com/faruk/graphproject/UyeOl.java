package com.faruk.graphproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UyeOl extends AppCompatActivity {
    EditText editTextKullanici, editTextParola, editTextParolaDogrula, editTextAd, editTextSoyad, editTextPuk;
    float parolaSize;
    Button btnUyeOl, btnGirisYap;
    HocaDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_ol);

        editTextKullanici = findViewById(R.id.uyeol_editTxtMail);
        editTextParola = findViewById(R.id.uyeol_editTxtParola);
        editTextParolaDogrula = findViewById(R.id.uyeol_editTxtParolaDogrula);
        editTextAd = findViewById(R.id.uyeol_editTxtAd);
        editTextSoyad = findViewById(R.id.uyeol_editTxtSoyad);
        editTextPuk = findViewById(R.id.uyeol_sifrePuk);
        btnUyeOl = findViewById(R.id.uyeol_btnKayitOl);
        btnGirisYap = findViewById(R.id.uyeol_btnGirisYap);
        db = new HocaDBHelper(this);

        btnUyeOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = editTextKullanici.getText().toString();
                String parola = editTextParola.getText().toString();
                String parolaDogrula = editTextParolaDogrula.getText().toString();
                String ad = editTextAd.getText().toString();
                String soyad = editTextSoyad.getText().toString();
                String puk = editTextPuk.getText().toString();
                parolaSize = editTextParola.length();

                try {
                    if (mail.equals("") || parola.equals("") || parolaDogrula.equals("") || ad.equals("") || soyad.equals("") || puk.equals("")) {
                        Toast.makeText(UyeOl.this, "Lütfen bütün alanları doldurun.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (parolaSize < 7) {
                            Toast.makeText(UyeOl.this, "Parola alanı 8 karakterden az olamaz.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (mail.contains("@") && mail.endsWith(".com")) {
                                if (parola.equals(parolaDogrula)) {
                                    Boolean checkMail = db.checkMail(mail);
                                    if (checkMail == false) {
                                        Boolean insert = db.insertData(mail, parola, ad, soyad, puk, 1);
                                        if (insert == true) {
                                            Toast.makeText(UyeOl.this, "Kayıt olma işlemi başarılı.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UyeOl.this, HomeActivity.class);
                                            finish();
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(UyeOl.this, "Kayıt olma işlemi başarısız.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(UyeOl.this, "Bu email adresi ile zaten bir kullanıcı mevcut! Lütfen giriş yapın.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(UyeOl.this, "Parolalar eşleşmiyor!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UyeOl.this, "Lütfen email adresinizi doğru formatta giriniz!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

    }

    public void uyeOl_girisOnClick(View v) {

        Intent backIntent = new Intent(UyeOl.this, MainActivity.class);
        finish();
        startActivity(backIntent);

    }
}