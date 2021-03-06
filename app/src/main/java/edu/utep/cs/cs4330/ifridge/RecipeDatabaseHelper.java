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

public class RecipeDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME   = "Recipes";
    public static final String TABLE_NAME = "Recipes_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "RECIPE_NAME";
    public static final String COL_3 = "INGREDIENTS";
    public static final String COL_4 = "STEPS";
    public static SQLiteDatabase db;
    public static ContentValues contentValues;
    public static int recipeCount = 0;

    public RecipeDatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, RECIPE_NAME TEXT, INGREDIENTS TEXT, STEPS TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean add(String item, String ingredients, String steps){
        item.toLowerCase();
        db = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(COL_2,item);
        contentValues.put(COL_3,ingredients);
        contentValues.put(COL_4,steps);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result < 0){
            return false;
        }
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

        return res;
    }

    public boolean delete(String item){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COL_2 + "=" + "'"+item+"'", null) > 0;
    }

    public void showRecipes(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

    }
}