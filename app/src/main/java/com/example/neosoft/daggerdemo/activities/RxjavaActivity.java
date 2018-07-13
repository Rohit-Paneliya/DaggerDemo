package com.example.neosoft.daggerdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.neosoft.daggerdemo.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Observable - (producer) - that produce or provide the data.
        Observable<Integer> observable = Observable
                .just(1, 2, 3, 4, 5)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer * integer;
                    }
                });

        // Observer (consumer) - that consume the data.
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onComplete() {
                System.out.println("All data emitted.");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error received: " + e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("New data received: " + integer);
            }
        };

        Disposable disposable = observable
                .subscribeOn(Schedulers.io())       //observable will run on IO thread.
                .observeOn(AndroidSchedulers.mainThread())      //Observer will run on main thread.
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("New disposable data received: " + integer);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("Error!!!!!!!!!!!!!!!!!! ");
                    }
                })
                .subscribe();

        //displayNumbers();
        //scan();
        //windowOperator();
        //bufferOperator();
        //groupByOperator();
        //zipOperator(); - combine two observables response.

    }

    private void zipOperator() {
        final Observable<Integer> ob1 = Observable.just(1, 2, 3, 4);
        final Observable<Integer> ob2 = Observable.just(5, 6, 7, 8);

        Observable.zip(ob1, ob2, new BiFunction<Integer, Integer, Boolean>() {
            @Override
            public Boolean apply(Integer integer, Integer integer2) throws Exception {
                return true;
            }
        }).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean value) {
                Toast.makeText(RxjavaActivity.this, "Got response", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void groupByOperator() {
        Observable.just(1, 2, 3, 4, 8, 7, 6, 5)
                .groupBy(new Function<Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GroupedObservable<Boolean, Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GroupedObservable<Boolean, Integer> value) {
                        if (value.getKey()) {
                            Log.d("-----group  ", "Even numbers");
                        } else {
                            Log.d("-----group  ", "odd numbers");
                        }

                        value.subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Integer value) {
                                Log.d("-----groupby -> ", String.valueOf(value));
                                /*public Boolean apply(Integer integer) throws Exception {
                                    return integer % 2 == 0;
                                }

                                output - [1,3,5,7]
                                         [2,4,6,8]
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

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void bufferOperator() {
        Observable.just(1, 3, 5, 7, 2, 4, 6, 8)
                .buffer(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> value) {
                        Log.d("-----buffer -> ", String.valueOf(value));
                        /* output : ->: [1, 3, 5]
                        *           ->: [7, 2, 4]
                        *           ->: [6, 8] when buffer(3).
                        *
                        * */
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void windowOperator() {
        Observable.just(1, 3, 5, 7, 2, 4, 6, 8)
                .window(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Observable<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Observable<Integer> value) {

                        value.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Integer>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Integer value) {
                                        Log.d("-----window ", " " + value);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void displayNumbers() {
        final Subscriber subscriber = new Subscriber() {

            @Override
            public void onSubscribe(Subscription s) {
            }

            @Override
            public void onNext(Object o) {
                Toast.makeText(getApplicationContext(), "onNext: " + o, Toast.LENGTH_SHORT).show();
                //output - 1,2,3
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(getApplicationContext(), "onComplete", Toast.LENGTH_SHORT).show();
            }
        };

        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onComplete();
            }
        });

        integerObservable.subscribe();
    }

    private void scan() {
        Observable.just(1, 3, 5, 7, 2, 4, 6, 8)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return (integer + integer2);
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        Log.d("-----", "onNext: scan " + value);
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
