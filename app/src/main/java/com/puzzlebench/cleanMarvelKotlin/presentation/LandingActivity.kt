package com.puzzlebench.cleanMarvelKotlin.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.puzzlebench.cleanMarvelKotlin.R
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.LandingPresenter
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.LandingView

class LandingActivity : AppCompatActivity(){
    private val presenter = LandingPresenter(LandingView(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }
}