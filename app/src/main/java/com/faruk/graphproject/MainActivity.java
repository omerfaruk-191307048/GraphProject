package com.faruk.graphproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtViewYanlisGiris;
    Button btnGiris, btnUyeOl, btnForgotPass;
    EditText editTextMail, editTextParola;
    HocaDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GreedyColoringExample greedyColoringExample = new GreedyColoringExample();
        greedyColoringExample.main();
        editTextMail = findViewById(R.id.main_activity_editTxtMail);
        editTextParola = findViewById(R.id.main_activity_editTxtParola);
        txtViewYanlisGiris = findViewById(R.id.main_activity_yanlisGirisUyari);
        btnGiris = findViewById(R.id.main_activity_btnGiris);
        btnUyeOl = findViewById(R.id.main_activity_btnUyeOl);
        btnForgotPass = findViewById(R.id.main_activity_btnForgotPassword);
        db = new HocaDBHelper(this);

        db.getWritableDatabase().execSQL("UPDATE Hocalar SET oturum = 0 WHERE oturum = 1");
    }

    public void checkOnClick(View v) {
        getData();
    }

    public void tabloSilOnClick(View v) {
        tabloyuSil();
    }

    public void forgotPasswordGirisOnClick(View v) {
        Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
        finish();
        startActivity(intent);
    }

    /*
    public void destroyHoca() {
        SQLiteDatabase db = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS Hocalar");
    }*/

    public void girisOnClick(View v) {
        String mail = editTextMail.getText().toString();
        String parola = editTextParola.getText().toString();
        if (mail.equals("") || parola.equals("")) {
            //Toast.makeText(this, "Lütfen bütün alanları doldurun.", Toast.LENGTH_SHORT).show();
            txtViewYanlisGiris.setText("Lütfen bütün alanları doldurun.");
        }
        else {
            Boolean checkMailParola = db.checkMailParola(mail, parola);
            if (checkMailParola == true) {
                Toast.makeText(this, "Giriş işlemi başarılı.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                //loggedIn(kullanici);
                finish();
                startActivity(intent);
            } else {
                //Toast.makeText(this, "Kimlik bilgileri hatalı.", Toast.LENGTH_SHORT).show();
                txtViewYanlisGiris.setText("Kimlik bilgileri hatalı.");
            }
        }
    }



    private void tabloyuSil() {
        db.getWritableDatabase().execSQL("DROP TABLE Hocalar");
    }

    private void getData() {
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM Hocalar", null);
        int hocaIdIndex = cursor.getColumnIndex("hoca_id");
        int mailIndex = cursor.getColumnIndex("mail");
        int parolaIndex = cursor.getColumnIndex("parola");
        int adIndex = cursor.getColumnIndex("ad");
        int soyadIndex = cursor.getColumnIndex("soyad");
        int pukIndex = cursor.getColumnIndex("puk");
        int oturumIndex = cursor.getColumnIndex("oturum");

        while (cursor.moveToNext()) {
            if (!cursor.isNull(mailIndex)) {
                System.out.println(cursor.getInt(hocaIdIndex) +" Mail: " + cursor.getString(mailIndex) + " Parola: " + cursor.getString(parolaIndex) + " Ad: " + cursor.getString(adIndex) + " Soyad: " + cursor.getString(soyadIndex) + " Puk: " + cursor.getString(pukIndex) + " Oturum: " + cursor.getString(oturumIndex));
            } else {
                System.out.println("Kayıt bulunamadı.");
            }
        }


        cursor.close();
    }



    public void uyeOlOnClick(View v) {
        Intent intent = new Intent(MainActivity.this, UyeOl.class);
        finish();
        startActivity(intent);
    }
}