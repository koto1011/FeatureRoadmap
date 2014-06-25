package featureRoadmap.projekt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class DragnDrop extends View {

	// events when touching the screen
	private ColorBall[] colorballs = new ColorBall[3]; // array that holds the balls
   private int balID = 0; // variable to know what ball is being dragged

   public DragnDrop(Context context) {
       super(context);
       setFocusable(true); //necessary for getting the touch events
       
       // setting the start point for the balls
       Point point1 = new Point();
       point1.x = 50;
       point1.y = 20;
       Point point2 = new Point();
       point2.x = 100;
       point2.y = 20;
       Point point3 = new Point();
       point3.x = 150;
       point3.y = 20;
       
                      
       // declare each ball with the ColorBall class
       colorballs[0] = new ColorBall(context,R.drawable.bol_groen, point1);
       colorballs[1] = new ColorBall(context,R.drawable.bol_rood, point2);
       colorballs[2] = new ColorBall(context,R.drawable.bol_blauw, point3);
       
       
   }
   
   // the method that draws the balls
   @Override protected void onDraw(Canvas canvas) {
       //canvas.drawColor(0xFFCCCCCC);     //if you want another background color       
       
   	//draw the balls on the canvas
   	for (ColorBall ball : colorballs) {
           canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(), null);
         }

   }
   
    public boolean onTouchEvent(MotionEvent event) {
 
        int eventaction = event.getAction();
        int X = (int)event.getX();
 
        int Y = (int)event.getY();
        switch (eventaction ) {
        case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball
 
                for (ColorBall ball : colorballs) {
 
                        // check all the bounds of the ball
 
                if (X > ball.getX() && X < ball.getX()+50 && Y > ball.getY() && Y < ball.getY()+50){
 
                        balID = ball.getID();
 
                        break;
                }
 
              }
 
             break;
        case MotionEvent.ACTION_MOVE:   // touch drag with the ball
                 // move the balls the same as the finger
            colorballs[balID-1].setX(X-25);
 
            colorballs[balID-1].setY(Y-25);
     
              break;
 
             case MotionEvent.ACTION_UP:
 
             // touch drop - just do things here after dropping
             break;
         }
 
        // redraw the canvas
 
        invalidate();
 
        return true;
 
    }
 
}
