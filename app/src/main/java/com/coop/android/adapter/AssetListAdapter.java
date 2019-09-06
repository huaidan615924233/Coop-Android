package com.coop.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coop.android.R;
import com.coop.android.activity.ProjectInfoActivity;
import com.coop.android.model.AssetBean;
import com.coop.android.utils.NumUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import zuo.biao.library.util.StringUtil;

/**
 * Created by MR-Z on 2019/7/24.
 */
public class AssetListAdapter extends PagerAdapter {
    private Context mContext;
    private List<AssetBean> list;

    public AssetListAdapter(Context context, List<AssetBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final AssetBean bean = list.get(position);
        View rootView = View.inflate(mContext, R.layout.item_enter, null);
        TextView projectTotalTV = rootView.findViewById(R.id.projectTotalTV);
        TextView tokenTotalTV = rootView.findViewById(R.id.tokenTotalTV);
        TextView tokenPerTV = rootView.findViewById(R.id.tokenPerTV);
        TextView assetNameTV = rootView.findViewById(R.id.assetNameTV);
        TextView assetTypeTV = rootView.findViewById(R.id.assetTypeTV);
        switch (bean.getAsset_type()) {
            case AssetBean.ASSET_TYPE_1:
                assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_1);
                break;
            case AssetBean.ASSET_TYPE_2:
                assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_2);
                break;
            case AssetBean.ASSET_TYPE_3:
                assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_3);
                break;
            case AssetBean.ASSET_TYPE_4:
                assetTypeTV.setText(AssetBean.ASSET_TYPE_NAME_4);
                break;
            default:
                break;
        }
        assetNameTV.setText(bean.getAsset_name());
        DecimalFormat df = new DecimalFormat("##.######%");
        tokenPerTV.setText(df.format(Double.parseDouble(bean.getStock_percent())));
        projectTotalTV.setText(NumUtils.formatNum(bean.getProject_amount(), false));
        tokenTotalTV.setText(bean.getProject_token());
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isEmail(bean.getId()))
                    return;
                mContext.startActivity(ProjectInfoActivity.createIntent(mContext, bean.getId()));
            }
        });
        container.addView(rootView);

        return rootView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }
}
