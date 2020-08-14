package com.loe.mvp.ext_java

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Html
import android.text.Spanned
import android.util.Log
import java.text.DecimalFormat
import java.util.*

val String?.int: Int
    get() = this?.toDouble()?.toInt() ?: 0

val String?.long: Long
    get() = this?.toDouble()?.toLong() ?: 0L

val String?.boolean: Boolean
    get() = this?.toBoolean() ?: false

val String?.double: Double
    get() = this?.toDouble() ?: 0.0

val String?.not0: String
    get() = if (this == null || this.double == 0.0) "" else this

val String?.string: String
    get() = if (this == null || this == "null") "" else this

val String?.stringNull: String?
    get() = if (this == null || isEmpty()) null else this

val String?.doubleLimit: String
    get() = DecimalFormat("0.##").format(this.double)

val Int?.string: String
    get() = this?.toString() ?: ""

val Double?.string: String
    get() {
        if (this != null && this - this.toLong() == 0.0) return this.toLong().toString()
        return this?.toString() ?: ""
    }

val Float?.string: String
    get() {
        if (this != null && this - this.toLong() == 0f) return this.toLong().toString()
        return this?.toString() ?: ""
    }

val Long?.string: String
    get() = this?.toString() ?: ""

val Boolean?.string: String
    get() = this?.toString() ?: ""

inline fun safe(run: () -> Unit) {
    try {
        run()
    } catch (e: Exception) {
        Log.e("AndroidRuntime", "" + e)
    }
}

fun delay(delay: Long = 0, callBack: () -> Unit): DelayTask
{
    val task = object : DelayTask(delay) {
        override fun run() {
            callBack()
        }
    }
    task.start()
    return task
}

fun timer(
    delay: Long,
    period: Long,
    times: Long = java.lang.Long.MAX_VALUE,
    callBack: () -> Unit
): AsyncTimer
{
    val task = object : AsyncTimer(delay, period, times) {
        override fun run() {
            callBack()
        }
    }
    task.start()
    return task
}

val Int.to99: String get() = if (this > 99) "99+" else toString()

val String.b: String get() = "<b>$this</b>"

val String.i: String get() = "<i>$this</i>"

val String.u: String get() = "<u>$this</u>"

fun String.c(c: String): String {
    return "<font color='$c'>$this</font>"
}

fun String.s(s: Int): String {
    return "<font size='$s'>$this</font>"
}

val String.red: String get() = c("#ff0000")

val String.blue: String get() = c("#5084fe")

val String.html: Spanned get() = Html.fromHtml(this)

val String.phone: String
    get() = try {
        this.substring(0, 3) + "****" + this.substring(7)
    } catch (e: Exception) {
        this
    }

val String.card: String
    get() = try {
        this.substring(0, 5) + "*********" + this.substring(14)
    } catch (e: Exception) {
        this
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

abstract class DelayTask(var delay: Long) : Handler(Looper.getMainLooper()) {

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

abstract class AsyncTimer(
    private val mDelay: Long,
    private val mPeriod: Long,
    private var mLastTime: Long = java.lang.Long.MAX_VALUE
):Timer() {
    private var mTimes: Long = 0

    private var mTask: TimerTask? = null

    protected var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            run()
        }
    }

    fun start() {
        try {
            mTimes = mLastTime
            mTask = object : TimerTask() {
                override fun run() {
                    if (mTimes > 0) {
                        mHandler.sendEmptyMessage(0)
                        mTimes--
                    }
                }
            }
            schedule(mTask, mDelay, mPeriod)
        } catch (e: Exception) {
        }
    }

    fun stop() {
        mLastTime = mTimes
        mTimes = 0
        if (mTask != null) {
            mTask!!.cancel()
        }
    }

    fun finish() {
        stop()
        cancel()
    }

    protected abstract fun run()
}