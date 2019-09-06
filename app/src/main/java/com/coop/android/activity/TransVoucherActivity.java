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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coop.android.CBaseActivity;
import com.coop.android.R;
import com.coop.android.UserConfigs;
import com.coop.android.utils.ConstantUtil;
import com.coop.android.utils.ToastUtil;
import com.coop.android.view.CommonPopupWindow;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import zuo.biao.library.ui.statusbar.StatusBarUtils;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.Log;

/**
 * Created by MR-Z on 2019/2/21.
 */
public class TransVoucherActivity extends CBaseActivity implements View.OnClickListener, CommonPopupWindow.ViewInterface, PlatformActionListener {
    private static final String TAG = "TransVoucherActivity";
    protected Toolbar toolBar;
    private TextView toolbar_right;
    private LinearLayout transVoucherLL;
    private String voucherName, transNum, transDate, entrRealName, inveRealName, tokenName;
    private TextView transVoucherTV, serviceDescTV, transCountTV, transDateTV, enterNameTV, partnerNameTV, shareTV;
    private TextView shareTransVoucherTV, shareTransCountTV, shareTransDateTV, shareEnterNameTV, sharePartnerNameTV;
    private CommonPopupWindow popupWindow;
    private String imagePath = "";

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
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

            @Override
            public void onSystemUiVisibilityChange(int arg0) {
                hideBottomUI(getWindow().getDecorView());
            }
        });
        setContentView(R.layout.activity_trans_voucher);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvBaseTitle.setText(getResources().getString(R.string.txt_title_trans_voucher));
        intent = getIntent();
        voucherName = intent.getStringExtra("voucherName");
        transNum = intent.getStringExtra("transNum");
        transDate = intent.getStringExtra("transDate");
        entrRealName = intent.getStringExtra("entrRealName");
        inveRealName = intent.getStringExtra("inveRealName");
        tokenName = intent.getStringExtra("tokenName");
        toolBar = findViewById(R.id.toolbar_img);
        toolBar.setNavigationIcon(R.mipmap.back_left_btn);
        toolbar_right = findViewById(R.id.toolbar_right);
        transVoucherTV = findViewById(R.id.transVoucherTV);
        shareTransVoucherTV = findViewById(R.id.shareTransVoucherTV);
        serviceDescTV = findViewById(R.id.serviceDescTV);
        transCountTV = findViewById(R.id.transCountTV);
        shareTransCountTV = findViewById(R.id.shareTransCountTV);
        transDateTV = findViewById(R.id.transDateTV);
        shareTransDateTV = findViewById(R.id.shareTransDateTV);
        enterNameTV = findViewById(R.id.enterNameTV);
        shareEnterNameTV = findViewById(R.id.shareEnterNameTV);
        partnerNameTV = findViewById(R.id.partnerNameTV);
        sharePartnerNameTV = findViewById(R.id.sharePartnerNameTV);
        transVoucherLL = findViewById(R.id.transVoucherLL);
        shareTV = findViewById(R.id.shareTV);
    }

    @Override
    public void initData() {
        SpannableStringBuilder spannableStringBuilder;
        SpannableStringBuilder spannableStringBuilder2;
        if (ConstantUtil.ENTERIDEN.equals(UserConfigs.getInstance().getLastLoginRole())) {
            spannableStringBuilder = new SpannableStringBuilder("恭喜您完成了与  " + inveRealName + "  进行的数字权证交易，此为交易凭证。");
            spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_003F8D)), 9, 9 + inveRealName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.setSpan(new UnderlineSpan(), 9, 9 + inveRealName.length(), 0);
            spannableStringBuilder2 = new SpannableStringBuilder("恭喜您完成了与  " + inveRealName + "  进行的数字权证交易，此为交易凭证。");
            spannableStringBuilder2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 9, 9 + inveRealName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder2.setSpan(new UnderlineSpan(), 9, 9 + inveRealName.length(), 0);
        } else {
            spannableStringBuilder = new SpannableStringBuilder("恭喜您完成了与  " + entrRealName + "  进行的数字权证交易，此为交易凭证。");
            spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_003F8D)), 9, 9 + entrRealName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.setSpan(new UnderlineSpan(), 9, 9 + entrRealName.length(), 0);
            spannableStringBuilder2 = new SpannableStringBuilder("恭喜您完成了与  " + inveRealName + "  进行的数字权证交易，此为交易凭证。");
            spannableStringBuilder2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 9, 9 + inveRealName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder2.setSpan(new UnderlineSpan(), 9, 9 + inveRealName.length(), 0);
        }
        transVoucherTV.setText(spannableStringBuilder);
        shareTransVoucherTV.setText(spannableStringBuilder2);
        transCountTV.setText(transNum);
        shareTransCountTV.setText(transNum);
        transDateTV.setText(transDate);
        shareTransDateTV.setText(transDate);
        enterNameTV.setText(entrRealName);
        shareEnterNameTV.setText(entrRealName);
        partnerNameTV.setText(inveRealName);
        sharePartnerNameTV.setText(inveRealName);
    }

    @Override
    public void initEvent() {
        toolbar_right.setOnClickListener(this);
        shareTV.setOnClickListener(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        transVoucherLL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                viewSaveToImage(transVoucherLL);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.toolbar_right:
                viewSaveToImage(transVoucherLL);
                break;
            case R.id.shareTV:
                showAtLocation(v);  //弹出分享pop
                break;
            default:
                break;
        }
    }

    //底部弹出
    public void showAtLocation(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View popView = LayoutInflater.from(this).inflate(R.layout.popup_home_choose, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(popView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_share)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
//                .setAnimationStyle(R.style.AnimUp)
                .setOutsideTouchable(false)
                .setViewOnclickListener(this)
                .create();
        int popupWidth = popView.getMeasuredWidth();
        int popupHeight = popView.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2,
                location[1] - popupHeight);
//        popupWindow.showAsDropDown(view, 0, 0, Gravity.CENTER);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_share:
                TextView shareWechatMomentsTV = view.findViewById(R.id.shareWechatMomentsTV);
                TextView shareWechatTV = view.findViewById(R.id.shareWechatTV);
                TextView cancelTV = view.findViewById(R.id.cancelTV);
                cancelTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null)
                            popupWindow.dismiss();
                    }
                });
                shareWechatTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Platform.ShareParams sp = new Platform.ShareParams();
