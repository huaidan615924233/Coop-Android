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
package com.coop.android.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coop.android.R;
import com.umeng.analytics.MobclickAgent;

import zuo.biao.library.base.BaseFragment;

/**
 * Created by MR-Z on 2018/12/21.
 */
public class DemoFragment extends BaseFragment {
    public static final String TAG = "DemoFragment";

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static DemoFragment createInstance() {
        return new DemoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_partner);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void initView() {


    }

    @Override
    public void initData() {
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("页面");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("页面");
    }
}
