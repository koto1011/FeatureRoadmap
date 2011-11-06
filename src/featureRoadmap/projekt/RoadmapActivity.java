package featureRoadmap.projekt;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class RoadmapActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		Toast.makeText(getApplicationContext(), FeatureRoadmapActivity.currentlySelected,
				Toast.LENGTH_SHORT).show();
        
    }
}
