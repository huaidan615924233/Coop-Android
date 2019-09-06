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
import com.coop.android.activity.PDFDatabaseActivity;
import com.coop.android.model.AgreementBean;

import java.util.List;

import zuo.biao.library.util.StringUtil;

public class AgreementProjectAdapter extends RecyclerView.Adapter<AgreementProjectAdapter.AgreementHolder> {

    private static final String TAG = "AgreementProjectAdapter";
    private Context context;
    private List<AgreementBean> data;

    public AgreementProjectAdapter(Context context, List<AgreementBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public AgreementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_agreement, parent, false);
        return new AgreementHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AgreementHolder holder, final int position) {
        final AgreementBean bean = data.get(position);
        holder.agreementNameTV.setText(bean.getFileName());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isEmpty(bean.getUrl()))
                    context.startActivity(PDFDatabaseActivity.createIntent(context, bean.getFileName(), AppConfigs.APP_BASE_FILE_URL + bean.getUrl()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class AgreementHolder extends RecyclerView.ViewHolder {
        TextView agreementNameTV;
        LinearLayout rootView;

        public AgreementHolder(View itemView) {
            super(itemView);
            agreementNameTV = itemView.findViewById(R.id.agreementNameTV);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }
}
