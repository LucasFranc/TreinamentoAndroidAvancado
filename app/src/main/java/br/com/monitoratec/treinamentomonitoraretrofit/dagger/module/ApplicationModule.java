package br.com.monitoratec.treinamentomonitoraretrofit.dagger.module;

import android.app.Application;
import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lucasfranco on 12/01/17.
 */

@Module
public class ApplicationModule {

    private Application application;

        public ApplicationModule(Application application){
            this.application=application;
        }

    @Provides
    @Singleton
    Application providesApplication() { return application; }

    @Provides
    @Singleton
    Context providesContext(Application app) { return app.getBaseContext(); }

    @Provides
    @Singleton
    LocationManager providesLocationManager(Context context){
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

}
