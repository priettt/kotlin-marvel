package com.puzzlebench.cleanMarvelKotlin.presentation.extension

import android.content.Context
import android.widget.Toast


// eg: extension function default parameter
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()
