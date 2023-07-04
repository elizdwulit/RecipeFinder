package com.example.finalproject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Class representing a recipe
 */
public class Recipe implements Serializable {
    // the name of the recipe
    private String title;

    // serving size
    private String servingSize;

    // list of ingredients and measurements
    private List<String> ingredients;

    // Map of ingredients and values
    private Map<Integer, String> ingredientsMeasurements;

    // list of instructions to make recipe
    private List<String> instructions;

    // nutrition facts of recipe
    private NutritionFacts nutritionFacts;

    /**
     * Empty Constructor
     */
    public Recipe() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<Integer, String> getIngredientsMeasurements() {
        return ingredientsMeasurements;
    }

    public void setIngredientsMeasurements(Map<Integer, String> ingredientsMeasurements) {
        this.ingredientsMeasurements = ingredientsMeasurements;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public NutritionFacts getNutritionFacts() {
        return nutritionFacts;
    }

    public void setNutritionFacts(NutritionFacts nutritionFacts) {
        this.nutritionFacts = nutritionFacts;
    }
}
