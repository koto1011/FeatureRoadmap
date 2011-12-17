package com.wpfandroid.presentation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DragNDropActivity extends Activity 
{
	public FrameLayout board;
	public View pawn;
	public View item;
	public int countItemsCreated;
	public static List<MilestoneItem> items;
	public static MotionEvent event;
	public static ViewGroup parentView;
	public static View v;
	public static int milestonePosX;
	public static Dialog dialog;
	public static String sprintName;
	
	public boolean created = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        items = new ArrayList<MilestoneItem>();
        
        setContentView(R.layout.timeline);

        // size the view according to the time period of the timeline of the roadmap
        RelativeLayout timeline = (RelativeLayout) findViewById(R.id.timeline);
        timeline.setOnTouchListener(touchBoard);
        
//        timeline.setLayoutParams(new LayoutParams(0,0)); // width and height
//        
//        FrameLayout oben = (FrameLayout) findViewById(R.id.oben);
//        oben.setLayoutParams(new LayoutParams(0,0)); // width and height
//        
//        FrameLayout unten = (FrameLayout) findViewById(R.id.unten);
//        unten.setLayoutParams(new LayoutParams(0,0)); // width and height
       
    }//onCreate
    
    OnTouchListener touchBoard = new OnTouchListener()
    {
    	public boolean onTouch(final View v, final MotionEvent event)
    	{
    		if((v.getId() == R.id.oben || v.getId() == R.id.unten) && event.getAction() == MotionEvent.ACTION_DOWN)
    		{
    			Log.e("onTouchBoard: ", "erreicht");   			
    			return true;
    		}
    		else if(v.getId() == R.id.timeline && event.getAction() == MotionEvent.ACTION_DOWN)
    		{
    			    			
    			DragNDropActivity.event = event;
    			DragNDropActivity.v = v;
    			
    			milestonePosX = (int) event.getRawX();
    			Log.e("RawX im Listener", ""+(int)event.getRawX());
    			
    			createItemDialog();
    			
    			return true;
    		}
    		else
    			return false;
    	}
    };
   
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	if(resultCode == 0)
    	{
	    	String sprintName = data.getStringExtra("SprintName");
	    	    	
	    	final LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if(v.getId() == R.id.timeline)
			{
				if(countItemsCreated % 2 == 0)
				{
					parentView = (ViewGroup) findViewById(R.id.oben);
					parentView = (ViewGroup) inflater.inflate(R.layout.item, parentView);
				}
				else
				{
					parentView = (ViewGroup) findViewById(R.id.unten);
					parentView = (ViewGroup) inflater.inflate(R.layout.item_unten, parentView);
				}
			}
			else
			{
				// Fehler
			}
			
	    	View itemView = parentView.getChildAt(parentView.getChildCount() - 1);
	
	        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			int displayWidth = display.getWidth();
	
			TextView beschriftung = new TextView(getApplicationContext());
			((RelativeLayout) findViewById(R.id.timeline)).addView(beschriftung);
			Log.e("RawX beim Erzeugen", "" + milestonePosX);
			beschriftung.setPadding(milestonePosX - (106 / 2), 0, 0, 0);
			beschriftung.setText(sprintName);
			
			MilestoneItem item = new MilestoneItem(itemView, beschriftung, (OwnHorizontalScrollView) ((LinearLayout) findViewById(R.id.roadmap)).getParent(), displayWidth);
			
			beschriftung.setPadding(MilestoneItem.scrollView.getScrollX() + milestonePosX - (106 / 2), 0, 0, 0);
			
			items.add(item);
			itemView.setOnTouchListener(item.dragItem);
		
			// position the new item at the clicked position
			FrameLayout.LayoutParams par = (LayoutParams) itemView.getLayoutParams();
	
			par.leftMargin = MilestoneItem.scrollView.getScrollX() + milestonePosX - (106 / 2);
			par.topMargin = 0;
			itemView.setLayoutParams(par);
			
			countItemsCreated++;
    	}
    	else
    	{
    		// action was cancelled
    	}
    	
    }
    
    
    public static void verschiebeBeschriftung(View v)
    {
    	for(int i = 0; i < items.size(); i++)
    	{
    		if(items.get(i).itemView.equals(v) == true)
    		{
    			items.get(i).verschiebeBeschriftung();
    		}
    	}
    }
    
    public void createItem()
    {
    	final LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(v.getId() == R.id.timeline)
		{
			if(countItemsCreated % 2 == 0)
			{
				parentView = (ViewGroup) findViewById(R.id.oben);
				parentView = (ViewGroup) inflater.inflate(R.layout.item, parentView);
			}
			else
			{
				parentView = (ViewGroup) findViewById(R.id.unten);
				parentView = (ViewGroup) inflater.inflate(R.layout.item_unten, parentView);
			}
		}
		else
		{
			// Fehler
		}
		
    	View itemView = parentView.getChildAt(parentView.getChildCount() - 1);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int displayWidth = display.getWidth();

		TextView beschriftung = new TextView(getApplicationContext());
		((RelativeLayout) findViewById(R.id.timeline)).addView(beschriftung);
		Log.e("RawX beim Erzeugen", "" + milestonePosX);
		beschriftung.setPadding(milestonePosX - (106 / 2), 0, 0, 0);
		beschriftung.setText(sprintName);
		
		MilestoneItem item = new MilestoneItem(itemView, beschriftung, (OwnHorizontalScrollView) ((LinearLayout) findViewById(R.id.roadmap)).getParent(), displayWidth);
		
		beschriftung.setPadding(MilestoneItem.scrollView.getScrollX() + milestonePosX - (106 / 2), 0, 0, 0);
		
		items.add(item);
		itemView.setOnTouchListener(item.dragItem);
	
		// position the new item at the clicked position
		FrameLayout.LayoutParams par = (LayoutParams) itemView.getLayoutParams();

		par.leftMargin = MilestoneItem.scrollView.getScrollX() + milestonePosX - (106 / 2);
		par.topMargin = 0;
		itemView.setLayoutParams(par);
		
		countItemsCreated++;

    }
    
    
    public void createItemDialog()
    {
    	dialog = new Dialog(DragNDropActivity.this);
		dialog.setContentView(R.layout.createitem);
		dialog.setTitle(this.getString(R.string.createMilestone));
		
		dialog.show();
		
		final Button buttonOk = (Button) dialog.findViewById(R.id.ButtonOk);
		buttonOk.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DragNDropActivity.sprintName = (String) ((EditText) dialog.findViewById(R.id.sprintName)).getText().toString();
    	    	createItem();
		    	dialog.dismiss();
			}
		});

		final Button buttonCancel = (Button) dialog.findViewById(R.id.ButtonCancel);
		buttonCancel.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("ButtonCancel", "sprintName");
				DragNDropActivity.sprintName = null;
				dialog.dismiss();
			}
		});
		
	}
   }
    
