package com.puzzlebench.cleanMarvelKotlin.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.puzzlebench.cleanMarvelKotlin.R
import com.puzzlebench.cleanMarvelKotlin.R.string.accept
import com.puzzlebench.cleanMarvelKotlin.presentation.extension.getImageByUrl
import com.squareup.picasso.Picasso


class CharacterDialogFragment() : DialogFragment() {


    companion object {
        private val NAME_TAG = "NAME"
        private val DESCRIPTION_TAG = "DESCRIPTION"
        private val IMAGE_TAG = "IMAGE"
        private val TOTAL_COMICS_TAG = "TOTAL_COMICS"
        private val COMICS_LINK_TAG = "COMICS_LINK"
        private val WIKI_LINK_TAG = "WIKI_LINK"

        fun newInstance(name: String, description: String, imagePath: String, totalComics: String, linkComics: String, linkWiki: String): CharacterDialogFragment {
            val dialog = CharacterDialogFragment()
            val args = Bundle().apply {
                putString(NAME_TAG, name)
                putString(DESCRIPTION_TAG, description)
                putString(IMAGE_TAG, imagePath)
                putString(TOTAL_COMICS_TAG, totalComics)
                putString(COMICS_LINK_TAG, linkComics)
                putString(WIKI_LINK_TAG, linkWiki)
            }
            dialog.arguments = args
            return dialog
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.character_pressed_dialog, null)

        val textName = view.findViewById<View>(R.id.character_name) as TextView
        val description = view.findViewById<View>(R.id.character_description) as TextView
        val image = view.findViewById<View>(R.id.dialog_character_image) as ImageView
        val totalComics = view.findViewById<View>(R.id.character_appearances_comics) as TextView
        val linkComics = view.findViewById<View>(R.id.character_comics_link) as TextView
        val linkWiki = view.findViewById<View>(R.id.character_details_link) as TextView

        textName.text = arguments?.getString(NAME_TAG)
        description.text = arguments?.getString(DESCRIPTION_TAG)
        totalComics.text = arguments?.getString(TOTAL_COMICS_TAG)
        linkComics.text = arguments?.getString(COMICS_LINK_TAG)
        linkWiki.text = arguments?.getString(WIKI_LINK_TAG)

        linkComics.movementMethod = LinkMovementMethod.getInstance()
        val imagePath: String? = arguments?.getString(IMAGE_TAG)
        if (imagePath != null)
            Picasso.get().load(imagePath).into(image)


        val builder = AlertDialog.Builder(context!!)
                .setView(view)
                .setNeutralButton(accept) { _, _ ->
                    dialog.cancel()
                }

        return builder.create()
    }
}