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
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.model.GitHubRepository;
import com.lonepulse.travisjr.model.GitHubUser;
import com.lonepulse.travisjr.net.GitHubEndpoint;
import com.lonepulse.travisjr.util.Resources;

/**
 * <p>A concrete implementation of {@link IntentFilterService} which offers services for working 
 * with {@link Uri} data filtered through an {@link IntentFilter}. Only {@link Uri}s whose host 
 * is <b>travis-ci.org</b> will be processed.
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class BasicIntentFilterService implements IntentFilterService {

	
	public static final String[] acceptedHosts = {"travis-ci.org", "www.travis-ci.org"};
	
	public static final String msgNotFound = Resources.error(R.string.err_github_msg_not_found);
	
	
	@Bite
	private GitHubEndpoint gitHubEndpoint;
	{
		Zombie.infect(this);
	}
	
	
	/**
	 * <p>See {@link IntentFilterService#resolveUser(Uri)}.</p>
	 * 
	 * <p><b>Note</b> that this service is a <b>blocking</b> operation which contacts the GitHub API 
	 * at <b>api.github.com</b> to validate the user. <b>The network call is already executed on a 
	 * worker thread</b>.</p>
	 */
	@Override
	public GitHubUser resolveUser(Uri uri) {
		
		UserAuthenticationFailedException uafe = new UserAuthenticationFailedException(uri);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		try {
			
			if(!isValidContext(uri)) {
				
				throw uafe;
			}
			
			final List<String> pathSegments = uri.getPathSegments();
			
			if(pathSegments == null || pathSegments.size() < 1) {
				
				throw uafe;
			}
			
			Future<GitHubUser> future = executorService.submit(new Callable<GitHubUser>() {
				
				@Override
				public GitHubUser call() throws Exception {
					
					return gitHubEndpoint.getUser(pathSegments.get(0));
				}
			});
			
			GitHubUser gitHubUser = future.get();
			
			String message = gitHubUser.getMessage();
			
			if(message != null && message.equals(msgNotFound)) {
				
				throw uafe;
			}
			
			return gitHubUser;
		} 
		catch (Exception e) {
		
			throw (e instanceof UserAuthenticationFailedException)? (UserAuthenticationFailedException)e 
				   :new UserAuthenticationFailedException(uri, e);
		}
		finally {
			
			executorService.shutdownNow();
		}
	}

	/**
	 * <p>See {@link IntentFilterService#resolveRepository(Uri)}.</p>
	 * 
	 * <p><b>Note</b> that this service is a <b>blocking</b> operation which contacts the GitHub API 
	 * at <b>api.github.com</b> to validate the repository. <b>The network call is already executed 
	 * on a worker thread</b>.</p>
	 */
	@Override
	public GitHubRepository resolveRepository(Uri uri) {
		
		RepositoryAuthenticationFailedException rafe = new RepositoryAuthenticationFailedException(uri);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		try {
			
			if(!isValidContext(uri)) {
				
				throw rafe;
			}
			
			List<String> pathSegments = uri.getPathSegments();
			
			if(pathSegments == null || pathSegments.size() != 2) {
				
				throw rafe;
			}
			
			final String user = pathSegments.get(0);
			final String repo = pathSegments.get(1);
			
			Future<GitHubRepository> future = executorService.submit(new Callable<GitHubRepository>() {
				
				@Override
				public GitHubRepository call() throws Exception {
					
					return gitHubEndpoint.getRepository(user, repo);
				}
			});
			
			GitHubRepository gitHubRepo = future.get();
			
			String message = gitHubRepo.getMessage();
			
			if(message != null && message.equals(msgNotFound)) {
				
				throw rafe;
			}
			
			return gitHubRepo;
		} 
		catch (Exception e) {
		
			throw (e instanceof RepositoryAuthenticationFailedException)? (RepositoryAuthenticationFailedException)e 
				   :new RepositoryAuthenticationFailedException(uri, e);
		}
		finally {
			
			executorService.shutdownNow();
		}
	}
	
	private boolean isValidContext(Uri uri) {

		if(uri == null) {
			
			return false;
		}
		
		if(uri.getHost() == null) {
			
			return false;
		}
		
		boolean hasNoAcceptedHost = true;
		
		for (String acceptedHost : acceptedHosts) {
			
			if(uri.getHost().equalsIgnoreCase(acceptedHost)) {
				
				hasNoAcceptedHost = false;
				break;
			}
		}
		
		if(hasNoAcceptedHost) {
			
			return false;
		}
		
		if(uri.getPath() == null || uri.getPath().isEmpty()) {
			
			return false;
		}
		
		return true;
	}
}
