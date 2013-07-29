package com.lonepulse.travisjr.test;

/*
 * #%L
 * Travis Jr. Integration Tests
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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lonepulse.travisjr.AuthenticationActivity;
import com.lonepulse.travisjr.BuildsActivity;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.model.GitHubUser;
import com.lonepulse.travisjr.service.AccountService;
import com.lonepulse.travisjr.service.BasicAccountService;
import com.lonepulse.travisjr.service.UserMode;
import com.lonepulse.travisjr.util.Resources;

/**
 * <p>Unit test for {@link BasicAccountService}.
 * 
 * @category test
 * <br><br>
 * @version 1.1.1
 * <br><br>
 * @since 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@RunWith(RobolectricTestRunner.class)
public class AccountServiceTest {

	
	/**
	 * <p>The GitHub username for which the unit tests are performed.
	 */
	private static final String USERNAME = "sahan";
	private static final String TRANSIENT_USERNAME = "twitter";
	
	/**
	 * <p>The instance of {@link BasicAccountService} on which the 
	 * unit tests are exercised.
	 */
	private AccountService accountService;

	/**
	 * <p>The base {@link Context} from which the tests are executed.
	 */
	private Activity activity;
	
	
	/**
	 * <p>Sets up the test by instantiating {@link #accountService}.
	 * 
	 * @throws Exception
	 * 			if set up terminated with an error
	 */
	@Before
	public final void setUp() throws Exception {

		activity = Robolectric.buildActivity(AuthenticationActivity.class).get();
		
		GitHubUser user = new GitHubUser();
		user.setId("1111");
		user.setLogin(TRANSIENT_USERNAME);
		user.setType("Organization");
		
		Intent intent = new Intent(activity, BuildsActivity.class);
		intent.putExtra(Resources.key(R.string.key_transient_user), user);
		
		activity.setIntent(intent);
		
		accountService = new BasicAccountService();
		accountService.setGitHubUsername(USERNAME);
		accountService.setUserMode(UserMode.MEMBER);
	}
	
	/**
	 * <p>Test for {@link AccountService#setGitHubUsername(String)}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testAccountCreation() throws Exception {
	
		String savedUsername = prefs().getString(activity.getString(R.string.key_username), "");
		assertEquals(USERNAME, savedUsername);
	}
	
	/**
	 * <p>Test for {@link AccountService#getGitHubUsername()}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testAccountRetrieval() throws Exception {
		
		String savedUsername = accountService.getGitHubUsername();
		assertEquals(USERNAME, savedUsername);
	}
	
	/**
	 * <p>Test for {@link AccountService#getGitHubUsername()}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testAccountAvailability() throws Exception {
		
		assertTrue(accountService.areCredentialsAvailable());
	}
	
	/**
	 * <p>Test for {@link AccountService#getGitHubUsername(Activity)}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testTransientAccountRetrieval() throws Exception {
		
		assertEquals(TRANSIENT_USERNAME, accountService.getGitHubUsername(activity));
	}
	
	/**
	 * <p>Test for {@link AccountService#getUserMode()}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testUserModeRetrieval() throws Exception {
		
		assertEquals(UserMode.MEMBER, accountService.getUserMode());
	}
	
	/**
	 * <p>Test for {@link AccountService#getUserMode(Activity)}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testTransientUserModeRetrieval() throws Exception {
		
		assertEquals(UserMode.ORGANIZATION, accountService.getUserMode(activity));
	}
	
	/**
	 * <p>Retrieves the default {@link SharedPreferences}. 
	 *
	 * @return the default {@link SharedPreferences}
	 * 
	 * @since 1.1.0
	 */
	private SharedPreferences prefs() {
	
		return PreferenceManager.getDefaultSharedPreferences(activity);
	}
}
