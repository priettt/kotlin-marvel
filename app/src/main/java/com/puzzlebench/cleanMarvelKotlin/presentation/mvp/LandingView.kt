package com.puzzlebench.cleanMarvelKotlin.presentation.mvp

import android.content.Intent
import com.puzzlebench.cleanMarvelKotlin.presentation.LandingActivity
import java.lang.ref.WeakReference
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.puzzlebench.cleanMarvelKotlin.presentation.MainActivity
import kotlinx.android.synthetic.main.activity_landing.landing_image

const val ANIMATION_DURATION = 1000L

class LandingView(private val activity: LandingActivity) {
    private val activityRef = WeakReference(activity)

    fun spinImage() {
        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.repeatCount = 3
        animation.duration = ANIMATION_DURATION
        animation.interpolator = LinearInterpolator()
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                startActivity()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        activity.landing_image.startAnimation(animation)
    }

    private fun startActivity() {
        activityRef.get()!!.startActivity(Intent(activityRef.get()!!, MainActivity::class.java))
        activityRef.get()!!.finish()
    }


}