package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.waterQuality;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;
import lxing14.software.edu.nju.cn.waterqualitymonitoring.util.PicassoUtil;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/3/11
 * @time : 下午8:33
 */

public class WaterQualityRVAdapter extends RecyclerView.Adapter<WaterQualityRVAdapter.WaterQualityVH>{

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

        holder.typeNum_tv.setText(String.valueOf(vo.getNum()));
        holder.typeHint_tv.setText(vo.getName());
        holder.root_layout.setBackgroundResource(vo.getBg_id());
        holder.icon_iv.setBackgroundResource(vo.getIcon_pic_id());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0:data.size();
    }

    public void setData(List<WaterQualityTypeVO> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class WaterQualityVH extends RecyclerView.ViewHolder{

        RelativeLayout root_layout;
        ImageView icon_iv;
        TextView typeNum_tv;
        TextView typeHint_tv;

        public WaterQualityVH(View itemView) {
            super(itemView);

            root_layout = (RelativeLayout) itemView.findViewById(R.id.root_layout);
            icon_iv = (ImageView) itemView.findViewById(R.id.icon_iv);
            typeNum_tv = (TextView) itemView.findViewById(R.id.typeNum_tv);
            typeHint_tv = (TextView) itemView.findViewById(R.id.typeHint_tv);
        }

    }

}
