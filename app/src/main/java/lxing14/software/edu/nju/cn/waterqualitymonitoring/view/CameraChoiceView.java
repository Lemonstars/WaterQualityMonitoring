package lxing14.software.edu.nju.cn.waterqualitymonitoring.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/18
 * @time : 下午6:59
 */

public class CameraChoiceView extends LinearLayout {

    public CameraChoiceView(Context context, char chNum) {
        super(context);

        inflate(context, R.layout.bg_camera_choice, this);
        init(chNum);
    }

    private void init(char chNum){
        TextView num_tv = findViewById(R.id.num_tv);
        View item_view = findViewById(R.id.item_view);
        ImageView camera_iv = findViewById(R.id.camera_iv);
        TextView char_tv = findViewById(R.id.char_tv);

        int num = chNum - 'A' + 1;
        String cameraNum = "1." + num;
        num_tv.setText(cameraNum);
        String cameraChar = String.valueOf(chNum);
        char_tv.setText(cameraChar);
    }

}
