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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lonepulse.icklebot.annotation.event.Click;
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
import com.lonepulse.travisjr.service.AccountService;
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
@Title(R.string.ttl_act_home)
@Layout(R.layout.activity_home)
public class HomeActivity extends TravisJrActivity {

	
	private static final int ASYNC_FETCH_REPOS = 0;
	private static final int UI_UPDATE_REPOS = 0;

	@InjectView(android.R.id.list)
	private ListView listView;
	
	@InjectView(R.id.alert_sync)
	private View alertSync;
	
	@InjectView(R.id.alert_data)
	private View alertData;
	
	@InjectView(R.id.alert_repos)
	private View alertRepos;
	
	@InjectPojo
	private RepoService repoService;
	
	@InjectPojo
	private AccountService accountService;
	
	@Stateful
	private List<Repo> repos;


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	
		super.onPostCreate(savedInstanceState);
		refresh();
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		if(!isSyncing() && repos == null) refresh();
	}
	
	@Override
	protected synchronized void onSync() {
	
		runAsyncTask(ASYNC_FETCH_REPOS);
	}
	
	/**
	 * <p>Updates the activity depending on the availability of data 
	 * and the existence of a connected data network.
	 *
	 * @since 1.1.0
	 */
	private void refresh() {
		
		boolean connected = network().isConnected();
		
		listView.setEmptyView(alertSync);
		alertData.setVisibility(View.GONE);
		
		if(repos == null && connected)
			onSync();
		
		else if(repos == null && !connected)
			listView.setEmptyView(alertData);
		
		else
			runUITask(UI_UPDATE_REPOS, repos);
	}
	
	@Async(ASYNC_FETCH_REPOS)
	private void fetchRepos() {
		
		startSyncAnimation();
		
		repos = repoService.getReposByMember();
		runUITask(UI_UPDATE_REPOS, repos);
	}
	
	@UI(UI_UPDATE_REPOS)
	private void updateRepos(List<Repo> repos) {

		if(repos.isEmpty()) {
			
			listView.setEmptyView(alertRepos);
			alertRepos.setVisibility(View.VISIBLE);
		}
		else {
			
			listView.setEmptyView(alertSync);
			alertRepos.setVisibility(View.GONE);
		}
		
		listView.setAdapter(RepoAdapter.newInstance(HomeActivity.this, repos));
		stopSyncAnimation();
	}
	
	@Click(R.id.alert_data)
	private void enableData() {
		
		startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
	}
	
	@Click(R.id.alert_repos)
	private void retryFetchRepos() {
	
		accountService.setGitHubUsername("");
		AuthenticationActivity.start(this);
		finish();
	}
	
	/**
	 * <p>Starts {@link HomeActivity} with the action bar and the default 
	 * set of action items.
	 *
	 * @param context
	 * 			the {@link Context} of initiation
	 * 
	 * @since 1.1.0
	 */
	public static final void start(Context context) {
		
		context.startActivity(new Intent(context, HomeActivity.class));
	}
}