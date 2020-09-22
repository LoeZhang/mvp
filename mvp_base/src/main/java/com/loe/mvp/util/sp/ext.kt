package com.loe.mvp.util.sp

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * SP代理扩展
 *
 * @author zls
 * @since 2020/9/22-11:01
 */
fun string(defaultValue: String = "") = object : ReadWriteProperty<Any, String>
{
    override fun getValue(thisRef: Any, property: KProperty<*>): String
    {
        return SPManager.getString(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String)
    {
        SPManager.putString(property.name, value)
    }
}

fun int(defaultValue: Int = 0) = object : ReadWriteProperty<Any, Int>
{

    override fun getValue(thisRef: Any, property: KProperty<*>): Int
    {
        return SPManager.getInt(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int)
    {
        SPManager.putInt(property.name, value)
    }
}

fun long(defaultValue: Long = 0L) = object : ReadWriteProperty<Any, Long>
{

    override fun getValue(thisRef: Any, property: KProperty<*>): Long
    {
        return SPManager.getLong(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long)
    {
        SPManager.putLong(property.name, value)
    }
}

fun float(defaultValue: Float = 0.0f) = object : ReadWriteProperty<Any, Float>
{
    override fun getValue(thisRef: Any, property: KProperty<*>): Float
    {
        return SPManager.getFloat(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Float)
    {
        SPManager.putFloat(property.name, value)
    }
}

fun double(defaultValue: Double = 0.0) = object : ReadWriteProperty<Any, Double>
{
    override fun getValue(thisRef: Any, property: KProperty<*>): Double
    {
        return SPManager.getDouble(property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Double)
    {
        SPManager.putDouble(property.name, value)
    }
}

fun boolean(defaultValue: Boolean = false) = object : ReadWriteProperty<Any, Boolean>
{
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean
    {
        return SPManager.getBoolean(property.name, defaultValue)
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: Boolean
    )
    {
        SPManager.putBoolean(property.name, value)
    }
}