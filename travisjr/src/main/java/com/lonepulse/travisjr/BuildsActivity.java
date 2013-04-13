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
import android.view.View;
import android.widget.ListView;

import com.lonepulse.icklebot.annotation.event.Click;
import com.lonepulse.icklebot.annotation.inject.InjectPojo;
import com.lonepulse.icklebot.annotation.inject.InjectView;
import com.lonepulse.icklebot.annotation.inject.Layout;
import com.lonepulse.icklebot.annotation.inject.Stateful;
import com.lonepulse.icklebot.annotation.thread.Async;
import com.lonepulse.icklebot.annotation.thread.UI;
import com.lonepulse.travisjr.adapter.BuildAdapter;
import com.lonepulse.travisjr.app.TravisJrActivity;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.model.Repo;
import com.lonepulse.travisjr.service.BuildService;
import com.lonepulse.travisjr.util.Resources;

/**
 * <p>Displays all recent {@link Build}s for the selected {@link Repo} 
 * in {@link ReposActivity}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Layout(R.layout.activity_builds)
public class BuildsActivity extends TravisJrActivity {

	
	private static final int ASYNC_FETCH_BUILDS = 0;
	private static final int UI_UPDATE_BUILDS = 0;

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
	
	/**
	 * <p>The {@link Repo} whose {@link Build}s are being displayed.
	 */
	private Repo repo;
	
	
	@Override
	protected String onInitTitle() {
	
		return getString(R.string.ttl_act_builds);
	}
	
	@Override
	protected String onInitSubtitle() {
		
		repo = (Repo)getIntent().getSerializableExtra(Resources.key(R.string.key_repo));
		return repo.getSlug();
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		refresh();
	}
	
	@Override
	protected synchronized void onSync() {
	
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
		
		boolean connected = network().isConnected();
		
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
	private void fetchRepos() {
		
		startSyncAnimation();
		
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
