package com.coop.android.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author Zhaop
 * @describe 测量工具类
 */
public class MeasureUtil {

    private static final String TAG = "MeasureUtil";

    public final static int PX = TypedValue.COMPLEX_UNIT_PX;
    public final static int DIP = TypedValue.COMPLEX_UNIT_DIP;
    public final static int SP = TypedValue.COMPLEX_UNIT_SP;

    /**
     * @param unit    单位 </br>0 px</br>1 dip</br>2 sp
     * @param value   size 大小
     * @param context
     * @return
     */
    public static float getDimensionPixelSize(int unit, float value,
                                              Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        switch (unit) {
            case PX:
                return value;
            case DIP:
            case SP:
                return TypedValue.applyDimension(unit, value, metrics);
            default:
                throw new IllegalArgumentException("unknow unix");
        }
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 测量listview的高度
     *
     * @param listview
     */
    public static void setListViewHeight(ListView listview) {

        ListAdapter listAdapter = listview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listview);
            if (listItem == null) {
                continue;
            }
            // 对每一个item进行测量
            listItem.measure(0, 0);
            totalHeight += (listItem.getMeasuredHeight() + listview
                    .getDividerHeight());

            Log.e(TAG, "ListView TotalHeight" + totalHeight);
        }

        listview.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, totalHeight));

    }

    /**
     * 测量gridview的高度
     *
     * @param context
     * @param gridView
     * @param numColumns
     */
    public static void setGridViewHeight(Context context, GridView gridView,
                                         int numColumns) {

        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        if (numColumns == 0) {
            Log.e(TAG, "除数不能为零");
            return;
        }
        int totalHeight = 0;
        int dimension = 3 + 10;
        int count = listAdapter.getCount();
        if (count % 4 == 0) {
            count = count - 1;
        }
        for (int i = 0; i < listAdapter.getCount() / numColumns + 1; i++) {
            View listItem = listAdapter.getView(i, null, gridView);
            if (listItem == null) {
                continue;
            }
            // 对每一个item进行测量
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            totalHeight += dimension;

            Log.e(TAG, "GridView TotalHeight" + totalHeight);
        }

        gridView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, totalHeight));
    }

    @SuppressLint("NewApi")
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int col = listView.getNumColumns();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight()
                    + listView.getHorizontalSpacing();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
//		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }

}
