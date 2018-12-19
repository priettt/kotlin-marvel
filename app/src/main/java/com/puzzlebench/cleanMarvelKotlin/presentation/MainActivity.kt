package com.puzzlebench.cleanMarvelKotlin.presentation

import android.os.Bundle
import com.puzzlebench.cleanMarvelKotlin.R
import com.puzzlebench.cleanMarvelKotlin.data.db.RealmImpl
import com.puzzlebench.cleanMarvelKotlin.data.service.CharacterServicesImpl
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterDatabaseUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterDetailServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.presentation.base.BaseRxActivity
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.CharacterPresenter
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.CharacterView
import com.puzzlebench.cleanMarvelKotlin.utils.bus.RxBus

class MainActivity : BaseRxActivity() {

    private val getCharacterServiceUseCase = GetCharacterServiceUseCase(CharacterServicesImpl())
    private val getCharacterDetailServiceUseCase = GetCharacterDetailServiceUseCase(CharacterServicesImpl())
    private val getCharacterDatabaseUseCase = GetCharacterDatabaseUseCase(RealmImpl())
    private val presenter = CharacterPresenter(
            CharacterView(this),
            getCharacterServiceUseCase,
            getCharacterDetailServiceUseCase,
            getCharacterDatabaseUseCase,
            subscriptions
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.clear(this)
    }
}
