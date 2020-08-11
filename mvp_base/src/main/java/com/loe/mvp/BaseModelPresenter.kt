package com.loe.mvp

open class BaseModelPresenter<MODEL: BaseModel> : BasePresenter
{
    lateinit var model: MODEL

    constructor()

    constructor(view: BaseView, model: MODEL)
    {
        init(view, model)
    }

    internal fun init(view: BaseView, model: MODEL)
    {
        this.view = view
        this.model = model
        onInit()
    }
}