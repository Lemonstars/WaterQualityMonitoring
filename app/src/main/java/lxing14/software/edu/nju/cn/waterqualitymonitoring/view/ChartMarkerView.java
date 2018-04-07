package lxing14.software.edu.nju.cn.waterqualitymonitoring.view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/3
 * @time : 下午4:32
 */

public class ChartMarkerView extends MarkerView {

    private String mEntry;
    private String mUnit;
    private TextView mEntry_tv;
    private TextView mEntryNum_tv;
    private TextView mTimeNum_tv;

    private IAxisValueFormatter mIAxisValueFormatter;

    public ChartMarkerView(Context context, String entry, String unit) {
        super(context, R.layout.bg_chart_marker_view);

        this.mEntry = entry;
        this.mUnit = unit;
        mEntry_tv = findViewById(R.id.entry_tv);
        mEntryNum_tv = findViewById(R.id.entryNum_tv);
        mTimeNum_tv = findViewById(R.id.timeNum_tv);

    }

    public ChartMarkerView(Context context) {
        this(context, "", "");
    }

    public void setIAxisValueFormatter(IAxisValueFormatter mIAxisValueFormatter) {
        this.mIAxisValueFormatter = mIAxisValueFormatter;
    }

    public void setEntry(String entry){
        this.mEntry = entry;
    }

    public void setUnit(String unit){
        this.mUnit = unit;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mEntry_tv.setText(mEntry);
        mEntryNum_tv.setText(String.valueOf(e.getY())+mUnit);
        mTimeNum_tv.setText(mIAxisValueFormatter.getFormattedValue(e.getX(), null));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

}
