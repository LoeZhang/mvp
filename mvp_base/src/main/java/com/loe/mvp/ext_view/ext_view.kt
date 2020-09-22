package com.loe.mvp.ext_view

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.Html
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

var View?.visible: Boolean
    get() = if (this != null) this.visibility == View.VISIBLE else false
    set(value)
    {
        this?.visibility = if (value) View.VISIBLE else View.GONE
    }

fun View?.setText(o: Any)
{
    if (this is TextView) text = o.toString()
}

val View?.string: String
    get() = if (this is TextView) text.trim().toString() else ""

val View?.stringNull: String?
    get() = if (string.isNotEmpty()) string else null

val View?.int: Int
    get() = try
    {
        string.toInt()
    } catch (e: Exception)
    {
        0
    }

var View?.html: String
    get() = if (this is TextView) text.trim().toString() else ""
    set(value)
    {
        (this as TextView).text = Html.fromHtml(value)
    }

val View?.double: Double
    get() = try
    {
        string.toDouble()
    } catch (e: Exception)
    {
        0.0
    }

val View?.tagString: String?
    get() = this?.tag?.toString()?.trim() ?: null


val View?.tagStringNull: String?
    get()
    {
        val s = this?.tag?.toString()?.trim()
        return if (s == null || s.isEmpty()) null else s
    }

val View?.tagJsonArray: JSONArray?
    get() = if (this?.tag is JSONArray) this.tag as JSONArray else try
    {
        JSONArray(this?.tag?.toString())
    } catch (e: Exception)
    {
        null
    }

val View?.tagJson: JSONObject?
    get() = if (this?.tag is JSONObject) this.tag as JSONObject else try
    {
        JSONObject(this?.tag?.toString())
    } catch (e: Exception)
    {
        null
    }

val View?.isEmpty get() = string.isEmpty()
val View?.isNotEmpty get() = string.isNotEmpty()

fun View?.putText(s: String)
{
    this.setText(s)
    if (this is EditText) setSelection(s.length)
}

fun View?.setMaxLen(i: Int)
{
    if (this is TextView) filters = arrayOf(InputFilter.LengthFilter(i))
}

fun View?.setFilter(s: String)
{
    if (this is TextView) keyListener = DigitsKeyListener.getInstance(s)
}

/**
 * 判断是否点击了某个View
 */
fun View.inRangeOfView(ev: MotionEvent?): Boolean
{
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    val x = location[0]
    val y = location[1]
    return !(ev!!.x < x || ev.x > x + this.width || ev.y < y || ev.y > y + this.height)
}

fun View.setDelayClickListener(delay: Long = 50, interval: Long = 1200, onClick: (v: View) -> Unit)
{
    var t = 0L
    setOnClickListener()
    {
        if (System.currentTimeMillis() - t > interval)
        {
            postDelayed({ onClick(it) }, delay)
            t = System.currentTimeMillis()
        }
    }
}

fun View.setOnDownListener(onDown: (v: View) -> Unit)
{
    setOnTouchListener()
    { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) onDown(v)
        true
    }
}


fun TextView.setOnEditorSearchListener(listener: () -> Unit)
{
    setOnEditorActionListener()
    { _, id, _ ->
        if (id == EditorInfo.IME_ACTION_SEARCH)
        {
            listener()
            return@setOnEditorActionListener true
        }
        false
    }
}

fun TextView.setOnEditorOkListener(ok: () -> Unit)
{
    setOnEditorActionListener()
    { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE)
        {
            ok()
        }
        false
    }
}

fun TextView.addTextAfterListener(after: (s: String) -> Unit)
{
    this.addTextChangedListener(object : TextWatcher
    {
        override fun afterTextChanged(s: Editable?)
        {
            after(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
        {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
        {
        }
    })
}

fun TextView.addTextAfterListener(delay: Long, after: (s: String) -> Unit)
{
    var task: DelayTask? = null
    addTextAfterListener()
    {
        task?.stop()
        task = object : DelayTask(delay)
        {
            override fun run()
            {
                after(it)
            }
        }
        task?.start()
    }
}

internal abstract class DelayTask(var delay: Long) : Handler(Looper.getMainLooper()) {

    private var runnable = Runnable { this@DelayTask.run() }

    fun start() {
        if (delay == 0L) {
            post(runnable)
        } else {
            postDelayed(runnable, delay)
        }
    }

    fun stop() {
        removeCallbacks(runnable)
    }

    protected abstract fun run()
}