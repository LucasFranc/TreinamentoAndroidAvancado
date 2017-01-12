package br.com.monitoratec.treinamentomonitoraretrofit;

import android.support.v7.app.AppCompatActivity;

import br.com.monitoratec.treinamentomonitoraretrofit.dagger.module.DiComponent;

/**
 * Created by lucasfranco on 12/01/17.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected MyApplication getMyApplication(){
     return (MyApplication) getApplication();
    }

    protected DiComponent getDaggerDiComponent(){
        return this.getMyApplication().getDaggerDiComponent();
    }


}
