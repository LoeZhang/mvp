package com.loe.mvp

open class BaseModelPresenter<MODEL : BaseModel> : BasePresenter
{
    lateinit var model: MODEL
        protected set

    constructor()

    constructor(view: BaseView, model: MODEL)
    {
        init(view, model)
    }

    fun init(view: BaseView, model: MODEL)
    {
        this.view = view
        this.model = model
        onInit()
    }
}