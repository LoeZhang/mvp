package com.loe.mvp.ext_app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import com.loe.mvp.R

fun Fragment.putStringArgs(key: String, value: String): Bundle
{
    val bundle = Bundle()
    bundle.putString(key, value)
    arguments = bundle
    return bundle
}

fun Fragment.putIntArgs(key: String, value: Int): Bundle
{
    val bundle = Bundle()
    bundle.putInt(key, value)
    arguments = bundle
    return bundle
}

fun Fragment.putDoubleArgs(key: String, value: Double): Bundle
{
    val bundle = Bundle()
    bundle.putDouble(key, value)
    arguments = bundle
    return bundle
}

fun Fragment.putBooleanArgs(key: String, value: Boolean): Bundle
{
    val bundle = Bundle()
    bundle.putBoolean(key, value)
    arguments = bundle
    return bundle
}

fun FragmentActivity.addFragment(view: View, fragment: Fragment)
{
    addFragment(view.id, fragment)
}

fun FragmentActivity.addFragment(containerViewId: Int, fragment: Fragment)
{
    val transaction = supportFragmentManager.beginTransaction()
    transaction.add(containerViewId, fragment)
    transaction.commit()
}

fun FragmentActivity.addInFragment(view: View, fragment: Fragment)
{
    addInFragment(view.id, fragment)
}

fun FragmentActivity.addInFragment(containerViewId: Int, fragment: Fragment)
{
    val transaction = supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
    transaction.add(containerViewId, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}

fun FragmentActivity.replaceFragment(view: View, fragment: Fragment)
{
    replaceFragment(view.id, fragment)
}

fun FragmentActivity.replaceFragment(containerViewId: Int, fragment: Fragment)
{
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(containerViewId, fragment)
    transaction.commit()
}

fun FragmentActivity.replaceInFragment(view: View, fragment: Fragment)
{
    replaceInFragment(view.id, fragment)
}

fun FragmentActivity.replaceInFragment(containerViewId: Int, fragment: Fragment)
{
    val transaction = supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
    transaction.replace(containerViewId, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}