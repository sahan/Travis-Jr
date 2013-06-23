package com.lonepulse.travisjr.dialog;

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
import static com.lonepulse.travisjr.util.TextUtils.isAvailable;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.lonepulse.icklebot.IckleActivity;
import com.lonepulse.icklebot.annotation.event.Click;
import com.lonepulse.icklebot.annotation.inject.InjectPojo;
import com.lonepulse.icklebot.annotation.inject.InjectView;
import com.lonepulse.icklebot.annotation.inject.Layout;
import com.lonepulse.icklebot.annotation.inject.Stateful;
import com.lonepulse.icklebot.annotation.thread.Async;
import com.lonepulse.icklebot.annotation.thread.UI;
import com.lonepulse.travisjr.IllegalArgumentException;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.model.BuildInfo;
import com.lonepulse.travisjr.model.BuildJob;
import com.lonepulse.travisjr.service.BuildInfoUnavailableException;
import com.lonepulse.travisjr.service.BuildService;
import com.lonepulse.travisjr.util.DateUtils;
import com.lonepulse.travisjr.util.TextUtils;

/**
 * <p>Displays detailed information about a single build.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Layout(R.layout.build_info)
public class BuildInfoActivity extends IckleActivity {
	
	
	private static final String EXTRA_BUILD_ID = "EXTRA_BUILD_ID";
	private static final String EXTRA_OWNER_NAME = "EXTRA_OWNER_NAME";
	private static final String EXTRA_REPO_NAME = "EXTRA_REPO_NAME";
	
	private static final int ASYNC_FETCH_BUILD_INFO = 0;
	private static final int UI_UPDATE_BUILD_INFO = 0;
	private static final int UI_SYNC = 1;
	private static final int UI_ERROR = 2;
	private static final int UI_CONTENT = 3;
	
	@InjectPojo
	private BuildService buildService;
	
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
	Map<BuildJob, StringBuilder> logs; 
	
	@InjectView(R.id.root)
	private View root;
	
	@InjectView(R.id.repo_name)
	private TextView slug;
	
	@InjectView(R.id.build_number)
	private TextView buildNo;
	
	@InjectView(R.id.start_time)
	private TextView startTime;
	
	@InjectView(R.id.start_date)
	private TextView startDate;
	
	@InjectView(R.id.duration)
	private TextView duration;
	
	@InjectView(R.id.committer_name)
	private TextView committerName;
	
	@InjectView(R.id.committer_email)
	private TextView committerEmail;
	
	@InjectView(R.id.commit_message)
	private TextView commitMessage;
	
	@InjectView(R.id.branch)
	private TextView branch;
	
	@InjectView(R.id.event)
	private TextView event;
	
	@InjectView(R.id.commit)
	private TextView commit;
	
	@InjectView(R.id.log)
	private WebView log;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point dimension = new Point();
		display.getSize(dimension);
		
		LayoutParams layoutParams = new LayoutParams(dimension.x - 40, LayoutParams.MATCH_PARENT);
		root.setLayoutParams(layoutParams);
		
		ownerName = getIntent().getStringExtra(EXTRA_OWNER_NAME);
		repoName = getIntent().getStringExtra(EXTRA_REPO_NAME);
		buildId = getIntent().getLongExtra(EXTRA_BUILD_ID, 0);
		
		slug.setText(ownerName + "/" + repoName);
	}
	
	@Override
	protected void onResume() {
	
		super.onResume();
		
		if(buildInfo != null)
			runUITask(UI_UPDATE_BUILD_INFO);
		else
			runAsyncTask(ASYNC_FETCH_BUILD_INFO);
	}
	
	@Async(ASYNC_FETCH_BUILD_INFO)
	private void fetchBuildInfo() {
		
		try {
			
			runUITask(UI_SYNC);
		
			buildInfo = buildService.getBuildInfo(ownerName, repoName, buildId);
			logs = buildService.getJobLogs(buildInfo);
			
			runUITask(UI_UPDATE_BUILD_INFO);
		}
		catch(BuildInfoUnavailableException biue) {
			
			runUITask(UI_ERROR);
			Log.e(getClass().getSimpleName(), "Failed to fetch build info.", biue);
		}
	}
	
	@UI(UI_UPDATE_BUILD_INFO)
	private void updateBuildInfo() {
		
		buildNo.setText(isAvailable(buildInfo.getNumber()));
		startTime.setText(isAvailable(DateUtils.formatTimeForDisplay(buildInfo.getStarted_at())));
		startDate.setText(isAvailable(DateUtils.formatDateForDisplay(buildInfo.getStarted_at())));
		duration.setText(String.valueOf(TextUtils.isAvailable(buildInfo.getDuration())));
		committerName.setText(isAvailable(buildInfo.getCommitter_name()));
		committerEmail.setText(isAvailable(buildInfo.getCommitter_email()));
		branch.setText(isAvailable(buildInfo.getBranch()));
		event.setText(isAvailable(buildInfo.getEvent_type()));
		commitMessage.setText(isAvailable(buildInfo.getMessage()));
		commit.setText(isAvailable(buildInfo.getCommit()));

		Set<Entry<BuildJob, StringBuilder>> logEntries = logs.entrySet();
		
		for (Entry<BuildJob, StringBuilder> logEntry : logEntries) {
			
			String content = "<html><body><small><code style=\"white-space:pre-line;\">" + 
							  logEntry.getValue().toString() + 
							  "</code></small></body></html>";
			
			log.loadData(content, "text/html", "utf-8");
		}
		
		runUITask(UI_CONTENT);
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
	}
	
	@UI(UI_CONTENT)
	private void uiContent() {
		
		alertSync.setVisibility(View.INVISIBLE);
		alertError.setVisibility(View.INVISIBLE);
		content.setVisibility(View.VISIBLE);
	}
	
	@Click(R.id.commit)
	private void displayCommitDetails() {
		
		String uri = "https://github.com/" + ownerName + "/" + repoName + 
					 "/commit/" + buildInfo.getCommit();
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(uri));
		
		startActivity(Intent.createChooser(intent, "Display Commit"));
	}
	
	@Click(R.id.section_repo)
	private void displayRepo() {
		
		String uri = "https://github.com/" + ownerName + "/" + repoName; 
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(uri));
		
		startActivity(Intent.createChooser(intent, "Display Repository"));
	}
	
	@Click(R.id.section_build)
	private void emailCommitter() {
		
		Intent intent = new Intent(Intent.ACTION_SEND);
		
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{buildInfo.getCommitter_email()});
		intent.putExtra(Intent.EXTRA_SUBJECT, "Build " + buildInfo.getNumber() + " on " + slug.getText().toString());
		intent.putExtra(Intent.EXTRA_TEXT, "Hi " + buildInfo.getCommitter_name().split(" ")[0] + ", ");

		startActivity(Intent.createChooser(intent, "Contact Committer"));
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
		
		context.startActivity(intent);
	}
}
