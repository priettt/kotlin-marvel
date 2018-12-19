package com.puzzlebench.cleanMarvelKotlin.presentation

import android.os.Bundle
import com.puzzlebench.cleanMarvelKotlin.R
import com.puzzlebench.cleanMarvelKotlin.data.db.RealmImpl
import com.puzzlebench.cleanMarvelKotlin.data.service.CharacterServicesImpl
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.AddToDatabaseUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterDetailServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetFromDatabaseUseCase
import com.puzzlebench.cleanMarvelKotlin.presentation.base.BaseRxActivity
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.CharacterPresenter
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.CharacterView
import com.puzzlebench.cleanMarvelKotlin.utils.bus.RxBus
import io.realm.Realm

class MainActivity : BaseRxActivity() {

    val getCharacterServiceUseCase = GetCharacterServiceUseCase(CharacterServicesImpl())
    val getCharacterDetailServiceUseCase = GetCharacterDetailServiceUseCase(CharacterServicesImpl())
    val getFromDatabaseUseCase = GetFromDatabaseUseCase(RealmImpl())
    val addToDatabaseUseCase = AddToDatabaseUseCase(RealmImpl())
    val presenter = CharacterPresenter(
            CharacterView(this),
            getCharacterServiceUseCase,
            getCharacterDetailServiceUseCase,
            getFromDatabaseUseCase,
            addToDatabaseUseCase,
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
