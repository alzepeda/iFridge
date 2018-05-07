package edu.utep.cs.cs4330.ifridge;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.String;

import java.util.ArrayList;

/**
 * Created by AnaZepeda on 5/3/18.
 */

public class FridgeActivity extends AppCompatActivity {
    FridgeDatabaseHelper fridgeDB;
    EditText editIngredient;
    Button addIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge);
        fridgeDB = new FridgeDatabaseHelper(this);

        editIngredient = findViewById(R.id.editIngredient);
        addIngredient = findViewById(R.id.addIngredientButton);

        showIngredients();

    }

    public void addButtonClicked(View view) {
        String ingredient = editIngredient.getText().toString();
        Cursor res = fridgeDB.getAllData();
        StringBuffer buffer;

        while(res.moveToNext()){
            buffer = new StringBuffer();
            buffer.append(res.getString(1));
            if(ingredient.equalsIgnoreCase(buffer.toString())){
                Toast.makeText(FridgeActivity.this, "Item already contained", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        boolean isInserted = fridgeDB.insertData(ingredient);
        if(isInserted)
            Toast.makeText(FridgeActivity.this, "Ingredient Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(FridgeActivity.this, "Ingredient not Inserted", Toast.LENGTH_SHORT).show();

        showIngredients();
    }

    public void showIngredients() {
        StringBuffer buffer;
        ArrayList foods = new ArrayList<String>();

        Cursor res = fridgeDB.getAllData();
        if(res.getCount() != 0) {

            while (res.moveToNext()) {
                buffer = new StringBuffer();
                buffer.append(res.getString(1));
                foods.add(buffer.toString());
            }

            ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foods);
            ListView listView = (ListView) findViewById(R.id.listViewforIngredients);
            listView.setAdapter(listAdapter);
        }
    }
}
