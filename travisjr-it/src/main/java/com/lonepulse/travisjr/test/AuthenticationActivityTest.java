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


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;
import com.lonepulse.travisjr.AuthenticationActivity;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.ReposActivity;

/**
 * <p>Unit test for {@link AuthenticationActivity}.
 *
 * @category test
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class AuthenticationActivityTest extends ActivityInstrumentationTestCase2<AuthenticationActivity> {
	
	
	/**
	 * <p>The instance of {@link Solo} used to exercise the unit tests.
	 */
	private Solo solo;
	
	
	public AuthenticationActivityTest() {
		
		super(AuthenticationActivity.class);
	}
	
	@Override
	public void setUp() throws Exception {
		
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	/**
	 * <p>Test case for authenticating users in mode <i>member</i>.
	 * 
	 * @throws Exception
	 * 			if the test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	public final void testMemberAuthentication() throws Exception {
		
		solo.assertCurrentActivity("Not " + AuthenticationActivity.class.getName(), AuthenticationActivity.class);
		
		String user = "sahan";
		
		EditText editTextUsername = (EditText) solo.getView(R.id.username);
		
		solo.clearEditText(editTextUsername);
		solo.enterText(editTextUsername, user);
		solo.getView(R.id.octocat).performClick();
		solo.sleep(2000);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		String username = prefs.getString(solo.getString(R.string.key_username), "");
		String userMode = prefs.getString(solo.getString(R.string.key_user_mode), "");
		
		assertEquals(username, user);
		assertEquals(userMode, solo.getString(R.string.key_member));
		
		solo.assertCurrentActivity("Navigation Failed.", ReposActivity.class);
	}
	
	/**
	 * <p>Test case for authenticating users in mode <i>organization</i>.
	 * 
	 * @throws Exception
	 * 			if the test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	public final void testOrganizationAuthentication() throws Exception {
		
		solo.assertCurrentActivity("Not " + AuthenticationActivity.class.getName(), AuthenticationActivity.class);
		
		String user = "mozilla";
		
		EditText editTextUsername = (EditText) solo.getView(R.id.username);
		
		solo.clearEditText(editTextUsername);
		solo.enterText(editTextUsername, user);
		((CheckBox) solo.getView(R.id.organization)).setChecked(true);
		solo.getView(R.id.octocat).performClick();
		solo.sleep(2000);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		String username = prefs.getString(solo.getString(R.string.key_username), "");
		String userMode = prefs.getString(solo.getString(R.string.key_user_mode), "");
		
		assertEquals(username, user);
		assertEquals(userMode, solo.getString(R.string.key_organization));
		
		solo.assertCurrentActivity("Navigation Failed.", ReposActivity.class);
	}

	@Override
	protected void tearDown() throws Exception {
		
		try {
			
			solo.finishOpenedActivities();
			solo.finalize();
		}
		catch(Throwable t) {
			
			Log.e(getClass().getSimpleName(), "Failed to finalize Robotium.", t);
		}
		
		super.tearDown();
	}
}
