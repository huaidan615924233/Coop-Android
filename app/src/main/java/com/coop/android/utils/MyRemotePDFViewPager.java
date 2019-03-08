package com.coop.android.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;

/**
 * Created by MR-Z on 2019/2/19.
 */
public class MyRemotePDFViewPager extends RemotePDFViewPager {

    public MyRemotePDFViewPager(Context context, String pdfUrl, DownloadFile.Listener listener) {
        super(context, pdfUrl, listener);
    }

    public MyRemotePDFViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
