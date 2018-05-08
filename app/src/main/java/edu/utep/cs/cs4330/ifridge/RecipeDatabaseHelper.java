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
    public static final String COL_1 = "RECIPE_NAME";
    public static SQLiteDatabase recipesDatabase;
    public static ContentValues contentValues;
    public RecipeDatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+"(RECIPE_NAME TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean add(String item){
        if(item.isEmpty()){
            return false;
        }
        item.toLowerCase();
        recipesDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(COL_1,item);
        long result = recipesDatabase.insert(TABLE_NAME,null,contentValues);
        if(result < 0){
            return false;
        }
            return true;
    }
    public boolean delete(String item){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COL_1 + "=" + "'"+item+"'", null) > 0;
        }
    public void showRecipes(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

    }
}
