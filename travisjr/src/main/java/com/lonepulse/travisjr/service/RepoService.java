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


import java.util.List;

import com.lonepulse.icklebot.annotation.inject.Pojo;
import com.lonepulse.travisjr.model.Repo;
import com.lonepulse.travisjr.view.NavigationSwipeListenerException;

/**
 * <p>This contract specifies the service offered on the {@link Repo}s which 
 * the user is a member of.
 * 
 * @version 1.1.2
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Pojo(BasicRepoService.class)
public interface RepoService {

	/**
	 * <p>Retrieves the set of {@link Repo}s which the user is a <b>member</b> of.
	 * 
	 * @return all associated {@link Repo}s
	 * 
	 * @throws NavigationSwipeListenerException
	 * 			if the {@link Repo}(s) cannot be read via the remote endpoint.
	 * 
	 * @since 1.1.0
	 */
	List<Repo> getReposByMember();
	
	/**
	 * <p>Retrieves the set of {@link Repo}s which the user is an <b>owner</b> of.
	 * 
	 * @return all associated {@link Repo}s
	 * 
	 * @throws NavigationSwipeListenerException
	 * 			if the {@link Repo}(s) cannot be read via the remote endpoint.
	 * 
	 * @since 1.1.2
	 */
	List<Repo> getReposByOwner();
	
	/**
	 * <p>Filters the given list of {@link Repo}s into a sublist containing the 
	 * repositories owned by the user.
	 * 
	 * @param repos
	 * 			the list of {@link Repo}s to be filtered
	 * 
	 * @return a sublist of the {@link Repo}s owned by the user
	 * 
	 * @throws RepoFilterException
	 * 			if the list of {@link Repo}s cannot be filtered by owner name
	 * 
	 * @since 1.1.1
	 */
	List<Repo> filterCreatedRepos(List<Repo> repos);
	
	/**
	 * <p>Filters the given list of {@link Repo}s into a sublist containing the 
	 * repositories contributed to (but not owned) by the user.
	 * 
	 * @param repos
	 * 			the list of {@link Repo}s to be filtered
	 * 
	 * @return a sublist of the {@link Repo}s contributed to by the user
	 * 
	 * @throws RepoFilterException
	 * 			if the list of {@link Repo}s cannot be filtered by owner name
	 * 
	 * @since 1.1.1
	 */
	List<Repo> filterContributedRepos(List<Repo> repos);
}
