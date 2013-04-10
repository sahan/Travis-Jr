package com.lonepulse.travisjr.app;

import java.util.concurrent.atomic.AtomicBoolean;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
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
	 * <p>This animation is invoked on {@link #actionViewSync} 
	 * when synchronization starts.
	 */
	private Animation rotate;
	
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
		rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
		
		actionViewSync.startAnimation(rotate);
		
		View header = getLayoutInflater().inflate(R.layout.header_home, null);
		((TextView)header.findViewById(R.id.title)).setText(R.string.ttl_act_home);
		((TextView)header.findViewById(R.id.subtitle)).setText(new BasicAccountService().getGitHubUsername());
		
		ActionBar actionBar = getActionBar();
		
		actionBar.setIcon(R.drawable.ic_action_bar);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(header);
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
					menuItemSync.setActionView(null);
					
					syncing.set(false);
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
