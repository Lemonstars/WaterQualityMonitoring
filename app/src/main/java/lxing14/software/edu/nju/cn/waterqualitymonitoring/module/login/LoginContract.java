package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.login;

import android.content.Context;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.BasePresenter;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.BaseView;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/23
 * @time : 下午2:03
 */

public interface LoginContract {

    interface ILoginView extends BaseView<ILoginPresenter>{

        /**
         * 获取上下文
         * @return
         */
        Context getViewContext();

        /**
         * 提示输入用户名
         */
        void onUserNameNotInput();

        /**
         * 提示输入密码
         */
        void onPasswordNotInput();

        /**
         * 用户名错误提示
         */
        void onUserNameInputError();

        /**
         * 密码错误提示
         */
        void onPasswordInputError();

        /**
         * 跳转至地图界面
         *
         * @param userName
         */
        void jumpToMapActivity(String userName);

    }

    interface ILoginPresenter extends BasePresenter{

        /**
         * 验证用户名密码的正确性
         * @param userName
         * @param password
         * @return
         */
        void verifyPassword(String userName, String password);

        /**
         * 检查是否已经登录过
         * 若登录过，则跳转至首页；
         * 否则，等待用户输入
         */
        void checkUserInfoExisted();

    }

}
