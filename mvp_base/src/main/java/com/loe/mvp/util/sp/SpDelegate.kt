package com.loe.mvp.util.sp

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SpDelegate
{
    lateinit var sp: SharedPreferences

    @JvmOverloads
    fun init(context: Context, name: String = "user", mode: Int = Context.MODE_PRIVATE)
    {
        sp = context.getSharedPreferences(name + this::class.java.name, mode)
    }

    /** 获取存储字符串  */
    fun getString(key: String): String
    {
        return sp.getString(key, "")
    }

    /** 获取存储字符串  */
    fun getString(key: String, dft: String): String
    {
        return sp.getString(key, dft)
    }

    /** 录入存储字符串  */
    fun putString(key: String, value: String)
    {
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /** 获取存储整数  */
    fun getInt(key: String): Int
    {
        return sp.getInt(key, 0)
    }

    /** 获取存储整数  */
    fun getInt(key: String, defValue: Int): Int
    {
        return sp.getInt(key, defValue)
    }

    /** 录入存储整数  */
    fun putInt(key: String, value: Int)
    {
        val editor = sp.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /** 获取存储长整数  */
    fun getLong(key: String): Long
    {
        return sp.getLong(key, 0)
    }

    /** 获取存储长整数  */
    fun getLong(key: String, defValue: Long): Long
    {
        return sp.getLong(key, defValue)
    }

    /** 录入存储长整数  */
    fun putLong(key: String, value: Long)
    {
        val editor = sp.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /** 获取存储小数  */
    fun getFloat(key: String): Float
    {
        return sp.getFloat(key, 0f)
    }

    /** 获取存储小数  */
    fun getFloat(key: String, defValue: Float): Float
    {
        return sp.getFloat(key, defValue)
    }

    /** 录入存储小数  */
    fun putFloat(key: String, value: Float)
    {
        val editor = sp.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    /** 获取存储双小数  */
    fun getDouble(key: String): Double
    {
        return sp.getString(key, "0").toDouble()
    }

    /** 获取存储双小数  */
    fun getDouble(key: String, defValue: Double): Double
    {
        return sp.getString(key, defValue.toString()).toDouble()
    }

    /** 录入存储双小数  */
    fun putDouble(key: String, value: Double)
    {
        val editor = sp.edit()
        editor.putString(key, value.toString() + "")
        editor.apply()
    }

    /** 获取存储布尔值  */
    fun getBoolean(key: String): Boolean
    {
        return sp.getBoolean(key, false)
    }

    /** 获取存储布尔值  */
    fun getBoolean(key: String, defValue: Boolean): Boolean
    {
        return sp.getBoolean(key, defValue)
    }

    /** 录入存储布尔值  */
    fun putBoolean(key: String, value: Boolean)
    {
        val editor = sp.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun clear()
    {
        val editor = sp.edit()
        editor.clear()
        editor.apply()
    }

    /***************************** 属性委托  ***************************/

    inline fun string(def: String = "") = object : ReadWriteProperty<SpDelegate, String>
    {
        override fun getValue(thisRef: SpDelegate, property: KProperty<*>): String
        {
            return getString(property.name, def)
        }

        override fun setValue(thisRef: SpDelegate, property: KProperty<*>, value: String)
        {
            putString(property.name, value)
        }
    }

    inline fun int(def: Int = 0) = object : ReadWriteProperty<SpDelegate, Int>
    {

        override fun getValue(thisRef: SpDelegate, property: KProperty<*>): Int
        {
            return getInt(property.name, def)
        }

        override fun setValue(thisRef: SpDelegate, property: KProperty<*>, value: Int)
        {
            putInt(property.name, value)
        }
    }

    inline fun long(def: Long = 0L) = object : ReadWriteProperty<SpDelegate, Long>
    {

        override fun getValue(thisRef: SpDelegate, property: KProperty<*>): Long
        {
            return getLong(property.name, def)
        }

        override fun setValue(thisRef: SpDelegate, property: KProperty<*>, value: Long)
        {
            putLong(property.name, value)
        }
    }

    inline fun float(def: Float = 0.0f) = object : ReadWriteProperty<SpDelegate, Float>
    {
        override fun getValue(thisRef: SpDelegate, property: KProperty<*>): Float
        {
            return getFloat(property.name, def)
        }

        override fun setValue(thisRef: SpDelegate, property: KProperty<*>, value: Float)
        {
            putFloat(property.name, value)
        }
    }

    inline fun double(def: Double = 0.0) = object : ReadWriteProperty<SpDelegate, Double>
    {
        override fun getValue(thisRef: SpDelegate, property: KProperty<*>): Double
        {
            return getDouble(property.name, def)
        }

        override fun setValue(thisRef: SpDelegate, property: KProperty<*>, value: Double)
        {
            putDouble(property.name, value)
        }
    }

    inline fun boolean(def: Boolean = false) = object : ReadWriteProperty<SpDelegate, Boolean>
    {
        override fun getValue(thisRef: SpDelegate, property: KProperty<*>): Boolean
        {
            return getBoolean(property.name, def)
        }

        override fun setValue(
            thisRef: SpDelegate,
            property: KProperty<*>,
            value: Boolean
        )
        {
            putBoolean(property.name, value)
        }
    }
}