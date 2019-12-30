package com.androidtutz.anushka.rxdemo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    private final static String TAG = "MyTag";

    private String greeting = "Hello From RxJava";

    private Observable<String> mObservable;

    //    private Observer<String> mObserver;
    private DisposableObserver<String> mObserver;
    private DisposableObserver<String> mObserver2;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    //  private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvGreeting);

        mObservable = Observable.just(greeting);

      //  mObservable.subscribeOn(Schedulers.io());


        // mObservable.observeOn(AndroidSchedulers.mainThread());
/*
        mObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: called");
                mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: called");
                textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: called");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: called");
            }
        };

       mObservable.subscribe(mObserver);
*/


        mObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: called");
                textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: called");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: called");
            }
        };

//        mCompositeDisposable.add(mObserver);
//        mObservable.subscribe(mObserver);

        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mObserver));

        mObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: called");
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: called");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: called");
            }
        };

//        mCompositeDisposable.add(mObserver2);
//        mObservable.subscribe(mObserver2);
        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(mObserver2)
        );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        mObserver.dispose();
//        mObserver2.dispose();

        mCompositeDisposable.clear();
    }
}
