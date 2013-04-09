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


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lonepulse.icklebot.IckleActivity;
import com.lonepulse.icklebot.annotation.event.Click;
import com.lonepulse.icklebot.annotation.inject.InjectAnimation;
import com.lonepulse.icklebot.annotation.inject.InjectPojo;
import com.lonepulse.icklebot.annotation.inject.InjectView;
import com.lonepulse.icklebot.annotation.inject.Layout;
import com.lonepulse.icklebot.annotation.thread.Async;
import com.lonepulse.icklebot.annotation.thread.UI;
import com.lonepulse.travisjr.service.AccountService;
import com.lonepulse.travisjr.service.MissingCredentialsException;
import com.lonepulse.travisjr.util.Resources;

/**
 * <p>Authenticates the user via <b>GitHub</b> and takes him to to the 
 * Home activity.
 * 
 * @version 1.1.0
 * <br><>br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Layout(R.layout.activity_authentication)
public class AuthenticationActivity extends IckleActivity {

	
	private static final int ASYNC_SET_GITHUB_ACCOUNT = 0;
	private static final int UI_UPDATE_USERNAME = 0;

	
	@InjectView(R.id.panelAuthentication)
	private ViewGroup panelAuthentication;
	
	@InjectView(R.id.octocat)
	private ImageView octocat;
	
	@InjectView(R.id.progress)
	private ProgressBar progress;
	
	@InjectView(R.id.username)
	private EditText username;
	
	@InjectAnimation(R.anim.slide_in_from_bottom)
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
	protected void onStart() {
	
		super.onStart();
		
		panelAuthentication.startAnimation(slideInFromBottom);
		octocat.startAnimation(pulsate);
		
		try {
			
			updateUsername(accountService.getGitHubUsername());
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
		
		fadeOut.setFillAfter(true);
		progress.startAnimation(fadeOut);
		
		HomeActivity.start(this);
	}
	
	@Async(ASYNC_SET_GITHUB_ACCOUNT)
	private void setGitHubAccount() {
		
		try {
			
			String username = accountService.queryGitHubAccount();
			runUITask(UI_UPDATE_USERNAME, username);
		} 
		catch (MissingCredentialsException mce) {
		
			Log.w(getClass().getSimpleName(), Resources.error(R.string.err_querying_github_account));
		}
	}
	
	@UI(UI_UPDATE_USERNAME)
	private void updateUsername(String status) {
		
		username.setText(status);
	}
}
