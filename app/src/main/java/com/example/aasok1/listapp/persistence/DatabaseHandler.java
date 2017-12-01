package com.example.aasok1.listapp.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.aasok1.listapp.entities.UserData;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database name
    private static final String DATABASE_NAME = "userDetailsManager";
    //Table name
    private static final String TABLE_USERS = "users";

    private static final String KEY_NAME = "username";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_PWD = "password";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_EMAIL = "email";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_NAME + " TEXT PRIMARY KEY," + KEY_PWD + " TEXT," + KEY_FULLNAME + " TEXT, " + KEY_EMAIL + " TEXT, "
                + KEY_PH_NO + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    public void addUser(UserData userData, Context context) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, userData.getUserName());
        values.put(KEY_FULLNAME, userData.getUserFullName());
        values.put(KEY_PWD, userData.getUserPassword());
        values.put(KEY_EMAIL, userData.getUserEmail());
        values.put(KEY_PH_NO, userData.getUserMobile());
        long count = database.insert(TABLE_USERS, null, values);
        Toast.makeText(context, "" + count + " No of Data Inserted", Toast.LENGTH_LONG).show();
        database.close();
    }

    public UserData getUserData(String userName) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_USERS,
                new String[] {KEY_NAME, KEY_PWD, KEY_FULLNAME, KEY_EMAIL, KEY_PH_NO},
                KEY_NAME + "=?",
                new String[] {String.valueOf(userName)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (cursor.getCount()>0) {
            UserData userData = new UserData(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4));

            return userData;
        }
        return null;
    }

    public List<UserData> getAllUserDetails() {
        List<UserData> userList = new ArrayList<UserData>();
        String query = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                UserData userData = new UserData();
                userData.setUserName(cursor.getString(0));
                userData.setUserPassword(cursor.getString(1));
                userData.setUserFullName(cursor.getString(2));
                userData.setUserEmail(cursor.getString(3));
                userData.setUserMobile(cursor.getString(4));
                userList.add(userData);
            } while (cursor.moveToNext());
        }
        database.close();
        return userList;
    }

    public int getUserCount() {
        String count = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(count, null);
        cursor.close();
        db.close();
        return cursor.getCount();
    }

    public int updateUserData(UserData userData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, userData.getUserName());
        values.put(KEY_FULLNAME, userData.getUserFullName());
        values.put(KEY_PWD, userData.getUserPassword());
        values.put(KEY_EMAIL, userData.getUserEmail());
        values.put(KEY_PH_NO, userData.getUserMobile());
        return db.update(TABLE_USERS, values, KEY_NAME + "=?",
                new String[] {String.valueOf(userData.getUserName())});
    }

    public void deleteUsers(UserData user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_NAME + "=?", new String[] {String.valueOf(user.getUserName())});
        db.close();
    }
}
