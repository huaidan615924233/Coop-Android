package com.coop.android.utils;

import android.content.Context;

public class DensityUtil {
    public static int dp2px(Context ctx, float dp) {
        float dendity = ctx.getResources().getDisplayMetrics().density;
        int px = (int) (dp * dendity + 0.5f);
        return px;
    }

    public static float px2dp(Context ctx, int px) {
        float dendity = ctx.getResources().getDisplayMetrics().density;
        float dp = px / dendity;
        return dp;
    }

    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

}