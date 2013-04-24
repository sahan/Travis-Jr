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


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;
import com.lonepulse.travisjr.AuthenticationActivity;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.ReposActivity;
import com.lonepulse.travisjr.app.TravisJr;

/**
 * <p>Unit test for {@link ReposActivity} in mode <i>member</i>.
 *
 * @category test
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class ReposActivityMemberTest extends ActivityInstrumentationTestCase2<AuthenticationActivity> {

	
	/**
	 * <p>The GitHub username for which the unit tests are performed.
	 */
	private static final String USER = "sahan";

	/**
	 * <p>The instance of {@link Solo} used to exercise the unit tests.
	 */
	private Solo solo;
	
	
	public ReposActivityMemberTest() {
		
		super(AuthenticationActivity.class);
	}
	
	@Override
	public void setUp() throws Exception {
		
		super.setUp();
		
		solo = new Solo(getInstrumentation(), getActivity());
		solo.assertCurrentActivity("Not " + AuthenticationActivity.class.getName(), AuthenticationActivity.class);
		
		EditText editTextUsername = (EditText) solo.getView(R.id.username);
		
		solo.clearEditText(editTextUsername);
		solo.enterText(editTextUsername, USER);
		solo.getView(R.id.octocat).performClick();
		solo.sleep(2000);
	}
	
	/**
	 * <p>Test case for testing the set up of navigation tabs for a user in mode <i>member</i>. 
	 *
	 * @throws Exception
	 * 			if the test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	public void testNavigationTabs() throws Exception {
		
		solo.assertCurrentActivity("Not " + ReposActivity.class.getName(), ReposActivity.class);
		
		Activity currentActivity = solo.getCurrentActivity();
		assertEquals(2, currentActivity.getActionBar().getTabCount());
	}
	
	/**
	 * <p>Test case for testing repository fetching for a user in mode <i>member</i>. 
	 *
	 * @throws Exception
	 * 			if the test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	public void testSyncing() throws Exception {
	
		solo.assertCurrentActivity("Not " + ReposActivity.class.getName(), ReposActivity.class);
		
		TravisJr travisJr = (TravisJr) solo.getCurrentActivity().getApplication();
		assertTrue(travisJr.isSyncing());
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
