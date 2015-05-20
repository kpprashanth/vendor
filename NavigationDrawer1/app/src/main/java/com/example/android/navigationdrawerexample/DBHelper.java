/**
 * Created by prashanth on 17/3/15.
 */
package com.example.android.navigationdrawerexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper {
    DBHelper2 helper;
    public static String logedname="name";


    public DBHelper(Context context){
        helper=new DBHelper2(context);
    }

    public long Insert(String email,String name,String pass,String phone){

        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(Student.KEY_ID,email);
        contentvalues.put(Student.KEY_name,name);
        contentvalues.put(Student.KEY_phone,phone);
        contentvalues.put(Student.KEY_pass,pass);


        long id=db.insert(Student.TABLE,null,contentvalues);
        return id;
    }

    public void vendor_item_insert(String id,String name,int price){

        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put("item_id",id);
        contentvalues.put("name",name);
        contentvalues.put("price",price);


        db.insert("VENDOR_ITEM",null,contentvalues);

    }


    public void vendor_get_item(){
        boolean flag=false;
        String id="",name="";
        int price=0;
        SQLiteDatabase db = helper.getReadableDatabase();
        //select email_id from vendor where email_id='kp@gmail.com' and password='1010';

        String selectQuery =  "select * from VENDOR_ITEM;";
        // "SELECT "+Student.KEY_ID+" FROM "+Student.TABLE+" WHERE "+Student.KEY_pass+"="+pass+";";
        //Student student = new Student();


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        try {


            if (cursor.moveToFirst()) {
                do {
                    flag = true;
                    id=cursor.getString(0);
                    name=cursor.getString(1);
                    price=cursor.getInt(2);
                } while (cursor.moveToNext());
            }
            Log.d("Id: "+id+" Name: "+name+" Price: "+price,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            cursor.close();
            db.close();
        }
        catch (Exception e)
        {

        }

    }


    public boolean GetLogin(String email,String pass){
        boolean flag=false;

        SQLiteDatabase db = helper.getReadableDatabase();
        //select email_id from vendor where email_id='kp@gmail.com' and password='1010';

        String selectQuery =  "select name from vendor where email_id='"+email+"' and password='"+pass+"';";
        // "SELECT "+Student.KEY_ID+" FROM "+Student.TABLE+" WHERE "+Student.KEY_pass+"="+pass+";";
        //Student student = new Student();


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

    try {


        if (cursor.moveToFirst()) {
            do {
                flag = true;
               logedname=cursor.getString(0);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }
    catch (Exception e)
    {

    }
        vendor_item_insert("item1","pizza",100);
        vendor_get_item();
        return flag;
    }


    class DBHelper2 extends SQLiteOpenHelper {
        //version number to upgrade database version
        //each time if you Add, Edit table, you need to change the
        //version number.
        private static final int DATABASE_VERSION = 4;

        // Database Name
        private static final String DATABASE_NAME = "vendor.db";

        public DBHelper2(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //All necessary tables you like to create will create here

            String vendor_item = "CREATE TABLE IF NOT EXISTS " + "VENDOR_ITEM" + "("//"VENDOR_ITEM"
                    + "item_id" + " TEXT ,"
                    + "name" + " TEXT, "
                    + "price" + " INT )";
            String CREATE_TABLE_STUDENT = "CREATE TABLE IF NOT EXISTS " + Student.TABLE + "("
                    + Student.KEY_ID + " TEXT ,"
                    + Student.KEY_name + " TEXT, "
                    + Student.KEY_phone + " TEXT, "
                    + Student.KEY_pass + " TEXT )";
          //  String sql = "drop table " + Student.TABLE;
          //  String sql1 = "drop table " + "VENDOR_ITEM";
          //  db.execSQL(sql1);
          //  db.execSQL(sql);

          //  vendor_item_insert("item2","burger",200);
            db.execSQL(CREATE_TABLE_STUDENT);
            db.execSQL(vendor_item);

          //  vendor_get_item();

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed, all data will be gone!!!


        }

    }
}