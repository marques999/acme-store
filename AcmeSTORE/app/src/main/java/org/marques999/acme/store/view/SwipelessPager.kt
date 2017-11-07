package org.marques999.acme.store.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class SwipelessPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean = false
    override fun onInterceptTouchEvent(motionEvent: MotionEvent?) = false
}