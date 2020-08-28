package com.loe.mvp.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.PermissionChecker
import android.text.Html
import android.util.Log

object PermissionUtil
{
    private const val TAG = "PermissionUtil"

    val STORAGE = listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    val SELECT_IMG = listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)

    val RECORD = listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)

    val PHONE = listOf(Manifest.permission.CALL_PHONE)

    val LOCATION = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    val CONTACTS = listOf(Manifest.permission.READ_CONTACTS)

    @JvmStatic
    fun requestForce(activity: Activity, permission: List<String>, permissionName: String, isFinish: Boolean = true, onPermissionOk: (() -> Unit)? = null)
    {
        request(activity, permission, {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("缺少“${permissionName}”权限")
            builder.setMessage(Html.fromHtml("<font color='#777777'>请点击 \"设置\"-\"权限\" 打开${permissionName}权限，点击两次后退按钮即可返回。</font>"))
            builder.setCancelable(false)
            builder.setPositiveButton("设置", null)
            builder.setNegativeButton("取消")
            { dialog, which ->
                if(isFinish) activity.finish()
            }
            val dialog = builder.create()
            dialog.show()
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener()
            {
                startAppSettings(activity)
                { isOk: Boolean, intent: Intent? ->
                    if (isPermissionOk(activity, permission)) dialog?.dismiss()
                }
            }
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                .setTextColor(Color.parseColor("#888888"))
            dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(Color.parseColor("#5084fe"))
        },onPermissionOk)
    }

    @JvmStatic
    fun request(activity: Activity, permission: List<String>, onPermissionError: ((List<String>) -> Unit)? = null, onPermissionOk: (() -> Unit)? = null)
    {
        if (activity is FragmentActivity)
        {
            if (permission == null || permission.isEmpty())
            {
                onPermissionOk?.invoke()
                return
            }
            val deniedPermissions = getDeniedPermissions(activity, permission)
            if (deniedPermissions.isEmpty())
            {
                onPermissionOk?.invoke()
                return
            }
            val manager = activity.supportFragmentManager
            var fragment = manager.findFragmentByTag(TAG) as? PermissionFragment
            if (fragment == null)
            {
                fragment = PermissionFragment()
                manager.beginTransaction()
                    .add(fragment, TAG)
                    .commitNowAllowingStateLoss()
            }
            fragment.onPermissionOk = onPermissionOk
            fragment.onPermissionError = onPermissionError
            fragment.requestPermissions(deniedPermissions.toTypedArray(), 15)
        } else
        {
            Log.e("Runtime-$TAG", "请传入FragmentActivity")
        }
    }

    private fun getDeniedPermissions(activity: Activity, permissions: List<String>): List<String>
    {
        val list = ArrayList<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.applicationInfo.targetSdkVersion >= Build.VERSION_CODES.M)
        {
            for (permission in permissions)
            {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                {
                    list.add(permission)
                }
            }
        }
        if (list.isEmpty())
        {
            for (permission in permissions)
            {
                if (PermissionChecker.checkSelfPermission(activity, permission) != PermissionChecker.PERMISSION_GRANTED)
                {
                    list.add(permission)
                }
            }
        }
        return list
    }

    class PermissionFragment : Fragment()
    {
        var onPermissionOk: (() -> Unit)? = null
        var onPermissionError: ((List<String>) -> Unit)? = null

        override fun onCreate(savedInstanceState: Bundle?)
        {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
        {
            if (requestCode != 15) return

            // android 10
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P)
            {
                if(isPermissionOk(activity!!,permissions.toList()))
                {
                    onPermissionOk?.invoke()
                }else
                {
                    onPermissionError?.invoke(permissions.toList())
                }
            }else
            {
                val deniedList = ArrayList<String>()
                grantResults.forEachIndexed()
                { i, grant ->
                    if (grant == PackageManager.PERMISSION_DENIED)
                    {
                        deniedList.add(permissions[i])
                    }
                }
                if (deniedList.isEmpty())
                {
                    onPermissionOk?.invoke()
                } else
                {
                    onPermissionError?.invoke(deniedList)
                }
            }
            onPermissionOk = null
            onPermissionError = null
        }
    }

    /** 判断权限是否完善 */
    private fun isPermissionOk(context: Context, permissions: List<String>): Boolean
    {
        permissions?.forEach {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (context.checkSelfPermission(it) == PackageManager.PERMISSION_DENIED)
                {
                    return false
                }
            }
            if (PermissionChecker.checkSelfPermission(context, it) != PermissionChecker.PERMISSION_GRANTED)
            {
                return false
            }
        }
        return true
    }

    /**
     * 启动应用的设置
     */
    private fun startAppSettings(activity: Activity, result: ((isOk: Boolean, Intent?) -> Unit)? = null)
    {
        try
        {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + activity.packageName)
            ResultUtil.startResult(activity, intent)
            {
                result?.invoke(it.isOk(), it.data)
            }
        } catch (e: Exception)
        {
            e.printStackTrace()
        }
    }
}