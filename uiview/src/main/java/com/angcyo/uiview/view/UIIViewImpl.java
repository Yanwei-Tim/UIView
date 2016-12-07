package com.angcyo.uiview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.resources.AnimUtil;
import com.angcyo.uiview.widget.UIViewPager;

import butterknife.ButterKnife;

/**
 * 接口的实现, 仅处理了一些动画, 其他实现都为空
 * 对对话框做了区分处理
 * <p>
 * Created by angcyo on 2016-11-12.
 */

public abstract class UIIViewImpl implements IView {

    public static final int DEFAULT_ANIM_TIME = 300;

    protected ILayout mILayout;
    protected Context mContext;
    /**
     * 根布局
     */
    protected View mRootView;

    public static void setDefaultConfig(Animation animation) {
        animation.setDuration(DEFAULT_ANIM_TIME);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setFillAfter(true);
    }

    @Override
    public TitleBarPattern loadTitleBar(Context context) {
        L.d(this.getClass().getSimpleName(), "loadTitleBar: ");
        return null;
    }

    @Override
    public View inflateContentView(Context context, ILayout iLayout, FrameLayout container, LayoutInflater inflater) {
        L.d(this.getClass().getSimpleName(), "inflateContentView: ");
        mContext = context;
        mILayout = iLayout;
        return inflateBaseView(container, inflater);
    }

    protected abstract View inflateBaseView(FrameLayout container, LayoutInflater inflater);

    @Override
    public void loadContentView(View rootView) {
        L.d(this.getClass().getSimpleName(), "loadContentView: ");
        mRootView = rootView;
        final Animation layoutAnimation = loadLayoutAnimation();
        if (layoutAnimation != null && mRootView instanceof ViewGroup) {
            final int childCount = ((ViewGroup) mRootView).getChildCount();
            if (childCount == 1) {
                final View firstView = ((ViewGroup) mRootView).getChildAt(0);
                if (firstView instanceof ViewGroup) {
                    AnimUtil.applyLayoutAnimation((ViewGroup) firstView, layoutAnimation);
                }
            }
        }
        try {
            ButterKnife.bind(this, mRootView);
        } catch (Exception e) {
        }
    }

    @Override
    public void onViewCreate() {
        L.d(this.getClass().getSimpleName(), "onViewCreate: ");
    }

    @Override
    public void onViewLoad() {
        L.d(this.getClass().getSimpleName(), "onViewLoad: ");
    }

    @Override
    public void onViewShow() {
        L.d(this.getClass().getSimpleName(), "onViewShow: ");
    }

    @Override
    public void onViewHide() {
        L.d(this.getClass().getSimpleName(), "onViewHide: ");
    }

    @Override
    public void onViewUnload() {
        L.d(this.getClass().getSimpleName(), "onViewUnload: ");
    }

    @Override
    public Animation loadStartAnimation() {
        L.d(this.getClass().getSimpleName(), "loadStartAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadFinishAnimation() {
        L.d(this.getClass().getSimpleName(), "loadFinishAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadShowAnimation() {
        L.d(this.getClass().getSimpleName(), "loadShowAnimation: ");
        return loadStartAnimation();
    }

    @Override
    public Animation loadHideAnimation() {
        L.d(this.getClass().getSimpleName(), "loadHideAnimation: ");
        return loadFinishAnimation();
    }

    @Override
    public Animation loadOtherExitAnimation() {
        L.d(this.getClass().getSimpleName(), "loadOtherExitAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadOtherEnterAnimation() {
        L.d(this.getClass().getSimpleName(), "loadOtherEnterAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public Animation loadOtherHideAnimation() {
        return loadOtherExitAnimation();
    }

    @Override
    public Animation loadOtherShowAnimation() {
        return loadOtherEnterAnimation();
    }

    @Override
    public Animation loadLayoutAnimation() {
        L.d(this.getClass().getSimpleName(), "loadLayoutAnimation: ");
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
        setDefaultConfig(translateAnimation);
        return translateAnimation;
    }

    @Override
    public boolean isDialog() {
        return false;
    }

    @Override
    public boolean isDimBehind() {
        return true;
    }

    @Override
    public boolean canCanceledOnOutside() {
        return true;
    }

    @Override
    public boolean canTouchOnOutside() {
        return true;
    }

    @Override
    public boolean canCancel() {
        return true;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    /**
     * 在inflateContentView之前调用, 返回的都是null
     */
    @Override
    public ILayout getILayout() {
        return mILayout;
    }

    /**
     * 此方法只在UIVIewPager中会调用, 当前IView显示时
     */
    @Override
    public void onShowInPager(UIViewPager viewPager) {

    }

    /**
     * 此方法只在UIVIewPager中会调, 当前IView隐藏时
     */
    @Override
    public void onHideInPager(UIViewPager viewPager) {

    }

    public void startIView(IView iView) {
        startIView(iView, true);
    }

    public void startIView(IView iView, boolean anim) {
        if (iView == null) {
            return;
        }
        if (mILayout == null) {
            throw new IllegalArgumentException("ILayout 还未初始化");
        }
        mILayout.startIView(iView, anim);
    }

    public void post(Runnable action) {
        if (mRootView != null) {
            mRootView.post(action);
        }
    }

    public void postDelayed(Runnable action, long delayMillis) {
        if (mRootView != null) {
            mRootView.postDelayed(action, delayMillis);
        }
    }
}
