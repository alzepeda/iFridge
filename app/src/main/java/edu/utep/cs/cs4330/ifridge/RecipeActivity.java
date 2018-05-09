/**Created by
 @author AnaZepeda
 @author SebastianGonzalez
 @version 2.1
 */
package edu.utep.cs.cs4330.ifridge;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import java.util.Hashtable;
import java.util.LinkedList;


public class RecipeActivity extends AppCompatActivity{
    private ListView listView;
    private ListAdapter listAdapter;
    private RecipeDatabaseHelper recipeDB;
    public Ingredients ingredients;
    private Recipes recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        recipes = new Recipes();
        ingredients = new Ingredients();
        listView = findViewById(R.id.recipeListView);
        listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,recipes.getRecipesList());
        recipeDB =new RecipeDatabaseHelper(this);
        listView.setOnItemClickListener((a, v, position, id) ->showAlertDialog(position));
        showRecipes();
    }

    private void toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void showRecipes(){
        listView.setAdapter(listAdapter);
    }

    /**Alert Dialog to Delete items from list and DB*/
    private void showAlertDialog(int index){
        toast(String.valueOf(index));
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("View Ingredients?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String itemSelected = listAdapter.getItem(index).toString();
                        recipes.getRecipesList().clear();
                        recipes.setRecipeSelected(itemSelected);// get the item user wants to delete from ListView
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        toast("Cancelled");
                    }
                });
        builder.create(); //build AlertDialog
        builder.show();//show AlertDialog
    }

    public void addRecipeClicked(View view) {
        Intent myIntent = new Intent(RecipeActivity.this,AddRecipeActivity.class);
        startActivity(myIntent);
    }
}