//                      sp.setImagePath("/mnt/sdcard/测试分享的图片.jpg");
                        sp.setImageData(loadBitmapFromView(transVoucherLL));
                        sp.setShareType(Platform.SHARE_IMAGE);
//                        sp.setImageUrl("https://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg");
                        Platform wchat = ShareSDK.getPlatform(Wechat.NAME);
                        wchat.setPlatformActionListener(TransVoucherActivity.this); // 设置分享事件回调
                        wchat.share(sp);
                    }
                });
                shareWechatMomentsTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Platform.ShareParams sp = new Platform.ShareParams();
//                      sp.setImagePath("/mnt/sdcard/测试分享的图片.jpg");
                        sp.setImageData(loadBitmapFromView(transVoucherLL));
                        sp.setShareType(Platform.SHARE_IMAGE);
//                        sp.setImageUrl("https://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg");
                        Platform wchat = ShareSDK.getPlatform(WechatMoments.NAME);
                        wchat.setPlatformActionListener(TransVoucherActivity.this); // 设置分享事件回调
                        wchat.share(sp);
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//        ToastUtil.showShortToast(mContext, "分享成功!");
        if (popupWindow != null)
            popupWindow.dismiss();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
//        ToastUtil.showShortToast(mContext, "分享失败!");
        if (popupWindow != null)
            popupWindow.dismiss();
    }

    @Override
    public void onCancel(Platform platform, int i) {
//        ToastUtil.showShortToast(mContext, "分享取消!");
        if (popupWindow != null)
            popupWindow.dismiss();
    }

    private void viewSaveToImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

        FileOutputStream fos;
        try {
            makeRootDirectory(getRootDir(mContext));
            File file = new File(getRootDir(mContext), Calendar.getInstance().getTimeInMillis() + ".png");
            fos = new FileOutputStream(file);
            imagePath = file.getAbsolutePath();

            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();
            ToastUtil.showShortToast(mContext, "图片已保存到本地");
            //这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).setData(Uri.fromFile(file)));

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
            rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/coop/";
        } else {
            rootDir = context.getApplicationContext().getCacheDir().getAbsolutePath() + "/coop/";
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

    public void hideBottomUI(View view) {
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_VISIBLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
        if (Build.VERSION.SDK_INT < 19 || !checkDeviceHasNavigationBar()) {
            StatusBarUtils.setStatusBarColorDefault(this);
            //一定要判断是否存在按键，否则在没有按键的手机调用会影响别的功能。如之前没有考虑到，导致图传全屏变成小屏显示。
            return;
        } else {
            // 获取属性
            view.setSystemUiVisibility(flag);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//显示状态栏
        }
    }

    /**
     * 判断是否存在虚拟按键
     *
     * @return
     */
    public boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
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
