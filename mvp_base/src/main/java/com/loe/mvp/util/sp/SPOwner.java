package com.loe.mvp.util.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPOwner
{
	public SharedPreferences sp;

	public SPOwner(Context context)
	{
		this(context, "user", Context.MODE_PRIVATE);
	}

	public SPOwner(Context context, String name)
	{
		this(context, name, Context.MODE_PRIVATE);
	}

	public SPOwner(Context context, String name, int mode)
	{
		sp = context.getSharedPreferences(name, mode);
	}

	/** 获取存储字符串 */
	public String getString(String key)
	{
		return sp.getString(key, "");
	}

	/** 获取存储字符串 */
	public String getString(String key, String dft)
	{
		return sp.getString(key, dft);
	}

	/** 录入存储字符串 */
	public void putString(String key, String value)
	{
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.apply();
	}

	/** 获取存储整数 */
	public int getInt(String key)
	{
		return sp.getInt(key, 0);
	}

	/** 获取存储整数 */
	public int getInt(String key, int defValue)
	{
		return sp.getInt(key, defValue);
	}

	/** 录入存储整数 */
	public void putInt(String key, int value)
	{
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	/** 获取存储长整数 */
	public long getLong(String key)
	{
		return sp.getLong(key, 0);
	}

	/** 获取存储长整数 */
	public long getLong(String key, long defValue)
	{
		return sp.getLong(key, defValue);
	}

	/** 录入存储长整数 */
	public void putLong(String key, long value)
	{
		Editor editor = sp.edit();
		editor.putLong(key, value);
		editor.apply();
	}

	/** 获取存储小数 */
	public float getFloat(String key)
	{
		return sp.getFloat(key, 0);
	}

	/** 获取存储小数 */
	public float getFloat(String key, float defValue)
	{
		return sp.getFloat(key, defValue);
	}

	/** 录入存储小数 */
	public void putFloat(String key, float value)
	{
		Editor editor = sp.edit();
		editor.putFloat(key, value);
		editor.apply();
	}

	/** 获取存储双小数 */
	public double getDouble(String key)
	{
		return Double.parseDouble(sp.getString(key, "0"));
	}

	/** 获取存储双小数 */
	public double getDouble(String key, double defValue)
	{
		return Double.parseDouble(sp.getString(key, String.valueOf(defValue)));
	}

	/** 录入存储双小数 */
	public void putDouble(String key, double value)
	{
		Editor editor = sp.edit();
		editor.putString(key, value+"");
		editor.apply();
	}

	/** 获取存储布尔值 */
	public boolean getBoolean(String key)
	{
		return sp.getBoolean(key, false);
	}

	/** 获取存储布尔值 */
	public boolean getBoolean(String key, boolean defValue)
	{
		return sp.getBoolean(key, defValue);
	}

	/** 录入存储布尔值 */
	public void putBoolean(String key, boolean value)
	{
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public void clear()
	{
		Editor editor = sp.edit();
		editor.clear();
		editor.apply();
	}
}
