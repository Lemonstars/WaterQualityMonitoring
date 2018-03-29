package lxing14.software.edu.nju.cn.waterqualitymonitoring.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/29
 * @time : 上午10:48
 */

public class ImageDialog extends Dialog{

    public ImageDialog(@NonNull Context context) {
        super(context, R.style.imageDialog_style);
        setContentView(R.layout.dialog_image);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.ic_default_pic);

        Window window = getWindow();
        WindowManager windowManager = window.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int)(display.getWidth() * 0.8);
        lp.height = (int) (display.getHeight() * 0.5);
        window.setAttributes(lp);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }



}
