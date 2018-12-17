package com.puzzlebench.cleanMarvelKotlin.presentation.extension

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.getImageByUrlCenterCrop(url: String) {
    Picasso.get().load(url).fit().centerCrop().into(this)
}

fun ImageView.getImageByUrl(url: String?) {
    if (url != null) Picasso.get().load(url).into(this)
}

