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
import android.widget.TextView;

import com.coop.android.AppConfigs;
import com.coop.android.R;
import com.coop.android.http.api.HttpPostApi;
import com.coop.android.model.EnterTeamBean;
import com.coop.android.model.WhiteMumberBean;
import com.coop.android.utils.GlideUtils;
import com.coop.android.view.CircleImageView;

import java.util.List;

import zuo.biao.library.util.StringUtil;

public class WhiteListAdapter extends RecyclerView.Adapter<WhiteListAdapter.FeedHolder> {

    private static final String TAG = "WhiteListAdapter";
    private Context context;
    private List<WhiteMumberBean> data;

    public WhiteListAdapter(Context context, List<WhiteMumberBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_white_mumber, parent, false);
        return new FeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        final WhiteMumberBean bean = data.get(position);
        holder.projectMemberNameTV.setText(bean.getNick_name());
        holder.projectMemberPhoneTV.setText(bean.getMobile_no());
        String photourl;
        if (StringUtil.isEmpty(bean.getAvatar()))
            photourl = "";
        else
            photourl = AppConfigs.APP_BASE_URL + HttpPostApi.KAPTCHA + bean.getAvatar();
        GlideUtils.loadImage(context, photourl, holder.memberHeaderImg, R.mipmap.default_user_header);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class FeedHolder extends RecyclerView.ViewHolder {
        TextView projectMemberNameTV, projectMemberPhoneTV;
        CircleImageView memberHeaderImg;

        public FeedHolder(View itemView) {
            super(itemView);
            projectMemberNameTV = itemView.findViewById(R.id.projectMemberNameTV);
            memberHeaderImg = itemView.findViewById(R.id.memberHeaderImg);
            projectMemberPhoneTV = itemView.findViewById(R.id.projectMemberPhoneTV);
        }
    }
}
