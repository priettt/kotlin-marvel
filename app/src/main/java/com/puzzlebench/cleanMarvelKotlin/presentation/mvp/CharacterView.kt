package com.puzzlebench.cleanMarvelKotlin.presentation.mvp

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.puzzlebench.cleanMarvelKotlin.R
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.presentation.CharacterDialogFragment
import com.puzzlebench.cleanMarvelKotlin.presentation.MainActivity
import com.puzzlebench.cleanMarvelKotlin.presentation.adapter.CharacterAdapter
import com.puzzlebench.cleanMarvelKotlin.presentation.extension.showToast
import com.puzzlebench.cleanMarvelKotlin.utils.bus.RxBus
import com.puzzlebench.cleanMarvelKotlin.utils.bus.observer.OnCharacterPressedBusObserver
import com.puzzlebench.cleanMarvelKotlin.utils.bus.observer.OnRefreshPressedBusObserver
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

const val SPAN_COUNT = 1
const val DIALOG_TAG = "Character Dialog"

class CharacterView(val activity: MainActivity) {
    private val activityRef = WeakReference(activity)

    private var adapter = CharacterAdapter { character ->
        RxBus.post(OnCharacterPressedBusObserver.OnCharacterPressed(character.id))
    }

    fun init() {
        val activity = activityRef.get()
        if (activity != null) {
            showLoading()
            activity.recycleView.layoutManager = GridLayoutManager(activity, SPAN_COUNT)
            activity.recycleView.adapter = adapter
            activity.fab_refresh.setOnClickListener {
                RxBus.post(OnRefreshPressedBusObserver.OnRefreshPressed())
            }
        }
    }

    fun reset() {
        activity.recycleView.adapter = null
        showLoading()
    }

    fun showToastNoItemToShow() {
        val activity = activityRef.get()
        if (activity != null) {
            val message = activity.baseContext.resources.getString(R.string.message_no_items_to_show)
            activity.applicationContext.showToast(message)
        }
    }

    fun showToastSavedToDatabase() {
        val activity = activityRef.get()
        if (activity != null) {
            val message = activity.baseContext.resources.getString(R.string.success_database)
            activity.applicationContext.showToast(message)
        }
    }

    fun showToastNetworkError(error: String) {
        activityRef.get()!!.applicationContext.showToast(error)
    }

    fun hideLoading() {
        activityRef.get()!!.progressBar.visibility = View.GONE
    }

    fun showCharacters(characters: List<Character>) {
        activity.recycleView.adapter = adapter
        adapter.data = characters
    }

    private fun showLoading() {
        activityRef.get()!!.progressBar.visibility = View.VISIBLE

    }

    fun showDialog(name: String, description: String, imageUrl: String, available: String, url_comic: String, url_wiki: String) {
        val dialog = CharacterDialogFragment.newInstance(name, description, imageUrl, available, url_comic, url_wiki)
        dialog.show(activity.supportFragmentManager, DIALOG_TAG)
    }

    fun showToastErrorSavingToDatabase() {
        val activity = activityRef.get()
        if (activity != null) {
            val message = activity.baseContext.resources.getString(R.string.error_database)
            activity.applicationContext.showToast(message)
        }
    }

    fun showToastLoadedFromDatabase() {
        val activity = activityRef.get()
        if (activity != null) {
            val message = activity.baseContext.resources.getString(R.string.loaded_from_database)
            activity.applicationContext.showToast(message)
        }
    }

    fun showToastLoadedFromServer() {
        val activity = activityRef.get()
        if (activity != null) {
            val message = activity.baseContext.resources.getString(R.string.loaded_from_server)
            activity.applicationContext.showToast(message)
        }
    }

}
