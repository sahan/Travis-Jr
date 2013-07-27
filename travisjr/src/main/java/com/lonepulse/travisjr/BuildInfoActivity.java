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


import static android.text.TextUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lonepulse.icklebot.annotation.inject.InjectArray;
import com.lonepulse.icklebot.annotation.inject.InjectIckleService;
import com.lonepulse.icklebot.annotation.inject.InjectPojo;
import com.lonepulse.icklebot.annotation.inject.InjectString;
import com.lonepulse.icklebot.annotation.inject.InjectView;
import com.lonepulse.icklebot.annotation.inject.Layout;
import com.lonepulse.icklebot.annotation.inject.Stateful;
import com.lonepulse.icklebot.annotation.thread.Async;
import com.lonepulse.icklebot.annotation.thread.UI;
import com.lonepulse.icklebot.bind.BindManager;
import com.lonepulse.travisjr.app.TravisJrActivity;
import com.lonepulse.travisjr.model.BuildInfo;
import com.lonepulse.travisjr.model.BuildJob;
import com.lonepulse.travisjr.service.BuildInfoUnavailableException;
import com.lonepulse.travisjr.service.BuildService;
import com.lonepulse.travisjr.util.BuildState;
import com.lonepulse.travisjr.util.BuildUtils;
import com.lonepulse.travisjr.util.DateUtils;
import com.lonepulse.travisjr.util.IntentUtils;
import com.lonepulse.travisjr.util.Resources;

