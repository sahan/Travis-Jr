package com.lonepulse.travisjr.app;

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


import java.util.concurrent.atomic.AtomicBoolean;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.lonepulse.icklebot.IckleActivity;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.service.BasicAccountService;
import com.lonepulse.travisjr.util.Resources;

/**
 * <p>A custom {@link IckleActivity} which is tailored to setup the 
 * action bar and provide support for synchronization.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class TravisJrActivity extends IckleActivity {

	
	/**
	 * <p>The {@link MenuItem} which initiates synchronization.
	 */
	private MenuItem menuItemSync;
	
	/**
	 * <p>A custom view which is set on the sync action when 
	 * synchronization begins.
	 */
	private View actionViewSync;
	
	/**
	 * <p>A custom view which is set on the sync action when 
	 * synchronization is complete.
	 */
	private View actionViewComplete;
	
	/**
	 * <p>A custom view which is set on the sync action when 
	 * a data connection is unavailable.
	 */
	private View actionViewData;
	
	/**
	 * <p>This animation is invoked on {@link #actionViewSync} 
	 * when synchronization starts.
	 */
	private Animation rotate;
	
	/**
	 * <p>This animation is invoked on {@link #actionViewComplete} 
	 * when synchronization is complete.
	 */
	private Animation fadeOut;
	
	/**
	 * <p>A flag which determines if a synchronization is already 
	 * taking place.
	 */
	private AtomicBoolean syncing = new AtomicBoolean(false);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		setTheme(android.R.style.Theme_Holo_Light);
		
		super.onCreate(savedInstanceState);
		
		actionViewSync = getLayoutInflater().inflate(R.layout.action_view_sync, null);
		actionViewComplete = getLayoutInflater().inflate(R.layout.action_view_complete, null);
		actionViewData = getLayoutInflater().inflate(R.layout.action_view_data, null);
		
		rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
		actionViewSync.startAnimation(rotate);
		
		fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
		fadeOut.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
			
				menuItemSync.setActionView(null);
				syncing.set(false);
			}
		});
		
		onInitActionBar(getActionBar());
	}
	
	/**
	 * <p>Override this callback to initialize the {@link ActionBar} associated 
	 * with this {@link Activity}.
	 *
	 * @param actionBar
	 * 			the {@link ActionBar} intance to be initialized
	 * 
	 * @since 1.1.0
	 */
	protected void onInitActionBar(ActionBar actionBar) {
		
		if(actionBar != null) {
		
			View header = getLayoutInflater().inflate(R.layout.action_view_title, null);
			((TextView)header.findViewById(R.id.title)).setText(onInitTitle());
			((TextView)header.findViewById(R.id.subtitle)).setText(onInitSubtitle());
	
			actionBar.setIcon(R.drawable.ic_action_bar);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			actionBar.setCustomView(header);
		}
	}
	
	/**
	 * <p>Override this callback to initialize the activity with 
	 * a custom title.
	 *
	 * @return the title to be set for this activity; defaults to 
	 * 		   {@link Activity#getTitle()}
	 * 
	 * @since 1.1.0
	 */
	protected String onInitTitle() {
		
		return getTitle().toString();
	}
	
	/**
	 * <p>Override this callback to initialize the activity with 
	 * a custom subtitle. 
	 *
	 * @return the subtitle to be set for this activity; defaults 
	 * 		   to the user's GitHub username
	 * 
	 * @since 1.1.0
	 */
	protected String onInitSubtitle() {
		
		return new BasicAccountService().getGitHubUsername();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	
		super.onSaveInstanceState(outState);
		outState.putSerializable(Resources.key(R.string.key_syncing), syncing);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		
		super.onRestoreInstanceState(savedInstanceState);
		syncing = (AtomicBoolean) savedInstanceState.getSerializable(Resources.key(R.string.key_syncing));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.basic, menu);
		menuItemSync = menu.findItem(R.id.menu_sync);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch (item.getItemId()) {
		
			case R.id.menu_sync: {
				
				if(network().isConnected()) {
					
					onSync();
				}
				else {
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							menuItemSync.setActionView(actionViewData);
							actionViewData.startAnimation(fadeOut);
						}
					});
				}
				
				break;
			}
		}
		
		return true;
	}
	
	/**
	 * <p>Override this callback to perform synchronization.  
	 * 
	 * @since 1.1.0
	 */
	protected synchronized void onSync() {}
	
	/**
	 * <p>Invoke this service to start the default sync animation 
	 * on the action bar.
	 * 
	 * @since 1.1.0
	 */
	protected synchronized void startSyncAnimation() {
		
		if(!isSyncing() && menuItemSync != null) {
			
			syncing.set(true);
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					menuItemSync.setActionView(actionViewSync);
					actionViewSync.startAnimation(rotate);
				}
			});
		}
	}
	
	/**
	 * <p>Invoke this service to stop the default sync animation 
	 * on the action bar.
	 * 
	 * @since 1.1.0
	 */
	protected synchronized void stopSyncAnimation() {

		if(isSyncing() && menuItemSync != null) {
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					actionViewSync.clearAnimation();
					menuItemSync.setActionView(actionViewComplete);
					actionViewComplete.startAnimation(fadeOut);
				}
			});
		}
	}
	
	/**
	 * <p>Specifies whether a sync operation is currently underway. 
	 *
	 * @return {@code true} if there is an ongoing sync
	 * 
	 * @since 1.1.0
	 */
	protected synchronized boolean isSyncing() {
		
		return syncing.get();
	}
}
