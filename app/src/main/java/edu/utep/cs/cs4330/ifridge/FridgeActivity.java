package edu.utep.cs.cs4330.ifridge;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

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
    }

    public void addButtonClicked(View view) {
        boolean isInserted = fridgeDB.insertData(editIngredient.getText().toString());
        if(isInserted = true)
            Toast.makeText(FridgeActivity.this, "Ingredient Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(FridgeActivity.this, "Ingredient not Inserted", Toast.LENGTH_SHORT).show();

        Cursor res = fridgeDB.getAllData();
        if(res.getCount() == 0){
            return;
        }
        StringBuffer buffer = new StringBuffer();
        ArrayList foods = new ArrayList<String>();
        while(res.moveToNext()){
            buffer.append(res.getString(1));
            foods.add(buffer.toString());
        }

        ListAdapter listAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foods);
        RecyclerView listView = (RecyclerView)findViewById(R.id.recyclerViewforIngredients);
        listView.setAdapter((RecyclerView.Adapter) listAdapter);
    }
}
