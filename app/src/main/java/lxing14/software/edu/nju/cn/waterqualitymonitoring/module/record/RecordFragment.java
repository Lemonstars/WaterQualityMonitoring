package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.record;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.api.vo.PdfVO;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.CommonConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.SharePreferencesConstant;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WebSite;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.PicassoUtil;

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

        showCurrentLocation();
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
            TextView download_tv = new TextView(context);
            String url = vo.getUrl();
            download_tv.setText(url);
            download_tv.setOnClickListener( v -> mPresenter.downloadPdfFile(url));

            configTextView(name_tv);
            configTextView(time_tv);
            configTextView(download_tv);
            tableRow.addView(name_tv);
            tableRow.addView(time_tv);
            tableRow.addView(download_tv);
            mPdf_layout.addView(tableRow);
        }
    }

    @Override
    public void showPic(List<String> picUrlList) {
        Context context = getContext();
        for(String str: picUrlList){
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            imageView.setLayoutParams(params);
            PicassoUtil.loadUrl(context, WebSite.PIC_Prefix+"/"+str, imageView);
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
        params.width=1;
        textView.setLayoutParams(params);
    }

    //show the current location
    private void showCurrentLocation(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SharePreferencesConstant.APP_NAME, Context.MODE_PRIVATE);
        float latitude = sharedPreferences.getFloat(SharePreferencesConstant.LATITUDE, CommonConstant.LATITUDE_OF_NJ);
        float longitude = sharedPreferences.getFloat(SharePreferencesConstant.LONGITUDE, CommonConstant.LONGITUDE_OF_NJ);

        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10f));
    }
}
