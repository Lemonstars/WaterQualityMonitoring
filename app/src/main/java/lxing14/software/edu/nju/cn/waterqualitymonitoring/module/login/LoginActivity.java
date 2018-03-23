package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.ActivityUtil;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = (LoginFragment)getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if(loginFragment == null){
            loginFragment = LoginFragment.generateFragment();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment, R.id.contentFrame);
        }

        new LoginPresenter(loginFragment);
    }
}
