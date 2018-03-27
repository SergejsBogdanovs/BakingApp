package lv.st.sbogdano.bakingapp;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import lv.st.sbogdano.bakingapp.data.RecipesRepository;
import lv.st.sbogdano.bakingapp.ui.recipedetail.RecipeDetailsViewModel;
import lv.st.sbogdano.bakingapp.ui.recipes.RecipesViewModel;
import lv.st.sbogdano.bakingapp.util.InjectorUtils;


public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private final RecipesRepository mRecipesRepository;

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(
                            InjectorUtils.provideRecipesRepository(application.getApplicationContext()));
                }
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(RecipesRepository recipesRepository) {
        this.mRecipesRepository = recipesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipesViewModel.class)) {
            return (T) new RecipesViewModel(mRecipesRepository);
        } else if (modelClass.isAssignableFrom(RecipeDetailsViewModel.class)) {
            return (T) new RecipeDetailsViewModel(mRecipesRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }

}
