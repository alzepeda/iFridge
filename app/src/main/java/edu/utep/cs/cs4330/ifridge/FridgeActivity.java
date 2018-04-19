package edu.utep.cs.cs4330.ifridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by AnaZepeda on 4/17/18.
 */

public class FridgeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge);
        Intent intent = getIntent();
    }
}
