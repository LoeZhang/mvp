package com.loe.mvp.ext_app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.loe.mvp.R
import com.loe.mvp.util.ResultUtil
import kotlin.reflect.KClass

class XIntent(packageContext: Context?, cls: Class<*>?) : Intent(packageContext, cls)
{
    var onResult: ((ResultUtil.ResultBean) -> Unit)? = null
    var onAfter: (Activity.() -> Unit)? = null
    var isFinish = false
}

fun Intent.onResult(onResult: (ResultUtil.ResultBean) -> Unit): Intent
{
    if (this is XIntent) this.onResult = onResult
    return this
}

fun Intent.onResultOk(onResult: (ResultUtil.ResultBean) -> Unit): Intent
{
    if (this is XIntent) this.onResult = { if (it.isOk()) onResult(it) }
    return this
}

fun Intent.after(onAfter: Activity.() -> Unit): Intent
{
    if (this is XIntent) this.onAfter = onAfter
    return this
}

fun Intent.afterFinish(onAfter: (Activity.() -> Unit)? = null): Intent
{
    if (this is XIntent)
    {
        this.onAfter = onAfter
        this.isFinish = true
    }
    return this
}

fun Context?.start(cls: Class<out Activity>, delay: Long = 0): XIntent
{
    val intent = XIntent(this, cls)
    Handler().postDelayed({
        if (intent.onResult == null)
        {
            this?.startActivity(intent)
        } else
        {
            if (this is FragmentActivity) ResultUtil.startResult(this, intent, intent.onResult)
        }
        if (this is Activity)
        {
            if (intent.isFinish) finish()
            intent.onAfter?.invoke(this)
        }
    }, delay)
    return intent
}

fun Context?.start(kCls: KClass<out Activity>, delay: Long = 0): XIntent
{
    return start(kCls.java, delay)
}

fun Fragment?.start(cls: Class<out Activity>, delay: Long = 0): XIntent
{
    return this?.activity.start(cls, delay)
}

fun Fragment?.start(kCls: KClass<out Activity>, delay: Long = 0): XIntent
{
    return this?.activity.start(kCls, delay)
}

/**
 * 状态栏
 */
