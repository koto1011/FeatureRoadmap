package com.wpfandroid.presentation;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
//import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class MilestoneItem 
	{
		public MilestoneItem(View itemView, TextView beschriftung, OwnHorizontalScrollView scrollView, int displayWidth) {
			this.itemView = itemView;
			this.beschriftung = beschriftung;
			this.displayWidth = displayWidth;
			MilestoneItem.scrollView = scrollView;    
	}

	public View itemView;
	public static OwnHorizontalScrollView scrollView;
	public int displayWidth;
	private TextView beschriftung;
	
	public OnTouchListener dragItem = new OnTouchListener()
    {
		public boolean onTouch(View v, MotionEvent event) 
		{
			Log.e("onTouch: ", "erreicht");
			FrameLayout.LayoutParams par = (LayoutParams) v.getLayoutParams();
			
			if(v.getId() != R.id.oben && v.getId() != R.id.unten)
			{	
					switch(event.getAction())
					{
						case MotionEvent.ACTION_MOVE:
						{
							Log.e("onTouch: ", "case: item - MOVE");
		                    par.leftMargin = MilestoneItem.scrollView.getScrollX() + (int)event.getRawX() - (v.getWidth()/2);		                    
							
		                    Log.e("Pos > disWidth", ""+ (int) event.getRawX() + "..." + displayWidth * 0.85);
		                    if((int) event.getRawX() > displayWidth * 0.85)
		                    {
		                    	Log.e("Scroll mich!", "5px rechts");
		                    	scrollView.smoothScrollBy(20, 0);
		                    }
		                    
		                    if((int) event.getRawX() < displayWidth * 0.15)
		                    {
		                    	Log.e("Scroll mich!", "5px rechts");
		                    	scrollView.smoothScrollBy(-20, 0);
		                    }
		                    
							v.setLayoutParams(par);		
							DragNDropActivity.verschiebeBeschriftung(v);
							
							break;
						}//inner case MOVE
						case MotionEvent.ACTION_UP:
						{
							Log.e("onTouch: ", "case: item - UP");
			
							Log.e("onTouch: ", "case: item - UP, RawX: "+(int)event.getRawX());
		                    par.leftMargin = MilestoneItem.scrollView.getScrollX() + (int)event.getRawX() - (v.getWidth()/2);
							v.setLayoutParams(par);
							DragNDropActivity.verschiebeBeschriftung(v);
							
							MilestoneItem.scrollView.setIsScrollable(true);	
							
							Log.e("LeftMargin: ", ""+v.getLeft());
							break;
						}//inner case UP
						case MotionEvent.ACTION_CANCEL:
						{
							Log.e("onTouch: ", "case: item - CANCEL");
							Log.e("onTouch: ", "case: item - CANCEL, RawX: "+(int)event.getRawX());
		                    par.leftMargin = (int)event.getRawX() - (v.getWidth()/2);
							v.setLayoutParams(par);
							DragNDropActivity.verschiebeBeschriftung(v);
							
							MilestoneItem.scrollView.setIsScrollable(false);	
							
							Log.e("LeftMargin: ", ""+v.getLeft());
							break;
						}//inner case UP

						case MotionEvent.ACTION_DOWN:
						{
							Log.e("onTouch: ", "case: item - DOWN");

						    MilestoneItem.scrollView.setIsScrollable(false);
		
						    v.setLayoutParams(par);
							DragNDropActivity.verschiebeBeschriftung(v);
							break;
						}//inner case UP
					}//inner switch
				}//if pawn
			return true;
		}//onTouch
    };//dragItem

    public void verschiebeBeschriftung()
    {
    	FrameLayout.LayoutParams par = (LayoutParams) this.itemView.getLayoutParams();
    	beschriftung.setPadding(par.leftMargin, par.topMargin, par.rightMargin, par.bottomMargin);
    	beschriftung.invalidate();
    }
}
