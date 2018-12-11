package com.puzzlebench.cleanMarvelKotlin.presentation

import android.os.Bundle
import com.puzzlebench.cleanMarvelKotlin.R
import com.puzzlebench.cleanMarvelKotlin.data.service.CharacterServicesImpl
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.presentation.base.BaseRxActivity
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.CharacterPresenter
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.CharacterView

class MainActivity : BaseRxActivity() {

    val getCharacterServiceUseCase = GetCharacterServiceUseCase(CharacterServicesImpl())
    val presenter = CharacterPresenter(CharacterView(this), getCharacterServiceUseCase, subscriptions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init()
    }
}
