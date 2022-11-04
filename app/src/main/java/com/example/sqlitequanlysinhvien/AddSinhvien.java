package com.example.sqlitequanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddSinhvien extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sinhvien);
        Button add = findViewById(R.id.buttonadd);
        ImageView back = findViewById(R.id.buttonback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tensv = findViewById(R.id.addtensv);
                EditText masv = findViewById(R.id.addmasv);
                EditText tuoisv = findViewById(R.id.addtuoisv);
                EditText lopsv = findViewById(R.id.addlopsv);
                if (tensv.getText().toString().equals("") ||
                    masv.getText().toString().equals("") ||
                    tuoisv.getText().toString().equals("") ||
                    lopsv.getText().toString().equals("") )
                {
                    Toast.makeText(AddSinhvien.this,"Nhập thiếu thông tin",Toast.LENGTH_SHORT).show();
                }
                else {
                    SQLiteDatabase database = openOrCreateDatabase("quanlysinhvien.db", MODE_PRIVATE, null);
                    ContentValues values = new ContentValues();

                    values.put("masv",masv.getText().toString());
                    values.put("tensv",tensv.getText().toString());
                    values.put("tuoi",tuoisv.getText().toString());
                    values.put("lop",lopsv.getText().toString())
                    ;
                    if (database.insert("sinhvien",null,values) == -1) {
                        Toast.makeText(AddSinhvien.this, "Fail to insert record", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        SinhVien newsv= new SinhVien(masv.getText().toString(),tensv.getText().toString(),lopsv.getText().toString(),Integer.parseInt(tuoisv.getText().toString()));
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("newsv",newsv);
                        Intent resultIntent  = new Intent();
                        resultIntent.putExtra("bundle",bundle);
                        setResult(RESULT_OK,resultIntent);
                        finish();
                    }
                }

            }
        });
    }
}