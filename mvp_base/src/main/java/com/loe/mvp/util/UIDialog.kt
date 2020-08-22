package com.loe.mvp.util

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.text.Html

class UIDialog
{
    companion object
    {
        @JvmStatic
        fun show(context: Context, msg: String, f: ((DialogInterface) -> Unit)? = null) : AlertDialog
        {
            return UIDialog(context, msg).show(f)
        }
    }

    private var context: Context

    constructor(context: Context)
    {
        this.context = context
    }

    constructor(context: Context, msg: String)
    {
        this.context = context
        msg(msg)
    }

    private var ok: ((DialogInterface) -> Unit)? = null
    private var cancel: ((DialogInterface) -> Unit)? = null

    private var title = "提示"
    private var msg = ""

    private var okButton: String = "确认"
    private var cancelButton: String = "取消"

    private var okColor: Int = Color.parseColor("#5084fe")
    private var cancelColor: Int = Color.parseColor("#888888")

    private var force: Boolean = false
    private var single: Boolean = false

    fun show(f: ((DialogInterface) -> Unit)? = null): AlertDialog
    {
        if (f != null) ok = f

        val builder = AlertDialog.Builder(context)
        builder.setTitle(Html.fromHtml(title))
        builder.setMessage(msg)
        builder.setCancelable(!force)
        builder.setOnCancelListener()
        {
            cancel?.invoke(it)
            it.dismiss()
        }

        if (force)
        {
            builder.setPositiveButton(okButton, null)
        } else
        {
            builder.setPositiveButton(okButton)
            { dialog, which ->
                ok?.invoke(dialog)
            }
        }
        if (!single) builder.setNegativeButton(cancelButton)
        { dialog, which ->
            cancel?.invoke(dialog)
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
        if (force) dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener()
        {
            ok?.invoke(dialog)
        }
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(okColor)
        if (!single) dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(cancelColor)
        return dialog
    }

    fun force(): UIDialog
    {
        force = true
        return this
    }

    fun single(): UIDialog
    {
        single = true
        return this
    }

    fun title(s: String): UIDialog
    {
        title = s
        return this
    }

    fun msg(s: String): UIDialog
    {
        msg = s
        return this
    }

    @JvmOverloads
    fun okButton(s: String, color: Int? = null): UIDialog
    {
        okButton = s
        if (color != null) okColor = color
        return this
    }

    @JvmOverloads
    fun cancelButton(s: String, color: Int? = null): UIDialog
    {
        cancelButton = s
        if (color != null) cancelColor = color
        return this
    }

    @JvmOverloads
    fun ok(s: String? = null, f: (DialogInterface) -> Unit): UIDialog
    {
        if (s != null) okButton = s
        ok = f
        return this
    }

    @JvmOverloads
    fun cancel(s: String? = null, f: (DialogInterface) -> Unit): UIDialog
    {
        if (s != null) cancelButton = s
        cancel = f
        return this
    }
}