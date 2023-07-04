package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Fragment containing the recipe information
 */
public class RecipeInfoFragment extends Fragment {

    // ui elements
    TextView recipeNameTextview;
    TextView servingSizeTextview;
    TextView ingredientsTextview;
    TextView instructionsTextview;

    // recipe
    Recipe recipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_info, container, false);

        // get the recipe
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipe = (Recipe) bundle.getSerializable(RecipeActivity.RECIPE_KEY);
        }

        // find the ui elements
        recipeNameTextview = rootView.findViewById(R.id.recipe_name_textview);
        servingSizeTextview = rootView.findViewById(R.id.serving_size_textview);
        ingredientsTextview = rootView.findViewById(R.id.ingredients_list_textview);
        instructionsTextview = rootView.findViewById(R.id.instructions_list_textview);

        // set the ui elements according to recipe info
        if (recipe != null) {
            recipeNameTextview.setText(recipe.getTitle());
            servingSizeTextview.setText(recipe.getServingSize());

            // add bullets and linebreaks between ingredients
            String ingredients = "";
            List<String> ingredientsList = recipe.getIngredients();
            for (String ingredient : ingredientsList) {
                ingredients += "•" + ingredient.trim() + "\n";
            }
            ingredientsTextview.setText(ingredients);

            // add bullets and linebreaks between instructions
            String instructions = "";
            List<String> instructionsList = recipe.getInstructions();
            for (String instruction : instructionsList) {
                instructions += "• " + instruction.trim() + "\n";
            }
            instructionsTextview.setText(instructions);
        }

        return rootView;
    }
}