package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper;

import android.content.Context;
import android.widget.Toast;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.NetworkUtil;
import rx.Subscriber;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/4
 * @time : 下午9:22
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtil.isNetworkAccessed(context)) {
            Toast.makeText(context, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            if (!isUnsubscribed()) {
                unsubscribe();
            }
        }

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {

    }
}
