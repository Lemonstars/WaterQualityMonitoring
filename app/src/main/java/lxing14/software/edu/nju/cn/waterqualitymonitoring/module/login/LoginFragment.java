package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map.MapActivity;
import rx.android.schedulers.AndroidSchedulers;

public class LoginFragment extends Fragment implements LoginContract.ILoginView, View.OnClickListener{

    public static final int TIME_SHOW_MILLISECOND = 1000;

    private LoginContract.ILoginPresenter mPresenter;

    private ViewGroup mUserNameHint_layout;
    private ViewGroup mPasswordHint_layout;
    private TextView mUserNameHint_tv;
    private TextView mPasswordHint_tv;
    private EditText mUserName_et;
    private EditText mPassword_et;
    private TextView  mLogin_tv;

    public static LoginFragment generateFragment(){
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.checkUserInfoExisted();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        mUserNameHint_layout = root.findViewById(R.id.userNameHint_layout);
        mPasswordHint_layout = root.findViewById(R.id.passwordHint_layout);
        mUserNameHint_tv = root.findViewById(R.id.userNameHint_tv);
        mPasswordHint_tv = root.findViewById(R.id.passwordHint_tv);
        mUserName_et = root.findViewById(R.id.userName_et);
        mPassword_et = root.findViewById(R.id.password_et);
        mLogin_tv = root.findViewById(R.id.login_tv);

        initListener();

        return root;
    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onUserNameNotInput() {
        mUserNameHint_layout.setVisibility(View.VISIBLE);
        mUserNameHint_tv.setText(R.string.pleaseInputUserName);
        rx.Observable.timer(TIME_SHOW_MILLISECOND, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e -> mUserNameHint_layout.setVisibility(View.GONE));
    }

    @Override
    public void onPasswordNotInput() {
        mPasswordHint_layout.setVisibility(View.VISIBLE);
        mPasswordHint_tv.setText(R.string.pleaseInputPassword);
        rx.Observable.timer(TIME_SHOW_MILLISECOND, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e -> mPasswordHint_layout.setVisibility(View.GONE));
    }

    @Override
    public void onUserNameInputError() {
        mUserNameHint_layout.setVisibility(View.VISIBLE);
        mUserNameHint_tv.setText(R.string.pleaseInputCorrectUserName);
        rx.Observable.timer(TIME_SHOW_MILLISECOND, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e -> mUserNameHint_layout.setVisibility(View.GONE));
    }

    @Override
    public void onPasswordInputError() {
        mPasswordHint_layout.setVisibility(View.VISIBLE);
        mPasswordHint_tv.setText(R.string.pleaseInputCorrectPassword);
        rx.Observable.timer(TIME_SHOW_MILLISECOND, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e -> mPasswordHint_layout.setVisibility(View.GONE));
    }

    @Override
    public void jumpToMapActivity(String userName) {
        startActivity(MapActivity.generateIntent(getContext(), userName));
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        mLogin_tv.setOnClickListener(null);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_tv:
                String userName = mUserName_et.getText().toString();
                String password = mPassword_et.getText().toString();
                mPresenter.verifyPassword(userName, password);
                break;
        }
    }

    @Override
    public Context getContextView() {
        return getContext();
    }

    //initialize the listener
    private void initListener(){
        mLogin_tv.setOnClickListener(this);
    }
}
