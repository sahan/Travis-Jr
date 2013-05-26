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

import com.lonepulse.travisjr.model.Repo;
import com.lonepulse.travisjr.service.AccountService;
import com.lonepulse.travisjr.service.BasicAccountService;
import com.lonepulse.travisjr.service.BasicRepoService;
import com.lonepulse.travisjr.service.RepoService;
import com.xtremelabs.robolectric.Robolectric;

/**
 * <p>Unit test for {@link BasicRepoService}.
 * 
 * @category test
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@RunWith(TravisJrTestRunner.class)
public class RepoServiceTest {

	
	/**
	 * <p>The instance of {@link BasicRepoService} on which the 
	 * unit tests are exercised.
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
	 * <p>Test for {@link RepoService#getReposByMember()}.
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
	 * <p>Test for {@link RepoService#getReposByOwner()}.
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
	 * <p>Test for {@link RepoService#filterCreatedRepos(List)}.
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
		
		List<Repo> createdRepos = repoService.filterCreatedRepos(allRepos);
		assertTrue(createdRepos.size() > 0);
		assertTrue(allRepos.containsAll(createdRepos));
		
		for (Repo repo : createdRepos) {
			
			assertFalse(repo.getSlug().contains("/"));
		}
	}
	
	/**
	 * <p>Test for {@link RepoService#filterContributedRepos(List)}.
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
		
		List<Repo> contributedRepos = repoService.filterContributedRepos(allRepos);
		assertTrue(contributedRepos.size() > 0);
		assertTrue(allRepos.containsAll(contributedRepos));
		
		for (Repo repo : contributedRepos) {
			
			assertFalse(repo.getSlug().startsWith(username));
		}
	}
	
	/**
	 * <p>Tears down the test case by shutting down the {@link #executorService}.
	 * 
	 * @throws java.lang.Exception
	 * 			if the tear-down failed
	 */
	@After
	public final void tearDown() throws Exception {
		
		executorService.shutdownNow();
	}
}
