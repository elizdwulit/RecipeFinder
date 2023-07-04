# RecipeFinder

## Description
The RecipeFinder app is an Android application that looks up a recipe based on a user's input request, then displays the recipe steps and nutritional information on the screen. In order to get this information, Rapid API is used in conjunction with the "recipe-by-api-ninjas" and "calorie-ninjas" endpoints provided on Rapid API.
Documentation for Recipe by API-Ninjas can be found here: https://rapidapi.com/apininjas/api/recipe-by-api-ninjas
and Documentation for Calorie Ninjas can be found here: https://rapidapi.com/calorieninjas/api/calorieninjas/

## How the App is Used
- When the application is first launched, the user must input a search term for a recipe they want to get information for. 
- Utilizing RapidAPI endpoints, a recipe is found and the information is extracted and displayed to the user.
- On one screen, the recipe steps and serving size is shown. On a separate tab, a list of nutrional information and measurements is provided.
- After searching for a recipe, the user has the option to save the recipe for future reference. A list of saved recipes is available in a separate Activity. Upon clicking one, the screens showing the recipe information are once again shown.

## Note if pulling the code
In order to use this code, the placeholder "X-RapidAPI-Key" request property in BOTH the AddNutritionInfoTask.java file AND the GetRecipeTask.java file MUST BE REPLACED WITH AN ACUTAL API KEY. An API key is provided by Rapid API. Please follow the instructions on Rapid API to generate one.

## Brief Codebase Overview
- NutritionFacts.java: Class for nutrition facts
- Recipe.java: Class representing a recipe
- AddNutritionInfoTask.java: Task to get nutrition info from Rapid API using "Calorie Ninjas"
- GetRecipeTask.java: Task to get a recipe from the "recipe-by-api-ninjas" API
- MainActivity.java: Activity for the home screen
- RecipeActivity.java: Activity holding TabLayout, Recipe info fragment, and nutrition info fragment
- RecipeHistoryActivity.java: Activity for providing a list of previously-searched recipes
- NutritionInfoFragment.java: Fragment for the screen that shows nutrition info for a recipe
- RecipeInfoFragment.java: Fragment containing the recipe information
- RecipeFragmentAdapter.java: Fragment Adapter
- JSONUtils.java: Utility class to parse data from a recipe json
- ParseUtils.java: Utility class to parse information from other information
