package com.androidtutz.anushka.rxsubjectdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "myApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //asyncSubjectDemo1();
       //asyncSubjectDemo2();

        //behaviorSubjectDemo1();
        //behaviorSubjectDemo2();

        //publishSubjectDemo1();
//        publishSubjectDemo2();


        //replaySubjectDemo1();
        replaySubjectDemo2();

    }

    /**
     * AsyncSubject only emits the last value of the Observable.
     * <p>
     * AsyncSubject испускает только последнее значение Observable.
     */
    void asyncSubjectDemo1() {

        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        AsyncSubject<String> asyncSubject = AsyncSubject.create();

        observable.subscribe(asyncSubject);

        asyncSubject.subscribe(getFirstObserver());
        asyncSubject.subscribe(getSecondObserver());
        asyncSubject.subscribe(getThirdObserver());

        /*
        AsyncSubject only emits the last value of the observable (JSON)

I:  First Observer onSubscribe
I:  First Observer Received JSON
I:  First Observer onComplete
I:  Second Observer onSubscribe
I:  Second Observer Received JSON
I:  Second Observer onComplete
I:  Third Observer onSubscribe
I:  Third Observer Received JSON
I:  Third Observer onComplete

         */
    }

    void asyncSubjectDemo2() {

        AsyncSubject<String> asyncSubject = AsyncSubject.create();

        asyncSubject.subscribe(getFirstObserver());
        asyncSubject.onNext("JAVA");
        asyncSubject.onNext("KOTLIN");
        asyncSubject.onNext("XML");


        asyncSubject.subscribe(getSecondObserver());
        asyncSubject.onNext("JSON");
        asyncSubject.onComplete();

        asyncSubject.subscribe(getThirdObserver());

        /*
                AsyncSubject only emits the last value of the observable (JSON)


I:  First Observer onSubscribe
I:  Second Observer onSubscribe
I:  First Observer Received JSON
I:  First Observer onComplete
I:  Second Observer Received JSON
I:  Second Observer onComplete
I:  Third Observer onSubscribe
I:  Third Observer Received JSON
I:  Third Observer onComplete
         */


    }

    /**
     * When an Observer subscribes to a behavior subject it emits the most recently emitted item
     * and all the subsequent items.
     * <p>
     * Когда Observer подписывается на BehaviorSubject, он испускает(BehaviorSubject) самый последний
     * испущенный элемент и все последующие элементы.
     */
    void behaviorSubjectDemo1() {

        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();


        behaviorSubject.onNext("TEST #1");
        behaviorSubject.onNext("TEST #2");
        behaviorSubject.onNext("TEST #3");

        behaviorSubject.subscribe(getFirstObserver());
        behaviorSubject.subscribe(getSecondObserver());
        behaviorSubject.subscribe(getThirdObserver());

        observable.subscribe(behaviorSubject);
        /*
        BehaviorSubject отправляет обсерверам ПОСЛЕДНИЙ полученный параметр в своём методе onNext, до того как его подписали
        и все значения observable.



I:  First Observer onSubscribe
I:  First Observer Received TEST #3
I:  Second Observer onSubscribe
I:  Second Observer Received TEST #3
I:  Third Observer onSubscribe
I:  Third Observer Received TEST #3
I:  First Observer Received JAVA
I:  Second Observer Received JAVA
I:  Third Observer Received JAVA
I:  First Observer Received KOTLIN
I:  Second Observer Received KOTLIN
I:  Third Observer Received KOTLIN
I:  First Observer Received XML
I:  Second Observer Received XML
I:  Third Observer Received XML
I:  First Observer Received JSON
I:  Second Observer Received JSON
I:  Third Observer Received JSON
I:  First Observer onComplete
I:  Second Observer onComplete
I:  Third Observer onComplete



        При вызову методово onNext, после подписки, сабджект передаст все обсерверам все.


        behaviorSubject.subscribe(getFirstObserver());
        behaviorSubject.subscribe(getSecondObserver());
        behaviorSubject.subscribe(getThirdObserver());

        observable.subscribe(behaviorSubject);

        behaviorSubject.onNext("TEST #1");
        behaviorSubject.onNext("TEST #2");
        behaviorSubject.onNext("TEST #3");


I:  First Observer onSubscribe
I:  Second Observer onSubscribe
I:  Third Observer onSubscribe
I:  First Observer Received TEST #1
I:  Second Observer Received TEST #1
I:  Third Observer Received TEST #1
I:  First Observer Received TEST #2
I:  Second Observer Received TEST #2
I:  Third Observer Received TEST #2
I:  First Observer Received TEST #3
I:  Second Observer Received TEST #3
I:  Third Observer Received TEST #3
I:  First Observer Received JAVA
I:  Second Observer Received JAVA
I:  Third Observer Received JAVA
I:  First Observer Received KOTLIN
I:  Second Observer Received KOTLIN
I:  Third Observer Received KOTLIN
I:  First Observer Received XML
I:  Second Observer Received XML
I:  Third Observer Received XML
I:  First Observer Received JSON
I:  Second Observer Received JSON
I:  Third Observer Received JSON
I:  First Observer onComplete
I:  Second Observer onComplete
I:  Third Observer onComplete

         */
    }

    void behaviorSubjectDemo2() {

        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

        behaviorSubject.subscribe(getFirstObserver());
        behaviorSubject.onNext("JAVA");
        behaviorSubject.onNext("KOTLIN");
        behaviorSubject.onNext("XML");


        behaviorSubject.subscribe(getSecondObserver());
        behaviorSubject.onNext("JSON");
        behaviorSubject.onNext("TEST000");

        behaviorSubject.onComplete();

        behaviorSubject.subscribe(getThirdObserver());

        /*
        FirstObserver receive JAVA, KOTLIN, XML and JSON AND TEST000
        after that we subscribe secondObserver and he received only XML(last item) and JSON and TEST000

        thirdObserver does not receive any data, because he was subscribed after  behaviorSubject.onComplete();

I:  First Observer onSubscribe
I:  First Observer Received JAVA
I:  First Observer Received KOTLIN
I:  First Observer Received XML
I:  Second Observer onSubscribe
I:  Second Observer Received XML
I:  First Observer Received JSON
I:  Second Observer Received JSON
I:  First Observer Received TEST000
I:  Second Observer Received TEST000
I:  First Observer onComplete
I:  Second Observer onComplete
I:  Third Observer onSubscribe
I:  Third Observer onComplete
         */
    }

    /**
     * Publish Subject emits all the subsequent items of the Observable at the time of subscription.
     * <p>
     * Subject Subject выпускает все последующие элементы Observable во время подписки.
     */
    void publishSubjectDemo1() {
        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        PublishSubject<String> publishSubject = PublishSubject.create();

        observable.subscribe(publishSubject);

        publishSubject.subscribe(getFirstObserver());
        publishSubject.subscribe(getSecondObserver());
        publishSubject.subscribe(getThirdObserver());
        /*
I:  First Observer onSubscribe
I:  Second Observer onSubscribe
I:  Third Observer onSubscribe
I:  First Observer Received JAVA
I:  Second Observer Received JAVA
I:  Third Observer Received JAVA
I:  First Observer Received KOTLIN
I:  Second Observer Received KOTLIN
I:  Third Observer Received KOTLIN
I:  First Observer Received XML
I:  Second Observer Received XML
I:  Third Observer Received XML
I:  First Observer Received JSON
I:  Second Observer Received JSON
I:  Third Observer Received JSON
I:  First Observer onComplete
I:  Second Observer onComplete
I:  Third Observer onComplete
         */

    }

    void publishSubjectDemo2() {

        PublishSubject<String> publishSubject = PublishSubject.create();

        publishSubject.subscribe(getFirstObserver());


        publishSubject.onNext("JAVA");
        publishSubject.onNext("KOTLIN");
        publishSubject.onNext("XML");


        publishSubject.subscribe(getSecondObserver());

        publishSubject.onNext("JSON");
        publishSubject.onNext("TEST000");

        publishSubject.onComplete();

        publishSubject.subscribe(getThirdObserver());
        /*
I:  First Observer onSubscribe

I:  First Observer Received JAVA
I:  First Observer Received KOTLIN
I:  First Observer Received XML

I:  Second Observer onSubscribe

I:  First Observer Received JSON

I:  Second Observer Received JSON
I:  First Observer Received TEST000
I:  Second Observer Received TEST000

I:  First Observer onComplete
I:  Second Observer onComplete
I:  Third Observer onSubscribe
I:  Third Observer onComplete
         */
    }

    /**
     * ReplaySubject emits all the items of the Observable without considering when the subscriber
     * subscribed.
     *
     * ReplaySubject испускает все элементы Observable без учета того, когда подписчик подписался.
     */
    void replaySubjectDemo1(){
        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        ReplaySubject<String> replaySubject = ReplaySubject.create();

        observable.subscribe(replaySubject);

        replaySubject.subscribe(getFirstObserver());
        replaySubject.subscribe(getSecondObserver());
        replaySubject.subscribe(getThirdObserver());
        /*
I:  First Observer onSubscribe
I:  Second Observer onSubscribe
I:  Third Observer onSubscribe
I:  First Observer Received JAVA
I:  Second Observer Received JAVA
I:  Third Observer Received JAVA
I:  First Observer Received KOTLIN
I:  Second Observer Received KOTLIN
I:  Third Observer Received KOTLIN
I:  First Observer Received XML
I:  Second Observer Received XML
I:  Third Observer Received XML
I:  First Observer Received JSON
I:  Second Observer Received JSON
I:  Third Observer Received JSON
I:  First Observer onComplete
I:  Second Observer onComplete
I:  Third Observer onComplete
         */

    }

    void replaySubjectDemo2(){
        ReplaySubject<String> replaySubject = ReplaySubject.create();

        replaySubject.subscribe(getFirstObserver());


        replaySubject.onNext("JAVA");
        replaySubject.onNext("KOTLIN");
        replaySubject.onNext("XML");


        replaySubject.subscribe(getSecondObserver());

        replaySubject.onNext("JSON");
        replaySubject.onNext("TEST000");

        replaySubject.onComplete();

        replaySubject.subscribe(getThirdObserver());
        /*
I:  First Observer onSubscribe
I:  First Observer Received JAVA
I:  First Observer Received KOTLIN
I:  First Observer Received XML

I:  Second Observer onSubscribe
I:  Second Observer Received JAVA
I:  Second Observer Received KOTLIN
I:  Second Observer Received XML
I:  First Observer Received JSON
I:  Second Observer Received JSON
I:  First Observer Received TEST000
I:  Second Observer Received TEST000
I:  First Observer onComplete
I:  Second Observer onComplete

I:  Third Observer onSubscribe
I:  Third Observer Received JAVA
I:  Third Observer Received KOTLIN
I:  Third Observer Received XML
I:  Third Observer Received JSON
I:  Third Observer Received TEST000
I:  Third Observer onComplete
         */
    }
    private Observer<String> getFirstObserver() {

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {


                Log.i(TAG, " First Observer onSubscribe ");
            }

            @Override
            public void onNext(String s) {

                Log.i(TAG, " First Observer Received " + s);

            }

            @Override
            public void onError(Throwable e) {

                Log.i(TAG, " First Observer onError ");
            }

            @Override
            public void onComplete() {

                Log.i(TAG, " First Observer onComplete ");

            }
        };

        return observer;
    }

    private Observer<String> getSecondObserver() {

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {


                Log.i(TAG, " Second Observer onSubscribe ");
            }

            @Override
            public void onNext(String s) {

                Log.i(TAG, " Second Observer Received " + s);

            }

            @Override
            public void onError(Throwable e) {

                Log.i(TAG, " Second Observer onError ");
            }

            @Override
            public void onComplete() {

                Log.i(TAG, " Second Observer onComplete ");

            }
        };

        return observer;
    }

    private Observer<String> getThirdObserver() {

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {


                Log.i(TAG, " Third Observer onSubscribe ");
            }

            @Override
            public void onNext(String s) {

                Log.i(TAG, " Third Observer Received " + s);

            }

            @Override
            public void onError(Throwable e) {

                Log.i(TAG, " Third Observer onError ");
            }

            @Override
            public void onComplete() {

                Log.i(TAG, " Third Observer onComplete ");

            }
        };

        return observer;
    }

}
