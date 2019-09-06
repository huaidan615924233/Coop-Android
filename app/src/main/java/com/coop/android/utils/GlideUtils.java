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
package com.coop.android.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.coop.android.R;

/**
 * Created by MR-Z on 2019/1/14.
 */
public class GlideUtils {
    static RequestOptions options = new RequestOptions().placeholder(R.mipmap.default_user_header)                //加载成功之前占位图
            .error(R.mipmap.default_user_header)                    //加载错误之后的错误图
//            .override(400, 400)                                //指定图片的尺寸
            //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
            .fitCenter()
            //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
            .centerCrop()
            .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
            .skipMemoryCache(true);                     //跳过内存缓存
//            .diskCacheStrategy(DiskCacheStrategy.ALL)        //缓存所有版本的图像
//            .diskCacheStrategy(DiskCacheStrategy.NONE)        //跳过磁盘缓存
//            .diskCacheStrategy(DiskCacheStrategy.DATA)        //只缓存原来分辨率的图片
//            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);    //只缓存最终的图片

    public static void loadImage(Context context, String url, ImageView view, int defaultImage) {
        if (url != null && TextUtils.isEmpty(url)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setImageResource(defaultImage);
            }
        } else {
            options.placeholder(defaultImage).error(defaultImage);
            Glide.with(context).load(url).apply(options).into(view);
        }
    }

    public static void loadImageRound(Context context, String url, ImageView view, int defaultImage) {
        if (url != null && TextUtils.isEmpty(url)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setImageResource(defaultImage);
            }
        } else {
            RequestOptions options = new RequestOptions().placeholder(defaultImage)                //加载成功之前占位图
                    .error(defaultImage)                    //加载错误之后的错误图
//            .override(400, 400)                                //指定图片的尺寸
                    //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                    .fitCenter()
                    //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                    .centerCrop()
//                    .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
                    .skipMemoryCache(true);                     //跳过内存缓存
//            .diskCacheStrategy(DiskCacheStrategy.ALL)        //缓存所有版本的图像
//            .diskCacheStrategy(DiskCacheStrategy.NONE)        //跳过磁盘缓存
//            .diskCacheStrategy(DiskCacheStrategy.DATA)        //只缓存原来分辨率的图片
//            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);    //只缓存最终的图片
            Glide.with(context).load(url).apply(options).into(view);
        }
    }
}
