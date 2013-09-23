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


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lonepulse.icklebot.activity.IckleActivity;
import com.lonepulse.icklebot.annotation.event.Click;
import com.lonepulse.icklebot.annotation.inject.InjectAnimation;
import com.lonepulse.icklebot.annotation.inject.InjectPojo;
import com.lonepulse.icklebot.annotation.inject.InjectView;
import com.lonepulse.icklebot.annotation.inject.Layout;
import com.lonepulse.icklebot.annotation.thread.Async;
import com.lonepulse.icklebot.annotation.thread.UI;
import com.lonepulse.travisjr.service.AccountService;
import com.lonepulse.travisjr.service.MissingCredentialsException;
import com.lonepulse.travisjr.util.Res;

/**
 * <p>Authenticates the user via <b>GitHub</b> and takes him to to the 
 * Home activity.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Layout(R.layout.activity_authentication)
public class AuthenticationActivity extends IckleActivity {

	
	private static final int ASYNC_SET_GITHUB_ACCOUNT = 0;
	private static final int UI_UPDATE_USERNAME = 0;

	
	@InjectView(R.id.panel_authentication)
	private ViewGroup panelAuthentication;
	
	@InjectView(R.id.octocat)
	private ImageView octocat;
	
	@InjectView(R.id.progress)
	private ProgressBar progress;
	
	@InjectView(R.id.username)
	private EditText username;
	
	@InjectView(R.id.organization)
	private CheckBox organization;
	
	@InjectAnimation(R.anim.slide_in_bottom)
	private Animation slideInFromBottom;
	
	@InjectAnimation(R.anim.pulsate)
	private Animation pulsate;
	
	@InjectAnimation(R.anim.shake_sideways_once)
	private Animation shakeSidewaysOnce;
	
	@InjectAnimation(android.R.anim.fade_in)
	private Animation fadeIn;
	
	@InjectAnimation(android.R.anim.fade_out)
	private Animation fadeOut;
	
	@InjectPojo
	private AccountService accountService;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
	
		super.onStart();
		
		panelAuthentication.startAnimation(slideInFromBottom);
		octocat.startAnimation(pulsate);
		
		try {
			
			updateUsername(accountService.getGitHubUsername(this));
			ReposActivity.start(this);
		}
		catch(MissingCredentialsException mce) {
			
			runAsyncTask(ASYNC_SET_GITHUB_ACCOUNT);
		}
	}
	
	@Click(R.id.octocat)
	private void authenticate() {
	
		if(TextUtils.isEmpty(username.getText().toString())) {
			
			username.startAnimation(shakeSidewaysOnce);
			return;
		}
		
		octocat.clearAnimation();
		octocat.startAnimation(fadeIn);
		
		progress.setVisibility(View.VISIBLE);
		progress.startAnimation(fadeIn);
		
		accountService.setGitHubUsername(username.getText().toString());
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		
		if(organization.isChecked())
			editor.putString(getString(R.string.key_user_mode), getString(R.string.key_organization));
		
		else
			editor.putString(getString(R.string.key_user_mode), getString(R.string.key_member));
		
		editor.commit();
		
		fadeOut.setFillAfter(true);
		progress.startAnimation(fadeOut);
		
		ReposActivity.start(this);
	}
	
	@Async(ASYNC_SET_GITHUB_ACCOUNT)
	private void setGitHubAccount() {
		
		try {
			
			String username = accountService.queryGitHubAccount();
			runUITask(UI_UPDATE_USERNAME, username);
		} 
		catch (MissingCredentialsException mce) {
		
			Log.w(getClass().getSimpleName(), Res.string(R.string.err_querying_github_account));
		}
	}
	
	@UI(UI_UPDATE_USERNAME)
	private void updateUsername(String status) {
		
		username.setText(status);
	}
	
	/**
	 * <p>Starts {@link AuthenticationActivity} with with a hidden action bar and 
	 * a transition which slides the activity from below.
	 *
	 * @param context
	 * 			the {@link Context} of initiation
	 * 
	 * @since 1.1.0
	 */
	public static final void start(Context context) {
		
		Intent intent = new Intent(context, AuthenticationActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
		
		context.startActivity(intent);
		
		if(context instanceof Activity)
			((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
}
