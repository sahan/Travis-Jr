package com.lonepulse.travisjr;

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


import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lonepulse.icklebot.annotation.event.Click;
import com.lonepulse.icklebot.annotation.event.ItemClick;
import com.lonepulse.icklebot.annotation.inject.InjectPojo;
import com.lonepulse.icklebot.annotation.inject.InjectView;
import com.lonepulse.icklebot.annotation.inject.Layout;
import com.lonepulse.icklebot.annotation.inject.Stateful;
import com.lonepulse.icklebot.annotation.inject.Title;
import com.lonepulse.icklebot.annotation.thread.Async;
import com.lonepulse.icklebot.annotation.thread.UI;
import com.lonepulse.travisjr.adapter.RepoAdapter;
import com.lonepulse.travisjr.app.TravisJrActivity;
import com.lonepulse.travisjr.model.Repo;
import com.lonepulse.travisjr.service.RepoAccessException;
import com.lonepulse.travisjr.service.RepoService;

/**
 * <p>Provides a statistical overview of the repositories under continuous 
 * integration for the authenticated user.</p>
 * 
 * <p>This activity may be started with its defaults by using any of the static 
 * methods prefixed with <i>start</i>.</p>
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Title(R.string.ttl_act_repos)
@Layout(R.layout.activity_repos)
public class ReposActivity extends TravisJrActivity {

	
	private static final int ASYNC_FETCH_REPOS = 0;
	private static final int UI_UPDATE_REPOS = 0;
	private static final int UI_ALERT_ERROR = 1;

	@InjectView(android.R.id.list)
	private ListView listView;
	
	@InjectView(R.id.alert_sync)
	private View alertSync;
	
	@InjectView(R.id.alert_data)
	private View alertData;
	
	@InjectView(R.id.alert_repos_empty)
	private View alertReposEmpty;
	
	@InjectView(R.id.alert_repos_error)
	private View alertReposError;
	
	@InjectPojo
	private RepoService repoService;
	
	@Stateful
	private List<Repo> repos;
	
	
	@Override
	protected void onInitActionBar(ActionBar actionBar) {
		
		super.onInitActionBar(actionBar);
		addTabs(R.string.key_member, R.string.key_owner);
	}
	
	@Override
	protected void onTabSelected(int stringResourceId) {
	
		if(repos != null) filterRepos(repos);
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		refresh();
	}
	
	@Override
	protected synchronized void onSync() {
	
		runAsyncTask(ASYNC_FETCH_REPOS);
	}
	
	/**
	 * <p>Updates the activity depending on the availability of data and the 
	 * existence of a connected data network.
	 */
	@Click(R.id.alert_repos_error)
	private void refresh() {
		
		if(isSyncing() && repos != null) {
			
			filterRepos(repos);
			return;
		}
		
		listView.setEmptyView(alertSync);
		alertData.setVisibility(View.GONE);
		alertReposError.setVisibility(View.GONE);
		
		boolean connected = network().isConnected();
		
		if(repos == null && connected) {
			
			onSync();
		}
		else if(repos == null && !connected) {
			
			listView.setEmptyView(alertData);
		}
		else {
			
			filterRepos(repos);
		}
	}
	
	/**
	 * <p>Retrieves a list of repositories which the user is a member of and 
	 * updates the display.
	 */
	@Async(ASYNC_FETCH_REPOS)
	private void fetchRepos() {
		
		startSyncAnimation();
		
		try {
			
			repos = repoService.getReposByMember();
			filterRepos(repos);
		}
		catch(RepoAccessException rae) {
		
			runUITask(UI_ALERT_ERROR);
			Log.e(getClass().getSimpleName(), rae.getMessage(), rae);
		}
	}
	
	/**
	 * <p>Alerts the user of an <b>unrecoverable</b> error which has occurred 
	 * while retrieving the list of repositories.
	 */
	@UI(UI_ALERT_ERROR)
	private void alertError() {
		
		listView.setEmptyView(alertReposError);
		alertReposError.setVisibility(View.VISIBLE);
		
		listView.setAdapter(new ArrayAdapter<Void>(this, 0));
		stopSyncAnimation();
	}
	
	/**
	 * <p>Updates the given list of {@link Repo}s on the displayed list.
	 *
	 * @param repos
	 * 			the {@link Repo}s which are to be updated
	 */
	@UI(UI_UPDATE_REPOS)
	private void updateRepos(List<Repo> repos) {

		if(repos.isEmpty()) {
			
			listView.setEmptyView(alertReposEmpty);
			alertReposEmpty.setVisibility(View.VISIBLE);
		}
		else {
			
			listView.setEmptyView(alertSync);
			alertReposEmpty.setVisibility(View.GONE);
		}
		
		listView.setAdapter(RepoAdapter.newInstance(ReposActivity.this, repos));
		stopSyncAnimation();
	}
	
	/**
	 * <p>Filters the displayed {@link Repo}s based on selected navigation 
	 * tab and invokes {@link #updateRepos(List)} for display.
	 * 
	 * @param repos
	 * 			the displayed {@link Repo}s to be filtered
	 */
	private synchronized void filterRepos(List<Repo> repos) {
		
		switch (getSelectedTab()) {
		
			case R.string.key_member:
				runUITask(UI_UPDATE_REPOS, repos);
				break;
					
			case R.string.key_owner:
				runUITask(UI_UPDATE_REPOS, repoService.filterOwnedRepos(repos));
				break;
		}
	}
	
	/**
	 * <p>Navigates to the <b>device settings</b> screen to allow the user to 
	 * switch-on a data connection.  
	 */
	@Click(R.id.alert_data)
	private void enableData() {
		
		startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
	}
	
	/**
	 * <p>Purges the user account and reverts to the authentication screen so that 
	 * the user may retry repository retrieval with an alternate GitHub username. 
	 */
	@Click(R.id.alert_repos_empty)
	private void retryFetchRepos() {
		
		getTravisJrApplication().purgeAccount(this);
	}
	
	/**
	 * <p>Handles the item click event for the repository list.
	 *
	 * @param parent
	 * 			the view within the list item which was clicked
	 * 
	 * @param position
	 * 			the position of the list item which was clicked
	 */
	@ItemClick(android.R.id.list)
	private void navigateToBuilds(final View parent, int position) {
		
		parent.setAlpha(0.60f);
		parent.postDelayed(new Runnable() {
			
			@Override
			public void run() {

				parent.setAlpha(1.00f);
			}
		}, 100);
		
		BuildsActivity.start(this, repos.get(position));
	}
	
	/**
	 * <p>Starts {@link ReposActivity} with the action bar and the default 
	 * set of action items.
	 *
	 * @param context
	 * 			the {@link Context} of initiation
	 * 
	 * @since 1.1.0
	 */
	public static final void start(Context context) {
		
		context.startActivity(new Intent(context, ReposActivity.class));
	}
}