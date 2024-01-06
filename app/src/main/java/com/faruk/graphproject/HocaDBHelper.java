package com.faruk.graphproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.fragment.app.Fragment;

public class HocaDBHelper extends SQLiteOpenHelper {

    public static final String DBName = "GraphProject";

    public HocaDBHelper(Context context) {
        super(context, DBName, null, 1);
        SQLiteDatabase myDb;
        myDb = context.openOrCreateDatabase(DBName, MODE_PRIVATE, null);
        myDb.execSQL("CREATE TABLE IF NOT EXISTS Hocalar (hoca_id INTEGER PRIMARY KEY AUTOINCREMENT, mail TEXT, parola TEXT,  ad TEXT, soyad TEXT, puk TEXT, oturum INT)");
    }

    public HocaDBHelper(Fragment fragment) {
        super(fragment.getContext(), DBName, null, 1);
        SQLiteDatabase myDb;
        myDb = fragment.getContext().openOrCreateDatabase(DBName, MODE_PRIVATE, null);
    }


    @Override
    public void onCreate(SQLiteDatabase myDb) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase myDb, int oldVersion, int newVersion) {
        myDb.execSQL("DROP TABLE IF EXISTS Hocalar");
    }

    public Boolean insertData(String mail, String parola, String ad, String soyad, String puk, int oturum) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mail", mail);
        contentValues.put("parola", parola);
        contentValues.put("ad", ad);
        contentValues.put("soyad", soyad);
        contentValues.put("puk", puk);
        contentValues.put("oturum", oturum);
        long result = myDB.insert("Hocalar", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    public void getAllHocalar() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Hocalar", null);
        int hocaIdIndex = cursor.getColumnIndex("hoca_id");
        int mailIndex = cursor.getColumnIndex("mail");
        int parolaIndex = cursor.getColumnIndex("parola");
        int adIndex = cursor.getColumnIndex("ad");
        int soyadIndex = cursor.getColumnIndex("soyad");
        int pukIndex =  cursor.getColumnIndex("puk");
        while (cursor.moveToNext()) {
            System.out.println(cursor.getInt(hocaIdIndex) + " Mail: " + cursor.getString(mailIndex) + " Parola: " + cursor.getString(parolaIndex) + " Ad: " + cursor.getString(adIndex) + " Soyad: " + cursor.getString(soyadIndex) + " Puk: " + cursor.getString(pukIndex));
        }
        cursor.close();
    }

    public String getDataFromDatabase(String sutun) {
        SQLiteDatabase db = this.getReadableDatabase();
        String result = "";
        int arananIndex;

        Cursor cursor = db.rawQuery("SELECT * FROM Hocalar", null);
        // Veritabanı sorgusu
        if (sutun.equals("mail")) {
            while (cursor.moveToNext()) {
                arananIndex = cursor.getColumnIndex("mail");
                int oturumIndex = cursor.getColumnIndex("oturum");
                if (cursor.getInt(oturumIndex) == 1) {
                    result = cursor.getString(arananIndex);
                    return result;
                }
            }
        } else if (sutun.equals("parola")) {
            while (cursor.moveToNext()) {
                arananIndex = cursor.getColumnIndex("parola");
                int oturumIndex = cursor.getColumnIndex("oturum");
                if (cursor.getInt(oturumIndex) == 1) {
                    result = cursor.getString(arananIndex);
                    return result;
                }
            }
        } else if (sutun.equals("ad")) {
            while (cursor.moveToNext()) {
                arananIndex = cursor.getColumnIndex("ad");
                int oturumIndex = cursor.getColumnIndex("oturum");
                if (cursor.getInt(oturumIndex) == 1) {
                    result = cursor.getString(arananIndex);
                    return result;
                }
            }
        } else if (sutun.equals("soyad")) {
            while (cursor.moveToNext()) {
                arananIndex = cursor.getColumnIndex("soyad");
                int oturumIndex = cursor.getColumnIndex("oturum");
                if (cursor.getInt(oturumIndex) == 1) {
                    result = cursor.getString(arananIndex);
                    return result;
                }
            }
        } else if (sutun.equals("puk")) {
            while (cursor.moveToNext()) {
                arananIndex = cursor.getColumnIndex("puk");
                int oturumIndex = cursor.getColumnIndex("oturum");
                if (cursor.getInt(oturumIndex) == 1) {
                    result = cursor.getString(arananIndex);
                    return result;
                }
            }
        }
        cursor.close();
        db.close();
        return result;
    }

    public void updateUser(String sutun, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (sutun.equals("parola")) {
            db.execSQL("UPDATE Hocalar SET parola = ? WHERE oturum = 1", new String[]{data});
        } else if (sutun.equals("ad")) {
            db.execSQL("UPDATE Hocalar SET ad = ? WHERE oturum = 1", new String[]{data});
        } else if (sutun.equals("soyad")) {
            db.execSQL("UPDATE Hocalar SET soyad = ? WHERE oturum = 1", new String[]{data});
        } else if (sutun.equals("puk")) {
            db.execSQL("UPDATE Hocalar SET puk = ? WHERE oturum = 1", new String[]{data});
        }
    }

    public void updatePassword(String puk, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Hocalar SET parola = ? WHERE puk = ?", new String[]{newPassword, puk});
    }

    public Boolean checkPuk(String puk) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM Hocalar WHERE puk = ?", new String[]{puk});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkMail(String mail) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM Hocalar WHERE mail = ?", new String[]{mail});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkMailParola(String mail, String parola) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM Hocalar WHERE mail = ? AND parola = ?", new String[]{mail, parola});
        if (cursor.getCount() > 0) {
            MyDB.execSQL("UPDATE Hocalar SET oturum = 1 WHERE mail = ?", new String[]{mail});
            return true;
        } else
            return false;
    }
}
