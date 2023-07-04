package com.example.finalproject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class to parse data from a recipe json
 */
public class JSONUtils {

    /**
     * Convert recipe-by-api-ninjas api results string to recipe object
     * @param apiResults string to get recipe from
     * @return recipe if found, else null
     */
    public static Recipe getRecipeFromAPIResults(String apiResults) {
        Recipe recipe = new Recipe();
        try {
            JSONArray jsonArr = new JSONArray(apiResults);
            // check that there is at least 1 jsonObj to generate a recipe for
            if (jsonArr == null || jsonArr.length() <= 0) {
                Log.d("getRecipeObj", "No recipe found");
                return null;
            }

            // create recipe object
            JSONObject obj = jsonArr.getJSONObject(0); // get the first found recipe
            String titleStr = obj.getString("title");
            String ingredientsStr = obj.getString("ingredients");
            String servingsStr = obj.getString("servings");
            String instructionsStr = obj.getString("instructions");
            List<String> ingredientsList = Arrays.asList(ingredientsStr.split("\\|"));
            List<String> instructionsList = Arrays.asList(instructionsStr.split("\\."));

            recipe.setTitle(titleStr);
            recipe.setIngredients(ingredientsList);
            recipe.setInstructions(instructionsList);
            recipe.setServingSize(servingsStr);
            return recipe;
        } catch (Exception e) {
            Log.e("getRecipeApiRes", "Exception getting recipe from api results: " + e.getMessage());
        }
        return null;
    }

    /**
     *  Convert caloriesninjas api results string to NutritionFacts object
     * @param apiResults string to get nutrition facts from
     * @return corresponding NutritionFacts object
     */
    public static NutritionFacts getNutritionFromAPIResults(String apiResults) {
        try {
            JSONObject jsonObject = new JSONObject(apiResults);
            JSONArray jsonArr = jsonObject.getJSONArray("items");
            // check that there is at least 1 jsonObj to generate a recipe for
            if (jsonArr == null || jsonArr.length() <= 0) {
                Log.d("getRecipeObj", "No recipe found");
                return null;
            }

            // get values from api results
            JSONObject itemsObj = jsonArr.getJSONObject(0); // get the first found recipe
            int sugar = itemsObj.getInt("sugar_g");
            int fiber = itemsObj.getInt("fiber_g");
            int servingSize = itemsObj.getInt("serving_size_g");
            int sodium = itemsObj.getInt("sodium_mg");
            int potassium = itemsObj.getInt("potassium_mg");
            int satFat = itemsObj.getInt("fat_saturated_g");
            int totalFat = itemsObj.getInt("fat_total_g");
            int calories = itemsObj.getInt("calories");
            int chol = itemsObj.getInt("cholesterol_mg");
            int protein = itemsObj.getInt("protein_g");
            int carbs = itemsObj.getInt("carbohydrates_total_g");

            // create nutrition facts obj
            NutritionFacts facts = new NutritionFacts();
            facts.setSugarInG(sugar);
            facts.setFiberInG(fiber);
            facts.setServingSizeInG(servingSize);
            facts.setSodiumInMg(sodium);
            facts.setPotassiumInMg(potassium);
            facts.setSatFatInG(satFat);
            facts.setTotalFatInG(totalFat);
            facts.setCalories(calories);
            facts.setCholesterolInMg(chol);
            facts.setProteinInG(protein);
            facts.setCarbsInG(carbs);
            return facts;
        } catch (Exception e) {
            Log.e("getUserApiRes", "Exception getting user from api results: " + e.getMessage());
        }
        return null;
    }
}
