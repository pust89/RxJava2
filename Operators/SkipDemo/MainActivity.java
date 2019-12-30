package com.example.asoft.skipdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "myApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<Integer> numbersObservable = Observable.just(1, 2, 3, 7, 5, 3, 5,  4, 4, 6);

        numbersObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skip(6)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "came to onNext " + integer);
                        /*
2019-12-29 11:45:02.480 5692-5692/com.example.asoft.skipdemo I/myApp: came to onNext 5
2019-12-29 11:45:02.480 5692-5692/com.example.asoft.skipdemo I/myApp: came to onNext 4
2019-12-29 11:45:02.480 5692-5692/com.example.asoft.skipdemo I/myApp: came to onNext 4
2019-12-29 11:45:02.480 5692-5692/com.example.asoft.skipdemo I/myApp: came to onNext 6
2019-12-29 11:45:02.480 5692-5692/com.example.asoft.skipdemo I/myApp: came to onComplete
                         */
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        Log.i(TAG, "came to onComplete ");
                    }
                });


    }
}
