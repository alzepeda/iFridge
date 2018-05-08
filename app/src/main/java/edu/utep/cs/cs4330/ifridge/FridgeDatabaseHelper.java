/**Created by
@author AnaZepeda
@author SebastianGonzalez
@version 2.1
*/
package edu.utep.cs.cs4330.ifridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class FridgeDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Fridge.db";
    public static final String TABLE_NAME = "fridge_table";
    public static final String COL_1 = "Ingredient";

    public FridgeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + TABLE_NAME + " (Ingredient VARCHAR(15))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1) {

            return false;
        }
        else{

            return true;}
    }

    public boolean delete(String item){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COL_1 + "=" + "'"+item+"'", null) > 0;
        }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

        return res;
    }
}
