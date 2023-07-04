package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Task to get a recipe from an api
 */
public final class GetRecipeTask extends AsyncTask<String, Integer, Recipe> {

    // the activity that created the task
    MainActivity srcActivity;

    // endpoint used to get a recipe
    private static final String GET_RECIPE_ENDPOINT = "https://recipe-by-api-ninjas.p.rapidapi.com/v1/recipe";

    /**
     * Constructor
     * @param activity activity the task was called from
     */
    public GetRecipeTask(MainActivity activity) {
        this.srcActivity = activity;
    }

    @Override
    protected Recipe doInBackground(String... strings) {
        if (strings == null || strings.length < 1) {
            Log.d("getRecipe", "no keyword provided by user");
            return null;
        }

        // get the word to search for
        String wordToSearch = strings[0];

        // build the request url for finding a recipe
        String searchUrl = GET_RECIPE_ENDPOINT + "?query=" + wordToSearch;
        try {
            URL url = new URL(searchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-RapidAPI-Key", "<put rapid api key here>");
            conn.setRequestProperty("X-RapidAPI-Host", "recipe-by-api-ninjas.p.rapidapi.com");
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            int resCode = conn.getResponseCode();
            Log.d("getRecipe", "response code=>" + conn.getResponseCode());

            // proceed if successful api call and read the call's results
            String findResults = "";
            if (resCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line  = br.readLine()) != null) {
                    findResults += line.trim();
                }
            } else {
                Log.e("getRecipe", "Error reading the returned recipe info");
                return null;
            }

            // build and return recipe object
            Recipe recipe = JSONUtils.getRecipeFromAPIResults(findResults);
            return recipe;

        } catch (Exception e) {
            Log.e("getRecipe", "Exception getting recipe: " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Recipe recipe) {
        //Log.d("getRecipe", "enter postExecute");

        // if recipe found, go to find the nutrition facts
        if (recipe != null) {
            new AddNutritionInfoTask(srcActivity).execute(recipe);
        } else { // if couldn't get recipe, show general error toast
            srcActivity.dismissSpinner(); // close the 'searching' spinner
            String toastText = srcActivity.getResources().getString(R.string.problem_getting_recipe);
            Toast.makeText(srcActivity, toastText, Toast.LENGTH_SHORT).show();
        }
    }
}