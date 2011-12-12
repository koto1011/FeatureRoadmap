package attempt.me;

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

public class Item{
	public Item(View itemView, TextView beschriftung, OwnHorizontalScrollView scrollView, int displayWidth) {
		this.itemView = itemView;
		this.beschriftung = beschriftung;
		this.displayWidth = displayWidth;
		Item.scrollView = scrollView;    
	}

	private Choords coords;
	public View itemView;
	public static OwnHorizontalScrollView scrollView;
	public int displayWidth;
	private TextView beschriftung;
	private ViewGroup parentView;
	private static int countItemsCreated = 0;
	
	
//	public void create(LayoutInflater inflater, View _parentView, MotionEvent event) {
//
//		//this.coords = coords;
//				
//		Log.e("Konstruktor Item", "Beginn");
//		Log.e("parentView:", ""+_parentView.getId());
//		
//		if(_parentView.getId() == R.id.timeline)
//		{
//			if(countItemsCreated % 2 == 0)
//			{
//				parentView = (ViewGroup) findViewById(R.id.oben);
//				parentView = (ViewGroup) inflater.inflate(R.layout.item, parentView);
//			}
//			else
//			{
//				parentView = (ViewGroup) findViewById(R.id.unten);
//				parentView = (ViewGroup) inflater.inflate(R.layout.item_unten, parentView);
//			}
//		}
//		else
//		{
//			// Fehler
//		}
//		
//		Log.e("child count", ""+parentView.getChildCount());
//		itemView = parentView; //parentView.getChildAt(parentView.getChildCount() - 1);
//		Log.e("itemView: ", itemView.toString());
//		itemView.setOnTouchListener(dragItem);
//	
//		// position the new item at the clicked position
//		FrameLayout.LayoutParams par = new LayoutParams(40,40);//(LayoutParams) itemView.getLayoutParams();
//
//		par.leftMargin = (int)event.getRawX() - (106 / 2);
//		par.topMargin = 0;			// (itemView.getWidth()/2);
//		itemView.setLayoutParams(par);
//		
//		countItemsCreated++;
//		
//	}
	
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
		                    par.leftMargin = Item.scrollView.getScrollX() + (int)event.getRawX() - (v.getWidth()/2);		                    
							
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
							dnd.verschiebeBeschriftung(v);
							
							break;
						}//inner case MOVE
						case MotionEvent.ACTION_UP:
						{
							Log.e("onTouch: ", "case: item - UP");
			
							Log.e("onTouch: ", "case: item - UP, RawX: "+(int)event.getRawX());
		                    par.leftMargin = Item.scrollView.getScrollX() + (int)event.getRawX() - (v.getWidth()/2);
							v.setLayoutParams(par);
							dnd.verschiebeBeschriftung(v);
							
							Item.scrollView.setIsScrollable(true);	
							
							Log.e("LeftMargin: ", ""+v.getLeft());
							break;
						}//inner case UP
						case MotionEvent.ACTION_CANCEL:
						{
							Log.e("onTouch: ", "case: item - CANCEL");
							Log.e("onTouch: ", "case: item - CANCEL, RawX: "+(int)event.getRawX());
		                    par.leftMargin = (int)event.getRawX() - (v.getWidth()/2);
							v.setLayoutParams(par);
							dnd.verschiebeBeschriftung(v);
							
							Item.scrollView.setIsScrollable(false);	
							
							Log.e("LeftMargin: ", ""+v.getLeft());
							break;
						}//inner case UP

						case MotionEvent.ACTION_DOWN:
						{
							Log.e("onTouch: ", "case: item - DOWN");

						    Item.scrollView.setIsScrollable(false);
		
						    v.setLayoutParams(par);
							dnd.verschiebeBeschriftung(v);
							break;
						}//inner case UP
					}//inner switch
				}//if pawn
			return true;
		}//onTouch
    };//dragItem

    public void verschiebeBeschriftung()
    {
    	FrameLayout.LayoutParams par = (LayoutParams) this.itemView.getLayoutParams();
    	beschriftung.setPadding(par.leftMargin, par.topMargin, par.rightMargin, par.bottomMargin);
    	beschriftung.invalidate();
    }
}
