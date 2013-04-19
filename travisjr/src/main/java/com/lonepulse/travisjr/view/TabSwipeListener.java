package com.lonepulse.travisjr.view;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * <p>An {@link OnTouchListener} which detects a lateral {@link ActionBar.Tab} 
 * swipe and switches to the appropriate tab once the {@link View} which responds 
 * to the swipe if <i>flinged</i>.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class TabSwipeListener extends SimpleOnGestureListener implements OnTouchListener {

	/**
	 * <p>The minimum fling distance which qualifies for a swipe.
	 */
	private static final int DISTANCE = 80;
	
	/**
	 * <p>The maximum deviation off the X-Axis which can be tolerated. 
	 */
	private static final int DEVIATION = 200;
	
	/**
	 * <p>The velocity which the fling should reach in order for it to qualify as a swipe.
	 */
	private static final int VELOCITY = 100;
	
	/**
	 * <p>The {@link ActionBar} whose tabs are to be swiped.
	 */
	private Activity activity;
	
	/**
	 * <p>The {@link GestureDetector} which realizes this {@link SimpleOnGestureListener}.
	 */
	private GestureDetector gestureDetector;
	
	
	/**
	 * <p>Creates a new {@link TabSwipeListener} by taking the {@link ActionBar} whose 
	 * navigation tabs are to swiped.
	 * 
	 * @param activity
	 * 			the {@link Activity} whose navigation tabs are to be swiped
	 *
	 * @since 1.1.0
	 */
	public TabSwipeListener(Activity activity) {
		
		this.activity = activity;
		this.gestureDetector = new GestureDetector(activity, this);
	}
	
	/**
	 * <p>Detects a lateral navigation {@link ActionBar.Tab} swipe and switched to the 
	 * appropriate tab.
	 * 
	 * @since 1.1.0
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		
		ActionBar actionBar = activity.getActionBar();
		if(actionBar == null) return false;
		
		Tab selectedTab = actionBar.getSelectedTab();
		if(selectedTab == null) return false;
		
		int selectedIndex = selectedTab.getPosition();
		int tabCount = actionBar.getTabCount();
		
		try {
			
			boolean hasTravelled = Math.abs(e1.getX() - e2.getX()) > DISTANCE;
			boolean velocityReached = Math.abs(velocityX) > VELOCITY;
			boolean hasDeviated = Math.abs(e1.getY() - e2.getY()) > DEVIATION;
			
			if(hasTravelled && !hasDeviated && velocityReached) {
				
				boolean swipedLeft = (e1.getX() - e2.getX()) > 0;
				
				if(swipedLeft && (++selectedIndex <= tabCount - 1)) {
					
					actionBar.setSelectedNavigationItem(selectedIndex);
					return true;
				}
				
				if(!swipedLeft && (--selectedIndex >= 0)) {
					
					actionBar.setSelectedNavigationItem(selectedIndex);
            		return true;
				}
			}
        }
		catch (Exception e) {
			
			Log.e(getClass().getSimpleName(), "Tab swipe gesture detection failed. ", e);
		}
		
		return false;
	}

	/**
	 * <p>Translates touch events to {@link ActionBar.Tab} swipe gestures. 
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		return gestureDetector.onTouchEvent(event);
	}
}
