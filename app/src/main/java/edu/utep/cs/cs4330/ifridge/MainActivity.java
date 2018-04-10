package edu.utep.cs.cs4330.ifridge;

/**iFridge app
 * @author Ana Zapeda, Sebastian Gonzalez
 * @version 1.0
 *
 * This app will allow the user to discover delicious recipes with the ingredients found in the user's refrigerator.**/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        recipesButton.setOnClickListener(e->recipesButtonClicked());
        fridgeButton.setOnClickListener(e->fridgeButtonClicked());

    }
    void recipesButtonClicked(){
        toast("Recipes");
    }
    void fridgeButtonClicked(){
        toast("Fridge");
    }
    void toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
