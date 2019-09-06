package zuo.biao.library.ui.statusbar;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import zuo.biao.library.R;

/**
 * 处理状态栏问题，使用fragment 时，用这个View 包装，再activity全屏状态下可以动态设置状态栏
 */
public class StatusBarWrapperView extends LinearLayout {

    private View mWrapperView;
    private StatusBarView mStatusBarView;

    private StatusBarWrapperView(Context context, View view) {
        this(context);
        mWrapperView = view;
    }

    private StatusBarWrapperView(Context context) {
        this(context, (AttributeSet) null);
    }

    private StatusBarWrapperView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private StatusBarWrapperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static View getWrapperView(Context context, View view) {
        StatusBarWrapperView wrapperView = new StatusBarWrapperView(context, view);
        wrapperView.init();
        return wrapperView;
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mWrapperView != null&&!StatusBarUtils.noControlStatusBar) {
                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                setLayoutParams(params);
                setOrientation(VERTICAL);
                mStatusBarView = new StatusBarView(getContext());
                mStatusBarView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark_new));
                addView(mStatusBarView);
                if (!StatusBarUtils.sCanLight) {
                    setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark_second));
                }
            }
        }
        addView(mWrapperView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 设置状态栏View 颜色
     *
     * @param color 颜色
     */
    public void setStatusBarColor(@ColorInt int color) {
        if (mStatusBarView != null) {
            mStatusBarView.setBackgroundColor(color);
        }
    }
}
