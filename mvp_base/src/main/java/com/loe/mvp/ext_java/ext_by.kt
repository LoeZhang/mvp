package com.loe.mvp.ext_java

import android.widget.TextView
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 委托监听
 */
inline fun <T> observe(init: T, crossinline onChange: (value: T) -> Unit):
    ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(init)
{
    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) =
        onChange(newValue)
}

inline fun observeString(init: String = "", crossinline onChange: (value: String) -> Unit):
    ReadWriteProperty<Any?, String> = object : ObservableProperty<String>(init)
{
    override fun afterChange(property: KProperty<*>, oldValue: String, newValue: String) =
        onChange(newValue)
}

inline fun observeInt(init: Int = 0, crossinline onChange: (value: Int) -> Unit):
    ReadWriteProperty<Any?, Int> = object : ObservableProperty<Int>(init)
{
    override fun afterChange(property: KProperty<*>, oldValue: Int, newValue: Int) =
        onChange(newValue)
}

inline fun observeLong(init: Long = 0L, crossinline onChange: (value: Long) -> Unit):
    ReadWriteProperty<Any?, Long> = object : ObservableProperty<Long>(init)
{
    override fun afterChange(property: KProperty<*>, oldValue: Long, newValue: Long) =
        onChange(newValue)
}

inline fun observeFloat(init: Float = 0f, crossinline onChange: (value: Float) -> Unit):
    ReadWriteProperty<Any?, Float> = object : ObservableProperty<Float>(init)
{
    override fun afterChange(property: KProperty<*>, oldValue: Float, newValue: Float) =
        onChange(newValue)
}

inline fun observeDouble(init: Double = 0.0, crossinline onChange: (value: Double) -> Unit):
    ReadWriteProperty<Any?, Double> = object : ObservableProperty<Double>(init)
{
    override fun afterChange(property: KProperty<*>, oldValue: Double, newValue: Double) =
        onChange(newValue)
}

inline fun observeBoolean(init: Boolean = false, crossinline onChange: (value: Boolean) -> Unit):
    ReadWriteProperty<Any?, Boolean> = object : ObservableProperty<Boolean>(init)
{
    override fun afterChange(property: KProperty<*>, oldValue: Boolean, newValue: Boolean) =
        onChange(newValue)
}

/**
 * 委托绑定
 */
fun bindString(getter: () -> TextView) = object : ReadWriteProperty<Any, String>
{
    override fun getValue(thisRef: Any, property: KProperty<*>): String
    {
        return getter().text.toString()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String)
    {
        getter().text = value
    }
}

fun bindString(textView: TextView) = object : ReadWriteProperty<Any, String>
{
    override fun getValue(thisRef: Any, property: KProperty<*>): String
    {
        return textView.text.toString()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String)
    {
        textView.text = value
    }
}

fun bindInt(getter: () -> TextView) = object : ReadWriteProperty<Any, Int>
{
    override fun getValue(thisRef: Any, property: KProperty<*>): Int
    {
        return try
        {
            getter().text.toString().toInt()
        }catch (e: java.lang.Exception)
        {
            0
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int)
    {
        getter().text = value.toString()
    }
}

fun bindInt(textView: TextView) = object : ReadWriteProperty<Any, Int>
{
    override fun getValue(thisRef: Any, property: KProperty<*>): Int
    {
        return try
        {
            textView.text.toString().toInt()
        }catch (e: java.lang.Exception)
        {
            0
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int)
    {
        textView.text = value.toString()
    }
}

fun bindDouble(getter: () -> TextView) = object : ReadWriteProperty<Any, Double>
{
    override fun getValue(thisRef: Any, property: KProperty<*>): Double
    {
        return try
        {
            getter().text.toString().toDouble()
        }catch (e: java.lang.Exception)
        {
            0.0
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Double)
    {
        getter().text = value.toString()
    }
}

fun bindDouble(textView: TextView) = object : ReadWriteProperty<Any, Double>
{
    override fun getValue(thisRef: Any, property: KProperty<*>): Double
    {
        return try
        {
            textView.text.toString().toDouble()
        }catch (e: java.lang.Exception)
        {
            0.0
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Double)
    {
        textView.text = value.toString()
    }
}