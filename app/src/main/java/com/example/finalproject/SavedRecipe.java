package com.example.finalproject;

import java.io.Serializable;

/**
 * Object representing recipe information stored in storage
 */
public class SavedRecipe implements Serializable {
    // name of the recipe
    private String name;

    // recipe
    private Recipe recipe;

    // nutrition info
    private NutritionFacts facts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public NutritionFacts getFacts() {
        return facts;
    }

    public void setFacts(NutritionFacts facts) {
        this.facts = facts;
    }

    @Override
    public String toString() {
        return name;
    }
}
