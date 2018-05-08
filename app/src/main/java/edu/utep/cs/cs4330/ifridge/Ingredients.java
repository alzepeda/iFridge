package edu.utep.cs.cs4330.ifridge;

import java.util.ArrayList;

public class Ingredients {
    public static ArrayList<String> ingredientList = new ArrayList<>();

    public Ingredients(){
    }
    public boolean insertIngredient(String ingredient){
       return ingredientList.add(ingredient);
    }
    public boolean removeIngredient(String ingredient){
        return ingredientList.remove(ingredient);
    }
    public int ingredientListSize(){
        return ingredientList.size();
    }
    public boolean hasIngredients(){
        return ingredientList.isEmpty();
    }

    public ArrayList<String> getIngredientList() {
        return ingredientList;
    }
}
