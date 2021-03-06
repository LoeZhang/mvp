package com.loe.mvp.initer

import android.view.MotionEvent


/**
 * BaseView初始化器
 *
 * @author 章路顺
 * @since 2020/6/18-16:09
 */
object BaseIniter
{
    var onBaseView: OnBaseView? = null
        private set

    fun init(onBaseView: OnBaseView)
    {
        BaseIniter.onBaseView = onBaseView
    }
}

interface OnBaseView
{
    fun onCreate(o: Any)

    fun onResume(o: Any){}

    fun onTouchEvent(ev: MotionEvent){}

    fun onDestroy(o: Any)
}