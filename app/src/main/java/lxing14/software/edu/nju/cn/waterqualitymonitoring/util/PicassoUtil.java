package lxing14.software.edu.nju.cn.waterqualitymonitoring.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WebSite;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/10
 * @time : 下午9:55
 */

public class PicassoUtil {

    public static void loadUrl(Context context, String url, ImageView imageView){

        if(StringUtil.isEmpty(url)){
            return;
        }

        String targetUrl = WebSite.PIC_Prefix+url.replace(" ", "%20");

        Picasso.with(context)
                .load(targetUrl)
                .fit()
                .into(imageView);
    }

}
