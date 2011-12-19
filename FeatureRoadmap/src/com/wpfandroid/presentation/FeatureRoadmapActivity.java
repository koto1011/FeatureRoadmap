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
import android.widget.TextView;
import android.widget.Toast;

import com.wpfandroid.dbaccess.DataHelper;
import com.wpfandroid.pojo.Roadmap;

public class FeatureRoadmapActivity extends ListActivity {
	
	public static String CURRENTLY_SELECTED = null;
	public static ArrayList<String> ROADMAPNAMES = new ArrayList<String>();
	public static Dialog dialog;
	public static String roadmapName;
	public static String beginDate;
	public static String endDate;
	public static ListView lv;
	private ArrayAdapter<String> arrayAdapter;
	public static String currentlySelected;
	
	public DataHelper dh;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.dh = new DataHelper(this);
        
        // Begin - sample data to fill database
        // When not necessary comment
		this.dh.deleteAllMilestones();
		this.dh.deleteAllRoadmaps();

		this.dh.createRoadmap("roadmapNameA", "2011/11/01", "2011/12/29", 1);
		this.dh.createRoadmap("roadmapNameB", "2011/11/02", "2011/12/30", 1);
		this.dh.createRoadmap("roadmapNameC", "2011/11/03", "2011/12/31", 1);

		this.dh.createMilestone("milestoneName1a", "description1a", "2011/12/01",
				1);
		this.dh.createMilestone("milestoneName1b", "description1b", "2011/12/11",
				1);
		this.dh.createMilestone("milestoneName1c", "description1c", "2011/12/21",
				1);
		
		this.dh.createMilestone("milestoneName2a", "description2", "2011/12/02",
				2);
		this.dh.createMilestone("milestoneName2b", "description2", "2011/12/12",
				2);
		this.dh.createMilestone("milestoneName2c", "description2", "2011/12/22",
				2);
		
		this.dh.createMilestone("milestoneName3a", "description3", "2011/12/03",
				3);
		this.dh.createMilestone("milestoneName3b", "description3", "2011/12/13",
				3);
		this.dh.createMilestone("milestoneName3c", "description3", "2011/12/23",
				3);
		// End - sample data to fill database
		
		List<Roadmap> roadmaps = this.dh.selectAllRoadmaps();
		
		for (Roadmap roadmap : roadmaps) {
			Log.d("EXPECTED", "Begin loop - Filling ArrayList of ROADMAPNAMES");
			ROADMAPNAMES.add(roadmap.getName());
			
			Log.d("EXPECTED", "End loop - Filling ArrayList of ROADMAPNAMES");
		}
		
		/// ####################
		arrayAdapter = new ArrayAdapter<String>(this, com.wpfandroid.presentation.R.layout.simpl_list_item, ROADMAPNAMES);
        setListAdapter(arrayAdapter);

        lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setClickable(true);
       
        
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
              currentlySelected = (String) (lv.getItemAtPosition(myItemInt));
            }});
   
        
        final Button loadButton = (Button) findViewById(R.id.loadRoadmap);
        if(loadButton != null)
        {
        	loadButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	if(currentlySelected != null)
	            	{
	            		Intent intent = new Intent(FeatureRoadmapActivity.this, DragNDropActivity.class);
	            		intent.putExtra("loadedRoadmap", "test");
	            		startActivity(intent);
	            	}
	            }
	        });
        }
        
        final Button newButton = (Button) findViewById(R.id.newRoadmap);
        if(newButton != null)
        {
        	newButton.setOnClickListener(new View.OnClickListener() {
	            @SuppressWarnings("unchecked")
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
				FeatureRoadmapActivity.beginDate = Integer.toString(((DatePicker) dialog.findViewById(R.id.beginDate)).getMonth());
				FeatureRoadmapActivity.endDate = Integer.toString(((DatePicker) dialog.findViewById(R.id.endDate)).getMonth());
				
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
    	
    	Long id = dh.createRoadmap(FeatureRoadmapActivity.roadmapName, FeatureRoadmapActivity.beginDate, FeatureRoadmapActivity.endDate, 1);
    	dh.selectRoadmap(0);
    	//Roadmap roadmap = dh.selectRoadmap(id);
    	
    	// Roadmaps neu aus der DB laden und ListView damit befüllen
		List<Roadmap> roadmaps = this.dh.selectAllRoadmaps();
		
		ROADMAPNAMES.clear();
		
		for (Roadmap roadmap2 : roadmaps) {
			Log.d("EXPECTED", "Begin loop - Filling ArrayList of ROADMAPNAMES");
			ROADMAPNAMES.add(roadmap2.getName());
			
			Log.d("EXPECTED", "End loop - Filling ArrayList of ROADMAPNAMES");
		}
		
		arrayAdapter.notifyDataSetChanged(); 
    }
}