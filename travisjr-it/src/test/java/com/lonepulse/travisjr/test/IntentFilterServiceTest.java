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
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.net.Uri;

import com.lonepulse.travisjr.model.GitHubRepository;
import com.lonepulse.travisjr.model.GitHubUser;
import com.lonepulse.travisjr.service.BasicIntentFilterService;
import com.lonepulse.travisjr.service.IntentFilterService;
import com.lonepulse.travisjr.service.RepositoryAuthenticationFailedException;

/**
 * <p>Unit test for {@link BasicIntentFilterService}.
 * 
 * @category test
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@RunWith(RobolectricTestRunner.class)
public class IntentFilterServiceTest {

	
	/**
	 * <p>The instance of {@link BasicIntentFilterService} on which the 
	 * unit tests are exercised.
	 */
	private IntentFilterService intentFilterService;
	
	/**
	 * <p>A {@link Uri} which points to a GitHub <b>user</b> Travis-CI. 
	 */
	private Uri userUri;
	
	/**
	 * <p>A {@link Uri} which points to a GitHub <b>repo</b> Travis-CI. 
	 */
	private Uri repoUri;

	
	/**
	 * <p>Sets up the test by instantiating {@link #accountService}.
	 * 
	 * @throws Exception
	 * 			if set up terminated with an error
	 */
	@Before
	public final void setUp() throws Exception {

		intentFilterService = new BasicIntentFilterService();
		
		userUri = Uri.parse("https://travis-ci.org/sahan");
		repoUri = Uri.parse("https://travis-ci.org/sahan/IckleBot");
	}
	
	/**
	 * <p>Test for {@link IntentFilterService#resolveUser(Uri)}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testUserResolution() throws Exception {
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		GitHubUser user1 = intentFilterService.resolveUser(userUri);
		assertEquals("sahan", user1.getLogin());
		
		GitHubUser user2 = intentFilterService.resolveUser(repoUri);
		assertEquals("sahan", user2.getLogin());
	}
	
	/**
	 * <p>Test for {@link IntentFilterService#resolveRepository(Uri)}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testRepoResolution() throws Exception {
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		GitHubRepository repo = intentFilterService.resolveRepository(repoUri);
		assertEquals("sahan/IckleBot", repo.getFull_name());
		
		try {
			
			intentFilterService.resolveRepository(userUri);
			fail("Repository resolution did not fail with a user Uri. ");
		}
		catch(RepositoryAuthenticationFailedException rafe) {
			
			assertTrue(true);
		}
	}
}
