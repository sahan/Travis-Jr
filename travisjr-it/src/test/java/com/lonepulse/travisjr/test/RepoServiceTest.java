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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.content.Intent;

import com.lonepulse.travisjr.BuildsActivity;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.ReposActivity;
import com.lonepulse.travisjr.model.GitHubUser;
import com.lonepulse.travisjr.model.Repo;
import com.lonepulse.travisjr.service.AccountService;
import com.lonepulse.travisjr.service.BasicAccountService;
import com.lonepulse.travisjr.service.BasicRepoService;
import com.lonepulse.travisjr.service.RepoService;
import com.lonepulse.travisjr.util.Resources;

/**
 * <p>Unit test for {@link BasicRepoService}.
 * 
 * @category test
 * <br><br>
 * @version 1.2.0
 * <br><br>
 * @since 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@RunWith(RobolectricTestRunner.class)
public class RepoServiceTest {

	
	/**
	 * <p>The instance of {@link BasicRepoService} on which the unit tests are exercised.</p>
	 */
	private RepoService repoService;
	
	/**
	 * <p>A single thread executor which performs network calls off the main thread.
	 */
	private ExecutorService executorService;
	
	
	/**
	 * <p>Sets up the test by instantiating {@link #repoService}.
	 * 
	 * @throws Exception
	 * 			if set up terminated with an error
	 */
	@Before
	public final void setUp() throws Exception {

		repoService = new BasicRepoService();
		executorService = Executors.newSingleThreadExecutor();
	}
	
	/**
	 * <p>Test for {@link RepoService#getReposByMember()}.</p>
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testMemberRepoSync() throws Exception {
	
		String username = "sahan";
		
		AccountService accountService = new BasicAccountService();
		accountService.setGitHubUsername(username);
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		Future<List<Repo>> future = executorService.submit(new Callable<List<Repo>>() {

			public List<Repo> call() throws Exception {
				
				return repoService.getReposByMember();
			}
		});
		
		List<Repo> repos = future.get();
		
		assertNotNull(repos);
		assertTrue(repos.size() > 0);
	}
	
	/**
	 * <p>Test for {@link RepoService#getReposByOwner()}.</p>
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testOrganizationRepoSync() throws Exception {
		
		String username = "mozilla";
		
		AccountService accountService = new BasicAccountService();
		accountService.setGitHubUsername(username);
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		Future<List<Repo>> future = executorService.submit(new Callable<List<Repo>>() {
			
			public List<Repo> call() throws Exception {
				
				return repoService.getReposByOwner();
			}
		});
		
		List<Repo> repos = future.get();
		
		assertNotNull(repos);
		assertTrue(repos.size() > 0);
	}
	
	/**
	 * <p>Test for {@link RepoService#filterCreatedRepos(List)}.</p>
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testCreatedRepoFiltering() throws Exception {
	
		String username = "kevinsawicki";
		
		AccountService accountService = new BasicAccountService();
		accountService.setGitHubUsername(username);
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		Future<List<Repo>> future = executorService.submit(new Callable<List<Repo>>() {

			public List<Repo> call() throws Exception {
				
				return repoService.getReposByMember();
			}
		});
		
		List<Repo> allRepos = future.get();
		assertTrue(allRepos.size() > 0);
		
		List<Repo> createdRepos = repoService.filterCreatedRepos(username, allRepos);
		assertTrue(createdRepos.size() > 0);
		assertTrue(allRepos.containsAll(createdRepos));
		
		for (Repo repo : createdRepos) {
			
			assertFalse(repo.getSlug().contains("/"));
		}
	}
	
	/**
	 * <p>Test for {@link RepoService#filterContributedRepos(List)}.</p>
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testContributedRepoFiltering() throws Exception {
		
		String username = "kevinsawicki";
		
		AccountService accountService = new BasicAccountService();
		accountService.setGitHubUsername(username);
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		Future<List<Repo>> future = executorService.submit(new Callable<List<Repo>>() {
			
			public List<Repo> call() throws Exception {
				
				return repoService.getReposByMember();
			}
		});
		
		List<Repo> allRepos = future.get();
		assertTrue(allRepos.size() > 0);
		
		List<Repo> contributedRepos = repoService.filterContributedRepos(username, allRepos);
		assertTrue(contributedRepos.size() > 0);
		assertTrue(allRepos.containsAll(contributedRepos));
		
		for (Repo repo : contributedRepos) {
			
			assertFalse(repo.getSlug().startsWith(username));
		}
	}
	
	/**
	 * <p>Test for {@link RepoService#findRepoByName(String, List)}.</p>
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testFindingRepoById() throws Exception {
		
		final String username = "sahan";
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		Future<List<Repo>> future = executorService.submit(new Callable<List<Repo>>() {
			
			public List<Repo> call() throws Exception {
				
				return repoService.getReposByMember(username);
			}
		});
		
		List<Repo> allRepos = future.get();
		assertTrue(allRepos.size() > 0);
		
		Repo repo = repoService.findRepoByName("ickle", allRepos);
		assertTrue(repo.getSlug().equals("sahan/IckleBot"));
	}
	
	/**
	 * <p>Test for {@link RepoService#getReposByMember(String)}.</p>
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testMemberRepoSyncWithUsername() throws Exception {
	
		final String username = "sahan";
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		Future<List<Repo>> future = executorService.submit(new Callable<List<Repo>>() {

			public List<Repo> call() throws Exception {
				
				return repoService.getReposByMember(username);
			}
		});
		
		List<Repo> repos = future.get();
		
		assertNotNull(repos);
		assertTrue(repos.size() > 0);
	}
	
	/**
	 * <p>Test for {@link RepoService#getReposByOwner(String)}.</p>
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testOrganizationRepoSyncWithUsername() throws Exception {
		
		final String username = "mozilla";
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		Future<List<Repo>> future = executorService.submit(new Callable<List<Repo>>() {
			
			public List<Repo> call() throws Exception {
				
				return repoService.getReposByOwner(username);
			}
		});
		
		List<Repo> repos = future.get();
		
		assertNotNull(repos);
		assertTrue(repos.size() > 0);
	}
	
	/**
	 * <p>Test for {@link RepoService#getRepos(android.app.Activity)}.</p>
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testTransientRepoSync() throws Exception {
	
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		final Activity activity = Robolectric.buildActivity(ReposActivity.class).get();
		
		GitHubUser user = new GitHubUser();
		user.setId("1111");
		user.setLogin("mozilla");
		user.setType("Organization");
		
		Intent intent = new Intent(activity, BuildsActivity.class);
		intent.putExtra(Resources.key(R.string.key_transient_user), user);
		
		activity.setIntent(intent);
		
		Future<List<Repo>> future = executorService.submit(new Callable<List<Repo>>() {
			
			public List<Repo> call() throws Exception {
				
				return repoService.getRepos(activity);
			}
		});
		
		List<Repo> repos = future.get();
		
		assertNotNull(repos);
		assertTrue(repos.size() > 0);
	}
	
	/**
	 * <p>Tears down the test case by shutting down the {@link #executorService}.</p>
	 * 
	 * @throws java.lang.Exception
	 * 			if the tear-down failed
	 */
	@After
	public final void tearDown() throws Exception {
		
		executorService.shutdownNow();
	}
}
