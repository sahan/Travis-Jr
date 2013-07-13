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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lonepulse.icklebot.annotation.event.Click;
import com.lonepulse.icklebot.annotation.event.ItemClick;
import com.lonepulse.icklebot.annotation.inject.InjectApplication;
import com.lonepulse.icklebot.annotation.inject.InjectIckleService;
import com.lonepulse.icklebot.annotation.inject.InjectPojo;
import com.lonepulse.icklebot.annotation.inject.InjectView;
import com.lonepulse.icklebot.annotation.inject.Layout;
import com.lonepulse.icklebot.annotation.inject.Stateful;
import com.lonepulse.icklebot.annotation.inject.Title;
import com.lonepulse.icklebot.annotation.thread.Async;
import com.lonepulse.icklebot.annotation.thread.UI;
import com.lonepulse.icklebot.network.NetworkManager;
import com.lonepulse.travisjr.adapter.BuildAdapter;
import com.lonepulse.travisjr.app.TravisJr;
import com.lonepulse.travisjr.app.TravisJrActivity;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.model.Repo;
import com.lonepulse.travisjr.service.BuildService;
import com.lonepulse.travisjr.util.IntentUtils;
import com.lonepulse.travisjr.util.Resources;

/**
 * <p>Displays all recent {@link Build}s for the selected {@link Repo} 
 * in {@link ReposActivity}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Title(R.string.ttl_act_builds)
@Layout(R.layout.activity_builds)
public class BuildsActivity extends TravisJrActivity {

	
	private static final int ASYNC_FETCH_BUILDS = 0;
	private static final int UI_UPDATE_BUILDS = 0;

	@InjectApplication
	private TravisJr application;
	
	@Layout(R.layout.header_repo)
	private View headerRepo;
	
	@InjectView(android.R.id.list)
	private ListView listView;
	
	@InjectView(R.id.alert_sync)
	private View alertSync;
	
	@InjectView(R.id.alert_data)
	private View alertData;
	
	@InjectPojo
	private BuildService buildService;
	
	@Stateful
	private List<Build> builds;
	
	@Stateful
	private int scrollPosition;
	
	@InjectIckleService
	private NetworkManager network;
	
	/**
	 * <p>The {@link Repo} whose {@link Build}s are being displayed.
	 */
	private Repo repo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		repo = (Repo)getIntent().getSerializableExtra(Resources.key(R.string.key_repo));
		
		((TextView)headerRepo.findViewById(R.id.repo_name))
		.setText(repo.getSlug());
		
		((TextView)headerRepo.findViewById(R.id.description))
		.setText(repo.getDescription());

		listView.addHeaderView(headerRepo);
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		refresh();
		listView.setSelectionFromTop(scrollPosition, 0);
	}
	
	@Override
	protected void onPause() {
		
		super.onPause();
		scrollPosition = listView.getFirstVisiblePosition();
	}
	
	@Override
	protected synchronized void onSync() {
		
		super.onSync();
		runAsyncTask(ASYNC_FETCH_BUILDS);
	}
	
	/**
	 * <p>Updates the activity depending on the availability of data 
	 * and the existence of a connected data network.
	 *
	 * @since 1.1.0
	 */
	private void refresh() {
		
		if(isSyncing() && builds != null) {
			
			runUITask(UI_UPDATE_BUILDS, builds);
			return;
		}
		
		listView.setEmptyView(alertSync);
		alertData.setVisibility(View.GONE);
		
		boolean connected = network.isConnected();
		
		if(builds == null && connected) {
			
			onSync();
		}
		else if(builds == null && !connected) {
			
			listView.setEmptyView(alertData);
		}
		else {
			
			runUITask(UI_UPDATE_BUILDS, builds);
		}
	}
	
	@Async(ASYNC_FETCH_BUILDS)
	private void fetchBuilds() {
		
		builds = buildService.getRecentBuilds(repo.getId());
		runUITask(UI_UPDATE_BUILDS, builds);
	}
	
	@UI(UI_UPDATE_BUILDS)
	private void updateBuilds(List<Build> builds) {

		listView.setAdapter(BuildAdapter.newInstance(BuildsActivity.this, builds));
		stopSyncAnimation();
	}
	
	@Click(R.id.alert_data)
	private void enableData() {
		
		startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
	}
	
	private void viewRepo() {

		if(repo == null) return; //syncing incomplete
		String slug = repo.getSlug();
		
		if(slug.contains("/")) {
			
			String[] slugTokens = slug.split("/");
			IntentUtils.viewRepo(BuildsActivity.this, slugTokens[0], slugTokens[1]);
		}
		else {
			
			IntentUtils.viewRepo(
				BuildsActivity.this, application.getAccountService().getGitHubUsername(), slug);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.builds, menu);
		setMenuItemSync(menu.findItem(R.id.menu_sync));
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
			case R.id.menu_repo: viewRepo(); break;
			
			default: return super.onOptionsItemSelected(item); 
		}
		
		return true;
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
	private void navigateToBuildInfo(final View parent, int position) {
		
		parent.setAlpha(0.60f);
		parent.postDelayed(new Runnable() {
			
			@Override
			public void run() {

				parent.setAlpha(1.00f);
			}
		}, 100);

		String[] slugTokens = repo.getSlug().split("/");
		String ownerName; 
		String repoName; 
		
		if(slugTokens.length > 1) {
			
			ownerName = slugTokens[0];
			repoName = slugTokens[1];
		}
		else {
			
			repoName = slugTokens[0];
			ownerName = application.getAccountService().getGitHubUsername();
		}
		
		Build build = ((Build)listView.getItemAtPosition(position));
		BuildInfoActivity.start(this, ownerName, repoName, build.getId());
	}
	
	/**
	 * <p>Starts {@link ReposActivity} with the action bar and the default 
	 * set of action items.
	 *
	 * @param context
	 * 			the {@link Context} of initiation
	 * 
	 * @param repo
	 * 			the {@link Repo} whose {@link Build}s are to displayed
	 * 
	 * @since 1.1.0
	 */
	public static final void start(Context context, Repo repo) {
		
		Intent intent = new Intent(context, BuildsActivity.class);
		intent.putExtra(Resources.key(R.string.key_repo), repo);
		
		context.startActivity(intent);
	}
}
