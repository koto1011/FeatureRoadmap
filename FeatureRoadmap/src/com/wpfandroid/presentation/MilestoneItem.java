package com.wpfandroid.presentation;

import com.wpfandroid.pojo.Milestone;

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

public class MilestoneItem extends Milestone
{

	private View itemView;
	private OwnHorizontalScrollView scrollView;
	private int displayWidth;
	private TextView beschriftung;
	
	
	public MilestoneItem(View itemView, TextView beschriftung, OwnHorizontalScrollView scrollView, int displayWidth) 
	{
		this.itemView = itemView;
		this.beschriftung = beschriftung;
		this.displayWidth = displayWidth;
		this.scrollView = scrollView; 
			
		//FeatureRoadmapActivity.this.dh.createMilestone(beschriftung.getText(), null, null, 0);
		
		beschriftung.setPadding(scrollView.getScrollX() + DragNDropActivity.milestonePosX - (106 / 2), 0, 0, 0);
		
		// position the new item at the clicked position
		FrameLayout.LayoutParams par = (LayoutParams) itemView.getLayoutParams();

		par.leftMargin = scrollView.getScrollX() + DragNDropActivity.milestonePosX - (106 / 2);
		par.topMargin = 0;
		itemView.setLayoutParams(par);
		
		itemView.setOnTouchListener(this.dragItem);
		
		verschiebeBeschriftung();
	}

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
		                    par.leftMargin = scrollView.getScrollX() + (int)event.getRawX() - (v.getWidth()/2);		                    
							
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
							verschiebeBeschriftung();
							
							break;
						}//inner case MOVE
						case MotionEvent.ACTION_UP:
						{
							Log.e("onTouch: ", "case: item - UP");
			
							Log.e("onTouch: ", "case: item - UP, RawX: "+(int)event.getRawX());
		                    par.leftMargin = scrollView.getScrollX() + (int)event.getRawX() - (v.getWidth()/2);
							v.setLayoutParams(par);
							verschiebeBeschriftung();
							
							scrollView.setIsScrollable(true);	
							
							Log.e("LeftMargin: ", ""+v.getLeft());
							break;
						}//inner case UP
						case MotionEvent.ACTION_CANCEL:
						{
							Log.e("onTouch: ", "case: item - CANCEL");
							Log.e("onTouch: ", "case: item - CANCEL, RawX: "+(int)event.getRawX());
		                    par.leftMargin = (int)event.getRawX() - (v.getWidth()/2);
							v.setLayoutParams(par);
							verschiebeBeschriftung();
							
							scrollView.setIsScrollable(false);	
							
							Log.e("LeftMargin: ", ""+v.getLeft());
							break;
						}//inner case UP

						case MotionEvent.ACTION_DOWN:
						{
							Log.e("onTouch: ", "case: item - DOWN");

						    scrollView.setIsScrollable(false);
		
						    v.setLayoutParams(par);
							verschiebeBeschriftung();
							break;
						}//inner case UP
					}//inner switch
				}//if pawn
			return true;
		}//onTouch
    };//dragItem

    private void verschiebeBeschriftung()
    {
    	FrameLayout.LayoutParams par = (LayoutParams) this.itemView.getLayoutParams();
    	beschriftung.setPadding(par.leftMargin, par.topMargin, par.rightMargin, par.bottomMargin);
    	beschriftung.invalidate();
    	
    	((TextView) itemView.findViewById(R.id.milestoneText)).setText(beschriftung.getText());
    	((TextView) itemView.findViewById(R.id.milestoneText)).invalidate();
    }
}
