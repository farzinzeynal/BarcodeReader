package com.example.barcodereader;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button_getbarcode;
    TextView textView_price;
    EditText editText_barCode;
    String result = null;
    TextView textView_setPrice;
    public static SQLite_DataBase sqLite_dataBase;
    ArrayList <ProductModel> myList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        sqLite_dataBase = new SQLite_DataBase(this);

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        }

        textView_setPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InsertData.class));
            }
        });

        button_getbarcode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getBarcode();
            }
        });


    }

    private void getAllData() {
        myList = new ArrayList<ProductModel>();

        Cursor cursor = sqLite_dataBase.getData();
        while (cursor.moveToNext()) {

            long id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String barcode = cursor.getString(3);

            myList.add(new ProductModel(id, name, price, barcode));

            Toast.makeText(getApplicationContext(),myList.toString(),Toast.LENGTH_LONG).show();

        }
    }


    private void getBarcode() {
        int LAUNCH_SECOND_ACTIVITY = 1;
        Intent i = new Intent(this, ScanActivity.class);
        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
    }


    private void init() {
        button_getbarcode = findViewById(R.id.button_getbarcode);
        textView_price = findViewById(R.id.textView_price);
        editText_barCode = findViewById(R.id.editText_barcode);
        textView_setPrice = findViewById(R.id.textView_setPrice);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
               result = data.getStringExtra("result");
            }
                editText_barCode.setText(result);
                getPriceByBarcode(result);
        }
    }


    public void getPriceByBarcode(String barCode)
    {
        myList = new ArrayList<ProductModel>();



        Cursor cursor = sqLite_dataBase.getDataByBacode(barCode);


            while (cursor.moveToNext()) {

                long id = cursor.getInt(0);
                String name = cursor.getString(1);
                String price = cursor.getString(2);
                String barcode = cursor.getString(3);

                myList.add(new ProductModel(id, name, price, barcode));

                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);

                ProductModel productModel = myList.get(0);
                textView_price.setText("Adı : " +productModel.name+ "\n \n"+"Qiyməti : "+productModel.price+" AZN");

            }
        }


    }
