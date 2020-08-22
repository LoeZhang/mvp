package com.loe.test

import android.app.Activity
import android.app.Application
import android.view.MotionEvent
import android.widget.Toast
import com.loe.mvp.ext_app.px
import com.loe.mvp.initer.BaseIniter
import com.loe.mvp.initer.OnBaseView
import org.greenrobot.eventbus.EventBus

/**
 * App
 *
 * @author 章路顺
 * @since 2020/6/19-10:02
 */
class App : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        /** 有默认loading和toast，可以不初始化 */

        // 初始化Loading构造器（若无需实例对象，可用<Nothing>替代实例类<AppProgress>，create返回null）
//        LoadingIniter.init(object : LoadingCreater<AppProgress>()
//        {
//            override fun create(activity: Activity): AppProgress?
//            {
//                return AppProgress(activity)
//            }
//
//            override fun onShow(loading: AppProgress?, msg: CharSequence)
//            {
//                if (msg.isEmpty())
//                {
//                    loading?.show()
//                } else
//                {
//                    loading?.show(msg.toString())
//                }
//            }
//
//            override fun onCancel(loading: AppProgress?)
//            {
//                loading?.cancel()
//            }
//        })
//
//        // 初始化toast构造器（若无需实例对象，可用<Nothing>替代实例类<AppToast>，create返回null）
//        ToastIniter.init(object : ToastCreater<AppToast>()
//        {
//            override fun create(activity: Activity): AppToast?
//            {
//                return AppToast(activity)
//            }
//
//            override fun onShow(toast: AppToast?, msg: CharSequence)
//            {
//                toast?.show(msg)
//            }
//        })

        BaseIniter.init(object : OnBaseView
        {
            override fun onCreate(o: Any)
            {
                runCatching { EventBus.getDefault().register(o) }
            }

            override fun onResume(o: Any)
            {
                if (o is Activity) Toast.makeText(o, "省市都是所多", Toast.LENGTH_SHORT).show()
            }

            override fun onDestroy(o: Any)
            {
                EventBus.getDefault().unregister(o)
            }

            private var touchCount = 1
            private var lastTouchTime = 0L

            override fun onTouchEvent(ev: MotionEvent)
            {
                if (ev?.action == MotionEvent.ACTION_DOWN)
                {
                    if (System.currentTimeMillis() - lastTouchTime < 800)
                    {
                        touchCount++
                        if (touchCount == 3 && ev?.rawY < px(100))
                        {
//                            LoeLogger.toLogger(activity)
                        }
                    } else
                    {
                        touchCount = 1
                    }
                    lastTouchTime = System.currentTimeMillis()
                }
                if (ev?.action == MotionEvent.ACTION_MOVE)
                {
                    touchCount = 0
                }
            }
        })
    }
}