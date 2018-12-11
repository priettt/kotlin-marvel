package com.puzzlebench.cleanMarvelKotlin.presentation.extension

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.getImageByUrl(url: String) {
    Picasso.with(context).load(url).fit().centerCrop().into(this)
}
