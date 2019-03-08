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
package com.coop.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2019/2/21.
 */
public class TransVoucherActivity extends CBaseActivity implements View.OnClickListener {
    private static final String TAG = "TransVoucherActivity";
    private LinearLayout closeLL, transVoucherLL;
    private String voucherName, transNum, transDate, entrRealName, inveRealName, tokenName;
    private TextView transVoucherTV, serviceDescTV, transCountTV, transDateTV, enterNameTV, partnerNameTV;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context, String tokenName, String voucherName, String transNum, String transDate, String entrRealName, String inveRealName) {
        return new Intent(context, TransVoucherActivity.class).putExtra("voucherName", voucherName)
                .putExtra("transNum", transNum).putExtra("transDate", transDate).putExtra("entrRealName", entrRealName)
                .putExtra("inveRealName", inveRealName).putExtra("tokenName", tokenName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_voucher);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        intent = getIntent();
        voucherName = intent.getStringExtra("voucherName");
        transNum = intent.getStringExtra("transNum");
        transDate = intent.getStringExtra("transDate");
        entrRealName = intent.getStringExtra("entrRealName");
        inveRealName = intent.getStringExtra("inveRealName");
        tokenName = intent.getStringExtra("tokenName");
        transVoucherTV = findViewById(R.id.transVoucherTV);
        serviceDescTV = findViewById(R.id.serviceDescTV);
        transCountTV = findViewById(R.id.transCountTV);
        transDateTV = findViewById(R.id.transDateTV);
        enterNameTV = findViewById(R.id.enterNameTV);
        partnerNameTV = findViewById(R.id.partnerNameTV);
        closeLL = findViewById(R.id.closeLL);
        transVoucherLL = findViewById(R.id.transVoucherLL);
    }

    @Override
    public void initData() {
        SpannableStringBuilder sb = new SpannableStringBuilder("    恭喜您完成了与  " + voucherName + "  进行的通证交易，此为交易凭证。");
        sb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_003F8D)), 13, 13 + voucherName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new UnderlineSpan(), 13, 13 + voucherName.length(), 0);
        transVoucherTV.setText(sb);
        transCountTV.setText(transNum + " " + tokenName);
        transDateTV.setText(transDate);
        enterNameTV.setText(entrRealName);
        partnerNameTV.setText(inveRealName);
    }

    @Override
    public void initEvent() {
        closeLL.setOnClickListener(this);
        transVoucherLL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                viewSaveToImage(transVoucherLL);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.closeLL:
                finish();
                break;
            default:
                break;
        }
    }

    private void viewSaveToImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

        FileOutputStream fos;
        String imagePath = "";
        try {
            makeRootDirectory(getRootDir(mContext));
            File file = new File(getRootDir(mContext), Calendar.getInstance().getTimeInMillis() + ".png");
            fos = new FileOutputStream(file);
            imagePath = file.getAbsolutePath();

            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();
            ToastUtil.showShortToast(mContext, "图片已保存到本地");

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        Log.e(TAG, "imagePath=" + imagePath);

        view.destroyDrawingCache();
        // TODO 临时刷新页面，需要修改
        setContentView(R.layout.activity_trans_voucher);
        initView();
        initData();
        initEvent();
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 获取SDCard卡或者手机内存的根路径（优先获取SDCard卡的根路径）
     *
     * @param context Context
     * @return SDCard卡或者手机内存的根路径
     */
    public static String getRootDir(Context context) {
        String rootDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            rootDir = context.getApplicationContext().getCacheDir().getAbsolutePath();
        }
        return rootDir;
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("交易凭证页面");
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("交易凭证页面");
        MobclickAgent.onPause(this);
    }

}
