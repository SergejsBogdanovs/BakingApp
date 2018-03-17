package lv.st.sbogdano.bakingapp.ui.recipes;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import lv.st.sbogdano.bakingapp.data.RecipesRepository;
import lv.st.sbogdano.bakingapp.util.InjectorUtils;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link lv.st.sbogdano.bakingapp.data.RecipesRepository}
 */
public class RecipesViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private static volatile RecipesViewModelFactory INSTANCE;
    private final Application mApplication;

    private final RecipesRepository mRecipesRepository;

    public static RecipesViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (RecipesViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RecipesViewModelFactory(
                            application,
                            InjectorUtils.provideRecipesRepository(application.getApplicationContext()));
                }
            }
        }
        return INSTANCE;
    }

    public RecipesViewModelFactory(Application application, RecipesRepository recipesRepository) {
        this.mRecipesRepository = recipesRepository;
        this.mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipesViewModel.class)) {
            return (T) new RecipesViewModel(mApplication, mRecipesRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }

}
