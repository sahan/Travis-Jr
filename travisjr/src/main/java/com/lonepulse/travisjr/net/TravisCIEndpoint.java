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


import com.lonepulse.robozombie.core.annotation.Endpoint;
import com.lonepulse.robozombie.core.annotation.Param;
import com.lonepulse.robozombie.core.annotation.Parser;
import com.lonepulse.robozombie.core.annotation.Request;
import com.lonepulse.robozombie.rest.annotation.PathParam;
import com.lonepulse.robozombie.rest.annotation.Rest;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.model.BuildInfo;
import com.lonepulse.travisjr.model.Repo;

/**
 * <p>This endpoint contract defines the remote services which are used 
 * by Travis Jr.
 * 
 * @version 1.1.3
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Endpoint(scheme = "https", value = "api.travis-ci.org")
@Parser(Parser.PARSER_TYPE.JSON)
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
	@Request(path = "repos")
	Repo[] getReposByMember(@Param("member") String member);
	
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
	@Request(path = "repos")
	Repo[] getReposByOwner(@Param("owner_name") String ownerName);
	
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
	@Request(path = "builds")
	Build[] getRecentBuilds(@Param("id") String repositoryId);
	
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
	 * 			the JSON file which is prepended with the build id, e.g. {@code 6060738.json}
	 * 
	 * @return the instance of {@link BuildInfo} which contains detailed 
	 * 		   information about the build
	 * 
	 * @since 1.1.3
	 */
	@Rest(path = "repositories/:owner/:repo/builds/:build_id")
	BuildInfo getBuildInfo(@PathParam("owner") String owner, 
						   @PathParam("repo") String repo,
						   @PathParam("build_id") String buildId);
}
