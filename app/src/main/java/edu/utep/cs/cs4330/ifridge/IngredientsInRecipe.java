package edu.utep.cs.cs4330.ifridge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class IngredientsInRecipe extends AppCompatActivity {
    private ListView ingredientsInRecipe;
    ListAdapter ingredientsInRecipeAdapter;
    private Recipes recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_in_recipe);
        recipes = new Recipes();
        ingredientsInRecipe = findViewById(R.id.ingredientsPerRecipe);
        ingredientsInRecipeAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,recipes.getRecipesList());
        ingredientsInRecipe.setAdapter(ingredientsInRecipeAdapter);

    }
}
