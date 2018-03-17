package lv.st.sbogdano.bakingapp.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;

@Dao
public interface RecipesDao {

    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeEntry>> getRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RecipeEntry> recipeEntries);
}
