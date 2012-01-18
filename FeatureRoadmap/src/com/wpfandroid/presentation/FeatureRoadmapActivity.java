package com.wpfandroid.presentation;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.wpfandroid.dbaccess.DataHelper;
import com.wpfandroid.pojo.Milestone;
import com.wpfandroid.pojo.Roadmap;

public class FeatureRoadmapActivity extends ListActivity {
	
	public static String CURRENTLY_SELECTED = null;
	public static ArrayList<String> ROADMAPNAMES = new ArrayList<String>();
	public static ArrayList<String> ROADMAPNAMES = null;
	public static Dialog dialog;
	public static String roadmapName;
	public static String beginDate;
	public static String endDate;
	public static ListView lv;
	private ArrayAdapter<String> arrayAdapter;
	public static Roadmap currentlySelected;
	
	public static DataHelper dh;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dh = new DataHelper(this);
			
		List<String> roadmaps = dh.getAllRoadmapNames();
		
		ROADMAPNAMES = new ArrayList<String>();
		for (String roadmap : roadmaps) {
			Log.d("EXPECTED", "Begin loop - Filling ArrayList of ROADMAPNAMES");
			ROADMAPNAMES.add(roadmap);
			
			Log.d("EXPECTED", "End loop - Filling ArrayList of ROADMAPNAMES");
		}
		
		arrayAdapter = new ArrayAdapter<String>(this, com.wpfandroid.presentation.R.layout.simpl_list_item, ROADMAPNAMES);
        setListAdapter(arrayAdapter);

        lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setClickable(true);
       
        
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
              FeatureRoadmapActivity.currentlySelected = dh.getRoadmapByName((String) lv.getItemAtPosition(myItemInt));
            }});
   
        
        final Button loadButton = (Button) findViewById(R.id.loadRoadmap);
        if(loadButton != null)
        {
        	loadButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	if(FeatureRoadmapActivity.currentlySelected != null)
	            	{
	            		Intent intent = new Intent(FeatureRoadmapActivity.this, TimelineActivity.class);
	            		//intent.putExtra("DataHelper", FeatureRoadmapActivity.dh);
	            		intent.putExtra("loadedRoadmap", FeatureRoadmapActivity.currentlySelected);
	            		startActivity(intent);
	            	}
	            }
	        });
        }
        
        final Button newButton = (Button) findViewById(R.id.newRoadmap);
        if(newButton != null)
        {
        	newButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
	            	createRoadmapDialog();         		
	            }
	        });
        }
    }
    
    public void createRoadmapDialog()
    {
    	
    	dialog = new Dialog(FeatureRoadmapActivity.this);
		dialog.setContentView(R.layout.createroadmap);
		dialog.setTitle(getString(R.string.createRoadmap));
		
		dialog.show();
		
		final Button buttonOk = (Button) dialog.findViewById(R.id.ButtonOk);
		buttonOk.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FeatureRoadmapActivity.roadmapName = (String) ((EditText) dialog.findViewById(R.id.roadmapName)).getText().toString();
				
				// führende Null
				String month;
				if(((DatePicker) dialog.findViewById(R.id.beginDate)).getMonth() < 10)
					month = "0" + Integer.toString(((DatePicker) dialog.findViewById(R.id.beginDate)).getMonth());
				else
					month = Integer.toString(((DatePicker) dialog.findViewById(R.id.beginDate)).getMonth());
				
				FeatureRoadmapActivity.beginDate = Integer.toString(((DatePicker) dialog.findViewById(R.id.beginDate)).getYear())
						+ "/"
						+ month
						+ "/01";
				
				// führende Null
				if(((DatePicker) dialog.findViewById(R.id.endDate)).getMonth() < 10)
					month = "0" + Integer.toString(((DatePicker) dialog.findViewById(R.id.endDate)).getMonth());
				else
					month = Integer.toString(((DatePicker) dialog.findViewById(R.id.endDate)).getMonth());
				
				FeatureRoadmapActivity.endDate = Integer.toString(((DatePicker) dialog.findViewById(R.id.endDate)).getYear())
						+ "/"
						+ month
						+ "/01";
				
				Log.e("beginDate: ", beginDate);
				Log.e("endDate: ", endDate);
				
    	    	createRoadmap();
		    	dialog.dismiss();
			}
		});
		
		final Button buttonCancel = (Button) dialog.findViewById(R.id.ButtonCancel);
		buttonCancel.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FeatureRoadmapActivity.roadmapName = null;
				dialog.dismiss();
			}
		});

    }
    
    public void createRoadmap()
    {
    	
    	FeatureRoadmapActivity.currentlySelected = dh.createRoadmap(FeatureRoadmapActivity.roadmapName, FeatureRoadmapActivity.beginDate, FeatureRoadmapActivity.endDate, 1);
    	
    	// Roadmaps neu aus der DB laden und ListView damit befüllen

		List<String> roadmaps = dh.getAllRoadmapNames();
		
		ROADMAPNAMES.clear();
		
		for (String roadmap2 : roadmaps) {
			Log.d("EXPECTED", "Begin loop - Filling ArrayList of ROADMAPNAMES");
			ROADMAPNAMES.add(roadmap2);
			
			Log.d("EXPECTED", "End loop - Filling ArrayList of ROADMAPNAMES");
		}
		
		arrayAdapter.notifyDataSetChanged(); 
    }
}