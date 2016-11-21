package com.example.andre.quiz3;

/**
 * Created by xiiip on 11/21/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student.db";
    public static final String STUDENT_TABLE_NAME = "student";
    public static final String STUDENT_COLUMN_ID = "id";
    public static final String STUDENT_COLUMN_NAME = "name";
    public static final String STUDENT_COLUMN_NOREG = "noreg";
    public static final String STUDENT_COLUMN_EMAIL = "email";
    public static final String STUDENT_COLUMN_PHONE = "phone";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table student " +
                        "(id integer primary key, name text, noreg text, email text, phone text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS student");
        onCreate(db);
    }

    public boolean insertStudent (String name, String noreg, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("noreg", noreg);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        db.insert("student", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from student where id="+id+"", null );
        return res;
    }
    public void cleanHistoryTable(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS history; VACUUM");
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, STUDENT_TABLE_NAME);
        return numRows;
    }

    public boolean updateStudent (Integer id, String name, String noreg, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("noreg", noreg);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        db.update("student", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteStudent (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("student",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllStudent() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from student", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(STUDENT_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}