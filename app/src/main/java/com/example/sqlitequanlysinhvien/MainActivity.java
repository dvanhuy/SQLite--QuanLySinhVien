package com.example.sqlitequanlysinhvien;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    ListView listsv;
    ArrayList<SinhVien> arraysv;
    ListViewAdapter listViewAdapter;
    int vitriupdate=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = openOrCreateDatabase("quanlysinhvien.db", MODE_PRIVATE, null);
        if (!isTableExists(database,"sinhvien")){
            doCreateDB(database);
        }
        initdata();
        listViewAdapter = new ListViewAdapter(this,R.layout.item_sinhvien,arraysv);
        listsv = findViewById(R.id.listsv);
        listsv.setAdapter(listViewAdapter);
        listsv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                XoaData(i);
                return false;
            }
        });
        listsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent add = new Intent(MainActivity.this,UpdateSV.class);
                Bundle bundle = new Bundle();
                vitriupdate = i;
                bundle.putSerializable("svupdate",arraysv.get(i));
                add.putExtra("bundle",bundle);
                getResultupdate.launch(add);
            }
        });
        ImageView updatebutton = findViewById(R.id.imageView5);
        ImageView addbutton = findViewById(R.id.imageView4);
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Nhấn vào sinh viên để sửa",Toast.LENGTH_SHORT).show();
            }
        });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(MainActivity.this,AddSinhvien.class);
                getResult.launch(add);
            }
        });
    }

    public boolean isTableExists(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name " +
                "from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void initdata(){
        SQLiteDatabase database = openOrCreateDatabase("quanlysinhvien.db", MODE_PRIVATE, null);
        Cursor cur=database.query("sinhvien", null, null, null, null, null, null);
        cur.moveToFirst();
        arraysv = new ArrayList();
        while(cur.isAfterLast()==false)
        {
            SinhVien s=new SinhVien();
            s.setMasv(cur.getString(0));
            s.setTensv(cur.getString(1));
            s.setTuoi(cur.getInt(2));
            s.setLop(cur.getString(3));
            arraysv.add(s);
            cur.moveToNext();
        }
        cur.close();
    }

    private ActivityResultLauncher<Intent> getResult =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Bundle dataacc =  data.getBundleExtra("bundle");
                        SinhVien newsv = (SinhVien) dataacc.getSerializable("newsv");
                        arraysv.add(newsv);
                        listViewAdapter.notifyDataSetChanged();
                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED){
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> getResultupdate =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Bundle dataacc =  data.getBundleExtra("bundle");
                        SinhVien newsv = (SinhVien) dataacc.getSerializable("newsv");
                        arraysv.set(vitriupdate,newsv);
                        listViewAdapter.notifyDataSetChanged();
                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED){
                    }
                }
            }
    );
//
    private void XoaData(final int  position){
        AlertDialog.Builder alterDialog  = new AlertDialog.Builder(this);
        alterDialog.setTitle("Thông báo ");
        alterDialog.setIcon(R.mipmap.ic_launcher);
        alterDialog.setMessage("Bạn có muốn xóa sinh viên này không ?");
        alterDialog.setPositiveButton("Có", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                XoaTableSVTheoID(arraysv.get(position).getMasv());
                arraysv.remove(position);
                listViewAdapter.notifyDataSetChanged();
            }
        });
        alterDialog.setNegativeButton("Không", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alterDialog.show();
    }

    public void doCreateDB(SQLiteDatabase database) {

//        doCreateSinhVienTable
        String sqlcomputer = "CREATE TABLE sinhvien (" +
                "masv TEXT PRIMARY KEY, " +
                "tensv TEXT, " +
                "tuoi TEXT,  " +
                "lop TEXT)" ;
        database.execSQL(sqlcomputer);

//        doInsertCategory
        ContentValues values = new ContentValues();

        values.put("masv","2050531200157");
        values.put("tensv","Đinh Văn Huy");
        values.put("tuoi",21);
        values.put("lop","20T1");
        if (database.insert("sinhvien",null,values) == -1) {
            Toast.makeText(this, "Fail to insert record", Toast.LENGTH_SHORT).show();
        }

        values.put("masv","531200157");
        values.put("tensv","Văn Huy");
        values.put("tuoi",21);
        values.put("lop","20T2");
        if (database.insert("sinhvien",null,values) == -1) {
            Toast.makeText(this, "Fail to insert record", Toast.LENGTH_SHORT).show();
        }

        values.put("masv","200157");
        values.put("tensv","Huy Đinh Văn");
        values.put("tuoi",21);
        values.put("lop","20T1");
        if (database.insert("sinhvien",null,values) == -1) {
            Toast.makeText(this, "Fail to insert record", Toast.LENGTH_SHORT).show();
        }

        values.put("masv","205053120158");
        values.put("tensv","Nguyễn Văn A");
        values.put("tuoi",20);
        values.put("lop","20T3");
        if (database.insert("sinhvien",null,values) == -1) {
            Toast.makeText(this, "Fail to insert record", Toast.LENGTH_SHORT).show();
        }

    }

//    public void updateLopName(String malop,String new_tenlop)
//    {
//        ContentValues values=new ContentValues();
//        values.put("tenlop", new_tenlop);
//        String msg="";
//        int ret=database.update("tbllop", values,
//                "malop=?", new String[]{malop});
//        if(ret==0){
//            msg="Failed to update";
//        }
//        else{
//            msg="updating is successful";
//        }
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
//    }
    //    public void dodeleteDB() {
//        String msg = "";
//        if (deleteDatabase("qlsinhvien.db") == true) {
//            msg = "Delete database [qlsinhvien.db] is successful";
//        } else {
//            msg = "Delete database [qlsinhvien.db] is failed";
//        }
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }

//    public void XoaTableLop(){
//        database.delete("tblop",null,null);
//        //xoa toan bo bang
//    }

    public void XoaTableSVTheoID(String id){
        database.delete("sinhvien","masv =?",new String[]{id});
        //xoa theo
    }
}