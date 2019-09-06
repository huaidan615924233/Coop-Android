package zuo.biao.library.ui.statusbar;

import android.os.Build;


public class StatusBarExclude {
    static boolean exclude = false;

    public static void excludeIncompatibleFlyMe() {
        try {
            Build.class.getMethod("hasSmartBar");
        } catch (NoSuchMethodException e) {
            exclude |= Build.BRAND.contains("Meizu");
        }
    }
}
