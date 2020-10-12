package com.loe.mvp.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.provider.Settings;

import com.loe.mvp.BuildConfig;

import java.util.UUID;

public class SystemUtil
{
    public static String getDeviceId(Context context)
    {
        String deviceId = "";
        String aid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String serial = android.os.Build.SERIAL;
        if (serial == null)
        {
            serial = "0";
        }
        if (aid == null || aid.isEmpty() || aid == "null" || aid == "")
        {
            deviceId = UUID.randomUUID().toString();
        }
        else
        {
            deviceId = aid + serial;
        }
        return deviceId;
    }

    public static String getUserAgent()
    {
        return "Android " + android.os.Build.VERSION.RELEASE + "; " + android.os.Build.BRAND + " " + android.os.Build.MODEL + "; v" + BuildConfig.VERSION_NAME;
    }

    public static boolean isDebug(Context context)
    {
        try
        {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e)
        {
            return false;
        }
    }

    public static boolean isRelease(Context context)
    {
        return !isDebug(context);
    }
}
