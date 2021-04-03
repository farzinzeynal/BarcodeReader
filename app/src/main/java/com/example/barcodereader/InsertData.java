package com.example.barcodereader;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertData extends AppCompatActivity {

    Button button_add;
    EditText editText_barcode_add,
             editText_name,
             editText_price;

    String barcode_result;
    public static SQLite_DataBase sqLite_dataBase;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        init();

        sqLite_dataBase = new SQLite_DataBase(this);

        if(editText_barcode_add.length()==0)
        { button_add.setEnabled(false); }
        else { button_add.setEnabled(true); }

        editText_barcode_add.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editText_barcode_add.getRight() - editText_barcode_add.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                        }

                        int LAUNCH_SECOND_ACTIVITY = 1;
                        Intent i = new Intent(InsertData.this, ScanActivity.class);
                        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                    }
                    return false;
                }
                return false;
            }
        });




        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(editText_name.getText().toString().length()==0)
                { editText_name.setError("Boş buraxıla bilməz"); return;}

                if(editText_barcode_add.getText().toString().length()==0)
                { editText_barcode_add.setError("Boş buraxıla bilməz"); return;}

                if(editText_price.getText().toString().length()==0)
                { editText_price.setError("Boş buraxıla bilməz"); return;}


                try
                {
                    sqLite_dataBase.insertData
                            (
                                    editText_name.getText().toString().trim(),
                                    editText_price.getText().toString().trim(),
                                    editText_barcode_add.getText().toString().trim()
                            );

                    Toast.makeText(getApplicationContext(),"Əlavə olundu",Toast.LENGTH_LONG).show();
                    editText_name.setText("");
                    editText_barcode_add.setText("");
                    editText_price.setText("");
                    finish();
                }
                catch (Exception e)
                { e.printStackTrace(); }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                barcode_result = data.getStringExtra("result");
            }
            editText_barcode_add.setText(barcode_result);
            button_add.setEnabled(true);


        }
    }

    private void init()
    {
        button_add = findViewById(R.id.button_add);
        editText_barcode_add = findViewById(R.id.editText_add_barcode);
        editText_name = findViewById(R.id.editTextname);
        editText_price = findViewById(R.id.editText_price);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(InsertData.this,MainActivity.class));
    }
}