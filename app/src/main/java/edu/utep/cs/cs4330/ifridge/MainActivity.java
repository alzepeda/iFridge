/**Created by
@author AnaZepeda
@author SebastianGonzalez
@version 2.1
*/
package edu.utep.cs.cs4330.ifridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button recipesButton;
    private Button fridgeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipesButton = findViewById(R.id.idOfRecipesButton);
        fridgeButton = findViewById(R.id.idOfFridgeButton);
    }

    void toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void fridgeButtonClicked(View view) {
        toast("Fridge");
        Intent myIntent = new Intent(MainActivity.this, FridgeActivity.class);
        startActivity(myIntent);
    }

    public void recipesButtonClicked(View view) {
        toast("Recipes");
        Intent myIntent = new Intent(MainActivity.this, RecipeActivity.class);
        startActivity(myIntent);
    }
}
