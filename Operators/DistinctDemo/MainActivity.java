package com.example.asoft.distinctdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "myApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Man m1 = new Man("OLEG", 23);
        Man m2 = new Man("PASHA", 22);
        Man m3 = new Man("TOLIAN", 53);


        Observable<Man> numbersObservable = Observable.just(m1, m2, m3, m2, m1, m2, m3, m3, m3);

        numbersObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .distinct()

                .subscribe(new Observer<Man>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Man man) {
                        Log.i(TAG, "onNext: " + man.toString());
                        /*
2019-12-29 11:40:49.280 5322-5322/? I/myApp: onNext: Man{name='OLEG', age=23}
2019-12-29 11:40:49.280 5322-5322/? I/myApp: onNext: Man{name='PASHA', age=22}
2019-12-29 11:40:49.280 5322-5322/? I/myApp: onNext: Man{name='TOLIAN', age=53}
                        */
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
