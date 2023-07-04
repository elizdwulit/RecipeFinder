package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    // ui edittext
    EditText editText;

    // variables for progress spinner dialog
    ProgressDialog spinnerDialog;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar customToolbar = findViewById(R.id.activitymain_custom_toolbar);
        setSupportActionBar(customToolbar);

        editText = findViewById(R.id.mainactivity_edittext);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dismissSpinner();
    }

    /**
     * Dismiss the spinner dialog
     */
    public void dismissSpinner() {
        // close the spinner dialog if it was previously open
        if (spinnerDialog != null) {
            spinnerDialog.dismiss();
        }
    }

    /**
     * Trigger recipe search task
     * @param v view
     */
    public void searchForRecipe(View v) {
        String searchWord = editText.getText().toString();
        // search for recipe with api if user provided search word(s)
        if (!searchWord.trim().equals("")) {
            // show dialog while looking up lyrics
            spinnerDialog = new ProgressDialog(this);
            String searchingStr = this.getResources().getString(R.string.searching_str);
            spinnerDialog.setMessage(searchingStr);
            spinnerDialog.setCancelable(false);
            spinnerDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            spinnerDialog.show();
            new GetRecipeTask(this).execute(searchWord);
        } else { // else, show message to user to input text
            String toastText = this.getResources().getString(R.string.please_enter_search_str);
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Go to activity to find previously searched recipes
     * @param v
     */
    public void findRecipe(View v) {
        Intent intent = new Intent(MainActivity.this, RecipeHistoryActivity.class);
        startActivity(intent);
    }
}