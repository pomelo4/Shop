package com.zykj.landous2;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Administrator on 2015/10/5.
 */
public class LandousApplication extends Application {
    private static LandousApplication instance;

    public static DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
    public static DisplayImageOptions options_head; // DisplayImageOptions是用于设置图片显示的类
    public static DisplayImageOptions options_circle; // DisplayImageOptions是用于设置图片显示的类
    public static DisplayImageOptions options_rectangle; // DisplayImageOptions是用于设置图片显示的类
    public static DisplayImageOptions options_square;
    public static DisplayImageOptions options_car;// 购物车
    public static DisplayImageOptions options_no_default; // DisplayImageOptions是用于设置图片显示的类

    public static LandousApplication getInstance() {
        if (instance == null) {
            instance = new LandousApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        initImageLoader(this);

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.searcher_no_result_empty_icon) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.searcher_no_result_empty_icon) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_image) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        options_car = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .showStubImage(R.drawable.default_image) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_image) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_image) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        options_head = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showStubImage(R.drawable.default_image) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_image) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_image) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        options_circle = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showStubImage(R.drawable.profile_head_placeholder_new) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.profile_head_placeholder_new) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.profile_head_placeholder_new) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(80)) // 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        options_rectangle = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showStubImage(R.drawable.default_image) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_image) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_image) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .resetViewBeforeLoading(false)
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        options_square = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showStubImage(R.drawable.searcher_no_result_empty_icon) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.searcher_no_result_empty_icon) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.searcher_no_result_empty_icon) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        options_no_default = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showStubImage(R.drawable.icon_default_800_400) //
                .showImageForEmptyUri(R.drawable.icon_default_800_400) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_default_800_400) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(5)
                .denyCacheImageMultipleSizesInMemory()

                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        // .writeDebugLogs() // Remove for release app
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(8 * 1024 * 1024)
                .discCache(new UnlimitedDiscCache(cacheDir))
                        // default
                .discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        Log.i("image_loader_init", "init");
    }
}
