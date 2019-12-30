package com.example.asoft.skiplastdemo;

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

        Observable<Integer> numbersObservable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numbersObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skipLast(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "came to onNext " + integer);
                    }
                    /*

                    skipLast(3)

2019-12-29 11:56:08.189 6231-6231/com.example.asoft.skiplastdemo I/myApp: came to onNext 1
2019-12-29 11:56:08.189 6231-6231/com.example.asoft.skiplastdemo I/myApp: came to onNext 2
2019-12-29 11:56:08.189 6231-6231/com.example.asoft.skiplastdemo I/myApp: came to onNext 3
2019-12-29 11:56:08.189 6231-6231/com.example.asoft.skiplastdemo I/myApp: came to onNext 4
2019-12-29 11:56:08.190 6231-6231/com.example.asoft.skiplastdemo I/myApp: came to onNext 5
2019-12-29 11:56:08.190 6231-6231/com.example.asoft.skiplastdemo I/myApp: came to onNext 6
2019-12-29 11:56:08.190 6231-6231/com.example.asoft.skiplastdemo I/myApp: came to onNext 7
2019-12-29 11:56:08.190 6231-6231/com.example.asoft.skiplastdemo I/myApp: came to onComplete

                    skip(3)
2019-12-29 11:57:23.865 6356-6356/com.example.asoft.skiplastdemo I/myApp: came to onNext 4
2019-12-29 11:57:23.865 6356-6356/com.example.asoft.skiplastdemo I/myApp: came to onNext 5
2019-12-29 11:57:23.865 6356-6356/com.example.asoft.skiplastdemo I/myApp: came to onNext 6
2019-12-29 11:57:23.865 6356-6356/com.example.asoft.skiplastdemo I/myApp: came to onNext 7
2019-12-29 11:57:23.865 6356-6356/com.example.asoft.skiplastdemo I/myApp: came to onNext 8
2019-12-29 11:57:23.866 6356-6356/com.example.asoft.skiplastdemo I/myApp: came to onNext 9
2019-12-29 11:57:23.866 6356-6356/com.example.asoft.skiplastdemo I/myApp: came to onNext 10
2019-12-29 11:57:23.866 6356-6356/com.example.asoft.skiplastdemo I/myApp: came to onComplete
                     */
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
