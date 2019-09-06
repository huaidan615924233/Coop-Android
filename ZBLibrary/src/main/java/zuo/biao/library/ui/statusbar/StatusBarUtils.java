package zuo.biao.library.ui.statusbar;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;


import zuo.biao.library.R;

public class StatusBarUtils {
    public static boolean sCanLight=true;
    /**
     * true 不会修改状态栏
     */
    public static boolean noControlStatusBar=false;
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        if (noControlStatusBar) {
            return;
        }
        StatusBarCompat.setStatusBarColorCompat(activity, color, ContextCompat.getColor(activity.getBaseContext(), R.color.colorPrimaryDark_second));
    }

    public static void setStatusBarColorDefault(Activity activity) {
        if (noControlStatusBar) {
            return;
        }
        setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.colorPrimaryDark_new));
    }

    public static boolean setLightBar(Activity activity, boolean isLight) {
        if (noControlStatusBar) {
            return true;
        }
        return StatusBarCompat.setLightStatusBar(activity.getWindow(), isLight);
    }
}