/**
 * <p>Displays detailed information about a single build.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Layout(R.layout.act_build_info)
public class BuildInfoActivity extends TravisJrActivity {
	
	
	private static final String EXTRA_BUILD_ID = "EXTRA_BUILD_ID";
	private static final String EXTRA_OWNER_NAME = "EXTRA_OWNER_NAME";
	private static final String EXTRA_REPO_NAME = "EXTRA_REPO_NAME";
	
	private static final int ASYNC_FETCH_BUILD_INFO = 0;
	private static final int UI_UPDATE_BUILD_INFO = 0;
	private static final int UI_SYNC = 1;
	private static final int UI_ERROR = 2;
	private static final int UI_CONTENT = 3;
	
	@InjectString(R.string.ttl_act_log)
	private String ttlActLog;
	
	@InjectString(R.string.ttl_act_build_info)
	private String ttlActBuildInfo;
	
	@InjectPojo
	private BuildService buildService;
	
	@InjectIckleService
	private BindManager bindManager;
	
	private String ownerName;
	private String repoName;
	private long buildId;
	
	@InjectView(R.id.alert_sync)
	private View alertSync;
	
	@InjectView(R.id.alert_error)
	private View alertError;
	
	@InjectView(R.id.content)
	private View content;
	
	@Stateful
	private BuildInfo buildInfo;
	
	@Stateful
	NavigableMap<String, StringBuilder> logs;
	
	@InjectView(R.id.root)
	private View root;
	
	@InjectView(R.id.repo_name)
	private TextView slug;
	
	@InjectView(R.id.log)
	private WebView log;
	
	@InjectArray(R.array.logs)
	private String[] logArray;
	
	private Spinner logChooser;
	
	
	@Override
	protected String onInitTitle() {
	
		return (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)?
				ttlActLog :ttlActBuildInfo;
	}
	
	@Override
	protected void onInitActionBar(ActionBar actionBar) {
		
		super.onInitActionBar(actionBar);
		
		View header = getLayoutInflater().inflate(R.layout.action_view_log, null);
		((TextView)header.findViewById(R.id.title)).setText(onInitTitle());
		((TextView)header.findViewById(R.id.subtitle)).setText(onInitSubtitle());

		logChooser = (Spinner)header.findViewById(R.id.action_log);
		logChooser.setVisibility(View.INVISIBLE);

		actionBar.setCustomView(header);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		runUITask(UI_SYNC);
		
		ownerName = getIntent().getStringExtra(EXTRA_OWNER_NAME);
		repoName = getIntent().getStringExtra(EXTRA_REPO_NAME);
		buildId = getIntent().getLongExtra(EXTRA_BUILD_ID, 0);
		
		slug.setText(ownerName + "/" + repoName);
		
		WebSettings settings = log.getSettings();
		
		settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		settings.setUseWideViewPort(true);
		settings.setBuiltInZoomControls(true);
		settings.setDisplayZoomControls(false);
		settings.setRenderPriority(RenderPriority.HIGH);
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
	}
	
	@Override
	protected void onResume() {
	
		super.onResume();
		
		if(buildInfo != null) {
			
			runUITask(UI_UPDATE_BUILD_INFO);
		}
		else {
			
			onSync();
		}
	}
	
	@Override
	protected synchronized void onSync() {
	
		super.onSync();
		runAsyncTask(ASYNC_FETCH_BUILD_INFO);
	}
	
	@Async(ASYNC_FETCH_BUILD_INFO)
	private void fetchBuildInfo() {
		
		try {
			
			buildInfo = buildService.getBuildInfo(ownerName, repoName, buildId);
			Set<Entry<BuildJob, StringBuilder>> logEntries = buildService.getJobLogs(buildInfo).entrySet();

			logs = new TreeMap<String, StringBuilder>();
			
			for (Entry<BuildJob, StringBuilder> entry : logEntries) {
				
				logs.put(String.valueOf(entry.getKey().getNumber()), entry.getValue());
			}
			
			runUITask(UI_UPDATE_BUILD_INFO);
		}
		catch(BuildInfoUnavailableException biue) {
			
			runUITask(UI_ERROR);
			Log.e(getClass().getSimpleName(), "Failed to fetch build info.", biue);
		}
	}
	
	@UI(UI_UPDATE_BUILD_INFO)
	private void updateBuildInfo() {
		
		String startedAt = buildInfo.getStarted_at();
		buildInfo.setStart_time(DateUtils.formatTimeForDisplay(startedAt));
		buildInfo.setStart_date(DateUtils.formatDateForDisplay(startedAt));
		
		bindManager.bind(content, buildInfo);

		List<String> logIds = new ArrayList<String>(logs.keySet());
		Collections.sort(logIds);
		updateLogChooser(logIds);
				
		runUITask(UI_CONTENT);
	}
	
	private void updateLogChooser(final List<String> logIds) {
		
		if(logIds != null) {
			
			ArrayAdapter<String> logAdapter = new ArrayAdapter<String>(
				getActionBar().getThemedContext(), R.layout.view_resource_log, logIds);
					
			logAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			logChooser.setAdapter(logAdapter);
			logChooser.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					
					final StringBuilder html = new StringBuilder()
					.append("<html><body style=\"background-color:black; color:white;") 
					.append(" white-space:nowrap;\"><code>")
					.append(logs.get(logIds.get(position)).toString().replaceAll("(\r\n|\n)", "<br/>"))
					.append("</code></body></html>");
					
					log.loadData(html.toString(), "text/html", "utf-8");
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					
					final StringBuilder html = new StringBuilder()
					.append("<html><body style=\"background-color:black; color:white;") 
					.append(" white-space:nowrap;\"><code>")
					.append(logs.get(logs.firstKey()).toString().replaceAll("(\r\n|\n)", "<br/>"))
					.append("</code></body></html>");
					
					log.loadData(html.toString(), "text/html", "utf-8");
				}
			});
			
			logChooser.setVisibility(View.VISIBLE);
		}
	}
	
	@UI(UI_SYNC)
	private void uiSync() {
	
		alertSync.setVisibility(View.VISIBLE);
		alertError.setVisibility(View.INVISIBLE);
		content.setVisibility(View.INVISIBLE);
	}
	
	@UI(UI_ERROR)
	private void uiError() {
		
		alertError.setVisibility(View.VISIBLE);
		alertSync.setVisibility(View.INVISIBLE);
		content.setVisibility(View.INVISIBLE);
		stopSyncAnimation();
	}
	
	@UI(UI_CONTENT)
	private void uiContent() {
		
		alertSync.setVisibility(View.GONE);
		alertError.setVisibility(View.GONE);
		content.setVisibility(View.VISIBLE);
		stopSyncAnimation();
	}
	
	private void viewCommit() {
		
		if(buildInfo == null) return; //syncing is incomplete
		IntentUtils.viewCommit(this, ownerName, repoName, buildInfo.getCommit());
	}
	
	private void shareBuildInfo() {
		
		if(buildInfo == null) return; //syncing is incomplete
		BuildState buildState = BuildUtils.discoverState(buildInfo);
		
		StringBuilder titleContext = new StringBuilder()
		.append("Travis Jr: ")
		.append(slug.getText().toString())
		.append("/")
		.append(buildInfo.getNumber());
		
		StringBuilder messageContext = new StringBuilder()
		.append("Travis Jr. says that build ")
		.append(buildInfo.getNumber())
		.append(" on ")
		.append(slug.getText().toString());
		
		switch (buildState) {
		
			case ONGOING: messageContext.append(" is ongoing. "); break;
			case FAILED: messageContext.append(" has failed. "); break;
			case ERRORED: messageContext.append(" has errored. "); break;
			case PASSED: messageContext.append(" has passed. "); break;
		}
		
		IntentUtils.send(this, new String[]{buildInfo.getCommitter_email()}, titleContext.toString(), 
						 messageContext.toString(), "Share this build...");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		getMenuInflater().inflate(R.menu.build_info, menu);
		setMenuItemSync(menu.findItem(R.id.menu_sync));
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
			case R.id.menu_commit: viewCommit(); break;
			case R.id.menu_share: shareBuildInfo(); break;
			default: return super.onOptionsItemSelected(item); 
		}
		
		return true;
	}
	
	/**
	 * <p>Starts {@link BuildInfoActivity} themed as a dialog.
	 *
	 * @param context
	 * 			the {@link Context} of initiation
	 * 
	 * @param buildId
	 * 			the ID of the build whose detailed information is to be displayed 
	 * 
	 * @throws IllegalArgumentException
	 * 			if any of the supplied parameters are empty or {@code null}
	 * 
	 * @since 1.1.0
	 */
	public static final void start(Context context, String ownerName, String repoName, long buildId) {

		boolean hasIllegalArguments = false;
		
		StringBuilder errorContext = new StringBuilder("The following required argument(s) must be supplied");
		
		if(context == null) { 
			
			errorContext.append(", context");
			hasIllegalArguments = true;
		}
		
		if(isEmpty(ownerName)) {
			
			errorContext.append(", ownerName");
			hasIllegalArguments = true;
		}
		
		if(isEmpty(repoName)) {
			
			errorContext.append(", repoName");
			hasIllegalArguments = true;
		}
		
		if(buildId == 0) {
			
			errorContext.append(", buildId");
			hasIllegalArguments = true;
		}
		
		if(hasIllegalArguments) {
			
			errorContext.append(".");
			throw new IllegalArgumentException(errorContext.toString());
		}
		
		Intent intent = new Intent(context, BuildInfoActivity.class);
		
		intent.putExtra(EXTRA_OWNER_NAME, ownerName);
		intent.putExtra(EXTRA_REPO_NAME, repoName);
		intent.putExtra(EXTRA_BUILD_ID, buildId);
		
		if(context instanceof Activity) {
			
			intent.putExtra(Resources.key(R.string.key_transient_user), 
				((Activity)context).getIntent().getSerializableExtra(Resources.key(R.string.key_transient_user)));
		}
		
		context.startActivity(intent);
	}
}
