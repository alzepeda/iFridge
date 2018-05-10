package edu.utep.cs.cs4330.ifridge;

/**Created by
 @author AnaZepeda
 @author SebastianGonzalez
 @version 2.1
 */

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;


public class FridgeActivity extends AppCompatActivity {
    private FridgeDatabaseHelper fridgeDB;
    public TextView editTotalItems;
    private EditText editIngredient;
    private Button addIngredient;
    private ListView listView;
    private ListAdapter listAdapter;
    public Ingredients ingredientList;
    private MenuInflater menuInflater;

    @Override
    protected void onPause() {
        super.onPause();
        StringBuffer buffer;
        Cursor res = fridgeDB.getAllData();
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                buffer = new StringBuffer();
                buffer.append(res.getString(0));
                ingredientList.insertIngredient(buffer.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case R.id.deleteOption:
                fridgeDB.deleteAll();
                toast("Fridge cas been cleared");
                ingredientList.getIngredientList().clear();
                showIngredients();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge);
        ingredientList = new Ingredients();
        ingredientList.getIngredientList().clear();
        fridgeDB = new FridgeDatabaseHelper(this);
        listView = findViewById(R.id.listViewforIngredients);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredientList.getIngredientList());
        editIngredient = findViewById(R.id.editIngredient);
        addIngredient = findViewById(R.id.addIngredientButton);
        editTotalItems = findViewById(R.id.totalItemsEditTExt);

        StringBuffer buffer;
        Cursor res = fridgeDB.getAllData();
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                buffer = new StringBuffer();
                buffer.append(res.getString(0));
                ingredientList.insertIngredient(buffer.toString());
            }
        }
        listView.setOnItemClickListener((a, v, position, id) -> showAlertDialog(position));
        showIngredients();
    }


    /**
     * Alert Dialog to Delete items from list and DB
     */
    private void showAlertDialog(int index) {
        toast(String.valueOf(index));
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String itemTOdelete = listAdapter.getItem(index).toString();// get the item user wants to delete from ListView
                        if (fridgeDB.delete(itemTOdelete)) { //if exists in DB and was Successfully deleted
                            toast("Successfully deleted " + itemTOdelete);
                            ingredientList.removeIngredient(itemTOdelete);
                            showIngredients();
                        } else {
                            toast("Failed or Not found");
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        toast("Cancelled");
                    }
                });
        builder.create(); //build AlertDialog
        builder.show();//show AlertDialog
    }

    /**
     * Toast a message to the user
     */
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Activated when user taps Add button
     */
    public void addButtonClicked(View view) {
        String ingredient = editIngredient.getText().toString();

        Cursor res = fridgeDB.getAllData(); //Call DB and retrieve all associated data

        while(res.moveToNext()){
           if(res.getString(0).equalsIgnoreCase(ingredient)) {
               toast("Ingredient already inserted");
               return;
           }
        }

        if (ingredient.isEmpty()) {    // if no input, request input
            toast("Please write an ingredient");
            return;
        }

        boolean isInserted = fridgeDB.insertData(ingredient);
        if (isInserted) {
            toast("Ingredient Inserted");
            ingredientList.insertIngredient(ingredient);
        } else {
            toast("Ingredient not Inserted");
        }

        showIngredients();
    }


    //show the ingredients currently in the Array
    public void showIngredients() {
        editTotalItems.setText(String.valueOf(ingredientList.ingredientListSize()));
        listView.setAdapter(listAdapter);
    }
}