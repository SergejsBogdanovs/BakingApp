package lv.st.sbogdano.bakingapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class AppDelegate extends Application {


    private static AppDelegate sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        Stetho.initializeWithDefaults(this);
    }

    public static AppDelegate getsInstance() {
        return sInstance;
    }

}
