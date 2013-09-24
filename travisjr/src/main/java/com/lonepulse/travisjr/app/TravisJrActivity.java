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


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lonepulse.icklebot.activity.IckleActivity;
import com.lonepulse.icklebot.network.NetworkManager;
import com.lonepulse.icklebot.network.NetworkService;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.adapter.NavigationAdapter;
import com.lonepulse.travisjr.pref.SettingsActivity;
import com.lonepulse.travisjr.service.AccountService;
import com.lonepulse.travisjr.service.BasicAccountService;
import com.lonepulse.travisjr.view.NavigationSwipeDetector;

/**
 * <p>A custom {@link IckleActivity} which is tailored to setup the {@link ActionBar} and provide support 
 * for syncing with Travis-CI.</p>
 * 
 * @version 1.3.0
 * <br><br>
 * @since 1.1.0
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
	 * <p>The instance of {@link NetworkManager} which will be used 
	 * to detect network state,
	 */
	private NetworkManager network;
	
	/**
	 * <p>The IDs of the strings resources which make up the navigation list. 
	 */
	private int[] navigationResourceIds;
	
	/**
	 * <p>The instance of {@link AccountService} which is used to discover information 
	 * about the current context's user.
	 */
	private AccountService accountService;
	
	
	protected MenuItem getMenuItemSync() {
		return menuItemSync;
	}
	
	protected void setMenuItemSync(MenuItem menuItemSync) {
		this.menuItemSync = menuItemSync;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		accountService = new BasicAccountService();
		network = new NetworkService(this);
		
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
			}
		});
		
		Uri uri = getIntent().getData();
		
		if(uri != null) {
			
			onHandleUri(uri);
		}
		
		onInitActionBar(getActionBar());
	}
	
	/**
	 * <p>Override this callback to initialize the {@link ActionBar} associated with this {@link Activity}.</p>
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
	
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			actionBar.setCustomView(header);
		}
	}
	
	/**
	 * <p>Creates a set of {@link ActionBar} navigation items for the given string resource IDs.</p>
	 * 
	 * <p>This implementation employs {@link ActionBar#NAVIGATION_MODE_LIST}.</p> 
	 *
	 * @param navigationResourceIds
	 * 			the array of String resource IDs for the navigation item titles  
	 * 
	 * @since 1.1.0
	 */
	protected void addNavigationItems(final int... navigationResourceIds) {
		
		ActionBar actionBar = getActionBar();
		
		if(actionBar != null) {
			
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
			
			this.navigationResourceIds = navigationResourceIds;
			String[] stringResources = new String[navigationResourceIds.length];
			
			for (int i = 0; i < navigationResourceIds.length; i++) {
				
				stringResources[i] = getString(navigationResourceIds[i]);
			}
			
			ArrayAdapter<String> adapter = NavigationAdapter.newInstance(actionBar.getThemedContext(), 
				R.layout.action_view_title_repo, android.R.id.text1, android.R.layout.simple_spinner_dropdown_item, 
				accountService.getGitHubUsername(this), stringResources);
			
		    actionBar.setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {
				
				@Override
				public boolean onNavigationItemSelected(int itemPosition, long itemId) {
					
					onTabSelected(navigationResourceIds[itemPosition]);
					return true;
				}
			});
		}
	}
	
	/**
	 * <p>Enables lateral swiping for {@link ActionBar} navigation tabs by detecting swipe gestures on the 
	 * given target {@link View}.</p>
	 *
	 * @param targetViewId
	 * 			the compulsory target {@link View} which is to be swiped 
	 * 
	 * @param moreTargetViewIds
	 * 			additional IDs of the {@link View}s which can be swiped 
	 * 
	 * @since 1.1.0
	 */
	protected void enableNavigationSwiping(int targetViewId, int... moreTargetViewIds) {
		
		NavigationSwipeDetector tabSwipeListener = new NavigationSwipeDetector(this);
		
		View main = findViewById(targetViewId);
		if(main != null) main.setOnTouchListener(tabSwipeListener);
	
		for (int id : moreTargetViewIds) {

			View view = findViewById(id);
			
			if(view != null) 
				view.setOnTouchListener(tabSwipeListener);
		}
	}
	
	/**
	 * <p>Override this callback to take action for a {@link Intent} which was fired in response to a <i>VIEW</i> 
	 * action on a URL from the host <i>travis-ci.org</i></p>
	 * 
	 * <p><b>Note</b> that this callback will be only be invoked if a {@link Uri} is present in the {@link Intent} 
	 * which started this {@link Activity} (see {@link Activity#getIntent()}) and <b>before</b> the {@link ActionBar} 
	 * is initialized using {@link #onInitActionBar(ActionBar)}.</p> 
	 *
	 * @param uri
	 * 			the {@link Uri} which was received in the {@link Intent} which started this {@link Activity} 
	 * 
	 * @since 1.1.0
	 */
	protected void onHandleUri(Uri uri) {}
	
	/**
	 * <p>Override this callback to take action upon <b>selecting</b> an {@link ActionBar.Tab} tab created with 
	 * the String resource ID.</p>
	 *
	 * @param stringResourceId
	 * 			the String resource ID which was used to created the 
	 * 			selected {@link ActionBar.Tab}
	 * 
	 * @since 1.1.0
	 */
	protected void onTabSelected(int stringResourceId) {}
	
	/**
	 * <p>Override this callback to take action upon <b>unselecting</b> an {@link ActionBar.Tab} tab created with 
	 * the String resource ID.</p> 
	 *
	 * @param stringResourceId
	 * 			the String resource ID which was used to created the unselected {@link ActionBar.Tab}
	 * 
	 * @since 1.1.0
	 */
	protected void onTabUnselected(int stringResourceId) {}
	
	/**
	 * <p>Override this callback to take action upon <b>reselecting</b> an {@link ActionBar.Tab} tab created with 
	 * the String resource ID.</p>
	 *
	 * @param stringResourceId
	 * 			the String resource ID which was used to created the reselected {@link ActionBar.Tab}
	 * 
	 * @since 1.1.0
	 */
	protected void onTabReselected(int stringResourceId) {}
	
	/**
	 * <p>Retrieves the String resource ID of the currently selected navigation tab.</p>
	 *
	 * @return the String resource ID of the selected {@link ActionBar.Tab}, else {@code 0} if no navigation tabs 
	 * 		   are available
	 * 
	 * @since 1.1.0
	 */
	protected int getSelectedTab() {
		
		ActionBar actionBar = getActionBar();
		return (actionBar != null && actionBar.getNavigationItemCount() > 0)? 
				navigationResourceIds[(Integer)actionBar.getSelectedNavigationIndex()] :0;
	}
	
	/**
	 * <p>Override this callback to initialize the activity with a custom title.</p>
	 *
	 * @return the title to be set for this activity; defaults to {@link Activity#getTitle()}
	 * 
	 * @since 1.1.0
	 */
	protected String onInitTitle() {
		
		return getTitle().toString();
	}
	
	/**
	 * <p>Override this callback to initialize the activity with a custom subtitle.</p> 
	 *
	 * @return the subtitle to be set for this activity; defaults to the user's GitHub username
	 * 
	 * @since 1.1.0
	 */
	protected String onInitSubtitle() {
		
		return accountService.getGitHubUsername(this);
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
				
				if(network.isConnected()) {
					
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
			
			case R.id.menu_settings: {
				
				SettingsActivity.start(this);
				break;
			}
		}
		
		return true;
	}
	
	/**
	 * <p>Override this callback to perform synchronization.</p>
	 * 
	 * @since 1.1.0
	 */
	protected synchronized void onSync() {
		
		startSyncAnimation();
	}
	
	/**
	 * <p>Invoke this service to set the sync lock and start the default sync animation on the action bar.</p>
	 * 
	 * @since 1.1.0
	 */
	protected synchronized void startSyncAnimation() {
		
		if(!isSyncing()) {
			
			getTravisJrApplication().setSyncing(true);
			
			if(menuItemSync != null) {
			
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						menuItemSync.setActionView(actionViewSync);
						actionViewSync.startAnimation(rotate);
					}
				});
			}
		}
	}
	
	/**
	 * <p>Invoke this service to clear the sync lock and stop the default sync animation on the action bar.</p>
	 * 
	 * @since 1.1.0
	 */
	protected synchronized void stopSyncAnimation() {

		if(isSyncing()) {
			
			getTravisJrApplication().setSyncing(false);
			
			if(menuItemSync != null) {
				
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
	}
	
	/**
	 * <p>Specifies whether a sync operation is currently underway.</p>
	 *
	 * @return {@code true} if there is an ongoing sync
	 * 
	 * @since 1.1.0
	 */
	protected final boolean isSyncing() {
		
		return getTravisJrApplication().isSyncing();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		
		ActionBar actionBar = getActionBar();
		
		if(actionBar != null && actionBar.getTabCount() > 1)
			outState.putInt(getString(R.string.key_tab), actionBar.getSelectedTab().getPosition());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		
		super.onRestoreInstanceState(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		
		if(actionBar != null && actionBar.getTabCount() > 1)
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt(getString(R.string.key_tab)));
	}
	
	/**
	 * <p>Returns the instance of {@link TravisJr} contract implementation.</p>
	 *
	 * @return the single instance of {@link TravisJr}
	 * 
	 * @since 1.1.0
	 */
	protected final TravisJr getTravisJrApplication() {
		
		return ((TravisJr)getApplication());
	}
}
