package com.wpfandroid.presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

class OwnHorizontalScrollView extends HorizontalScrollView {

    public OwnHorizontalScrollView(Context context, AttributeSet attrSet) {
		super(context);
		//this.setFocusable(false);
		// TODO Auto-generated constructor stub
	}

	// true if we can scroll (not locked)
    // false if we cannot scroll (locked)
    private boolean mScrollable = false;

    public void setIsScrollable(boolean scrollable) {
        mScrollable = false;
    }
    public boolean getIsScrollable() {
        return mScrollable;
    }

    public boolean onTouchEvent(MotionEvent ev) {
    	Log.e("ScrollView touched","");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // if we can scroll pass the event to the superclass
                if (!mScrollable) return super.onTouchEvent(ev);
                // only continue to handle the touch event if scrolling enabled
                return mScrollable; // mScrollable is always false at this point
            default:
                return super.onTouchEvent(ev);
        }
    }
    
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	return false;
        }
}

