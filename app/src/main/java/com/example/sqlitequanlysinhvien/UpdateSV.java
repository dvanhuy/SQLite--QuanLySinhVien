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

public class UpdateSV extends AppCompatActivity {

    EditText masv,tensv,tuoisv,lopsv;
    SinhVien sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sv);
        Button update = findViewById(R.id.buttonupdate);
        ImageView back = findViewById(R.id.buttonback);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        sv = (SinhVien) bundle.getSerializable("svupdate");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tensv.getText().toString().equals("") ||
                        masv.getText().toString().equals("") ||
                        tuoisv.getText().toString().equals("") ||
                        lopsv.getText().toString().equals("") )
                {
                    Toast.makeText(UpdateSV.this,"Nhập thiếu thông tin",Toast.LENGTH_SHORT).show();
                }
                else {
                    SQLiteDatabase database = openOrCreateDatabase("quanlysinhvien.db", MODE_PRIVATE, null);
                    ContentValues values=new ContentValues();
                    values.put("tensv",tensv.getText().toString());
                    values.put("tuoi",Integer.parseInt(tuoisv.getText().toString()));
                    values.put("lop",lopsv.getText().toString());

                    if(database.update("sinhvien", values, "masv=?", new String[]{sv.getMasv()})==0){
                        Toast.makeText(UpdateSV.this, "Failed to update", Toast.LENGTH_LONG).show();
                    }
                    else{
                        SinhVien newsv= new SinhVien(sv.getMasv(),tensv.getText().toString(),lopsv.getText().toString(),Integer.parseInt(tuoisv.getText().toString()));
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

    public void AnhXa(){
        tensv = findViewById(R.id.addtensv);
        masv = findViewById(R.id.addmasv);
        tuoisv = findViewById(R.id.addtuoisv);
        lopsv = findViewById(R.id.addlopsv);
    }
}