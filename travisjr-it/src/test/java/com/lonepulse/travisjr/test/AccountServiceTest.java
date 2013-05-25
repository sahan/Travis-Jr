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

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lonepulse.travisjr.AuthenticationActivity;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.service.AccountService;
import com.lonepulse.travisjr.service.BasicAccountService;
import com.lonepulse.travisjr.test.app.TravisJr;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * <p>Unit test for {@link BasicAccountService}.
 * 
 * @category test
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@RunWith(RobolectricTestRunner.class)
public class AccountServiceTest {

	
	/**
	 * <p>The GitHub username for which the unit tests are performed.
	 */
	private static final String USERNAME = "sahan";
	
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

		TravisJr.Application.context = Robolectric.application;
		activity = new AuthenticationActivity();
		
		accountService = new BasicAccountService();
		accountService.setGitHubUsername(USERNAME);
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
