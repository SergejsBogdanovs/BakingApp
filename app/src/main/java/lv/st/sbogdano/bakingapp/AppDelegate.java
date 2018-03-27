package lv.st.sbogdano.bakingapp;

import android.app.Application;

public class AppDelegate extends Application {


    private static AppDelegate sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //Stetho.initializeWithDefaults(this);
    }

    public static AppDelegate getsInstance() {
        return sInstance;
    }

}
