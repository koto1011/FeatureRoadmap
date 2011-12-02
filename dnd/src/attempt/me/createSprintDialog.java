package attempt.me;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class createSprintDialog extends Activity{
	public createSprintDialog(View v, MotionEvent event)
	{
		super();
		Log.e("v: ", v.toString());
		
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
