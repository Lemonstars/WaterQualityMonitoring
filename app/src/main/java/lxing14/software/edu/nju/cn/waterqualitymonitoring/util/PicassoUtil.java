package lxing14.software.edu.nju.cn.waterqualitymonitoring.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/10
 * @time : 下午9:55
 */

public class PicassoUtil {

    public static void loadUrl(Context context, String url, ImageView imageView){
        Picasso.with(context)
                .load(url)
                .fit()
                .into(imageView);
    }

}
