package com.example.finalproject;

import java.io.Serializable;

/**
 * Nutrition Facts
 */
public class NutritionFacts implements Serializable {
    // amount of sugar in grams
    private double sugarInG = 0;

    // amount of fiber in grams
    private double fiberInG = 0;

    // the serving size the other measurements map to (in grams)
    private double servingSizeInG = 0;

    // amount of sodium in milligrams
    private double sodiumInMg = 0;

    // amount of potassium in milligrams
    private double potassiumInMg = 0;

    // amount of saturated fat in grams
    private double satFatInG = 0;

    // total amount of fat in grams
    private double totalFatInG = 0;

    // number of calories
    private double calories = 0;

    // amount of cholesterol in milligrams
    private double cholesterolInMg = 0;

    // amount of protein in grams
    private double proteinInG = 0;

    // amount of carbs in grams
    private double carbsInG = 0;

    public double getSugarInG() {
        return sugarInG;
    }

    public void setSugarInG(double sugarInG) {
        this.sugarInG = sugarInG;
    }

    public double getFiberInG() {
        return fiberInG;
    }

    public void setFiberInG(double fiberInG) {
        this.fiberInG = fiberInG;
    }

    public double getServingSizeInG() {
        return servingSizeInG;
    }

    public void setServingSizeInG(double servingSizeInG) {
        this.servingSizeInG = servingSizeInG;
    }

    public double getSodiumInMg() {
        return sodiumInMg;
    }

    public void setSodiumInMg(double sodiumInMg) {
        this.sodiumInMg = sodiumInMg;
    }

    public double getPotassiumInMg() {
        return potassiumInMg;
    }

    public void setPotassiumInMg(double potassiumInMg) {
        this.potassiumInMg = potassiumInMg;
    }

    public double getSatFatInG() {
        return satFatInG;
    }

    public void setSatFatInG(double satFatInG) {
        this.satFatInG = satFatInG;
    }

    public double getTotalFatInG() {
        return totalFatInG;
    }

    public void setTotalFatInG(double totalFatInG) {
        this.totalFatInG = totalFatInG;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getCholesterolInMg() {
        return cholesterolInMg;
    }

    public void setCholesterolInMg(double cholesterolInMg) {
        this.cholesterolInMg = cholesterolInMg;
    }

    public double getProteinInG() {
        return proteinInG;
    }

    public void setProteinInG(double proteinInG) {
        this.proteinInG = proteinInG;
    }

    public double getCarbsInG() {
        return carbsInG;
    }

    public void setCarbsInG(double carbsInG) {
        this.carbsInG = carbsInG;
    }
}
