package com.faruk.graphproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ProgramGosterActivity extends AppCompatActivity {

    private EditText editText;
    private LinearLayout.LayoutParams parentLayoutParams, layoutParams1, layoutParams2, layoutParams3, layoutParams4, layoutParams5; //LinearLayout uzerinde palet ayari yapacagimiz icin
    private LinearLayout parentLinearLayout, childLinearLayout1, childLinearLayout2, childLinearLayout3, childLinearLayout4, childLinearLayout5;
    private ScrollView scroll;


    private void init() {

        SQLiteDatabase db = this.openOrCreateDatabase("GraphProject", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT *, (SELECT gun_adi from Gunler where gun_id = DersProgrami.gun_id) as gun_adi," +
                " (select ad from Hocalar where hoca_id = DersProgrami.hoca_id) as hoca_ad," +
                " (select soyad from Hocalar where hoca_id = DersProgrami.hoca_id) as hoca_soyad," +
                " (select ders_adi from Dersler where ders_id = DersProgrami.ders_id) as ders_adi," +
                " (select sinif_adi from Siniflar where sinif_id = DersProgrami.sinif_id) as sinif_adi," +
                " (select saat from Saatler where saat_id = DersProgrami.saat_id) as saat from DersProgrami", null);
        int gunAdiIndex = cursor.getColumnIndex("gun_adi");
        int hocaAdIndex = cursor.getColumnIndex("hoca_ad");
        int hocaSoyadIndex = cursor.getColumnIndex("hoca_soyad");
        int dersAdiIndex = cursor.getColumnIndex("ders_adi");
        int sinifAdiIndex = cursor.getColumnIndex("sinif_adi");
        int saatIndex = cursor.getColumnIndex("saat");



        //ScrollView ayari
        scroll = new ScrollView(this);
        scroll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        scroll.setBackgroundColor(getResources().getColor(R.color.bg));

        //Parent LinearLayout
        parentLinearLayout = new LinearLayout(this);
        parentLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        parentLinearLayout.setLayoutParams(parentLayoutParams);
        parentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Ilk LinearLayout
        childLinearLayout1 = new LinearLayout(this);
        layoutParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        childLinearLayout1.setLayoutParams(layoutParams1);
        childLinearLayout1.setOrientation(LinearLayout.VERTICAL);

        editText = new EditText(this);
        editText.setText("Ders Adı");
        editText.setEnabled(false);
        editText.setSingleLine(false);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setTextSize(13);
        editText.setBackgroundColor(getColor(R.color.bgg));
        layoutParams1 = new LinearLayout.LayoutParams(300, 150);
        layoutParams1.topMargin = 15;
        layoutParams1.leftMargin = 10;
        editText.setLayoutParams(layoutParams1);
        childLinearLayout1.addView(editText);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            editText = new EditText(this);
            editText.setText(cursor.getString(dersAdiIndex));
            editText.setEnabled(false);
            editText.setTextColor(getResources().getColor(R.color.white));
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            editText.setTextSize(13);
            layoutParams1 = new LinearLayout.LayoutParams(300, 150);
            layoutParams1.topMargin = 15;
            layoutParams1.leftMargin = 10;
            editText.setLayoutParams(layoutParams1); //boylece bu buton params linearlayout ozelliklerini almis olacak
            cursor.moveToNext();
            childLinearLayout1.addView(editText); //ilgili nesneyi ilgili layout'a eklemek icin bu kodu yazmamiz ve ardindan contentView'e de yazmamiz sart
        }

        // Ikinci LinearLayout
        childLinearLayout2 = new LinearLayout(this);
        layoutParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        childLinearLayout2.setLayoutParams(layoutParams2);
        childLinearLayout2.setOrientation(LinearLayout.VERTICAL);

        editText = new EditText(this);
        editText.setText("Hoca Adı");
        editText.setEnabled(false);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setTextSize(13);
        editText.setBackgroundColor(getColor(R.color.bgg));
        layoutParams2 = new LinearLayout.LayoutParams(250, 150);
        layoutParams2.topMargin = 15;
        layoutParams2.leftMargin = 15;
        editText.setLayoutParams(layoutParams2);
        childLinearLayout2.addView(editText);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            editText = new EditText(this);
            editText.setEnabled(false);
            editText.setText(cursor.getString(hocaAdIndex) + cursor.getString(hocaSoyadIndex));
            editText.setTextColor(getResources().getColor(R.color.white));
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            editText.setTextSize(13);
            layoutParams2 = new LinearLayout.LayoutParams(250, 150);
            layoutParams2.topMargin = 15;
            layoutParams2.leftMargin = 15;
            editText.setLayoutParams(layoutParams2); //boylece bu buton params linearlayout ozelliklerini almis olacak
            cursor.moveToNext();
            childLinearLayout2.addView(editText); //ilgili nesneyi ilgili layout'a eklemek icin bu kodu yazmamiz ve ardindan contentView'e de yazmamiz sart
        }

        // Ucuncu LinearLayout
        childLinearLayout3 = new LinearLayout(this);
        layoutParams3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        childLinearLayout3.setLayoutParams(layoutParams3);
        childLinearLayout3.setOrientation(LinearLayout.VERTICAL);

        editText = new EditText(this);
        editText.setText("Sınıf");
        editText.setEnabled(false);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setTextSize(13);
        editText.setBackgroundColor(getColor(R.color.bgg));
        layoutParams3 = new LinearLayout.LayoutParams(100, 150);
        layoutParams3.topMargin = 15;
        layoutParams3.leftMargin = 10;
        editText.setLayoutParams(layoutParams3);
        childLinearLayout3.addView(editText);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            editText = new EditText(this);
            editText.setEnabled(false);
            editText.setText(cursor.getString(sinifAdiIndex));
            editText.setTextColor(getResources().getColor(R.color.white));
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            editText.setTextSize(13);
            layoutParams3 = new LinearLayout.LayoutParams(100, 150);
            layoutParams3.topMargin = 15;
            layoutParams3.leftMargin = 20;
            editText.setLayoutParams(layoutParams3); //boylece bu buton params linearlayout ozelliklerini almis olacak
            cursor.moveToNext();
            childLinearLayout3.addView(editText); //ilgili nesneyi ilgili layout'a eklemek icin bu kodu yazmamiz ve ardindan contentView'e de yazmamiz sart
        }


        // Dorduncu LinearLayout
        childLinearLayout4 = new LinearLayout(this);
        layoutParams4 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        childLinearLayout4.setLayoutParams(layoutParams4);
        childLinearLayout4.setOrientation(LinearLayout.VERTICAL);

        editText = new EditText(this);
        editText.setText("Gün");
        editText.setEnabled(false);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setTextSize(13);
        editText.setBackgroundColor(getColor(R.color.bgg));
        layoutParams4 = new LinearLayout.LayoutParams(180, 150);
        layoutParams4.topMargin = 15;
        layoutParams4.leftMargin = 10;
        editText.setLayoutParams(layoutParams4);
        childLinearLayout4.addView(editText);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            editText = new EditText(this);
            editText.setEnabled(false);
            editText.setText(cursor.getString(gunAdiIndex));
            editText.setTextColor(getResources().getColor(R.color.white));
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            editText.setTextSize(13);
            layoutParams4 = new LinearLayout.LayoutParams(150, 150);
            layoutParams4.topMargin = 15;
            layoutParams4.leftMargin = 25;
            editText.setLayoutParams(layoutParams4);
            cursor.moveToNext();
            childLinearLayout4.addView(editText);
        }

        // Besinci LinearLayout
        childLinearLayout5 = new LinearLayout(this);
        layoutParams5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        childLinearLayout5.setLayoutParams(layoutParams5);
        childLinearLayout5.setOrientation(LinearLayout.VERTICAL);

        editText = new EditText(this);
        editText.setEnabled(false);
        editText.setText("Saat");
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setTextSize(13);
        editText.setBackgroundColor(getColor(R.color.bgg));
        layoutParams5 = new LinearLayout.LayoutParams(180, 150);
        layoutParams5.topMargin = 15;
        layoutParams5.leftMargin = 10;
        layoutParams5.rightMargin = 10;
        editText.setLayoutParams(layoutParams5);
        childLinearLayout5.addView(editText);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            editText = new EditText(this);
            editText.setEnabled(false);
            editText.setText(cursor.getString(saatIndex));
            editText.setTextColor(getResources().getColor(R.color.white));
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            editText.setTextSize(13);
            layoutParams5 = new LinearLayout.LayoutParams(200, 150);
            layoutParams5.topMargin = 15;
            editText.setLayoutParams(layoutParams5);
            cursor.moveToNext();
            childLinearLayout5.addView(editText);
        }
        cursor.close();

        scroll.addView(parentLinearLayout);

        // İlk LinearLayout'u parent'a ekleme
        parentLinearLayout.addView(childLinearLayout1);

        // İkinci LinearLayout'u parent'a ekleme
        parentLinearLayout.addView(childLinearLayout2);
        parentLinearLayout.addView(childLinearLayout3);
        parentLinearLayout.addView(childLinearLayout4);
        parentLinearLayout.addView(childLinearLayout5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        //setContentView(R.layout.activity_main);
        setContentView(scroll); //olusturdugumuz layout'u burada setContentView'e gondererek kodumuza bagladik
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProgramGosterActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);
    }
}