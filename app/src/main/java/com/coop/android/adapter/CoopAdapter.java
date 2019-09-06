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
import com.coop.android.activity.TransDetailForEnterActivity;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.EnterTransBean;
import com.coop.android.model.TransInfoBean;
import com.coop.android.utils.GlideUtils;
import com.coop.android.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

public class CoopAdapter extends RecyclerView.Adapter<CoopAdapter.FeedHolder> {

    private static final String TAG = "CoopAdapter";
    private Context context;
    private List<EnterTransBean> data;

    public CoopAdapter(Context context, List<EnterTransBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tran_enter, parent, false);
        return new FeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        final EnterTransBean bean = data.get(position);
//
//        holder.tv_day.setText(mBackDatas.getStatusBeanList().get(position).getDay_time());
//        holder.tv_week.setText(mBackDatas.getStatusBeanList().get(position).getWeek_time());

//        timeBeanList = mBackDatas.getStatusBeanList().get(position).getTimeBeanList();
//        ListViewAdapter adapter = new ListViewAdapter(context,timeBeanList);
//
        holder.userNameTV.setText(bean.getInve_nick_name());
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
        if (StringUtil.isEmpty(bean.getEntr_avatar()))
            photourl = "";
        else
            photourl = AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA + bean.getEntr_avatar();
        GlideUtils.loadImage(context, photourl, holder.userHeaderImg, R.mipmap.default_user_header);
        holder.tranTokenTV.setText(bean.getToken_num());
//        holder.hasTokenTV.setText(bean.getBalanceAmount());
        holder.tranDescTV.setText(bean.getInve_remark());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(TransDetailForEnterActivity.createIntent(context, bean.getTrans_no()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class FeedHolder extends RecyclerView.ViewHolder {
        TextView userNameTV, tranTokenTV, tranTimeTV, tranDescTV;
        LinearLayout rootView;
        CircleImageView userHeaderImg;

        public FeedHolder(View itemView) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.userNameTV);
            tranTimeTV = itemView.findViewById(R.id.tranTimeTV);
            tranTokenTV = itemView.findViewById(R.id.tranTokenTV);
            tranDescTV = itemView.findViewById(R.id.tranDescTV);
            rootView = itemView.findViewById(R.id.rootView);
            userHeaderImg = itemView.findViewById(R.id.userHeaderImg);
        }
    }
}
