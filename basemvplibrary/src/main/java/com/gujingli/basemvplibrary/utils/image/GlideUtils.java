package com.gujingli.basemvplibrary.utils.image;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Administrator on 2016/12/9.
 */

public class GlideUtils {

    /**
     * Glide特点
     * 使用简单
     * 可配置度高，自适应程度高
     * 支持常见图片格式 Jpg png gif webp
     * 支持多种数据源  网络、本地、资源、Assets 等
     * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
     * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
     * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
     * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
     */
    //默认加载
    public static void loadImageView(Context context, int error, String path, ImageView mImageView) {
        Glide.with(context).load(path).error(error).into(mImageView);
    }//默认加载

    public static void loadImageView(Fragment fragment, int error, String path, ImageView mImageView) {
        Glide.with(fragment).load(path).error(error).into(mImageView);
    }

    public static void loadImageView(Context context, String path, ImageView mImageView) {
        Glide.with(context).load(path).into(mImageView);
    }

    //默认加载
    public static void loadImageView(Fragment fragment, String path, ImageView mImageView) {
        Glide.with(fragment).load(path).into(mImageView);
    }

    //本地加载
    public static void loadGifImage(Context context, int res, ImageView mImageView) {
        Glide.with(context).load(res).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImageView);
    }

    //本地加载1
    public static void loadActionGifImage(Context context, int res, ImageView mImageView) {
        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(mImageView, 1);
        Glide.with(context).load(res).into(target);
    }

    public static void loadGifOnce(Context context, int res, ImageView mImageView) {
        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(mImageView, 1);
        Glide.with(context).load(res).crossFade().into(target);
    }

    //加载指定大小
    public static void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        Glide.with(mContext).load(path).override(width, height).into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLoding(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load(path).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }

    //设置加载中以及加载失败图片并且指定大小
    public static void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load(path).override(width, height).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }

    //设置跳过内存缓存
    public static void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).skipMemoryCache(true).into(mImageView);
    }

    //设置跳过内存缓存
    public static void loadImageViewNoCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(mImageView);
    }

    //设置下载优先级
    public static void loadImageViewPriority(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).priority(Priority.NORMAL).into(mImageView);
    }

    /**
     * 策略解说：
     * <p/>
     * all:缓存源资源和转换后的资源
     * <p/>
     * none:不作任何磁盘缓存
     * <p/>
     * source:缓存源资源
     * <p/>
     * result：缓存转换后的资源
     */

    //设置缓存策略
    public static void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageView);
    }

    /**
     * api也提供了几个常用的动画：比如crossFade()
     */

    //设置加载动画
    public static void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView) {
        Glide.with(mContext).load(path).animate(anim).into(mImageView);
    }

    /**
     * 会先加载缩略图
     */

    //设置缩略图支持
    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
    }

    /**
     * api提供了比如：centerCrop()、fitCenter()等
     */

    //设置动态转换
    public static void loadImageViewCrop(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).centerCrop().into(mImageView);
    }

    //设置动态GIF加载方式
    public static void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).asGif().into(mImageView);
    }

    //设置静态GIF加载方式
    public static void loadImageViewStaticGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).asBitmap().into(mImageView);
    }

    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

    //设置监听请求接口
    public static void loadImageViewListener(Context mContext, String path, ImageView mImageView, RequestListener<String, GlideDrawable> requstlistener) {
        Glide.with(mContext).load(path).listener(requstlistener).into(mImageView);
    }

    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排

    //设置要加载的内容
    public static void loadImageViewContent(Context mContext, String path, SimpleTarget<GlideDrawable> simpleTarget) {
        Glide.with(mContext).load(path).centerCrop().into(simpleTarget);
    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }

    //加载圆形图
    public static void loadCircleImg(Context mContext, String url, ImageView mImagview) {
        Glide.with(mContext).load(url).transform(new GlideCircleTransform(mContext)).into(mImagview);
    }
    //加载圆形图
    public static void loadCircleImg(Context mContext, int url, ImageView mImagview) {
        Glide.with(mContext).load(url).transform(new GlideCircleTransform(mContext)).into(mImagview);
    }

    //加载圆形图
    public static void loadCircleImg(Context mContext, int error, String url, ImageView mImagview) {
        Glide.with(mContext).load(url).error(error).transform(new GlideCircleTransform(mContext)).into(mImagview);
    }

    //加载圆形图
    public static void loadCircleImg(Context mContext, int error, int url, ImageView mImagview) {
        Glide.with(mContext).load(url).error(error).transform(new GlideCircleTransform(mContext)).into(mImagview);
    }

    //加载本地圆形
    public static void loadLocalCircleImg(Context mContext, int url, ImageView mImagview) {
        Glide.with(mContext).load(url).transform(new GlideCircleTransform(mContext)).into(mImagview);
    }

    //加载圆角图
    public static void loadCircleSideImg(Context mContext, String url, ImageView mImagview) {
        Glide.with(mContext).load(url).transform(new GlideRoundTransform(mContext)).into(mImagview);
    }

    //加载本地圆角
    public static void loadLocalCircleSideImg(Context mContext, int url, ImageView mImagview) {
        Glide.with(mContext).load(url).transform(new GlideRoundTransform(mContext)).into(mImagview);
    }

    //加载圆角图
    public static void loadCircleSide4InputImg(Context mContext, String url, ImageView mImagview, int dp) {
        Glide.with(mContext).load(url).transform(new GlideRoundTransform(mContext, dp)).into(mImagview);
    }

    //加载本地圆角
    public static void loadLocalCircleSide4InputImg(Context mContext, int url, ImageView mImagview, int dp) {
        Glide.with(mContext).load(url).transform(new GlideRoundTransform(mContext, dp)).into(mImagview);
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context mContext, final int imageUrl, final ImageView imageView) {
        Glide.with(mContext)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Integer, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        imageView.setImageResource(imageUrl);
                        return false;
                    }
                })
                .into(imageView);
    }
}

