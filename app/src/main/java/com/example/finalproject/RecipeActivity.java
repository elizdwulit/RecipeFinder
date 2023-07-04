package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity holding TabLayout, Recipe info fragment, and nutrition info fragment
 */
public class RecipeActivity extends AppCompatActivity {

    // keys used to pass objects through activity and fragments
    public static final String RECIPE_KEY = "RECIPE_INFO";
    public static final String NUTRITION_KEY = "NUTRITION_INFO";

    // keys used to store info in storage
    public static final String SAVED_RECIPES_KEY = "SAVED_RECIPES";

    // UI elements in the recipe activity
    private TabLayout tabLayout;
    private ViewPager viewPager;

    // info
    private Recipe recipe;
    private NutritionFacts nutritionFacts;

    // gson instance
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);



        // set up the tab layout
        tabLayout = findViewById(R.id.recipeact_tablayout);
        viewPager = findViewById(R.id.recipeact_viewpager);
        tabLayout.setupWithViewPager(viewPager);

        // get the recipe and nutrition info
        Intent srcIntent = getIntent();
        recipe = (Recipe) srcIntent.getSerializableExtra(RECIPE_KEY);
        nutritionFacts = (NutritionFacts) srcIntent.getSerializableExtra(NUTRITION_KEY);

        // set up the toolbar
        Toolbar customToolbar = findViewById(R.id.recipeact_custom_toolbar);
        if (recipe != null) { // set the toolbar title
            customToolbar.setTitle(recipe.getTitle());
        }
        setSupportActionBar(customToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // pass the recipe info to the recipe fragment
        Bundle recipeInfoBundle = new Bundle();
        recipeInfoBundle.putSerializable(RECIPE_KEY, recipe);
        RecipeInfoFragment recipeInfoFragment = new RecipeInfoFragment();
        recipeInfoFragment.setArguments(recipeInfoBundle);

        // pass the nutrition facts to the nutrition info fragment
        Bundle nutritionInfoBundle = new Bundle();
        nutritionInfoBundle.putSerializable(NUTRITION_KEY, nutritionFacts);
        NutritionInfoFragment nutritionInfoFragment = new NutritionInfoFragment();
        nutritionInfoFragment.setArguments(nutritionInfoBundle);

        // setup the fragments
        String recipeStr = this.getResources().getString(R.string.recipe_str);
        String nutritionInfoStr = this.getResources().getString(R.string.nutrition_info_str);
        RecipeFragmentAdapter fragmentAdapter = new RecipeFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragmentAdapter.addFragment(recipeInfoFragment, recipeStr);
        fragmentAdapter.addFragment(nutritionInfoFragment, nutritionInfoStr);
        viewPager.setAdapter(fragmentAdapter);
    }

    /**
     * Save the currently opened recipe info in shared preferences
     * @param v view
     */
    public void saveRecipe(View v) {
        try {
            // create a new savedrecipe obj to store
            SavedRecipe newRecipe = new SavedRecipe();
            newRecipe.setName(recipe.getTitle());
            newRecipe.setRecipe(recipe);
            newRecipe.setFacts(nutritionFacts);

            // get the currently saved recipes
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String savedRecipesStr = prefs.getString(RecipeActivity.SAVED_RECIPES_KEY, "");
            Type type = new TypeToken<List<SavedRecipe>>() {}.getType();
            List<SavedRecipe> savedRecipeList = gson.fromJson(savedRecipesStr, type);
            if (savedRecipeList == null) {
                savedRecipeList = new ArrayList<>();
            }

            // add the new recipe to the list if unique name
            boolean alreadyInList = savedRecipeList.stream()
                    .anyMatch(savedRecipe-> savedRecipe.getName().equalsIgnoreCase(newRecipe.getName()));
            if (!alreadyInList) {
                savedRecipeList.add(newRecipe);
            }

            // override the stored key
            SharedPreferences.Editor editor = prefs.edit();
            String newRecipeList = gson.toJson(savedRecipeList);
            editor.putString(SAVED_RECIPES_KEY, newRecipeList);
            editor.commit();

            // show toast to tell user recipe was saved
            String savedStr = this.getResources().getString(R.string.saved_recipe_str);
            Toast.makeText(this, savedStr, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("saveRecipe", "Exception saving recipe");
        }
    }
}