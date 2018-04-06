package lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper;

import android.content.Context;
import android.widget.Toast;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.NetworkUtil;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.view.LoadingDialog;
import rx.Subscriber;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/4
 * @time : 下午9:22
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private LoadingDialog mLoadingDialog;

    public BaseSubscriber(Context context) {
        this.mContext = context;
        mLoadingDialog = new LoadingDialog(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtil.isNetworkAccessed(mContext)) {
            Toast.makeText(mContext, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            if (!isUnsubscribed()) {
                unsubscribe();
            }
        }

        if (mLoadingDialog != null){
            mLoadingDialog.show();
        }
    }

    @Override
    public void onCompleted() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {

    }
}
