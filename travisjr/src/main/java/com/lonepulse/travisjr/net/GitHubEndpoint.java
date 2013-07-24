package com.lonepulse.travisjr.net;

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


import com.lonepulse.robozombie.core.annotation.Endpoint;
import com.lonepulse.robozombie.core.annotation.Parser;
import com.lonepulse.robozombie.rest.annotation.PathParam;
import com.lonepulse.robozombie.rest.annotation.Rest;

/**
/**
 * <p>This endpoint contract defines the subset of GitHub API services which are used. 
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Endpoint(scheme = "https", value = "api.github.com")
public interface GitHubEndpoint {
	
	/**
	 * <p>A simple call to the <i>/users</i> service at <b>api.github.com</b> 
	 * which is used to check the existence of a user.
	 *
	 * @param user
	 * 			the username of the user whose existence is to be checked
	 * 
	 * @return {@code true} if the GitHub user exists
	 * 
	 * @since 1.1.0
	 */
	@Rest(path = "/users/:user")
	@Parser(type = GitHubValidationParser.class)
	Boolean isValidUser(@PathParam("user") String user);
	
	/**
	 * <p>A simple call to the <i>/users</i> service at <b>api.github.com</b> 
	 * which is used to check the existence of a user.
	 *
	 * @param user
	 * 			the username of the user who owns the repository
	 * 
	 * @param repo
	 * 			the repository whose existence is to be validated
	 * 
	 * @return {@code true} if a valid repository exists for the given user and repo
	 * 
	 * @since 1.1.0
	 */
	@Rest(path = "/users/:user")
	@Parser(type = GitHubValidationParser.class)
	Boolean isValidRepository(@PathParam("user") String user, @PathParam("repo") String repo);
}
