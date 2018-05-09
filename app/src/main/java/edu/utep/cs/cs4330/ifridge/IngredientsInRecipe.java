package edu.utep.cs.cs4330.ifridge;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class IngredientsInRecipe extends AppCompatActivity {
    private ListView ingredientsInRecipe;
    ListAdapter ingredientsInRecipeAdapter;
    ArrayList<String> recipeList = new ArrayList();
    RecipeDatabaseHelper recipeDB;
    int row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_in_recipe);
        ingredientsInRecipe = findViewById(R.id.ingredientsPerRecipe);
        recipeDB = new RecipeDatabaseHelper(this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                row=0;
            } else {
                row= extras.getInt("KEY");
            }
        } else {
            row = (int) savedInstanceState.getSerializable("KEY");
        }

        setList();

        ingredientsInRecipeAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,recipeList);
        ingredientsInRecipe.setAdapter(ingredientsInRecipeAdapter);
    }

    private void setList(){
        Cursor res = recipeDB.getAllData();
        while(res.moveToNext()){
            if(res.getInt(0)==row){
                recipeList.add(res.getString(1));
                recipeList.add(res.getString(2));
                recipeList.add(res.getString(3));
            }
        }
    }
}