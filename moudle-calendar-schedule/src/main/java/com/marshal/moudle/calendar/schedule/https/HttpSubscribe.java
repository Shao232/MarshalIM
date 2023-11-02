package com.marshal.moudle.calendar.schedule.https;

import com.marshal.base_common.store.StoreCommonDataKt;
import com.marshal.moudle.calendar.schedule.StoreCalendarDataKt;
import com.marshal.moudle.calendar.schedule.pojo.ResponseResultBean;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class HttpSubscribe<T> implements Observer<T> {

    public HttpSubscribe() {
    }

    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        onStart();
    }

    @Override
    public void onNext(@NonNull T t) {
        if (t instanceof ResponseResultBean) {
            ResponseResultBean<T> bean = (ResponseResultBean<T>) t;
            if (bean.getCode() == 200) {
                onSuccess(t);
            } else {
                if (bean.getMsg().equals("token已过期，请重新登陆")) {
                    StoreCalendarDataKt.putAppInfoToken("");
                }
                onThrowable(new Throwable(bean.getMsg()));
            }
        } else {
            onThrowable(new Throwable("类型错误"));
        }
    }

    @Override
    public void onComplete() {
        onCustomComplete();
        disposable.dispose();
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String errorMsg = CalendarThrowableHelper.tryThrowableMessage(e);
        onThrowable(new Throwable(errorMsg));
    }

    public abstract void onSuccess(T response);

    public abstract void onThrowable(Throwable e);

    public void onStart() {
    }

    public void onCustomComplete() {
    }

}
