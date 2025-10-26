package org.example.threads_demo;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

public class ThreadsDemoApplication {

    //не знаю сколько нужно было написать, я нашла оснонные функции этой библиотеки

    public static void main(String[] args) throws InterruptedException {
        demoObservable();
        demoFlowable();
        demoOperators();
        demoZip();
        demoSubject();
        demoErrorHandling();
        demoTimerInterval();
        demoDisposable();
    }

    // observable
    private static void demoObservable() {
        System.out.println("\nObservable:");

        Observable<String> source = Observable.just("A", "B", "C");
        source.subscribe(item -> System.out.println("Received: " + item));
    }

    //flowable
    private static void demoFlowable() {
        System.out.println("\nFlowable:");

        Flowable.range(1, 10)
                .onBackpressureBuffer()
                .observeOn(Schedulers.computation())
                .subscribe(i -> System.out.println("Flowable: " + i));


    }

    //map, filter, flatMap
    private static void demoOperators() {
        System.out.println("\nOperators: map, filter, flatMap:");

        Observable.just(1, 2, 3, 4, 5)
                .filter(x -> x % 2 == 0)
                .map(x -> x * 10)
                .flatMap(x ->
                        Observable.just("Value: " + x)
                                .subscribeOn(Schedulers.io())
                )
                .subscribe(System.out::println);
    }

    //zip
    private static void demoZip() {
        System.out.println("\nZip streams:");

        Observable<String> first = Observable.just("A", "B");
        Observable<String> second = Observable.just("1", "2");

        Observable.zip(first, second, (a, b) -> a + b)
                .subscribe(System.out::println);
    }

    //subject
    private static void demoSubject() {
        System.out.println("\nPublishSubject:");

        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(x -> System.out.println("Subject: " + x));

        subject.onNext("Hello Java");
        subject.onNext("Hello RxJava!!");
    }

    //error handling
    private static void demoErrorHandling()
    {
        System.out.println("\nError Handling:");

        Observable.just(5, 0, 2)
                .map(x -> 10 / x)
                .onErrorReturnItem(-1)
                .subscribe(System.out::println);
    }

    //timers
    private static void demoTimerInterval() throws InterruptedException
    {
        System.out.println("\nTimer & Interval:");

        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(x -> System.out.println("Timer worked!"));

        Observable.interval(500, TimeUnit.MILLISECONDS)
                .take(3)
                .subscribe(x -> System.out.println("Tick: " + x));

        Thread.sleep(2000);
    }

    //disposable
    private static void demoDisposable() throws InterruptedException {
        System.out.println("\nDisposable:");

        Disposable d = Observable.interval(300, TimeUnit.MILLISECONDS)
                .subscribe(x -> System.out.println("Streaming: " + x));

        Thread.sleep(1000);
        d.dispose();

        System.out.println("Stopped!");
        Thread.sleep(500);
    }
}
