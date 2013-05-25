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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.service.BasicBuildService;
import com.lonepulse.travisjr.service.BuildService;
import com.lonepulse.travisjr.test.app.TravisJr;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * <p>Unit test for {@link BasicBuildService}.
 * 
 * @category test
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@RunWith(RobolectricTestRunner.class)
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
		
		TravisJr.Application.context = Robolectric.application;
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
