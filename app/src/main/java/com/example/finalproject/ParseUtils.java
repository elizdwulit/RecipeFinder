package com.example.finalproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to parse information from other information
 */
public class ParseUtils {

    // Pattern containing valid measurements
    private static final Pattern validMeasurementsPattern = Pattern.compile("\\s(tb|tbsp|ts|tsp|c|cup|oz|-oz|g)\\s", Pattern.CASE_INSENSITIVE);

    /**
     * Get an amount of an ingredient in grams
     * @param ingredientStr ingredient to parse
     * @return amount of ingredient in grams
     */
    public static double getIngredientsAmtInGrams(String ingredientStr) {
        // get the start and end index of the measurement portion of an ingredient line
        int measurementStartIndx = 0;
        int measurementEndIndx = -1;
        Matcher matcher = validMeasurementsPattern.matcher(ingredientStr);
        if (matcher.find()) { // find the first instance of the measurement
            measurementStartIndx = matcher.start();
            measurementEndIndx = matcher.end() - 1;
        }

        // if ingredient is not an accepted measurement, store it as -1 in the map for future info
        if (measurementEndIndx == -1) {
            return -1;
        }

        // split the string to get measurement type and the amount of the measurement
        String measurementType = ingredientStr.substring(measurementStartIndx, measurementEndIndx + 1).toLowerCase().trim();
        String measurementValStr = ingredientStr.substring(0, measurementStartIndx).trim();
        double measurementVal = 0;
        try {
            // handle fractions and parse the measurement value into a decimal value
            String[] measureValSplit = measurementValStr.split(" ");
            for (String val : measureValSplit) {
                if (val.contains("/")) {
                    String[] rat = val.split("/");
                    measurementVal += Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
                } else {
                    measurementVal += Double.parseDouble(measurementValStr);
                }
            }
        } catch(NumberFormatException e) {
            // if not a number, store it as -1 in the map for future info
            return -1;
        }

        // convert the amount of a measurement into grams and return it
        return convertMeasurementToGrams(measurementType.trim(), measurementVal);
    }

    /**
     * Convert a given amount to grams
     * @param measurementType type of measurement (ex: tbsp)
     * @param val number of the given measurement type
     * @return amount in grams
     */
    private static double convertMeasurementToGrams(String measurementType, double val) {
        double multiplyVal = 1;
        switch(measurementType.toLowerCase()) {
            case "tb":
            case "tbsp":
                multiplyVal = 14.3;
                break;
            case "ts":
            case "tsp":
                multiplyVal = 4.9;
                break;
            case "c":
            case "cup":
                multiplyVal = 250;
                break;
            case "oz":
                multiplyVal = 28.3;
                break;
            case "g":
                multiplyVal = 1;
                break;
            default:
                multiplyVal = 0;
                break;
        }
        return val * multiplyVal; // return the converted the measurement value
    }
}
