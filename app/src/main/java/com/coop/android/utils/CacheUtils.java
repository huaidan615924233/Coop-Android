package com.coop.android.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 缓存工具类
 * Created by wangz on 2018/1/4.
 */
public class CacheUtils {
    private static DecimalFormat sFileIntegerFormat = new DecimalFormat("#0");
    private static DecimalFormat sFileDecimalFormat = new DecimalFormat("#0.#");

    public static ArrayList<String> getChachePath(Context context) {
        ArrayList<String> paths = new ArrayList<>();
        paths.add(context.getApplicationContext().getCacheDir().getAbsolutePath() + "/coop/");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            paths.add(Environment.getExternalStorageDirectory().getAbsolutePath() + "/coop/");
        return paths;
    }

    /**
     * 返回 文件的缓存大小
     */
    public static String getAppCacheSize(Context context) {
        ArrayList<String> cachePath = getChachePath(context);

        long cacheSize = 0;
        for (int i = 0; i < cachePath.size(); i++) {
            cacheSize += getDirSize(new File(cachePath.get(i)));
        }

        return cacheSize == 0 ? "0.0M" : formatFileSize(cacheSize, false);
    }

    /**
     * 清除APP缓存
     */
    public static boolean clearAppCache(Context context) {
        ArrayList<String> cachePath = getChachePath(context);
        try {
            for (int i = 0; i < cachePath.size(); i++) {
                deleteFile(new File(cachePath.get(i)));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取目录文件大小
     */
    public static long getDirSize(File dir) {
        long dirSize = 0;
        try {
            if (dir == null) {
                return 0;
            }
            if (!dir.isDirectory()) {
                return 0;
            }

            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize += file.length();
                    dirSize += getDirSize(file); // 递归调用继续统计
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirSize;
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void deleteFile(File file) {
        try {
            if (!file.exists()) {
                return;
            } else {
                if (file.isFile()) {
                    file.delete();
                    return;
                }
                if (file.isDirectory()) {
                    File[] childFile = file.listFiles();
                    if (childFile == null || childFile.length == 0) {
                        file.delete();
                        return;
                    }
                    for (File f : childFile) {
                        deleteFile(f);
                    }
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单位换算
     *
     * @param size      单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? sFileIntegerFormat : sFileDecimalFormat;
        String fileSizeString = "0M";
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取应用缓存路径
     */
    public static File externalFilesDir(Context c) {
        File f = c.getExternalFilesDir(null);
        // not support android 2.1
        if (f == null || !f.exists()) {
            f = c.getFilesDir();
        }
        return f;
    }

    /**
     * 创建目录
     */
    public static boolean createFileDir(String path) {
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
            return true;
        }
        return false;
    }
}