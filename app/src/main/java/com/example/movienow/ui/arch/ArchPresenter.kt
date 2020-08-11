package com.example.movienow.ui.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.lang.ref.WeakReference

/**
 * Created by n7 on 8/8/20.
 */
interface ArchPresenter<V : ArchView> {
    fun attachView(v: V)
}

abstract class AbstractArchPresenter<V : ArchView> : ArchPresenter<V>, ViewModel()
    , CoroutineScope by MainScope() {

    private var viewWeakReference: WeakReference<V>? = null

    protected val view: V?
        get() = viewWeakReference?.get()


    override fun attachView(v: V) {
        viewWeakReference = WeakReference(v)
    }
}