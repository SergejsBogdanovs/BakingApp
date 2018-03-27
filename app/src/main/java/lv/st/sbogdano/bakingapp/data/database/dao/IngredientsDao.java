package lv.st.sbogdano.bakingapp.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;

@Dao
public interface IngredientsDao {

    @Query("SELECT * FROM ingredients")
    LiveData<List<IngredientEntry>> getIngredients();

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    LiveData<List<IngredientEntry>> getIngredientsForRecipe(int recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<IngredientEntry> ingredientEntries);
}
