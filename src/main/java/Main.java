import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by ngoctranfire on 4/5/17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        testOnErrorResumeNext();
    }


    public static void testOnErrorResumeNext() {
        Observable.just(1L,2L,3L,4L,5L,6L,6L)
                .flatMap(aLong -> {
                    double result = Math.random();
                    System.out.println("This is my start: " + result);
                    if (result > .5d) {
                        return Observable.just(aLong/0);
                    }
                    return Observable.just(aLong);
                })
                .onExceptionResumeNext(Observable.just(-1L))
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .retry()
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Finiushed and copleted!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("This is my error" + e);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("What happened here?" + aLong);
                    }
                });

    }
}
