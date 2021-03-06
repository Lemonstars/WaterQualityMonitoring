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

public class CameraChoiceView extends LinearLayout{

    private OnLayoutClick onLayoutClick;
    private int mNum;
    private TextView mNum_tv;
    private View mItem_view;
    private ImageView mCamera_iv;
    private TextView mChar_tv;

    public CameraChoiceView(Context context, int num, OnLayoutClick onLayoutClick) {
        super(context);
        this.mNum = num;
        this.onLayoutClick = onLayoutClick;
        init(context);
    }

    private void init(Context context){
        inflate(context, R.layout.bg_camera_choice, this);

        mNum_tv = findViewById(R.id.num_tv);
        mItem_view = findViewById(R.id.item_view);
        mCamera_iv = findViewById(R.id.camera_iv);
        mChar_tv = findViewById(R.id.char_tv);

        String cameraNum = "1." + mNum;
        mNum_tv.setText(cameraNum);
        String cameraChar = "相机"+(char)('A'-1+ mNum);
        mChar_tv.setText(cameraChar);

        setOnClickListener( e -> onLayoutClick.onClick());
    }

    public void setClick() {
        mNum_tv.setTextColor(getResources().getColor(R.color.lightBlue));
        mChar_tv.setTextColor(getResources().getColor(R.color.lightBlue));
        mItem_view.setBackgroundColor(getResources().getColor(R.color.lightBlue));
        mCamera_iv.setImageResource(R.drawable.ic_camera_blue);
    }

    public void setUnClick(){
        mNum_tv.setTextColor(getResources().getColor(R.color.lightGray));
        mChar_tv.setTextColor(getResources().getColor(R.color.lightGray));
        mItem_view.setBackgroundColor(getResources().getColor(R.color.lightGray));
        mCamera_iv.setImageResource(R.drawable.ic_camera);
    }


    public interface OnLayoutClick{
        void onClick();
    }

}
