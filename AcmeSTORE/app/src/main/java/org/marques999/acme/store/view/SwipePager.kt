package org.marques999.acme.store.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class SwipePager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var pagingEnabled = true

    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean = if (this.pagingEnabled) {
        super.onTouchEvent(motionEvent)
    } else {
        false
    }

    override fun onInterceptTouchEvent(motionEvent: MotionEvent?) = if (this.pagingEnabled) {
        super.onInterceptTouchEvent(motionEvent)
    } else {
        false
    }

    fun setPagingEnabled(enabled: Boolean) {
        pagingEnabled = enabled
    }
}