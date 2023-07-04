package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for providing a list of previously-searched recipes
 */
public class RecipeHistoryActivity extends AppCompatActivity {

    // Listview
    private ListView recipeListView;

    // List of saved recipes
    private List<SavedRecipe> savedRecipeList;

    // Gson instance
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_history);

        // set up the toolbar
        String titleStr = this.getResources().getString(R.string.prev_recipes_str);
        Toolbar customToolbar = findViewById(R.id.history_custom_toolbar);
        customToolbar.setTitle(titleStr);
        setSupportActionBar(customToolbar);
        // enable back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // get the listview
        recipeListView = findViewById(R.id.history_listview);

        try {
            // get the saved recipes from shared prefs
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String savedRecipesStr = prefs.getString(RecipeActivity.SAVED_RECIPES_KEY, "");
            Type type = new TypeToken<List<SavedRecipe>>() {}.getType();
            savedRecipeList = gson.fromJson(savedRecipesStr, type);
            if (savedRecipeList == null) { // if no shared preferences found, initialize list
                savedRecipeList = new ArrayList<>();
            }

            // setup the list view with recipes
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, savedRecipeList);
            recipeListView.setAdapter(arrayAdapter);
            recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // when item clicked, launch recipe activity with found recipe's recipe and nutrition info
                    SavedRecipe savedRec = savedRecipeList.get(position);
                    Intent intent = new Intent(RecipeHistoryActivity.this, RecipeActivity.class);
                    intent.putExtra(RecipeActivity.RECIPE_KEY, savedRec.getRecipe());
                    intent.putExtra(RecipeActivity.NUTRITION_KEY, savedRec.getFacts());
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            Log.e("getHistory", "Exception getting past recipes: " + e.getMessage());
        }

    }
}