fun Activity.setStatusAlpha(isBlack: Boolean = true)
{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (isBlack)
        {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else
        {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }
}

fun Activity.setStatusText(isBlack: Boolean = true)
{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
        val window = this.window
        if (isBlack)
        {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else
        {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}

fun Activity.setStatusBar(color: Int = color(R.color.colorPrimary), isTextBlack: Boolean = false)
{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
        val window = this.window
        if (isTextBlack)
        {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else
        {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }
}

/**
 * 列表
 */

fun RecyclerView.addOnItemClickListener(onClick: (i: Int) -> Unit)
{
    this.addOnItemTouchListener(object : OnItemClickListener()
    {
        override fun onSimpleItemClick(baseQuickAdapter: BaseQuickAdapter<*, *>?, view: View?, i: Int)
        {
            onClick(i)
        }
    })
}

/**
 * 添加延时、防重复点击的itemClick事件
 */
fun RecyclerView.addDelayItemClickListener(delay: Long = 100, interval: Long = 1200, onClick: (i: Int) -> Unit)
{
    var t = 0L
    this.addOnItemTouchListener(object : OnItemClickListener()
    {
        override fun onSimpleItemClick(baseQuickAdapter: BaseQuickAdapter<*, *>?, view: View?, i: Int)
        {
            if (System.currentTimeMillis() - t > interval)
            {
                postDelayed({ onClick(i) }, delay)
                t = System.currentTimeMillis()
            }
        }
    })
}

fun RecyclerView.addOnItemChildClickListener(id: Int = 0, onClick: (i: Int) -> Unit)
{
    this.addOnItemTouchListener(object : OnItemChildClickListener()
    {
        override fun onSimpleItemChildClick(baseQuickAdapter: BaseQuickAdapter<*, *>?, view: View?, i: Int)
        {
            if (id == 0 || view?.id == id)
            {
                onClick(i)
            }
        }
    })
}

fun RecyclerView.addOnItemChildClickListener(id: Int = 0, interval: Long, onClick: (i: Int) -> Unit)
{
    var t = 0L
    this.addOnItemTouchListener(object : OnItemChildClickListener()
    {
        override fun onSimpleItemChildClick(baseQuickAdapter: BaseQuickAdapter<*, *>?, view: View?, i: Int)
        {
            if (id == 0 || view?.id == id && System.currentTimeMillis() - t > interval)
            {
                onClick(i)
                t = System.currentTimeMillis()
            }
        }
    })
}

fun RecyclerView.addOnItemLongClickListener(onClick: (i: Int) -> Unit)
{
    this.addOnItemTouchListener(object : OnItemLongClickListener()
    {
        override fun onSimpleItemLongClick(baseQuickAdapter: BaseQuickAdapter<*, *>?, view: View?, i: Int)
        {
            onClick(i)
        }
    })
}

fun RecyclerView.addOnItemChildLongClickListener(id: Int = 0, onClick: (i: Int) -> Unit)
{
    this.addOnItemTouchListener(object : OnItemChildLongClickListener()
    {
        override fun onSimpleItemChildLongClick(baseQuickAdapter: BaseQuickAdapter<*, *>?, view: View?, i: Int)
        {
            if (id == 0 || view?.id == id)
            {
                onClick(i)
            }
        }
    })
}

fun RecyclerView.addHeaderView(view: View)
{
    (adapter as BaseQuickAdapter<*, *>).addHeaderView(view)
}

fun RecyclerView.addHeaderView(view: View, index: Int)
{
    (adapter as BaseQuickAdapter<*, *>).addHeaderView(view, index)
}

fun RecyclerView.addFooterView(view: View)
{
    (adapter as BaseQuickAdapter<*, *>).addFooterView(view)
}

fun RecyclerView.addFooterView(view: View, index: Int)
{
    (adapter as BaseQuickAdapter<*, *>).addFooterView(view, index)
}

fun <T> createAdapter(layoutId: Int, list: List<T> = ArrayList(), convert: (holder: BaseViewHolder, bean: T) -> Unit): BaseQuickAdapter<T, BaseViewHolder>
{
    return object : BaseQuickAdapter<T, BaseViewHolder>(layoutId, list)
    {
        override fun convert(holder: BaseViewHolder, bean: T)
        {
            convert(holder, bean)
        }
    }
}

fun <T> RecyclerView.setQuickAdapter(layoutId: Int, list: List<T> = ArrayList(), convert: (holder: BaseViewHolder, bean: T) -> Unit): BaseQuickAdapter<T, BaseViewHolder>
{
    adapter = object : BaseQuickAdapter<T, BaseViewHolder>(layoutId, list)
    {
        override fun convert(holder: BaseViewHolder, bean: T)
        {
            convert(holder, bean)
        }
    }
    return adapter as BaseQuickAdapter<T, BaseViewHolder>
}

/**
 * 其他
 */
fun px(dp: Int): Int
{
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun Context.color(id: Int): Int
{
    return ContextCompat.getColor(this, id)
}

fun Context.inflate(layoutId: Int, root: ViewGroup? = null): View
{
    return LayoutInflater.from(this).inflate(layoutId, root)
}

fun Activity.getWindowWidth(): Int
{
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun Activity.getWindowHeight(): Int
{
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

fun Activity.hideSoft()
{
    runCatching()
    {
        val view = this.currentFocus
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        view.clearFocus()
    }
}

fun Activity.showSoft(view: View)
{
    runCatching()
    {
        view.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * 跳转动画
 */
fun Activity?.transQuick() = this?.overridePendingTransition(R.anim.quick, R.anim.quick)

fun Activity?.transInAlpha() = this?.overridePendingTransition(R.anim.alpha_in, R.anim.on)

fun Activity?.transInScale() = this?.overridePendingTransition(R.anim.scale_in, R.anim.on)

fun Activity?.transOutScale() = this?.overridePendingTransition(R.anim.on, R.anim.scale_out)

fun Activity?.transInTop() = this?.overridePendingTransition(R.anim.slide_top_in, R.anim.on)

fun Activity?.transOutTop() = this?.overridePendingTransition(R.anim.on, R.anim.slide_top_out)

fun Activity?.transInBottom() = this?.overridePendingTransition(R.anim.slide_bottom_in, R.anim.on)

fun Activity?.transOutBottom() = this?.overridePendingTransition(R.anim.on, R.anim.slide_bottom_out)

fun Activity?.transOut() = this?.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out)


fun Context?.showToast(o: Any?)
{
    Toast.makeText(this, "" + o, Toast.LENGTH_SHORT).show()
}