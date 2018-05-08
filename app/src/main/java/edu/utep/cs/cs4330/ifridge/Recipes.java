package edu.utep.cs.cs4330.ifridge;

import java.util.ArrayList;
/**This list holds all ingredients associated with a Recipe*/
/**Using ingredientList as name for this this has become redundant*/

public class Recipes {
    public static ArrayList<String> ingredientsInRecipe = new ArrayList<>();
    public String recipeSelected = new String();
    public void insertRecipeIngredients(String recipeName){
        ingredientsInRecipe.add(recipeName);
    }
    public ArrayList<String> getRecipesList(){
        return ingredientsInRecipe;
    }
    public void setRecipeSelected(String selection){
        this.recipeSelected = selection;
    }
}
