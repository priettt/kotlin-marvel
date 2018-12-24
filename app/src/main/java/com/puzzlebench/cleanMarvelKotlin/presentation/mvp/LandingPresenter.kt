package com.puzzlebench.cleanMarvelKotlin.presentation.mvp

import com.puzzlebench.cleanMarvelKotlin.presentation.base.Presenter

class LandingPresenter(view: LandingView) : Presenter<LandingView>(view)
{
    fun onResume() {
        view.spinImage()
    }
}
