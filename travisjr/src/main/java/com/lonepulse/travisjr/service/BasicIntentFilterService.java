package com.lonepulse.travisjr.service;

/*
 * #%L
 * Travis Jr. App
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


import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.IntentFilter;
import android.net.Uri;

import com.lonepulse.robozombie.core.annotation.Bite;
import com.lonepulse.robozombie.core.inject.Zombie;
import com.lonepulse.travisjr.net.GitHubEndpoint;

/**
 * <p>A concrete implementation of {@link IntentFilterService} which offers services for working 
 * with {@link Uri} data filtered through an {@link IntentFilter}. Only {@link Uri}s whose host is 
 * <b>travis-ci.org</b> will be processed.
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class BasicIntentFilterService implements IntentFilterService {

	
	public static final String travisCIHost = "travis-ci.org";
	
	
	@Bite
	private GitHubEndpoint gitHubEndpoint;
	{
		Zombie.infect(this);
	}
	
	
	/**
	 * <p>See {@link IntentFilterService#isValidUser(Uri)}.</p>
	 * 
	 * <p><b>Note</b> that this service is a <b>blocking</b> operation which contacts the GitHub API 
	 * at <b>api.github.com</b> to validate the user.
	 */
	@Override
	public boolean isValidUser(Uri uri) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		
		try {
			
			if(!isValidContext(uri)) {
				
				return false;
			}
			
			List<String> pathSegments = uri.getPathSegments();
			
			if(pathSegments == null || pathSegments.size() != 1) {
				
				return false;
			}
			
			final String user = pathSegments.get(0);
			
			Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
	
				public Boolean call() throws Exception {
	
					return gitHubEndpoint.isValidUser(user);
				}
			});
			
			return future.get();
		} 
		catch (Exception e) {
		
			return Boolean.FALSE;
		}
		finally {
			
			executorService.shutdownNow();
		}
	}

	/**
	 * <p>See {@link IntentFilterService#isValidRepository(Uri)}.</p>
	 * 
	 * <p><b>Note</b> that this service is a <b>blocking</b> operation which contacts the GitHub API 
	 * at <b>api.github.com</b> to validate the repository.
	 */
	@Override
	public boolean isValidRepository(Uri uri) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		
		try {
			
			if(!isValidContext(uri)) {
				
				return false;
			}
			
			List<String> pathSegments = uri.getPathSegments();
			
			if(pathSegments == null || pathSegments.size() != 2) {
				
				return false;
			}
			
			final String user = pathSegments.get(0);
			final String repo = pathSegments.get(1);
			
			Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
				
				public Boolean call() throws Exception {
					
					return gitHubEndpoint.isValidRepository(user, repo);
				}
			});
		
			return future.get();
		} 
		catch (Exception e) {
		
			return Boolean.FALSE;
		}
		finally {
			
			executorService.shutdownNow();
		}
	}
	
	private boolean isValidContext(Uri uri) {

		if(uri == null) {
			
			return false;
		}
		
		if(uri.getHost() == null || !uri.getHost().equals(travisCIHost)) {
			
			return false;
		}
		
		if(uri.getPath() == null || uri.getPath().isEmpty()) {
			
			return false;
		}
		
		return true;
	}
}
