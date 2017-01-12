package br.com.monitoratec.treinamentomonitoraretrofit;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by lucasfranco on 12/01/17.
 */

public abstract class MySubscriber<T> extends Subscriber<T> {

    public static  final String TAG = MySubscriber.class.getSimpleName();

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {
        Log.d(TAG,e.getMessage(),e);
        onError(e.getMessage());
    }

    public abstract void onError(String message);

}
