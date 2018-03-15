package lxing14.software.edu.nju.cn.waterqualitymonitoring.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;


/**
 * @author 刘兴
 * @version 1.0
 * @date 08/03/18
 */

public class WaterInfoView extends FrameLayout {

    private TextView more_tv;

    public WaterInfoView(Context context, String infoType, String infoNum) {
        super(context);
        View root = inflate(context, R.layout.item_water_info, this);

        TextView infoType_tv = (TextView) root.findViewById(R.id.infoType_tv);
        TextView infoNum_tv = (TextView) root.findViewById(R.id.infoNum_tv);
        more_tv = (TextView) root.findViewById(R.id.more_tv);

        infoType_tv.setText(infoType);
        infoNum_tv.setText(infoNum);
    }

    public void setClickMoreListener(OnClickListener clickListener){
        if(clickListener!=null){
            more_tv.setOnClickListener(clickListener);
        }
    }

}