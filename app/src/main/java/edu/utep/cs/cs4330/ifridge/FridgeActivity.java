package edu.utep.cs.cs4330.ifridge;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
/**
 * Created by AnaZepeda on 5/3/18.
 */
public class FridgeActivity extends AppCompatActivity{
    FridgeDatabaseHelper fridgeDB;
    EditText editIngredient, editQuantity;
    TextView  editTotalItems;;
    Button addIngredient;
    List<String> foods;

    ListView listView;
    ListAdapter listAdapter;
    @Override
    protected void onPause() {
        super.onPause();
        Hashtable<String,Integer> table = new Hashtable<>(foods.size());
        for (String a: foods) {
            int items = foods.indexOf(a) - foods.lastIndexOf(a);
            if(items == 0){
                items = 1;
            }
            table.put(a,items);
            for (int i = 0; i < items; i++) {
                foods.add(a);

            }
        }


        StringBuffer buffer;
        Cursor res = fridgeDB.getAllData();
        if(res.getCount() != 0) {
            while (res.moveToNext()) {
                buffer = new StringBuffer();
                buffer.append(res.getString(0));
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge);
        foods = new ArrayList<>();

        fridgeDB = new FridgeDatabaseHelper(this);
        listView = findViewById(R.id.listViewforIngredients);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);
        editIngredient = findViewById(R.id.editIngredientEditTExt);
        addIngredient = findViewById(R.id.addIngredientButton);
        editTotalItems = findViewById(R.id.totalItemsEditTExt);
        editQuantity = findViewById(R.id.ingredientQuatity);
        StringBuffer buffer;
        Cursor res = fridgeDB.getAllData();
        if(res.getCount() != 0) {

            while (res.moveToNext()) {

                buffer = new StringBuffer();
                buffer.append(res.getString(0));
                foods.add(buffer.toString());
            }
        }
new Thread(){
    @Override
    public void run() {
        super.run();
        listView.setOnItemClickListener((a, v, position, id) ->
                showAlertDialog(position));
    }
}.start();
        showIngredients();
    }
    private void showAlertDialog(int index){
        toast(String.valueOf(index));
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String itemTOdelete = listAdapter.getItem(index).toString();

                        if(fridgeDB.delete(itemTOdelete)){
                         toast("Successfully deleted "+itemTOdelete);
                         foods.remove(index);
                         showIngredients();}
                        else {
                            toast("Failed");
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      toast("Cancelled");
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    private void toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public void addButtonClicked(View view) {
        String ingredient = editIngredient.getText().toString();
        if(editQuantity.getText().toString().isEmpty()){
            toast("Insert a Quantity please");
            return;
        }
        else if(editIngredient.getText().toString().isEmpty()) {
            toast("Please write an ingredient");
            return;
        }
        int quantity =Integer.parseInt(editQuantity.getText().toString());
        if(quantity > 10){
            toast(" limit is 10 per ingredient");
            return;
        }
        Cursor res = fridgeDB.getAllData();




        for (int i = 0; i < quantity; i++) {
        boolean isInserted = fridgeDB.insertData(ingredient);
        if(isInserted) {
            Toast.makeText(FridgeActivity.this, "Ingredient Inserted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(FridgeActivity.this, "Ingredient not Inserted", Toast.LENGTH_SHORT).show();
        }
            foods.add(ingredient);
        }

        showIngredients();
    }

    public void showIngredients() {

           editTotalItems.setText(String.valueOf(foods.size()));
            listView.setAdapter(listAdapter);

        }
        private void saveCount(){


        }
    }

