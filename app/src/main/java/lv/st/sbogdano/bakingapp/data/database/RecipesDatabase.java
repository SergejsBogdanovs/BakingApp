package lv.st.sbogdano.bakingapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import lv.st.sbogdano.bakingapp.data.database.dao.IngredientsDao;
import lv.st.sbogdano.bakingapp.data.database.dao.RecipesDao;
import lv.st.sbogdano.bakingapp.data.database.dao.StepsDao;
import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;

@Database(entities = {RecipeEntry.class, IngredientEntry.class, StepEntry.class}, version = 1)
public abstract class RecipesDatabase extends RoomDatabase{

    private static final String DATABASE_NAME = "recipes";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    protected static RecipesDatabase sInstance;

    public static RecipesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        RecipesDatabase.class,
                        RecipesDatabase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract RecipesDao recipesDao();
    public abstract IngredientsDao ingredientsDao();
    public abstract StepsDao stepsDao();

}
