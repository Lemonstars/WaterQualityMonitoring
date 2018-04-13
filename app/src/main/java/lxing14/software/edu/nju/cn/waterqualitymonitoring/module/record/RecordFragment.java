package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.record;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.PdfVO;

public class RecordFragment extends Fragment implements RecordContract.View{

    private RecordContract.Presenter mPresenter;
    private MapView mMapView;
    private AMap mAMap;
    private LinearLayout mPicture_layout;
    private TableLayout mPdf_layout;

    public static RecordFragment generateFragment(){
        return new RecordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_record, container, false);

        findView(root);

        mPresenter.loadRecordData();
        mMapView.onCreate(savedInstanceState);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        mMapView.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void setPresenter(RecordContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Context getContextView() {
        return getContext();
    }

    @Override
    public void showStationLocation(ArrayList<MarkerOptions> markerOptionsList) {
        AMap aMap = mMapView.getMap();
        aMap.clear();
        aMap.addMarkers(markerOptionsList, false);
    }

    @Override
    public void showPDFData(List<PdfVO> list) {
        Context context = getContext();
        for(PdfVO vo: list){
            TableRow tableRow = new TableRow(context);
            TextView name_tv = new TextView(context);
            name_tv.setText(vo.getName());
            TextView time_tv = new TextView(context);
            time_tv.setText(vo.getTime());
            TextView note_tv = new TextView(context);
            note_tv.setText(vo.getNote());
            TextView download_tv = new TextView(context);
            download_tv.setText(vo.getUrl());

            configTextView(name_tv);
            configTextView(time_tv);
            configTextView(note_tv);
            configTextView(download_tv);
            tableRow.addView(name_tv);
            tableRow.addView(time_tv);
            tableRow.addView(note_tv);
            tableRow.addView(download_tv);
            mPdf_layout.addView(tableRow);
        }
    }

    @Override
    public void showPic(List<String> picUrlList) {
        Context context = getContext();
        for(int i=0;i<3;i++){
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.ic_default_pic);
            mPicture_layout.addView(imageView);
        }
    }

    //find the view by the id
    private void findView(View root){
        mMapView = root.findViewById(R.id.map);
        mAMap = mMapView.getMap();
        mPicture_layout = root.findViewById(R.id.picture_layout);
        mPdf_layout = root.findViewById(R.id.pdf_layout);
    }

    //configure the text view
    private void configTextView(TextView textView){
        textView.setLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        textView.setTextColor(getResources().getColor(R.color.lightGray));
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.gravity= Gravity.CENTER;
        textView.setLayoutParams(params);
    }
}
