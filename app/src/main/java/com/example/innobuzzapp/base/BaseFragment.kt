package com.example.innobuzzapp.base

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    open lateinit var viewModel: T
    abstract fun setViewModel(): T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = setViewModel()
    }
}