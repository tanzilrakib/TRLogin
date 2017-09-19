package com.example.rakib.trlogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by RakiB on 9/19/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "login.timestamp.db";
    private static final String TABLE_NAME = "users";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_TIMESTAMP = "timestamp";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table users (id integer primary key autoincrement not null, " +
            "name text not null, email text not null unique, password text not null, timestamp text not null);";

    public DbHelper(Context context){
        super(context, TABLE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String q = "drop table is exists "+TABLE_NAME;
        db.execSQL(q);
        this.onCreate(db);
    }

    public String matchPassword(String email, String password){

           db = this.getReadableDatabase();
           String q = "select email, password from "+ TABLE_NAME;
           Cursor cursor = db.rawQuery(q, null);
           cursor.moveToFirst();

           String e , p;
           p = "Not found";

            do{
                e = cursor.getString(0);
                if(e.equals(email)){
                    if(p.equals(password)){
                         p = cursor.getString(1);
                        break;
                    }
                }
            }while(cursor.moveToNext());

            cursor.close();
            db.close();
            return p;
    }

    public boolean userExists(String email, String password){

           db = this.getReadableDatabase();


           Cursor cursor = db.rawQuery("SELECT email FROM users WHERE email = ? AND password = ?", new String[] {email, password});

           int cursorCount = cursor.getCount();
           cursor.close();
           db.close();

           if(cursorCount>0){
               return true;
           } else{
               return false;
           }

    }


    public boolean emailExists(String email){

        db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE email = ?", new String[] {email});

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount>0){
            return true;
        } else{
            return false;
        }

    }


    public String getUserName(String email){

        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT name FROM users WHERE email = ?", new String[] {email});

        cursor.moveToFirst();
        String userName = cursor.getString(0);


        cursor.close();
        db.close();


        return userName;


    }

    public String getLastLogin(String email){

        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT timestamp FROM users WHERE email = ?", new String[] {email});

        cursor.moveToFirst();

        String timestamp = cursor.getString(0);

        cursor.close();
        db.close();

        return timestamp;

    }
    public void setLastLogin(String email, String timestamp){

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_TIMESTAMP, timestamp);

        db.update(TABLE_NAME,values,"email = ?", new String[] {email});

        db.close();


    }

    public void insertUser(User u) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, u.getName());
        values.put(USER_EMAIL, u.getEmail());
        values.put(USER_PASSWORD, u.getPassword());
        values.put(USER_TIMESTAMP, u.getTimestamp());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String getDateTime() {

        Date currentTime = Calendar.getInstance().getTime();
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(currentTime);
            return currentDateTimeString;

    }
}
