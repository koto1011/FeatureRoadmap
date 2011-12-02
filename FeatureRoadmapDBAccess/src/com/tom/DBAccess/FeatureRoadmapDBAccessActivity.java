package com.tom.DBAccess;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FeatureRoadmapDBAccessActivity extends Activity {
	private TextView output;
    
	   private DataHelper dh;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	         
	        this.output = (TextView) this.findViewById(R.id.out_text);
	         
	        this.dh = new DataHelper(this);
	        this.dh.deleteAll();
	        this.dh.insert("Tobi");
	        this.dh.insert("Tom");
	        this.dh.insert("Amelie");        
	        List<String> names = this.dh.selectAll();
	        StringBuilder sb = new StringBuilder();
	        sb.append("Names in database:\n");
	        for (String name : names) {
	           sb.append(name + "\n");
	        }
	        
	        this.dh.closeDB();
	         
	        Log.d("EXAMPLE", "names size - " + names.size());
	         
	        this.output.setText(sb.toString());
	         
	    }
}