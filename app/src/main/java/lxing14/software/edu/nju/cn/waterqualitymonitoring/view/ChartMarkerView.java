package lxing14.software.edu.nju.cn.waterqualitymonitoring.view;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/3
 * @time : 下午4:32
 */

public class ChartMarkerView extends MarkerView {

    private TextView mEntry_tv;
    private TextView mEntryNum_tv;
    private TextView mTimeNum_tv;

    public ChartMarkerView(Context context, int layoutResource, String entry) {
        super(context, layoutResource);

        mEntry_tv = findViewById(R.id.entry_tv);
        mEntryNum_tv = findViewById(R.id.entryNum_tv);
        mTimeNum_tv = findViewById(R.id.timeNum_tv);

        mEntry_tv.setText(entry);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mEntryNum_tv.setText(String.valueOf(e.getY()));
    }

}
