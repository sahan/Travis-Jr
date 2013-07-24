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


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.app.TravisJr;
import com.lonepulse.travisjr.util.Resources;

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
	 * {@inheritDoc}
	 */
	@Override
	public String getGitHubUsername() throws MissingCredentialsException {
	
		String username = prefs().getString(Resources.key(R.string.key_username), "");
		
		if(TextUtils.isEmpty(username))
			throw new MissingCredentialsException();
		
		return username;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGitHubUsername(String username) {
		
		SharedPreferences.Editor editor = prefs().edit();
		editor.putString(Resources.key(R.string.key_username), username);
		editor.commit();
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
	public void setUserMode(UserMode userMode) {
	
		UserMode.setCurrent(userMode);
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
	public boolean isTypeOrganization() {
		
		return UserMode.isCurrent(UserMode.ORGANIZATION);
	}
	
	private static final SharedPreferences prefs() {

		return PreferenceManager.getDefaultSharedPreferences(TravisJr.Application.getContext());
	}
}
