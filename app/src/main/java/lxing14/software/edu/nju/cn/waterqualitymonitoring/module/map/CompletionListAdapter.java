package lxing14.software.edu.nju.cn.waterqualitymonitoring.module.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lxing14.software.edu.nju.cn.waterqualitymonitoring.R;

/**
 * @version : 1.0
 * @auther : starrylemon
 * @date : 2018/4/11
 * @time : 上午10:36
 */

public class CompletionListAdapter extends BaseAdapter implements Filterable {

    private Filter mFilter;
    private List<String> mFilterList;
    private List<String> mDataList;
    private Context mContext;

    public CompletionListAdapter(List<String> stringArrayList, Context context) {
        this.mDataList = stringArrayList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDataList ==null?0: mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList ==null?null: mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_completion, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.item_tv = view.findViewById(R.id.completion_tv);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.item_tv.setText(mDataList.get(i));

        return view;
    }

    @Override
    public Filter getFilter() {
        if(mFilter==null){
            mFilter = new CompletionFilter();
        }
        return mFilter;
    }

    static class ViewHolder{
        private TextView item_tv;
    }


    class CompletionFilter extends  Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (mFilterList == null) {
                mFilterList = new ArrayList<>(mDataList);
            }

            if(constraint==null || constraint.length()==0){
                results.values = mFilterList;
                results.count = mFilterList.size();
            }else {
                List<String> resList = new ArrayList<>();
                for(String str: mDataList){
                    if(str.contains(constraint)){
                        resList.add(str);
                    }
                }
                results.values = resList;
                results.count = resList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mDataList = (List<String>)results.values;

            if(results.count>0){
                notifyDataSetChanged();
            }else {
                notifyDataSetInvalidated();
            }
        }

    }

}
