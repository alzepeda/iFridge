package edu.utep.cs.cs4330.ifridge;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity {
    ArrayList<String> ingredientList = new ArrayList();
    RecipeDatabaseHelper recipeDB;
    ListView ingredientPreview;
    ListAdapter listAdapter;
    ImageButton addIngredient;
    EditText editIngredient;
    EditText editRecipeName;
    MultiAutoCompleteTextView editInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_add);
        addIngredient = findViewById(R.id.addIngredientButton);
        editIngredient = findViewById(R.id.editIngredients);
        editRecipeName = findViewById(R.id.editRecipeName);
        editInstructions = findViewById(R.id.editInstructions);
        ingredientPreview = findViewById(R.id.ingredientsPreview);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredientList);
        recipeDB = new RecipeDatabaseHelper(this);
    }

    /**
     * Toast a message to the user
     */
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void addIngredientClicked(View view) {
        String ingredient = editIngredient.getText().toString();
        if(ingredient.length()==0)
            return;
        for (int i = 0; i < ingredientList.size(); i++) {
            if(ingredientList.get(i)==ingredient){
                toast("Ingredient already inserted");
                return;
            }
        }
        ingredientList.add(ingredient);
        ingredientPreview.setAdapter(listAdapter);
        editIngredient.setText("");
    }

    public void doneEditingClicked(View view) {
        String title = editRecipeName.getText().toString();
        String steps = editInstructions.getText().toString();
        String ingredients = "";

        if(ingredientList.size()==0 || title.length()==0)
            return;

        for (int i = 0; i < ingredientList.size(); i++) {
            ingredients += ingredientList.get(i) + "\n";
        }

        Cursor res = recipeDB.getAllData(); //Call DB and retrieve all associated data

        boolean isInserted = recipeDB.add(title,ingredients,steps);

        if (isInserted) {
            toast("Recipe Added");
            Intent myIntent = new Intent(AddRecipeActivity.this,RecipeActivity.class);
            startActivity(myIntent);
        } else {
            toast("Recipe not Added. Add a title or ingredients");
        }
    }
}
