package com.faruk.graphproject;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DersEkleActivity extends AppCompatActivity {
    private EditText editTxtDersIsmi, editTxtHoca, editTxtKredi;
    private RadioButton radioBtn1, radioBtn2, radioBtn3, radioBtn4;
    private Button btnKaydet;
    private String dersAdi, kredi;
    private HocaDBHelper hocaDb;

    public void init() {
        editTxtDersIsmi = findViewById(R.id.ders_ekle_activity_editTextDersIsmi);
        editTxtHoca = findViewById(R.id.ders_ekle_activity_editTextHoca);
        editTxtKredi = findViewById(R.id.ders_ekle_activity_editTextDersKredi);
        radioBtn1 = findViewById(R.id.ders_ekle_activity_radioBtnBir);
        radioBtn2 = findViewById(R.id.ders_ekle_activity_radioBtnIki);
        radioBtn3 = findViewById(R.id.ders_ekle_activity_radioBtnUc);
        radioBtn4 = findViewById(R.id.ders_ekle_activity_radioBtnDort);
        btnKaydet = findViewById(R.id.ders_ekle_activity_btnKaydet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ders_ekle);
        SQLiteDatabase db = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS Dersler (ders_id INTEGER PRIMARY KEY AUTOINCREMENT, ders_adi TEXT, kredi TEXT, hoca_id INTEGER, sinif_id INTEGER, FOREIGN KEY (hoca_id) REFERENCES Hocalar (hoca_id), FOREIGN KEY (sinif_id) REFERENCES Siniflar (sinif_id))");
        hocaDb = new HocaDBHelper(this);
        init();
        String dersAdi = editTxtDersIsmi.getText().toString();
        String kredi = editTxtKredi.getText().toString();
        getHocaIsimSoyisim();
        if (!TextUtils.isEmpty(dersAdi) && !TextUtils.isEmpty(kredi)) {
            btnKaydet.setEnabled(true);
        }
    }

    public void showToast(String mesaj) {
        Toast.makeText(this, mesaj, Toast.LENGTH_SHORT).show();
    }

    public void dersKaydetOnClick(View v) {
        dersAdi = editTxtDersIsmi.getText().toString();
        kredi = editTxtKredi.getText().toString();
        init();
        if (!TextUtils.isEmpty(dersAdi)) {
            if (!TextUtils.isEmpty(kredi)) {
                if (!radioBtn1.isChecked() && !radioBtn2.isChecked() && !radioBtn3.isChecked() && !radioBtn4.isChecked()) {
                    showToast("Dersi atayabilmek için sınıflardan birini seçmelisiniz!");
                } else {
                    try {
                        int sinifId = 0;
                        int hocaId = 0;
                        if (radioBtn1.isChecked()) {
                            sinifId = 1;
                        } else if (radioBtn2.isChecked()) {
                            sinifId = 2;
                        } else if (radioBtn3.isChecked()) {
                            sinifId = 3;
                        } else if (radioBtn4.isChecked()) {
                            sinifId = 4;
                        }

                        SQLiteDatabase db = openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
                        Cursor cursor = db.rawQuery("SELECT * FROM Hocalar", null);
                        int oturumIndex = cursor.getColumnIndex("oturum");
                        int hocaIdIndex = cursor.getColumnIndex("hoca_id");
                        while (cursor.moveToNext()) {
                            if (cursor.getInt(oturumIndex) == 1) {
                                hocaId = cursor.getInt(hocaIdIndex);
                            }
                        }
                        cursor.close();

                        String sqlQuery = "INSERT INTO Dersler (ders_adi, kredi, hoca_id, sinif_id) VALUES (?, ?, ?, ?)";
                        SQLiteStatement statement = db.compileStatement(sqlQuery);
                        statement.bindString(1, dersAdi);
                        statement.bindString(2, kredi);
                        statement.bindLong(3, hocaId);
                        statement.bindLong(4, sinifId);
                        statement.execute();
                        temizle();
                        showToast("Ders başarıyla eklendi.");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } else {
                showToast("Kredi boş bırakılamaz!");
            }
        } else {
            showToast("Ders ismi boş bırakılamaz!");
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DersEkleActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);
    }

    public void dersEkleActivityGeriOnClick(View v) {
        Intent intent = new Intent(DersEkleActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);
    }

    public void dersEkleGetDataOnClick(View v) {
        getDataDersler();
        getDataSiniflar();
        getDataHocalar();
    }

    public void getDataDersler() {
        SQLiteDatabase db = openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM Dersler", null);
        int dersIdIndex = cursor.getColumnIndex("ders_id");
        int dersAdiIndex = cursor.getColumnIndex("ders_adi");
        int krediIndex = cursor.getColumnIndex("kredi");
        int hocaIdIndex = cursor.getColumnIndex("hoca_id");
        int sinifIdIndex = cursor.getColumnIndex("sinif_id");
        while (cursor.moveToNext()) {
            System.out.println("Dersler:\n" + cursor.getInt(dersIdIndex) + " Ders adı: " + cursor.getString(dersAdiIndex) + " Kredi: " + cursor.getString(krediIndex) + " Hoca id: " + cursor.getInt(hocaIdIndex) + " Sınıf id: " + cursor.getInt(sinifIdIndex));
        }
        cursor.close();
        dersAdi = editTxtDersIsmi.getText().toString();
        kredi = editTxtKredi.getText().toString();
        System.out.println(dersAdi + " " + kredi);
    }

    public void getDataSiniflar() {
        SQLiteDatabase db = openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM Siniflar", null);
        int sinifIdIndex = cursor.getColumnIndex("sinif_id");
        int sinifAdiIndex = cursor.getColumnIndex("sinif_adi");
        while (cursor.moveToNext()) {
            System.out.println("Sınıflar:\n" + cursor.getInt(sinifIdIndex) + " Sınıf adı: " + cursor.getString(sinifAdiIndex));
        }
    }

    public void getDataHocalar() {
        hocaDb.getAllHocalar();
    }

    public void getHocaIsimSoyisim() {
        String ad = hocaDb.getDataFromDatabase("ad");
        String soyad = hocaDb.getDataFromDatabase("soyad");
        String birlesmis = ad + " " + soyad;
        editTxtHoca.setText(birlesmis);
    }

    public void temizle() {
        editTxtDersIsmi.setText("");
        editTxtKredi.setText("");
        radioBtn1.setChecked(false);
        radioBtn2.setChecked(false);
        radioBtn3.setChecked(false);
        radioBtn4.setChecked(false);
        btnKaydet.setEnabled(false);
    }


}