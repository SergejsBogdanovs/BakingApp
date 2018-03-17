package lv.st.sbogdano.bakingapp.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;

@Dao
public interface StepsDao {

    @Query("SELECT * FROM steps")
    LiveData<List<StepEntry>> getSteps();

    @Query("SELECT * FROM steps WHERE recipeId = :recipeId")
    LiveData<List<StepEntry>> getStepsForRecipe(int recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<StepEntry> stepEntries);
}
