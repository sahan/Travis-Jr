package com.lonepulse.travisjr.service;

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


import java.util.concurrent.locks.ReentrantLock;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.lonepulse.travisjr.AuthenticationActivity;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.app.TravisJr;
import com.lonepulse.travisjr.app.TravisJr.Application;
import com.lonepulse.travisjr.model.GitHubUser;
import com.lonepulse.travisjr.util.Res;

/**
 * <p>A basic implementation of {@link AccountService}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class BasicAccountService implements AccountService {

	
	private static final String GITHUB_TOKEN = "com.github";
	
	/**
	 * <p>An {@link Application} level {@link ReentrantLock} to manage global race conditions.
	 */
	private static final ReentrantLock PURGE_LOCK = new ReentrantLock();

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getGitHubUsername() throws MissingCredentialsException {
	
		String username = prefs().getString(Res.string(R.string.key_username), "");
		
		if(TextUtils.isEmpty(username)) {
			
			throw new MissingCredentialsException();
		}
		
		return username;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getGitHubUsername(Activity activity) throws MissingCredentialsException {
		
		GitHubUser user = (GitHubUser) activity.getIntent()
			.getSerializableExtra(Res.string(R.string.key_transient_user));
			
		return user == null? getGitHubUsername() :user.getLogin();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGitHubUsername(String username) {
		
		SharedPreferences.Editor editor = prefs().edit();
		editor.putString(Res.string(R.string.key_username), username);
		editor.commit();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserMode getUserMode() {
		
		return UserMode.getCurrent();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserMode getUserMode(Activity activity) {
		
		try {
			
			GitHubUser user = (GitHubUser) activity.getIntent()
					.getSerializableExtra(Res.string(R.string.key_transient_user));
			
			if(user == null) {
				
				return getUserMode();
			}
			else {
				
				return user.getType().equalsIgnoreCase(
					UserMode.ORGANIZATION.getKey())? UserMode.ORGANIZATION :UserMode.MEMBER;
			}
		}
		catch(Exception e) {
			
			StringBuilder errorContext = new StringBuilder("Failed to resolve a user mode ")
			.append("from the current user in context. Defaulting to UserMode.ORGANIZATION. ");
			
			Log.e(getClass().getSimpleName(), errorContext.toString(), e);
			
			return UserMode.ORGANIZATION;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUserMode(UserMode userMode) {
		
		UserMode.setCurrent(userMode);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String queryGitHubAccount() throws MissingCredentialsException {
		
		AccountManager accountManager = AccountManager.get(TravisJr.Application.getContext());
		
		Account[] accounts = accountManager.getAccountsByType(GITHUB_TOKEN);
		
		if(accounts == null || accounts.length == 0)
			throw new MissingCredentialsException("GitHub account is missing.");
		
		String username = accounts[0].name;
		
		if(TextUtils.isEmpty(username))
			throw new MissingCredentialsException("GitHub username is missing.");
		
		return username;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean areCredentialsAvailable() {
	
		try {
			
			return (!TextUtils.isEmpty(getGitHubUsername()))? true :false;
		}
		catch(MissingCredentialsException mce) {
			
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void purgeAccount(final Context context) {
		
		if(PURGE_LOCK.tryLock()) {
		
			StringBuilder builder = new StringBuilder()
			.append(context instanceof Activity? getGitHubUsername((Activity)context) :"User ")
			.append(", ")
			.append(context.getString(R.string.conf_clear_account));
			
			new AlertDialog.Builder(context)
			.setTitle(context.getString(R.string.ttl_dialog_account))
			.setMessage(builder.toString())
			.setPositiveButton(context.getString(R.string.lbl_yes_uc), new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					setGitHubUsername("");
					AuthenticationActivity.start(context);
					
					PURGE_LOCK.unlock();
				}
			})
			.setNegativeButton(context.getString(R.string.lbl_no_uc), new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					PURGE_LOCK.unlock();
				}
			})
			.show();
		}
	}
	
	private static final SharedPreferences prefs() {

		return PreferenceManager.getDefaultSharedPreferences(TravisJr.Application.getContext());
	}
}
