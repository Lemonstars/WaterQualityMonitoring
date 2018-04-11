package lxing14.software.edu.nju.cn.waterqualitymonitoring.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.Display;
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

    private ImageView imageView;

    public ImageDialog(@NonNull Context context, Drawable drawable) {
        super(context, R.style.imageDialog_style);
        setContentView(R.layout.dialog_image);

        imageView = findViewById(R.id.imageView);
        if(drawable == null){
            imageView.setImageResource(R.drawable.ic_default_pic);
        }else {
            imageView.setImageDrawable(drawable);
        }

        Window window = getWindow();
        WindowManager windowManager = window.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth();
        lp.height = display.getHeight();
        window.setAttributes(lp);

        imageView.setOnClickListener(v -> dismiss());
    }

    public void setImage(Drawable drawable){
        imageView.setImageDrawable(drawable);
    }


}
