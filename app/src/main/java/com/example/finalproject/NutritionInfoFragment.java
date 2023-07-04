package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

/**
 * Fragment for the screen that shows nutrition info for a recipe
 */
public class NutritionInfoFragment extends Fragment {

    // the Nutrition info represented in the fragment
    NutritionFacts nutritionFacts;

    // format for decimals
    DecimalFormat decFormat = new DecimalFormat("##.00");

    // UI elements
    TextView sugarTextview;
    TextView fiberTextview;
    TextView sodiumTextview;
    TextView potassiumTextview;
    TextView satFatTextview;
    TextView totalFatTextview;
    TextView caloriesTextview;
    TextView cholesterolTextview;
    TextView proteinTextview;
    TextView carbsTextview;

    /**
     * Empty constructor
     */
    public NutritionInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nutrition_info, container, false);

        // get the nutrition facts
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            nutritionFacts = (NutritionFacts) bundle.getSerializable(RecipeActivity.NUTRITION_KEY);
        }

        // find the ui elements
        sugarTextview = rootView.findViewById(R.id.sugar_val);
        fiberTextview = rootView.findViewById(R.id.fiber_val);
        sodiumTextview = rootView.findViewById(R.id.sodium_val);
        potassiumTextview = rootView.findViewById(R.id.potassium_val);
        satFatTextview = rootView.findViewById(R.id.sat_fat_val);
        totalFatTextview = rootView.findViewById(R.id.total_fat_val);
        caloriesTextview = rootView.findViewById(R.id.calories_val);
        cholesterolTextview = rootView.findViewById(R.id.chol_val);
        proteinTextview = rootView.findViewById(R.id.protein_val);
        carbsTextview = rootView.findViewById(R.id.carbs_val);

        // set the text on the screen
        if (nutritionFacts != null) {
            sugarTextview.setText(decFormat.format(nutritionFacts.getSugarInG()) + "g");
            fiberTextview.setText(decFormat.format(nutritionFacts.getFiberInG()) + "g");
            sodiumTextview.setText(decFormat.format(nutritionFacts.getSodiumInMg()) + "mg");
            potassiumTextview.setText(decFormat.format(nutritionFacts.getPotassiumInMg()) + "mg");
            satFatTextview.setText(decFormat.format(nutritionFacts.getSatFatInG()) + "g");
            totalFatTextview.setText(decFormat.format(nutritionFacts.getTotalFatInG()) + "g");
            caloriesTextview.setText(String.valueOf(Math.ceil(nutritionFacts.getCalories())));
            cholesterolTextview.setText(decFormat.format(nutritionFacts.getCholesterolInMg()) + "mg");
            proteinTextview.setText(decFormat.format(nutritionFacts.getProteinInG()) + "g");
            carbsTextview.setText(decFormat.format(nutritionFacts.getCarbsInG()) + "g");
        }

        return rootView;
    }
}