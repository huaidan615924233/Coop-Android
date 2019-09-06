package retrofit_rx.http;

import android.text.TextUtils;
import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit_rx.Api.BaseApi;
import retrofit_rx.RxRetrofitApp;
import retrofit_rx.exception.RetryWhenNetworkException;
import retrofit_rx.http.fastjson.FastJsonConverterFactory;
import retrofit_rx.listener.HttpOnNextListener;
import retrofit_rx.subscribers.ProgressSubscriber;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * http交互处理类
 * Created by WZG on 2016/7/16.
 */
public class HttpManager {
    private volatile static HttpManager INSTANCE;

    //构造方法私有
    private HttpManager() {
    }

    //获取单例
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void doHttpDeal(BaseApi basePar) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);
//        builder.addInterceptor(new CookieInterceptor(basePar.isCache(), basePar.getUrl()));
        if (RxRetrofitApp.isDebug()) {
            builder.addInterceptor(getHttpLoggingInterceptor());
        }
        Interceptor mTokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                String token = basePar.getToken();
                if (TextUtils.isEmpty(token)) {//表示第一次登陆还没拉取过token
                    return chain.proceed(originalRequest);//执行登陆的操作
                }
                //此处表示已有token 这时只需要判断401即可
                Request authorised = originalRequest.newBuilder()
                        .header("Authorization","Bearer " + token)//此处的token 是你保存在本地的
                        .build();
                Response response = chain.proceed(authorised);//执行此次请求
//                if (response.code() == 700) {//返回为token失效
//                    refreshToken();//重新获取token，此处的刷新token需要同步执行以防止异步到来的问题
//                    Request newRequest = originalRequest.newBuilder()
//                            .header("Authorization", token)
//                            .build();//
//                    return chain.proceed(newRequest);
//                }
                return response;
            }
        };
        builder.addInterceptor(mTokenInterceptor);

        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(basePar.getBaseUrl())
                .build();


        /*rx处理*/
        ProgressSubscriber subscriber = new ProgressSubscriber(basePar);
        Observable observable = basePar.getObservable(retrofit)
                /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException(basePar.getRetryCount(),
                        basePar.getRetryDelay(), basePar.getRetryIncreaseDelay()))
                /*生命周期管理*/
//                .compose(basePar.getRxAppCompatActivity().bindToLifecycle())
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidScheduler.mainThread())
                /*结果判断*/
                .map(basePar);


        /*链接式对象返回*/
        SoftReference<HttpOnNextListener> httpOnNextListener = basePar.getListener();
        if (httpOnNextListener != null && httpOnNextListener.get() != null) {
            httpOnNextListener.get().onNext(observable);
        }

        /*数据回调*/
        observable.subscribe(subscriber);

    }


    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit", "Retrofit====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }
}
