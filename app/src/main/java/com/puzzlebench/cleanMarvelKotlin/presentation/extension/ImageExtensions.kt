package com.puzzlebench.cleanMarvelKotlin.presentation.extension

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.getImageByUrlCenterCrop(url: String) {
    Picasso.with(context)
            .load(url)
            .fit()
            .centerCrop()
            .into(this)
}

fun ImageView.getImageByUrl(url: String?) {
    if (url != null) {
        Picasso.with(context)
                .load(url)
                .into(this)
    }
}

