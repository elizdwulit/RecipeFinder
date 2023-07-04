package com.example.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Task to get nutrition info from Rapid API using "Calorie Ninjas"
 */
public final class AddNutritionInfoTask extends AsyncTask<Recipe, Integer, NutritionFacts> {

    // the activity that created the task
    MainActivity srcActivity;

    // get the recipe corresponding to the nutrition facts
    Recipe recipe;

    // endpoint used to get a recipe
    private static final String GET_NUTRITION_ENDPOINT = "https://calorieninjas.p.rapidapi.com/v1/nutrition";

    /**
     * Constructor
     * @param activity activity the task was called from
     */
    public AddNutritionInfoTask(MainActivity activity) {
        this.srcActivity = activity;
    }

    @Override
    protected NutritionFacts doInBackground(Recipe... recipes) {
        if (recipes == null || recipes.length < 1) {
            Log.d("getNutritionInfo", "no keyword provided by user");
            return null;
        }

        recipe = recipes[0];
        NutritionFacts totalIngredientFacts = new NutritionFacts();

        // Note: the ingredients list has a lot of details other than the ingredient name,
        // but calorieninja api filters out the extra text
        List<String> ingredientsList = recipe.getIngredients();
        for (String ingredient : ingredientsList) {
            ingredient = ingredient.replaceAll(";", ""); // semicolon causes issue with api
            String searchUrl = GET_NUTRITION_ENDPOINT + "?query=" + ingredient;
            try {
                URL url = new URL(searchUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("X-RapidAPI-Key", "<put rapid api key here>");
                conn.setRequestProperty("X-RapidAPI-Host", "calorieninjas.p.rapidapi.com");
                conn.setDoInput(true);
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.connect();
                int resCode = conn.getResponseCode();
                Log.d("getNutritionInfo", "response code=>" + conn.getResponseCode());

                // proceed if successful api call and get the returned nutrition info
                String findResults = "";
                if (resCode == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line  = br.readLine()) != null) {
                        findResults += line.trim();
                    }

                } else {
                    Log.e("getNutritionInfo", "Error getting nutrition info");
                    return null;
                }

                // for each new ingredient, get new nutrition facts and add to existing nutrition facts
                double ingredientAmt = ParseUtils.getIngredientsAmtInGrams(ingredient);
                if (ingredientAmt != -1) {
                    NutritionFacts ingredientFacts = JSONUtils.getNutritionFromAPIResults(findResults); // get the facts for curr ingredient
                    if (ingredientFacts != null) {
                        // add to add to existing nutrition facts
                        totalIngredientFacts.setSugarInG(totalIngredientFacts.getSugarInG()
                                + getAmountForRecipe(ingredientFacts.getServingSizeInG(), ingredientFacts.getSugarInG(), ingredientAmt));
                        totalIngredientFacts.setFiberInG(totalIngredientFacts.getFiberInG()
                                + getAmountForRecipe(ingredientFacts.getServingSizeInG(), ingredientFacts.getFiberInG(), ingredientAmt));
                        totalIngredientFacts.setSodiumInMg(totalIngredientFacts.getSodiumInMg()
                                + getAmountForRecipe(ingredientFacts.getServingSizeInG(), ingredientFacts.getSodiumInMg(), ingredientAmt));
                        totalIngredientFacts.setPotassiumInMg(totalIngredientFacts.getPotassiumInMg()
                                + getAmountForRecipe(ingredientFacts.getServingSizeInG(), ingredientFacts.getPotassiumInMg(), ingredientAmt));
                        totalIngredientFacts.setSatFatInG(totalIngredientFacts.getSatFatInG()
                                + getAmountForRecipe(ingredientFacts.getServingSizeInG(), ingredientFacts.getSatFatInG(), ingredientAmt));
                        totalIngredientFacts.setTotalFatInG(totalIngredientFacts.getTotalFatInG()
                                + getAmountForRecipe(ingredientFacts.getServingSizeInG(), ingredientFacts.getTotalFatInG(), ingredientAmt));
                        totalIngredientFacts.setCalories(totalIngredientFacts.getCalories()
                                + getAmountForRecipe(ingredientFacts.getServingSizeInG(), ingredientFacts.getCalories(), ingredientAmt));
                        totalIngredientFacts.setCholesterolInMg(totalIngredientFacts.getCholesterolInMg()
                                + getAmountForRecipe(ingredientFacts.getServingSizeInG(), ingredientFacts.getCholesterolInMg(), ingredientAmt));
                        totalIngredientFacts.setProteinInG(totalIngredientFacts.getProteinInG()
                                + getAmountForRecipe(ingredientFacts.getServingSizeInG(), ingredientFacts.getProteinInG(), ingredientAmt));
                        totalIngredientFacts.setCarbsInG(totalIngredientFacts.getCarbsInG()
                                + getAmountForRecipe(ingredientFacts.getServingSizeInG(), ingredientFacts.getCarbsInG(), ingredientAmt));
                    } else {
                        Log.e("getNutritionInfo", "Could not get nutrition info from api results");
                    }
                } else {
                    Log.e("getNutritionInfo", "Could not get ingredient amount in grams for: " + ingredient);
                }

            } catch (Exception e) {
                Log.e("getNutritionInfo", "Exception getting nutrition info: " + e.getMessage());
            }
        }
        return totalIngredientFacts;
    }

    @Override
    protected void onPostExecute(NutritionFacts totalFacts) {
        Log.d("getNutritionInfo", "enter postExecute");

        if (totalFacts != null) { // if found facts, start the recipe info page activity
            Intent intent = new Intent(srcActivity, RecipeActivity.class);
            intent.putExtra(RecipeActivity.RECIPE_KEY, recipe); // pass in recipe
            intent.putExtra(RecipeActivity.NUTRITION_KEY, totalFacts); // pass in nutrition facts
            srcActivity.dismissSpinner(); // close the 'searching' spinner
            srcActivity.startActivity(intent);
        } else { // if couldn't get nutrition facts, show general error toast
            srcActivity.dismissSpinner(); // close the 'searching' spinner
            String toastText = srcActivity.getResources().getString(R.string.problem_getting_recipe);
            Toast.makeText(srcActivity, toastText, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Get the amount of a property given a ratio
     * @param nutritionServingSize serving size of nutrition info from api
     * @param nutritionAmount amount of value in nutrition info
     * @param recipeServingSize serving size of recipe
     * @return a measurement of how much of something is in a recipe property
     */
    private double getAmountForRecipe(double nutritionServingSize, double nutritionAmount, double recipeServingSize) {
        return (nutritionAmount * recipeServingSize)/nutritionServingSize;
    }
}