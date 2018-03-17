package lv.st.sbogdano.bakingapp.util;

import java.util.ArrayList;
import java.util.List;

import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;
import lv.st.sbogdano.bakingapp.data.model.Ingredient;
import lv.st.sbogdano.bakingapp.data.model.Recipe;
import lv.st.sbogdano.bakingapp.data.model.Step;

public class RecipeTransformer {

    public static List<RecipeEntry> transformRecipes(List<Recipe> item) {
        List<RecipeEntry> recipeEntries = new ArrayList<>();

        for (Recipe recipe : item) {
            int id = recipe.getId();
            String name = recipe.getName();
            int servings = recipe.getServings();
            String image = recipe.getImage();

            recipeEntries.add(new RecipeEntry(id, name, servings, image));
        }

        return recipeEntries;
    }

    public static List<StepEntry> transformSteps(List<Recipe> item) {

        List<StepEntry> stepEntries = new ArrayList<>();

        for (int i = 0; i < item.size(); i++) {

            List<Step> steps = item.get(i).getSteps();
            int recipeId = item.get(i).getId();

            for (int j = 0; j < steps.size(); j++) {
                int stepId = steps.get(j).getStepId();
                String shortDescription = steps.get(j).getShortDescription();
                String description = steps.get(j).getDescription();
                String videoURL = steps.get(j).getVideoURL();
                String thumbnailURL = steps.get(j).getThumbnailURL();

                stepEntries.add(new StepEntry(stepId, shortDescription, description, videoURL, thumbnailURL, recipeId));
            }
        }

        return stepEntries;
    }

    public static List<IngredientEntry> transformIngredients(List<Recipe> item) {
        List<IngredientEntry> ingredientEntries = new ArrayList<>();

        for (int i = 0; i < item.size(); i++) {

            List<Ingredient> ingredients = item.get(i).getIngredients();
            int recipeId = item.get(i).getId();

            for (int j = 0; j < ingredients.size(); j++) {
                Float quantity = ingredients.get(j).getQuantity();
                String measure = ingredients.get(j).getMeasure();
                String ingredient = ingredients.get(j).getIngredient();

                ingredientEntries.add(new IngredientEntry(quantity, measure, ingredient, recipeId));
            }
        }

        return ingredientEntries;
    }
}
