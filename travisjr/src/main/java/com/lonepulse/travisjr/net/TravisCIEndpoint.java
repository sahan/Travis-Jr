package com.lonepulse.travisjr.net;

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

import static com.lonepulse.robozombie.annotation.Entity.ContentType.JSON;

import java.util.List;

import com.lonepulse.robozombie.annotation.Config;
import com.lonepulse.robozombie.annotation.Deserialize;
import com.lonepulse.robozombie.annotation.Endpoint;
import com.lonepulse.robozombie.annotation.GET;
import com.lonepulse.robozombie.annotation.PathParam;
import com.lonepulse.robozombie.annotation.QueryParam;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.model.BuildInfo;
import com.lonepulse.travisjr.model.Repo;

/**
 * <p>This endpoint contract defines the remote services which are used by Travis Jr.
 * 
 * @version 1.1.4
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */

@Deserialize(JSON)
@Config(ZombieConfig.class)
@Endpoint("https://api.travis-ci.org")
public interface TravisCIEndpoint {
	
	/**
	 * <p>Takes a GitHub username and retrieves the repositories which 
	 * that user is a member of.
	 *
	 * @param member
	 * 			the GitHub username of the target member
	 * 			 
	 * @return the set of {@link Repo}s which the user is a member of
	 * 
	 * @since 1.1.0
	 */
	@GET("/repos")
	List<Repo> getReposByMember(@QueryParam("member") String member);
	
	/**
	 * <p>Takes a GitHub username and retrieves the repositories which 
	 * that user is an owner of.
	 *
	 * @param ownerName
	 * 			the GitHub username of the target owner
	 * 			 
	 * @return the set of {@link Repo}s which the user is a owner of
	 * 
	 * @since 1.1.2
	 */
	@GET("/repos")
	List<Repo> getReposByOwner(@QueryParam("owner_name") String ownerName);
	
	/**
	 * <p>Takes a repository ID and retrieves its recent set of builds.
	 *
	 * @param repositoryId
	 * 			the repository ID at {@link Repo#getId()}
	 * 
	 * @return the set of recent {@link Build}s for the given repository
	 * 
	 * @since 1.1.1
	 */
	@GET("/builds")
	List<Build> getRecentBuilds(@QueryParam("id") String repositoryId);
	
	/**
	 * <p>Takes the repository name and owner name together with the id 
	 * of the information file and retrieves an instance of {@link BuildInfo}.
	 *
	 * @param owner
	 * 			the GitHub username of the repository owner
	 * 
	 * @param repo
	 * 			the name of the repository
	 * 
	 * @param buildId
	 * 			the id of the build job 
	 * 
	 * @return the instance of {@link BuildInfo} which contains detailed 
	 * 		   information about the build
	 * 
	 * @since 1.1.3
	 */
	@GET("/repositories/{owner}/{repo}/builds/{build_id}")
	BuildInfo getBuildInfo(@PathParam("owner") String owner,
						   @PathParam("repo") String repo,
						   @PathParam("build_id") String buildId);
}
