package com.wpfandroid.presentation;

import java.util.ArrayList;
import java.util.List;

import com.wpfandroid.dbaccess.DataHelper;
import com.wpfandroid.pojo.Milestone;
import com.wpfandroid.pojo.Roadmap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
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

public class TimelineActivity extends Activity 
{
	public FrameLayout board;
	public View pawn;
	public View item;
	private List<MilestoneItem> inflatedMilestones;
	public static MotionEvent event;
	public static ViewGroup parentView;
	public static View v;
	public static int milestonePosX;
	public static Dialog dialog;
	public static String milestoneName;
	public Roadmap roadmap;
	
	private int beginDateMonth;
	private int beginDateYear;
	private int endDateMonth;
	private int endDateYear;
	private int months;
	private int roadmapWidth;
	
	private List<String> beschriftungen = null;
	private List<Integer> positionen = null; 
	
	private final String[] monate = {"Jan", "Feb", "M�r", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez" };
	
	private DataHelper dh;
	
	public boolean created = false;
	private boolean updated = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        inflatedMilestones = new ArrayList<MilestoneItem>();
        
        setContentView(R.layout.timeline);
                
        dh = FeatureRoadmapActivity.dh;
        
        final Button buttonSave = (Button) findViewById(R.id.buttonSaveAndBack);
        buttonSave.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				saveMilestones();
				startActivity(new Intent(TimelineActivity.this, FeatureRoadmapActivity.class));
			}
		});
        
        final Button buttonBack = (Button) findViewById(R.id.buttonBackWithoutSave);
        buttonBack.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(TimelineActivity.this, FeatureRoadmapActivity.class));
			}
		});
        
        Log.e("Roadmap-Obj", ""+getIntent().getSerializableExtra("loadedRoadmap"));
        
        roadmap = (Roadmap) getIntent().getSerializableExtra("loadedRoadmap");
            
        beginDateMonth = Integer.parseInt(roadmap.getStartDate().split("/")[1]);
        beginDateYear = Integer.parseInt(roadmap.getStartDate().split("/")[0]);
        
        endDateMonth = Integer.parseInt(roadmap.getEndDate().split("/")[1]);
        endDateYear = Integer.parseInt(roadmap.getEndDate().split("/")[0]);
        
        months = 0;
        
        if(beginDateYear == endDateYear)
        {
        	months = endDateMonth - beginDateMonth;
        }
        else
        {
        	months = endDateMonth - beginDateMonth;
        	months = months + (endDateYear - beginDateYear) * 12;
        }
        
        Log.e("months: ", ""+months);
        
        // size the view according to the time period of the timeline of the roadmap
        RelativeLayout timeline = (RelativeLayout) findViewById(R.id.timeline);
        timeline.setOnTouchListener(touchTimeline);
        
        //Test:
        roadmapWidth = (int) Math.round(months * 72 * 2);
        
        Log.e("Width of item: ", ""+ ((RelativeLayout) findViewById(R.id.item)).getWidth());
        int height = 100;
        Log.e("width: ", ""+roadmapWidth);
        
        timeline.setLayoutParams(new LinearLayout.LayoutParams(roadmapWidth, 30)); // width and height
                
        FrameLayout oben = (FrameLayout) findViewById(R.id.oben);
        oben.setLayoutParams(new LinearLayout.LayoutParams(roadmapWidth,height)); // width and height
        
        FrameLayout unten = (FrameLayout) findViewById(R.id.unten);
        unten.setLayoutParams(new LinearLayout.LayoutParams(roadmapWidth,height)); // width and height
        
        createTimelineInscription();
        inflateMilestones();
       
    }//onCreate
    
    OnTouchListener touchTimeline = new OnTouchListener()
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
    			    			
    			TimelineActivity.event = event;
    			TimelineActivity.v = v;
    			
    			milestonePosX = (int) event.getRawX();
    			Log.e("RawX im Listener", ""+(int)event.getRawX());
    			
    			createMilestoneDialog(false);
    			
    			return true;
    		}
    		else
    			return false;
    	}
    };

    
    public void createMilestone()
    {
    	if(updated == false)
    	{
	    	final LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if(v.getId() == R.id.timeline)
			{
				if(inflatedMilestones.size() % 2 == 0)
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
			
	    	View itemView = parentView.getChildAt(parentView.getChildCount() - 1);
	
	        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			int displayWidth = display.getWidth();
		
			MilestoneItem milestone 
				= new MilestoneItem(itemView, 
						milestoneName,
						(OwnHorizontalScrollView) ((LinearLayout) findViewById(R.id.roadmap)).getParent(), 
						TimelineActivity.milestonePosX,
						displayWidth);
			//Log.e("LongClick", "Listener setzen");
			
			//itemView.setOnLongClickListener(editItem);
			inflatedMilestones.add(milestone);
    	}
    	else
    	{
    		// DB-Update
    	}

    }
    
    
    public void createMilestoneDialog(boolean updated)
    {
    	dialog = new Dialog(TimelineActivity.this);
		dialog.setContentView(R.layout.createmilestone);
		dialog.setTitle(R.string.createMilestone);
		
		dialog.show();
		
		this.updated = updated;
		
		final Button buttonOk = (Button) dialog.findViewById(R.id.ButtonOk);
		buttonOk.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimelineActivity.milestoneName = (String) ((EditText) dialog.findViewById(R.id.milestoneName)).getText().toString();
    	    	createMilestone();
		    	dialog.dismiss();
			}
		});

		final Button buttonCancel = (Button) dialog.findViewById(R.id.ButtonCancel);
		buttonCancel.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("ButtonCancel", "sprintName");
				TimelineActivity.milestoneName = null;
				dialog.dismiss();
			}
		});
		
	}
    
	public OnLongClickListener editItem = new OnLongClickListener() {
		
		public boolean onLongClick(View v) {
			Log.e("Long Clicked", "Long Clicked");
			createMilestoneDialog(true);
			return false;
		}
	};
	
	public void saveMilestones()
	{
		dh.deleteAllMilestonesByRoadmapId(roadmap.getId());
		
		for(int i = 0; i < inflatedMilestones.size(); i++)
		{
			MilestoneItem milestoneItem = inflatedMilestones.get(i);
			int pos = milestoneItem.getPos();
			
			int posIndexBest = -1;
			int difference = 999;
			for(int posIndex = 0; posIndex < positionen.size(); posIndex++)
			{
				if(Math.abs(pos - positionen.get(posIndex)) < difference)
				{
					difference = Math.abs(pos - positionen.get(posIndex));
					posIndexBest = posIndex;
				}				
			}
			
			String text = beschriftungen.get(posIndexBest);
			Log.e("Ausgew�hlte Beschriftung: ", text);
			String monthText = text.split("[0-9]")[0];
			String year = text.replace(monthText, "");
			
			int month = -1;
			for(int k = 0; k < monate.length; k++)
			{
				if(monthText.equals(monate[k]))
				{
					month = k;
				}
			}

			Log.e("Speichern eines Milestone: ", milestoneItem.getName()+"..."+year + "/" + month + "/01");
			dh.createMilestone(milestoneItem.getName(), "", year + "/" + month + "/01", roadmap.getId());
			
		}
	}
	
	private void createTimelineInscription()
	{
	
		beschriftungen = new ArrayList<String>();
		positionen = new ArrayList<Integer>();
		
		String beschriftung = "";
		
		for(int monthIndex = 1; monthIndex <= months; monthIndex++)
		{
			beschriftung = monate[(beginDateMonth + monthIndex - 1) % 12] + (beginDateYear + (int) Math.floor((beginDateMonth + monthIndex - 1) / 12));
			beschriftungen.add(beschriftung);
			
			Log.e("Beschriftung " + monthIndex, beschriftung);
			Log.e("Position ", "" + roadmapWidth / months * (monthIndex - 1));
			
			TextView beschriftungView = new TextView(getApplicationContext());
			beschriftungView.setLayoutParams(new LayoutParams(android.widget.RelativeLayout.LayoutParams.FILL_PARENT, android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));
			((RelativeLayout) findViewById(R.id.timeline)).addView(beschriftungView);
			int position = roadmapWidth / months * (monthIndex - 1);
			beschriftungView.setPadding(position, 0, 0, 0);
			positionen.add(position);
			beschriftungView.setText(beschriftung);
			beschriftungView.setTextColor(Color.WHITE);
		}
	}

	private void inflateMilestones()
	{
		List<Milestone> milestones = dh.getAllMilestonesByRoadmapId(roadmap.getId());
		
		for(int i = 0; i < milestones.size(); i++)
		{
			Milestone milestone = milestones.get(i);
			
			final LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if(inflatedMilestones.size() % 2 == 0)
			{
				parentView = (ViewGroup) findViewById(R.id.oben);
				parentView = (ViewGroup) inflater.inflate(R.layout.item, parentView);
			}
			else
			{
				parentView = (ViewGroup) findViewById(R.id.unten);
				parentView = (ViewGroup) inflater.inflate(R.layout.item_unten, parentView);
			}

			View itemView = parentView.getChildAt(parentView.getChildCount() - 1);
	
	        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			int displayWidth = display.getWidth();
	
			int milestoneMonth = Integer.parseInt(milestone.getDate().split("/")[1]);
			Log.e("milestoneMonth", ""+ (milestoneMonth - 1));
			
			String yearText = milestone.getDate().split("/")[0];
			String monthText = monate[milestoneMonth - 1];
			
			int targetIndex = -1;
			for(int j = 0; j < beschriftungen.size(); j++)
			{
				if(beschriftungen.get(j).equals(monthText+yearText))
				{
					targetIndex = j;
				}
			}
			
			MilestoneItem item 
				= new MilestoneItem(itemView, 
									milestone.getName(), 
									(OwnHorizontalScrollView) ((LinearLayout) findViewById(R.id.roadmap)).getParent(),
									positionen.get(targetIndex),
									displayWidth);

			//itemView.setOnLongClickListener(editItem);
			inflatedMilestones.add(item);
			
			Log.e("Milestone inflated beim Laden: ", milestone.getName() + "..." + milestone.getDate());
		}
		
	}
}
    
