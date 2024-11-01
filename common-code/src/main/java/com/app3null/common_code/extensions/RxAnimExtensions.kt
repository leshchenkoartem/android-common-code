package com.app3null.common_code.extensions

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import io.reactivex.Completable

fun View.fadeIn(startOffset: Long = 0, duration: Long = 200, interpolator: Interpolator = AccelerateDecelerateInterpolator()): Completable {
    alpha = 0f
    return Completable.create {
        ViewCompat.animate(this)
            .setDuration(duration)
            .setStartDelay(startOffset)
            .setInterpolator(interpolator)
            .alpha(1f)
            .withEndAction { it.onComplete() }
    }
}

fun View.fadeOn(startOffset: Long = 0, duration: Long = 200, interpolator: Interpolator = AccelerateDecelerateInterpolator()): Completable {
    alpha = 1f
    return Completable.create {
        ViewCompat.animate(this)
            .setDuration(duration)
            .setStartDelay(startOffset)
            .setInterpolator(interpolator)
            .alpha(0f)
            .withEndAction { it.onComplete() }
    }
}

fun View.transitionX(startOffset: Long = 0, duration: Long = 700, interpolator: Interpolator = AccelerateDecelerateInterpolator(), startPosition: Float, endPosition: Float): Completable {
    translationX = startPosition
    return Completable.create {
        ViewCompat.animate(this)
            .setDuration(duration)
            .setStartDelay(startOffset)
            .setInterpolator(interpolator)
            .translationX(endPosition)
            .withEndAction { it.onComplete() }
    }
}

fun View.transitionY(startOffset: Long = 0, duration: Long = 700, interpolator: Interpolator = AccelerateDecelerateInterpolator(), startPosition: Float, endPosition: Float): Completable {
    translationY = startPosition
    return Completable.create {
        ViewCompat.animate(this)
            .setDuration(duration)
            .setStartDelay(startOffset)
            .setInterpolator(interpolator)
            .translationY(endPosition)
            .withEndAction { it.onComplete() }
    }
}

fun ViewGroup.transitionX(startOffset: Long = 0, duration: Long = 700, interpolator: Interpolator = AccelerateDecelerateInterpolator(), startPosition: Float, endPosition: Float): Completable {
    translationX = startPosition
    return Completable.create {
        ViewCompat.animate(this)
            .setDuration(duration)
            .setStartDelay(startOffset)
            .setInterpolator(interpolator)
            .translationX(endPosition)
            .withEndAction { it.onComplete() }
    }
}

fun ViewGroup.transitionY(startOffset: Long = 0, duration: Long = 700, interpolator: Interpolator = AccelerateDecelerateInterpolator(), startPosition: Float, endPosition: Float): Completable {
    translationY = startPosition
    return Completable.create {
        ViewCompat.animate(this)
            .setDuration(duration)
            .setStartDelay(startOffset)
            .setInterpolator(interpolator)
            .translationY(endPosition)
            .withEndAction { it.onComplete() }
    }
}

fun TextView.animColor(from: Int, to: Int, duration: Long = 200): Completable {
    return Completable.create {
        val completable = it
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), ContextCompat.getColor(this.context, from), ContextCompat.getColor(this.context, to))
        colorAnimation.addUpdateListener { animator -> this.setTextColor(animator.animatedValue as Int) }
//        colorAnimation.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationRepeat(p0: Animator?) {
//
//            }
//
//            override fun onAnimationEnd(p0: Animator?) {
//                it.onComplete()
//            }
//
//            override fun onAnimationCancel(p0: Animator?) {
//
//            }
//
//            override fun onAnimationStart(p0: Animator?) {
//
//            }
//
//        })

        colorAnimation.addListener(onEnd = {
            completable.onComplete()
        })
        colorAnimation.duration = duration
        colorAnimation.start()
    }
}

fun ViewGroup.animColor(from: Int, to: Int, duration: Long = 150): Completable {
    return Completable.create {
        val completable = it
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), ContextCompat.getColor(this.context, from), ContextCompat.getColor(this.context, to))
        colorAnimation.addUpdateListener { animator -> this.setBackgroundColor(animator.animatedValue as Int) }
        colorAnimation.addListener(onEnd = {
            completable.onComplete()
        })
        colorAnimation.duration = duration
        colorAnimation.start()
    }
}