package com.faruk.graphproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class ProgramDuzenleActivity extends AppCompatActivity {
    private EditText editTxtHocaIsmi;
    private Spinner spinnerDers, spinnerGun, spinnerSaat;
    private Button btnKaydet, btnGeri;
    private String secilenDers = "", secilenGun = "", secilenSaat = "", secilenSinif = "";
    private ArrayList dersList = new ArrayList<>(), gunList = new ArrayList<>(), saatList = new ArrayList<>();
    private HocaDBHelper hocaDb;
    private SQLiteDatabase db;

    public void init() {
        editTxtHocaIsmi = findViewById(R.id.program_duzenle_activity_editTextHoca);
        spinnerDers = findViewById(R.id.program_duzenle_activity_editTextDersSpinner);
        spinnerGun = findViewById(R.id.program_duzenle_activity_editTextDersGunSpinner);
        spinnerSaat = findViewById(R.id.program_duzenle_activity_editTextDersSaatSpinner);
        btnKaydet = findViewById(R.id.program_duzenle_activity_btnKaydet);
        btnGeri = findViewById(R.id.program_duzenle_activity_btnKaydetgeri);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_duzenle);
        gunTablosuOlustur();
        saatTablosuOlustur();
        SQLiteDatabase dbProgram;
        dbProgram = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        dbProgram.execSQL("CREATE TABLE IF NOT EXISTS DersProgrami (program_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " gun_id INTEGER, hoca_id INTEGER, ders_id INTEGER, sinif_id INTEGER, saat_id INTEGER," +
                " FOREIGN KEY (gun_id) REFERENCES Gunler (gun_id)," +
                " FOREIGN KEY (hoca_id) REFERENCES Hocalar (hoca_id)," +
                " FOREIGN KEY (ders_id) REFERENCES Dersler (ders_id)," +
                " FOREIGN KEY (sinif_id) REFERENCES Siniflar (sinif_id)," +
                " FOREIGN KEY (saat_id) REFERENCES Saatler (saat_id))");
        init();
        getDersToSpinner();
        getGunToSpinner();
        getSaatToSpinner();
        hocaDb = new HocaDBHelper(this);
        getHocaIsimSoyisim();
    }

    public void getHocaIsimSoyisim() {
        String ad = hocaDb.getDataFromDatabase("ad");
        String soyad = hocaDb.getDataFromDatabase("soyad");
        String birlesmis = ad + " " + soyad;
        editTxtHocaIsmi.setText(birlesmis);
    }

    public void dersAtaOnClick(View v) {
        try {

            secilenDers = spinnerDers.getSelectedItem().toString();
            secilenGun = spinnerGun.getSelectedItem().toString();
            secilenSaat = spinnerSaat.getSelectedItem().toString();

            int ders_id = 0;
            int sinif_id = 0;
            int gun_id = 0;
            int saat_id = 0;
            int hoca_id = 0;
            //dersi ve sinifi spinner'dan aliyoruz
            Cursor cursorDers = db.rawQuery("SELECT * FROM Dersler", null);
            int dersIsmiIdIndex = cursorDers.getColumnIndex("ders_adi");
            while (cursorDers.moveToNext()) {
                if (cursorDers.getString(dersIsmiIdIndex).equals(secilenDers)) {
                    int dersIdIndex = cursorDers.getColumnIndex("ders_id");
                    int sinifIdIndex = cursorDers.getColumnIndex("sinif_id");
                    ders_id = cursorDers.getInt(dersIdIndex);
                    sinif_id = cursorDers.getInt(sinifIdIndex);
                }
            }

            //gunu spinner'dan aliyoruz
            Cursor cursorGun = db.rawQuery("SELECT * FROM Gunler", null);
            int gunIsmiIdIndex = cursorGun.getColumnIndex("gun_adi");
            while (cursorGun.moveToNext()) {
                if (cursorGun.getString(gunIsmiIdIndex).equals(secilenGun)) {
                    int gunIdIndex = cursorGun.getColumnIndex("gun_id");
                    gun_id = cursorGun.getInt(gunIdIndex);
                }
            }

            //saati spinner'dan aliyoruz
            Cursor cursorSaat = db.rawQuery("SELECT * FROM Saatler", null);
            int saatIsmiIdIndex = cursorSaat.getColumnIndex("saat");
            while (cursorSaat.moveToNext()) {
                if (cursorSaat.getString(saatIsmiIdIndex).equals(secilenSaat)) {
                    int saatIdIndex = cursorSaat.getColumnIndex("saat_id");
                    saat_id = cursorSaat.getInt(saatIdIndex);
                }
            }

            //hocayi cekiyoruz
            Cursor cursorHoca = db.rawQuery("SELECT * FROM Hocalar", null);
            int hocaOturumIndex = cursorHoca.getColumnIndex("oturum");
            while (cursorHoca.moveToNext()) {
                if (cursorHoca.getInt(hocaOturumIndex) == 1) {
                    int hocaIdIndex = cursorHoca.getColumnIndex("hoca_id");
                    hoca_id = cursorHoca.getInt(hocaIdIndex);
                }
            }

            cursorDers.close();
            cursorGun.close();
            cursorSaat.close();
            cursorHoca.close();
            if (!checkGraphRowIfExists(gun_id, hoca_id, sinif_id, saat_id)) {
                String sqlQuery = "INSERT INTO DersProgrami (gun_id, hoca_id, ders_id, sinif_id, saat_id) VALUES (?, ?, ?, ?, ?)";
                SQLiteStatement statement = db.compileStatement(sqlQuery);
                statement.bindLong(1, gun_id);
                statement.bindLong(2, hoca_id);
                statement.bindLong(3, ders_id);
                statement.bindLong(4, sinif_id);
                statement.bindLong(5, saat_id);
                statement.execute();
                showToast("Program ilgili saat ve güne başarıyla atandı.");
                graphOlustur(gun_id, hoca_id, ders_id, sinif_id, saat_id);
            } else {
                showToast("Hata! Veri kaydedilemez.");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e + " program atama gecersiz.");
        }
    }


    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void checkData() {
        SQLiteDatabase db = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM DersProgrami", null);
        int programIdIndex = cursor.getColumnIndex("program_id");
        int gunIdIndex = cursor.getColumnIndex("gun_id");
        int hocaIdIndex = cursor.getColumnIndex("hoca_id");
        int dersIdIndex = cursor.getColumnIndex("ders_id");
        int sinifIdIndex = cursor.getColumnIndex("sinif_id");
        int saatIdIndex = cursor.getColumnIndex("saat_id");
        while (cursor.moveToNext()) {
            System.out.println(cursor.getInt(programIdIndex) + " Hoca: " + cursor.getInt(hocaIdIndex) + " Gün: " + cursor.getInt(gunIdIndex) + " Ders: " + cursor.getInt(dersIdIndex) + " Sınıf: " + cursor.getInt(sinifIdIndex) + " Saat: " + cursor.getInt(saatIdIndex));
        }
        cursor.close();
    }

    public void programEkleActivityGetDataOnClick(View v) {
        checkData();
    }

    public void dersProgramTabloDropOnClick(View v) {
        db.execSQL("DROP TABLE DersProgrami");
    }

    public void programDuzenleActivityGeriOnClick(View v) {
        Intent intent = new Intent(ProgramDuzenleActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProgramDuzenleActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);
    }

    public void getDersToSpinner() {
        db = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM Dersler", null);
        int dersAdiIndex = cursor.getColumnIndex("ders_adi");
        cursor.moveToFirst();
        if (cursor.getCount() != 1) {
            do {
                dersList.add(cursor.getString(dersAdiIndex));
                //System.out.println(dersAdiIndex);
            } while (cursor.moveToNext());
        } else if (cursor.getCount() == 1) {
            dersList.add(cursor.getString(dersAdiIndex));
        }
        @SuppressLint("ResourceType") ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProgramDuzenleActivity.this,
                R.drawable.spinner_item, dersList);
        spinnerDers.setAdapter(adapter);
        cursor.close();
    }

    public void getGunToSpinner() {
        db = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM Gunler", null);
        int gunAdiIndex = cursor.getColumnIndex("gun_adi");
        cursor.moveToFirst();
        do {
            gunList.add(cursor.getString(gunAdiIndex));
        } while (cursor.moveToNext());
        @SuppressLint("ResourceType") ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProgramDuzenleActivity.this,
                R.drawable.spinner_item, gunList);
        spinnerGun.setAdapter(adapter);
        cursor.close();
    }

    public void getSaatToSpinner() {
        db = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM Saatler", null);
        int saatIndex = cursor.getColumnIndex("saat");
        cursor.moveToFirst();
        do {
            saatList.add(cursor.getString(saatIndex));
        } while (cursor.moveToNext());
        @SuppressLint("ResourceType") ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProgramDuzenleActivity.this,
                R.drawable.spinner_item, saatList);
        spinnerSaat.setAdapter(adapter);
        cursor.close();
    }

    public void saatTablosuOlustur() {
        hocaDb = new HocaDBHelper(this);
        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Saatler (saat_id INTEGER PRIMARY KEY AUTOINCREMENT, saat TEXT)");
        Cursor cursor = hocaDb.getReadableDatabase().rawQuery("SELECT * FROM Saatler", null);
        do {
            if (cursor.getCount() == 0) {
                String sqlQuery = "INSERT INTO Saatler (saat) VALUES (?)";
                SQLiteStatement statement9 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statement10 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statement11 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statement13 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statement14 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statement15 = sqLiteDatabase.compileStatement(sqlQuery);
                statement9.bindString(1, "09:00-10:00");
                statement10.bindString(1, "10:00-11:00");
                statement11.bindString(1, "11:00-12:00");
                statement13.bindString(1, "13:00-14:00");
                statement14.bindString(1, "14:00-15:00");
                statement15.bindString(1, "15:00-16:00");
                statement9.execute();
                statement10.execute();
                statement11.execute();
                statement13.execute();
                statement14.execute();
                statement15.execute();
            }
        } while (cursor.moveToNext());
    }

    public void gunTablosuOlustur() {
        hocaDb = new HocaDBHelper(this);
        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Gunler (gun_id INTEGER PRIMARY KEY AUTOINCREMENT, gun_adi TEXT)");
        Cursor cursor = hocaDb.getReadableDatabase().rawQuery("SELECT * FROM Gunler", null);
        do {
            if (cursor.getCount() == 0) {
                String sqlQuery = "INSERT INTO Gunler (gun_adi) VALUES (?)";
                SQLiteStatement statementGun1 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statementGun2 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statementGun3 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statementGun4 = sqLiteDatabase.compileStatement(sqlQuery);
                SQLiteStatement statementGun5 = sqLiteDatabase.compileStatement(sqlQuery);
                statementGun1.bindString(1, "Pazartesi");
                statementGun2.bindString(1, "Salı");
                statementGun3.bindString(1, "Çarşamba");
                statementGun4.bindString(1, "Perşembe");
                statementGun5.bindString(1, "Cuma");
                statementGun1.execute();
                statementGun2.execute();
                statementGun3.execute();
                statementGun4.execute();
                statementGun5.execute();
            }
        } while (cursor.moveToNext());
    }

    public void graphOlustur(int gunId, int hocaId, int dersId, int sinifId, int saatId) {
        // Graf oluştur
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        // Düğümleri ve kenarları ekle


        Object gun = "gun", hoca = "hoca", ders = "ders", sinif = "sinif", saat = "saat";
        // Düğümleri ekle
        graph.addVertex(ders.toString());
        graph.addVertex(hoca.toString());
        graph.addVertex(sinif.toString());
        graph.addVertex(gun.toString());
        graph.addVertex(saat.toString());

        // Kenarları ekle
        graph.addEdge(ders.toString(), hoca.toString());
        graph.addEdge(ders.toString(), sinif.toString());
        graph.addEdge(ders.toString(), gun.toString());
        graph.addEdge(hoca.toString(), sinif.toString());
        graph.addEdge(hoca.toString(), saat.toString());
        graph.addEdge(gun.toString(), saat.toString());

        // Grafı yazdır
        System.out.println("Graph vertices: " + graph.vertexSet());
        System.out.println("Graph edges: " + graph.edgeSet());

        // Greedy Coloring uygula
        GreedyColoring<String, DefaultEdge> coloring = new GreedyColoring<>(graph);
        Map<String, Integer> colorMap = coloring.getColoring().getColors();

        // Renklendirilmiş düğümleri yazdır
        for (String vertex : graph.vertexSet()) {
            System.out.println("Vertex " + vertex + " has color " + colorMap.get(vertex));
        }

    }

    public Boolean checkGraphRowIfExists(int gun_id, int hoca_id, int sinif_id, int saat_id) {
        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM DersProgrami", null);
        // SQLite veritabanına bağlan
        try {

            cursor = db.rawQuery("SELECT * FROM DersProgrami WHERE (gun_id = ? AND saat_id = ? AND sinif_id = ?) OR (hoca_id = ? AND gun_id = ? AND saat_id = ?)", new String[]{String.valueOf(gun_id), String.valueOf(saat_id), String.valueOf(sinif_id), String.valueOf(hoca_id), String.valueOf(gun_id), String.valueOf(saat_id)});
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}