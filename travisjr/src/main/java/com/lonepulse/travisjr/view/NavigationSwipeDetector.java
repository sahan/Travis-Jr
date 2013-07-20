package com.lonepulse.travisjr.view;

/*
 * #%L
 * Travis Jr.
 * %%
 * Copyright (C) 2013 Lonepulse
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.lonepulse.travisjr.R;

/**
 * <p>An {@link OnTouchListener} which detects a lateral {@link ActionBar} navigation 
 * swipe and switches to the appropriate tab once the {@link View} which responds to 
 * the swipe is <i>flinged</i>.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class NavigationSwipeDetector extends SimpleOnGestureListener implements OnTouchListener {
	
	
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
	 * <p>The transition for stale content after a left swipe.
	 */
	private Animation slideOutLeft;
	
	/**
	 * <p>The transition for fresh content after a left swipe.
	 */
	private Animation slideInRight;
	
	/**
	 * <p>The transition for stale content after a right swipe.
	 */
	private Animation slideOutRight;
	
	/**
	 * <p>The transition for fresh content after a right swipe.
	 */
	private Animation slideInLeft;
	
	/**
	 * <p>The transition to notify a futile right swipe.
	 */
	private ObjectAnimator flipEdgeLeft;
	
	/**
	 * <p>The transition to notify a futile left swipe.
	 */
	private ObjectAnimator flipEdgeRight;
	
	/**
	 * <p>The root content view to which the transition animations should be applied.
	 */
	private View content;
	
	/**
	 * <p>The {@link ActionBar} whose tabs are to be swiped.
	 */
	private Activity activity;
	
	/**
	 * <p>The {@link GestureDetector} which realizes this {@link SimpleOnGestureListener}.
	 */
	private GestureDetector gestureDetector;
	
	
	/**
	 * <p>Creates a new {@link NavigationSwipeDetector} by taking the {@link ActionBar} for which 
	 * navigation swiping is to be enabled.
	 * 
	 * @param activity
	 * 			the {@link Activity} whose navigation tabs are to be swiped
	 *
	 * @since 1.1.0
	 */
	public NavigationSwipeDetector(Activity activity) {
		
		View tabContent = activity.findViewById(R.id.tab_content);
			
		if(tabContent == null) {
			
			RuntimeException mve = new MissingViewException("R.id.tab_content");
			throw new NavigationSwipeListenerException(activity.getLocalClassName(), mve);
		}
		
		if(!(tabContent instanceof ViewGroup)) {
			
			String reason = "R.id.tab_content should be a ViewGroup with only one child view.";
			throw new NavigationSwipeListenerException(activity.getLocalClassName(), reason);
		}
		
		if(((ViewGroup)tabContent).getChildCount() != 1) {
			
			String reason = "R.id.tab_content must have one and only one child view.";
			throw new NavigationSwipeListenerException(activity.getLocalClassName(), reason);
		}
		
		this.content = ((ViewGroup)tabContent).getChildAt(0);
		this.activity = activity;
		this.gestureDetector = new GestureDetector(activity, this);
		this.slideOutRight = AnimationUtils.loadAnimation(activity, R.anim.slide_out_right);
		this.slideOutLeft = AnimationUtils.loadAnimation(activity, R.anim.slide_out_left);
		this.slideInRight = AnimationUtils.loadAnimation(activity, R.anim.slide_in_right);
		this.slideInLeft = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left);
		
		flipEdgeLeft = ObjectAnimator.ofFloat(content, "rotationY", 5, 0);
		flipEdgeLeft.setInterpolator(new LinearInterpolator());
		flipEdgeLeft.setDuration(activity.getResources().getInteger(R.integer.dur_anim_xs));
		
		flipEdgeRight = ObjectAnimator.ofFloat(content, "rotationY", -5, 0);
		flipEdgeRight.setInterpolator(new LinearInterpolator());
		flipEdgeRight.setDuration(activity.getResources().getInteger(R.integer.dur_anim_xs));
	}
	
	/**
	 * <p>Detects a lateral navigation swipe and switches to the appropriate navigation item 
	 * on the {@link ActionBar}.
	 * 
	 * @since 1.1.0
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		
		final ActionBar actionBar = activity.getActionBar();
		if(actionBar == null) return false;
		
		int navigationItemCount = actionBar.getNavigationItemCount();
		if(navigationItemCount == 0) return false;
		
		int selectedIndex = actionBar.getSelectedNavigationIndex();
		
		try {
			
			boolean hasTravelled = Math.abs(e1.getX() - e2.getX()) > DISTANCE;
			boolean velocityReached = Math.abs(velocityX) > VELOCITY;
			boolean hasDeviated = Math.abs(e1.getY() - e2.getY()) > DEVIATION;
			
			if(hasTravelled && !hasDeviated && velocityReached) {
				
				boolean swipedLeft = (e1.getX() - e2.getX()) > 0;
				
				if(swipedLeft) {
					
					if(++selectedIndex <= navigationItemCount - 1) {
					
						final int index = selectedIndex; 
						
						slideOutLeft.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {}
							
							@Override
							public void onAnimationRepeat(Animation animation) {}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								
								actionBar.setSelectedNavigationItem(index);
								content.startAnimation(slideInRight);
							}
						});
						
						content.startAnimation(slideOutLeft);
					}
					else {
						
						flipEdgeRight.start();
					}
					
					return true;
				}
				
				if(!swipedLeft) {
					
					if(--selectedIndex >= 0) {
					
						final int index = selectedIndex;
						
						slideOutRight.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {}
							
							@Override
							public void onAnimationRepeat(Animation animation) {}
							
							@Override
							public void onAnimationEnd(Animation animation) {
							
								actionBar.setSelectedNavigationItem(index);
								content.startAnimation(slideInLeft);
							}
						});
						
						content.startAnimation(slideOutRight);
					}
					else {
						
						flipEdgeLeft.start();
					}
					
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
	 * <p>Translates touch events to {@link ActionBar} navigation swipes. 
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		return gestureDetector.onTouchEvent(event);
	}
}
