package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.login;

import android.util.Log;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.StringUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/23
 * @time : 下午2:03
 */

public class LoginPresenter implements LoginContract.ILoginPresenter {

    private LoginContract.ILoginView mView;

    public LoginPresenter(LoginContract.ILoginView mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void verifyPassword(String userName, String password) {
        if(StringUtil.isEmtpy(userName)){
            mView.onUserNameNotInput();
            return;
        }

        if(StringUtil.isEmtpy(password)){
            mView.onPasswordNotInput();
            return;
        }

        RetrofitHelper.getUserInterface().login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("LogPresenter", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("LogPresenter", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("LogPresenter", "onNext: ");
                        if("success".equals(s)){
                            mView.jumpToMapActivity(userName);
                        }else {
                            mView.onPasswordInputError();
                        }
                    }
                });
    }
}
