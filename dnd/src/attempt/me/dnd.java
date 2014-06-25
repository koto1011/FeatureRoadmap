package attempt.me;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class dnd extends Activity 
{
	public FrameLayout board;
	public View pawn;
	public View item;
	public int countItemsCreated;
	public static List<Item> items;
	
	public boolean created = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        items = new ArrayList<Item>();
        countItemsCreated = 0;
        setContentView(R.layout.main);

        //((FrameLayout)findViewById(R.id.oben)).setOnTouchListener(touchBoard);
        //((FrameLayout)findViewById(R.id.unten)).setOnTouchListener(touchBoard);
        findViewById(R.id.timeline).setOnTouchListener(touchBoard);
       
    }//onCreate
    
    OnTouchListener touchBoard = new OnTouchListener()
    {
    	public boolean onTouch(final View v, final MotionEvent event)
    	{
    		if((v.getId() == R.id.oben || v.getId() == R.id.unten) && event.getAction() == MotionEvent.ACTION_DOWN)
    		{
    			Log.e("onTouchBoard: ", "erreicht");
    			
//    			createSprintDialog dialog = new createSprintDialog(v, event);
//    			dialog.show();
//    			
//    			final LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    			
//    			View.inflate(getApplicationContext(), R.layout.createitem, (ViewGroup) findViewById(R.id.roadmap));
//    			
//    			AlertDialog.Builder builder;
//    			AlertDialog alertDialog;
//
//    			Context mContext = getApplicationContext();
//
////    			View layout = inflater.createView(name, prefix, attrs).inflate(R.layout.createitem,
////    			                               (ViewGroup) v);
//
////    			TextView text = (TextView) layout.findViewById(R.id.editText1);
////    			text.setText("Hello, this is a custom dialog!");
//
//    			builder = new AlertDialog.Builder(mContext);
//    			Log.e("Parent: ",findViewById(R.id.createItemDialog).getParent().toString());
//
//    			builder.setView(findViewById(R.id.createItemDialog));
//    			
//    			builder.setTitle("Sprint erzeugen");
//    			
//    			builder.setPositiveButton("text", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                    	Item item = new Item(inflater, (ViewGroup) v, event);
//                    }
//                });
//
//                builder.setNegativeButton("text", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                        /* User clicked Cancel so do some stuff */
//                    }
//                });
//               
//                builder.show();
//    			//alertDialog = builder.create();
//   			
    			
    			return true;
    		}
    		else if(v.getId() == R.id.timeline && event.getAction() == MotionEvent.ACTION_DOWN)
    		{
    			final LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			
    			//Item item = new Item(getApplicationContext());
    			//item.create(inflater, v, event);
    			ViewGroup parentView = null;
    			
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
				beschriftung.setPadding((int)event.getRawX() - (106 / 2), 0, 0, 0);
    			beschriftung.setText("text");
    			
    			Item item = new Item(itemView, beschriftung, (OwnHorizontalScrollView) ((LinearLayout) findViewById(R.id.roadmap)).getParent(), displayWidth);
    			
    			items.add(item);
    			itemView.setOnTouchListener(item.dragItem);
    		
    			// position the new item at the clicked position
    			FrameLayout.LayoutParams par = (LayoutParams) itemView.getLayoutParams();

    			par.leftMargin = Item.scrollView.getScrollX() + (int)event.getRawX() - (106 / 2);
    			par.topMargin = 0;
    			itemView.setLayoutParams(par);
    			
    			countItemsCreated++;

    			return true;
    		}
    		else
    			return false;
    	}
    };
    
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
    
    private void pickContact() { 
    	 // Create an intent to "pick" a contact, as defined by the content provider URI    
    	Intent intent = new Intent(this, createSprintDialog.class);
    	startActivity(intent);
    	} 
}