package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.login;

import android.content.Context;
import android.content.SharedPreferences;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.BaseSubscriber;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.helper.RetrofitHelper;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.MD5Util;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.StringUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/23
 * @time : 下午2:03
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }
    @Override
    public void verifyPassword(String userName, String password) {
        if(StringUtil.isEmpty(userName)){
            mView.onUserNameNotInput();
            return;
        }

        if(StringUtil.isEmpty(password)){
            mView.onPasswordNotInput();
            return;
        }

        String encodePassword = MD5Util.encode(password);
        RetrofitHelper.getUserInterface().login(userName, encodePassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>(mView.getContextView()) {
                    @Override
                    public void onNext(String s) {
                        if("success".equals(s)){
                            saveUserInfo(userName, password);
                            mView.jumpToMapActivity(userName);
                        }else {
                            mView.onPasswordInputError();
                        }
                    }
                });
    }

    @Override
    public void checkUserInfoExisted() {
        Context context = mView.getContextView();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString(SharePreferencesConstant.USER_NAME, null);
        String password = sharedPreferences.getString(SharePreferencesConstant.PASSWORD, null);
        if(null==userName || null==password){
            return;
        }

        verifyPassword(userName, password);
    }

    //save the user information
    private void saveUserInfo(String userName, String password){
        Context context = mView.getContextView();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharePreferencesConstant.USER_NAME, userName);
        editor.putString(SharePreferencesConstant.PASSWORD, password);
        editor.apply();
    }

}
