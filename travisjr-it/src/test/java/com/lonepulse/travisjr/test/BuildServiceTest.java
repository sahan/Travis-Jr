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


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.model.BuildInfo;
import com.lonepulse.travisjr.model.BuildJob;
import com.lonepulse.travisjr.service.BasicBuildService;
import com.lonepulse.travisjr.service.BuildService;
import com.xtremelabs.robolectric.Robolectric;

/**
 * <p>Unit test for {@link BasicBuildService}.
 * 
 * @category test
 * <br><br>
 * @version 1.1.1
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@RunWith(TravisJrTestRunner.class)
public class BuildServiceTest {

	
	/**
	 * <p>The instance of {@link BasicBuildService} on which the 
	 * unit tests are exercised.
	 */
	private BuildService buildService;
	
	/**
	 * <p>A single thread executor which performs network calls off the main thread.
	 */
	private ExecutorService executorService;
	
	
	/**
	 * <p>Sets up the test by instantiating {@link #buildService}.
	 * 
	 * @throws Exception
	 * 			if set up terminated with an error
	 */
	@Before
	public final void setUp() throws Exception {
		
		buildService = new BasicBuildService();
		executorService = Executors.newSingleThreadExecutor();
	}
	
	/**
	 * <p>Test for {@link BuildService#getRecentBuilds(long)}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.0
	 */
	@Test
	public final void testRecentBuilds() throws Exception {
	
		final long repoId = 435658;
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		Future<List<Build>> future = executorService.submit(new Callable<List<Build>>() {

			public List<Build> call() throws Exception {
				
				return buildService.getRecentBuilds(repoId);
			}
		});
		
		List<Build> repos = future.get();
				
		assertNotNull(repos);
		assertTrue(repos.size() > 0);
	}
	
	/**
	 * <p>Test for {@link BuildService#getBuildInfo(String, String, long)}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.1
	 */
	@Test
	public final void testBuildInfo() throws Exception {
		
		final String owner = "sahan";
		final String repo = "IckleBot";
		final long buildId = 8432801;
		
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		Future<BuildInfo> future = executorService.submit(new Callable<BuildInfo>() {
			
			public BuildInfo call() throws Exception {
				
				return buildService.getBuildInfo(owner, repo, buildId);
			}
		});
		
		BuildInfo buildInfo = future.get();
		
		assertNotNull(buildInfo);
	}
	
	/**
	 * <p>Test for {@link BuildService#getBuildInfo(String, String, long)}.
	 * 
	 * @throws Exception
	 * 			if test terminated with an error
	 * 
	 * @since 1.1.1
	 */
	@Test
	public final void testJobLogs() throws Exception {
		
		final String owner = "sahan";
		final String repo = "IckleBot";
		final long buildId = 8432801;
		
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
		
		Future<Map<BuildJob, StringBuilder>> future = executorService.submit(
		new Callable<Map<BuildJob, StringBuilder>>() {
			
				public Map<BuildJob, StringBuilder> call() throws Exception {
	
					BuildInfo buildInfo = buildService.getBuildInfo(owner, repo, buildId);
					return buildService.getJobLogs(buildInfo);
				}
		});
		
		Map<BuildJob, StringBuilder> jobLogs = future.get();
		assertNotNull(jobLogs);
		assertTrue(jobLogs.entrySet().size() > 0);
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
