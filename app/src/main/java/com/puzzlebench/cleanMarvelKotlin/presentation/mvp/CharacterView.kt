package com.puzzlebench.cleanMarvelKotlin.presentation.mvp

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.puzzlebench.cleanMarvelKotlin.R
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.presentation.CharacterDialogFragment
import com.puzzlebench.cleanMarvelKotlin.presentation.MainActivity
import com.puzzlebench.cleanMarvelKotlin.presentation.adapter.CharacterAdapter
import com.puzzlebench.cleanMarvelKotlin.presentation.extension.showToast
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progress_overlay.progress_overlay
import java.lang.ref.WeakReference

const val SPAN_COUNT = 1
const val DIALOG_TAG = "Character Dialog"

class CharacterView(val activity: MainActivity) {
    private val activityRef = WeakReference(activity)
    val characterPublisher = PublishSubject.create<Int>()

    private var adapter = CharacterAdapter { character ->
        run {
            showLoadingSpinner()
            characterPublisher.onNext(character.id)
        }
    }

    private fun showLoadingSpinner() {
        activity.progress_overlay.visibility = View.VISIBLE
    }

    fun hideLoadingSpinner() {
        activity.progress_overlay.visibility = View.GONE
    }

    fun init() {
        val activity = activityRef.get()
        if (activity != null) {
            showLoadingSpinner()
            activity.recycleView.layoutManager = GridLayoutManager(activity, SPAN_COUNT)
            activity.recycleView.adapter = adapter
            refreshPressedObservable()
        }
    }

    fun refreshPressedObservable(): Observable<Boolean> {
        return Observable.create { emitter ->
            activity.fab_refresh.setOnClickListener {
                emitter.onNext(true)
            }
        }
    }

    fun reset() {
        activity.recycleView.adapter = null
        showLoadingSpinner()
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

    fun showCharacters(characters: List<Character>) {
        activity.recycleView.adapter = adapter
        adapter.data = characters
        hideLoadingSpinner()
    }

    fun showDialog(name: String, description: String, imageUrl: String, available: String, url_comic: String, url_wiki: String) {
        val dialog = CharacterDialogFragment.newInstance(name, description, imageUrl, available, url_comic, url_wiki)
        dialog.show(activity.supportFragmentManager, DIALOG_TAG)
        hideLoadingSpinner()
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
