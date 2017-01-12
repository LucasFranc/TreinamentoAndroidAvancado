package br.com.monitoratec.treinamentomonitoraretrofit;

import android.app.Application;

import br.com.monitoratec.treinamentomonitoraretrofit.dagger.module.ApplicationModule;
import br.com.monitoratec.treinamentomonitoraretrofit.dagger.module.DaggerDiComponent;
import br.com.monitoratec.treinamentomonitoraretrofit.dagger.module.DiComponent;

/**
 * Created by lucasfranco on 12/01/17.
 */

public class MyApplication extends Application {

    private DiComponent mDiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mDiComponent = DaggerDiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public DiComponent getDaggerDiComponent() {
        return mDiComponent;
    }
}
