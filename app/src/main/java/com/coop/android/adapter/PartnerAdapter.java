//
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \|     |// '.
//                 / \|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \  -  /// |     |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG
package com.coop.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.AppConfigs;
import com.coop.android.R;
import com.coop.android.activity.TransDetailActivity;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.AssetBean;
import com.coop.android.model.PartnerTransBean;
import com.coop.android.model.TransInfoBean;
import com.coop.android.utils.GlideUtils;
import com.coop.android.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.FeedHolder> {

    private static final String TAG = "PartnerAdapter";
    private Context context;
    private List<PartnerTransBean> data;

    public PartnerAdapter(Context context, List<PartnerTransBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tran_partner, parent, false);
        return new FeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        final PartnerTransBean bean = data.get(position);
//
//        holder.tv_day.setText(mBackDatas.getStatusBeanList().get(position).getDay_time());
//        holder.tv_week.setText(mBackDatas.getStatusBeanList().get(position).getWeek_time());

//        timeBeanList = mBackDatas.getStatusBeanList().get(position).getTimeBeanList();
//        ListViewAdapter adapter = new ListViewAdapter(context,timeBeanList);
//
//        if (position == 0)
//            holder.rootView.setBackgroundResource(R.drawable.bg_radius_white);
//        else
//            holder.rootView.setBackgroundResource(R.drawable.bg_radius_f8f9fb);
        switch (bean.getAsset_type()) {
            case AssetBean.ASSET_TYPE_1:
                holder.assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_1);
                break;
            case AssetBean.ASSET_TYPE_2:
                holder.assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_2);
                break;
            case AssetBean.ASSET_TYPE_3:
                holder.assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_3);
                break;
            case AssetBean.ASSET_TYPE_4:
                holder.assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_4);
                break;
            default:
                break;
        }
        holder.userNameTV.setText(bean.getAsset_name());
        String projectTime = bean.getCreate_time();
        if (!StringUtil.isEmpty(projectTime)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projectTime);
                String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                holder.tranTimeTV.setText(now);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        String photourl;
        if (StringUtil.isEmpty(bean.getProject_logo()))
            photourl = "";
        else
            photourl = AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA + bean.getProject_logo();
        GlideUtils.loadImage(context, photourl, holder.userHeaderImg, R.mipmap.default_user_header);
        holder.tranTokenTV.setText(bean.getToken_num());
        holder.tranDescTV.setText(bean.getInve_remark());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(TransDetailActivity.createIntent(context, bean.getAsset_id(), bean.getTrans_no()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class FeedHolder extends RecyclerView.ViewHolder {
        TextView userNameTV, tranTokenTV, tranTimeTV, tranDescTV, assetTypeTV;
        LinearLayout rootView;
        CircleImageView userHeaderImg;

        public FeedHolder(View itemView) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.userNameTV);
            tranTimeTV = itemView.findViewById(R.id.tranTimeTV);
            tranTokenTV = itemView.findViewById(R.id.tranTokenTV);
            tranDescTV = itemView.findViewById(R.id.tranDescTV);
            assetTypeTV = itemView.findViewById(R.id.assetTypeTV);
            rootView = itemView.findViewById(R.id.rootView);
            userHeaderImg = itemView.findViewById(R.id.userHeaderImg);
        }
    }
}
