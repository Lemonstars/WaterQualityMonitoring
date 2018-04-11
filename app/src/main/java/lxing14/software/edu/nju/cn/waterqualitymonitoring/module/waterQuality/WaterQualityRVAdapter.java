package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.constant.WaterQualityData;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/11
 * @time : 下午8:33
 */

public class WaterQualityRVAdapter extends RecyclerView.Adapter<WaterQualityRVAdapter.WaterQualityVH> {

    private Context mContext;
    private List<WaterQualityTypeVO> data;

    public WaterQualityRVAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public WaterQualityVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_water_quality, parent, false);
        WaterQualityVH vh = new WaterQualityVH(root);
        return vh;
    }

    @Override
    public void onBindViewHolder(WaterQualityVH holder, int position) {
        WaterQualityTypeVO vo = data.get(position);
        int type = vo.getType();
        String num = vo.getNum();

        holder.typeNum_tv.setText(num);
        holder.typeHint_tv.setText(WaterQualityData.getChineseName(type));
        holder.icon_iv.setBackgroundResource(WaterQualityData.getPicResId(type));
        holder.root_layout.setBackgroundResource(WaterQualityData.getBgResId(type));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<WaterQualityTypeVO> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class WaterQualityVH extends RecyclerView.ViewHolder {

        RelativeLayout root_layout;
        ImageView icon_iv;
        TextView typeNum_tv;
        TextView typeHint_tv;

        public WaterQualityVH(View itemView) {
            super(itemView);

            root_layout = itemView.findViewById(R.id.root_layout);
            icon_iv = itemView.findViewById(R.id.icon_iv);
            typeNum_tv = itemView.findViewById(R.id.typeNum_tv);
            typeHint_tv = itemView.findViewById(R.id.typeHint_tv);
        }

    }

}
