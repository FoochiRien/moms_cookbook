package com.adrienne.cookbook_app.My_cookbook;

/**
 * Created by Admin on 5/22/17.
 */

public class Ingredients {

    private String mIngredientsRecipeTitle, mIngredients;

    public Ingredients(String ingredientsRecipeTitle, String ingredients) {
        mIngredientsRecipeTitle = ingredientsRecipeTitle;
        mIngredients = ingredients;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public void setIngredients(String ingredients) {
        mIngredients = ingredients;
    }

    public String getIngredientsRecipeTitle() {

        return mIngredientsRecipeTitle;
    }

    public void setIngredientsRecipeTitle(String ingredientsRecipeTitle) {
        mIngredientsRecipeTitle = ingredientsRecipeTitle;
    }
}
