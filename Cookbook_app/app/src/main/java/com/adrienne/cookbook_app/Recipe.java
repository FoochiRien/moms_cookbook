package com.adrienne.cookbook_app;

import java.util.ArrayList;

/**
 * Created by Admin on 5/21/17.
 */

public class Recipe {

    String mImage, mSourceTitle,mTitle, mNotes, mCategory, mSourceUrl;
    Float mServings, mCookTime;
    ArrayList<String> mIngredients, mDirections;

    public Recipe(String image, String sourceTitle, String title, String notes, String category,
                  String sourceUrl, Float servings, Float cookTime, ArrayList<String> ingredients,
                  ArrayList<String> directions) {
        mImage = image;
        mSourceTitle = sourceTitle;
        mTitle = title;
        mNotes = notes;
        mCategory = category;
        mSourceUrl = sourceUrl;
        mServings = servings;
        mCookTime = cookTime;
        mIngredients = ingredients;
        mDirections = directions;
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

    public ArrayList<String> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        mIngredients = ingredients;
    }

    public ArrayList<String> getDirections() {
        return mDirections;
    }

    public void setDirections(ArrayList<String> directions) {
        mDirections = directions;
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
}
