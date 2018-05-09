/**Created by
 @author AnaZepeda
 @author SebastianGonzalez
 @version 2.1
 */
package edu.utep.cs.cs4330.ifridge;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;


public class RecipeActivity extends AppCompatActivity{
    private ListView listView;
    private ListAdapter listAdapter;
    private RecipeDatabaseHelper recipeDB;
    public Ingredients ingredients;
    ArrayList<String> recipeList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        ingredients = new Ingredients();
        recipeDB =new RecipeDatabaseHelper(this);
        updateList();
        listView = findViewById(R.id.recipeListView);
        listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,recipeList);
        listView.setOnItemClickListener((a, v, position, id) ->showAlertDialog(position));
        listView.setAdapter(listAdapter);
    }

    private void toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**Alert Dialog to Delete items from list and DB*/
    private void showAlertDialog(int index){
        toast(String.valueOf(index));
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to view the recipe?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent myIntent = new Intent(RecipeActivity.this,IngredientsInRecipe.class);
                        myIntent.putExtra("KEY", index+1);
                        startActivity(myIntent);
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        toast("Cancelled");
                    }
                });
        builder.create(); //build AlertDialog
        builder.show();//show AlertDialog
    }

    public void updateList(){
        recipeList.clear();
        Cursor res = recipeDB.getAllData();
        String ingredientsList;
        ArrayList<String> currentRecipe;
        ArrayList<Boolean> contained;
        boolean available;

        while(res.moveToNext()) {
            ingredientsList = res.getString(2);
            currentRecipe = new ArrayList();
            contained = new ArrayList();
            available = true;
            while(!ingredientsList.isEmpty()){
                currentRecipe.add(ingredientsList.substring(0,ingredientsList.indexOf('\n')));
                ingredientsList = ingredientsList.substring(ingredientsList.indexOf('\n')+1);
            }

            for (int i = 0; i < currentRecipe.size(); i++) {
                contained.add(false);
                for (int j = 0; j < ingredients.ingredientListSize(); j++) {
                    if(currentRecipe.get(i).equalsIgnoreCase(ingredients.getIngredientList().get(j)))
                        contained.set(i, true);
                }
            }

            for (int i = 0; i < contained.size(); i++) {
                if(!contained.get(i))
                    available = false;
            }

            if(available)
                recipeList.add("*" + res.getString(1));
            else
                recipeList.add(res.getString(1));
        }
    }

    public void addRecipeClicked(View view) {
        Intent myIntent = new Intent(RecipeActivity.this,AddRecipeActivity.class);
        startActivity(myIntent);
    }
}