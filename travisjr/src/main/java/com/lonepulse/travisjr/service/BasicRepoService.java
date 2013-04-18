package com.lonepulse.travisjr.service;

/*
 * #%L
 * Travis Jr.
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lonepulse.robozombie.core.annotation.Bite;
import com.lonepulse.robozombie.core.inject.Zombie;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.model.Repo;
import com.lonepulse.travisjr.net.TravisCIEndpoint;

/**
 * <p>A basic implementation of {@link RepoService}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class BasicRepoService implements RepoService {


	@Bite
	private TravisCIEndpoint travisCIEndpoint;
	{
		Zombie.infect(this);
	}
	
	private AccountService accountService;
	
	
	/**
	 * <p>Default constructor initializes {@link #accountService}.
	 */
	public BasicRepoService() {
	
		this.accountService = new BasicAccountService();
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<Repo> getReposByMember() {
		
		String username = "<n/a>";
		
		try {
			
			username = accountService.getGitHubUsername();
			
			Repo[] repos = travisCIEndpoint.getReposByMember(username);
			Build[] builds;
			
			for (Repo repo : repos) {
				
				if(repo.getLast_build_status() == null) {
					
					builds = travisCIEndpoint.getRecentBuilds(String.valueOf(repo.getId()));
						
					if(builds != null && builds.length != 0)
						repo.setBuilds(Arrays.asList(builds));
				}
			}
			
			return Arrays.asList(repos);
		} 
		catch (Exception e) {
			
			throw new RepoAccessException(username, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Repo> getReposByOwner() {
		
		String username = "<n/a>";
		
		try {
			
			username = accountService.getGitHubUsername();
			
			Repo[] repos = travisCIEndpoint.getReposByOwner(username);
			Build[] builds;
			
			for (Repo repo : repos) {
				
				repo.setSlug(repo.getSlug().replaceFirst(username + "/", ""));
				
				if(repo.getLast_build_status() == null) {
					
					builds = travisCIEndpoint.getRecentBuilds(String.valueOf(repo.getId()));
						
					if(builds != null && builds.length != 0)
						repo.setBuilds(Arrays.asList(builds));
				}
			}
			
			return Arrays.asList(repos);
		} 
		catch (Exception e) {
			
			throw new RepoAccessException(username, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Repo> filterCreatedRepos(List<Repo> repos) {
		
		String username = "<n/a>";
		
		try {
		
			username = accountService.getGitHubUsername();
			List<Repo> ownedRepos = new ArrayList<Repo>();
			
			for (Repo repo : repos) {
				
				if(repo.getSlug().startsWith(username)) {
				
					Repo clone = (Repo)repo.clone();
					clone.setSlug(repo.getSlug().replaceFirst(username + "/", ""));
					ownedRepos.add(clone);
				}
			}
			
			return ownedRepos;
		}
		catch(Exception e) {
			
			throw new RepoFilterException(username);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Repo> filterContributedRepos(List<Repo> repos) {

		String username = "<n/a>";
		
		try {
		
			username = accountService.getGitHubUsername();
			List<Repo> contributedRepos = new ArrayList<Repo>();
			
			for (Repo repo : repos) {
				
				if(!repo.getSlug().startsWith(username)) {
					
					contributedRepos.add(repo);
				}
			}
			
			return contributedRepos;
		}
		catch(Exception e) {
			
			throw new RepoFilterException(username);
		}
	}
}
