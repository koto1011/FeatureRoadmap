package attempt.me;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.EventLog.Event;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class createSprintDialog extends Activity{
	private int parentLayout;
	private ViewGroup v;
	private MotionEvent event;
	
	public createSprintDialog(View v, MotionEvent event)
	{
		super();
		this.v = (ViewGroup) v;
		Log.e("v: ", v.toString());
		this.event = event;
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	};
	
	
	public void show()
	{
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
		Log.e("Parent: ",findViewById(R.id.createItemDialog).getParent().toString());

		builder.setView(findViewById(R.id.createItemDialog));
		
		builder.setTitle("Sprint erzeugen");
		
		builder.setPositiveButton("text", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

//            	Item item = new Item(getApplicationContext());
//            	item.create((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), (ViewGroup) v, event);
            }
        });

        builder.setNegativeButton("text", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                /* User clicked Cancel so do some stuff */
            }
        });
       
        builder.show();
	}

}
