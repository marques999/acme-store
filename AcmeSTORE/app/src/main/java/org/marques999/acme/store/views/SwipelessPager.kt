package org.marques999.acme.store.views

import android.content.Context
import android.view.MotionEvent
import android.util.AttributeSet
import android.support.v4.view.ViewPager

class SwipelessPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean = false
    override fun onInterceptTouchEvent(motionEvent: MotionEvent?) = false
}