package com.adrienne.cookbook_app.My_cookbook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/21/17.
 */

public class MyRecipe {
    /*The parts of the recipes */
    private String mImage, mSourceTitle,mTitle, mNotes, mCategory, mSourceUrl, mViewToShow, mDirections;

    private Float mServings, mCookTime;
    private int mBookmarked;
    private long mRecipeId;
    private List<String> mIngredients;
    //These two arrays should not even exist. When I begin working on the project after the class
    //I'm going to begin by redoing the classes so that it isn't so BAD.
    // Change to strings not arrays, remove joins from DB.


    public MyRecipe(Long recipeId, String image, String sourceTitle, String title, String notes, String category,
                    String sourceUrl, Float servings, Float cookTime, String directions,
                    List<String> ingredients, int bookmarked, String viewToShow) {
        mRecipeId = recipeId;
        mImage = image;
        mSourceTitle = sourceTitle;
        mTitle = title;
        mNotes = notes;
        mCategory = category;
        mSourceUrl = sourceUrl;
        mServings = servings;
        mCookTime = cookTime;
        mDirections = directions;
        mIngredients = ingredients;
        mBookmarked = bookmarked;
        mViewToShow = viewToShow;
    }

    public long getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(long recipeId) {
        mRecipeId = recipeId;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getSourceTitle() {
        return mSourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        mSourceTitle = sourceTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDirections() {
        return mDirections;
    }

    public void setDirections(String directions) {
        mDirections = directions;
    }

    public List<String> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<String> ingredients) {
        mIngredients = ingredients;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getSourceUrl() {
        return mSourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        mSourceUrl = sourceUrl;
    }

    public Float getServings() {
        return mServings;
    }

    public void setServings(Float servings) {
        mServings = servings;
    }

    public Float getCookTime() {
        return mCookTime;
    }

    public void setCookTime(Float cookTime) {
        mCookTime = cookTime;
    }

    public int getBookmarked() {
        return mBookmarked;
    }

    public void setBookmarked(int bookmarked) {
        mBookmarked = bookmarked;
    }

    public String getViewToShow() {
        return mViewToShow;
    }

    public void setViewToShow(String viewToShow) {
        mViewToShow = viewToShow;
    }
}
