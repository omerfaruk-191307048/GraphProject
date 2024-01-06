package com.faruk.graphproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Dialog quitDialog;
    private Button profil, dersEKle, programDuzenle, programGoruntule, cikis, btnCancel, btnQuit;
    private HocaDBHelper db;

    private void init() {
        profil = findViewById(R.id.activity_home_btnProfil);
        dersEKle = findViewById(R.id.activity_home_btnDersEkle);
        programDuzenle = findViewById(R.id.activity_home_dersProgramiDuzenle);
        programGoruntule = findViewById(R.id.activity_home_dersProgramiGoruntule);
        cikis = findViewById(R.id.activity_home_btnCikis);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new HocaDBHelper(this);
        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Siniflar (sinif_id INTEGER PRIMARY KEY AUTOINCREMENT, sinif_adi TEXT)");
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM Siniflar", null);
        do {
            if (cursor.getCount() == 0) {
                String sqlQuery = "INSERT INTO Siniflar (sinif_adi) VALUES (?)";
                SQLiteStatement statement1 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statement2 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statement3 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statement4 = sqLiteDatabase.compileStatement(sqlQuery);
                statement1.bindString(1, "1. sınıf");
                statement2.bindString(1, "2. sınıf");
                statement3.bindString(1, "3. sınıf");
                statement4.bindString(1, "4. sınıf");
                statement1.execute();
                statement2.execute();
                statement3.execute();
                statement4.execute();
            }
        } while (cursor.moveToNext());
        init();
    }

    public void silSinifOnClick(View v) {
        SQLiteDatabase database = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        database.execSQL("DROP TABLE Siniflar");
        System.out.println("Tablo başarıyla silindi.");
    }

    public void cikisOnClick(View v) {
        showQuitDialog();
    }

    public void getDataOnClick(View v) {
        getData();
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
                System.out.println(cursor.getInt(hocaIdIndex) + " Mail: " + cursor.getString(mailIndex) + " Parola: " + cursor.getString(parolaIndex) + " Ad: " + cursor.getString(adIndex) + " Soyad: " + cursor.getString(soyadIndex) + " Puk: " + cursor.getString(pukIndex) + " Oturum: " + cursor.getString(oturumIndex));
            } else {
                System.out.println("Kayıt bulunamadı.");
            }
        }

        cursor.close();
    }

    public void activity_home_dersEkleOnClick(View v) {
        Intent intent = new Intent(HomeActivity.this, DersEkleActivity.class);
        finish();
        startActivity(intent);
    }

    public void programGoruntuleOnClick(View v) {
        Intent intent = new Intent(HomeActivity.this, ProgramGosterActivity.class);
        finish();
        startActivity(intent);
    }

    public void programDuzenleOnClick(View v) {
        Intent intent = new Intent(HomeActivity.this, ProgramDuzenleActivity.class);
        finish();
        startActivity(intent);
    }

    public void profilOnClick(View v) {
        Intent intent = new Intent(HomeActivity.this, ProfilActivity.class);
        finish();
        startActivity(intent);
    }

    private void showQuitDialog() {
        quitDialog = new Dialog(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(quitDialog.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        quitDialog.setContentView(R.layout.customdialog_quit);

        quitDialog.setCancelable(false);

        btnCancel = quitDialog.findViewById(R.id.btnCustomdialog_cancel);
        btnQuit = quitDialog.findViewById(R.id.btnCustomdialog_quit);

        quitDialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitDialog.dismiss();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }
}