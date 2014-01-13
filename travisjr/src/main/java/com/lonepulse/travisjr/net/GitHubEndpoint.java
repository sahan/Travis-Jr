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

import static com.lonepulse.robozombie.annotation.Entity.ContentType.JSON;

import org.apache.http.client.methods.HttpRequestBase;

import com.lonepulse.robozombie.annotation.Config;
import com.lonepulse.robozombie.annotation.Deserialize;
import com.lonepulse.robozombie.annotation.Endpoint;
import com.lonepulse.robozombie.annotation.GET;
import com.lonepulse.robozombie.annotation.Intercept;
import com.lonepulse.robozombie.annotation.PathParam;
import com.lonepulse.robozombie.proxy.InvocationContext;
import com.lonepulse.robozombie.request.Interceptor;
import com.lonepulse.travisjr.model.GitHubRepository;
import com.lonepulse.travisjr.model.GitHubUser;
import com.lonepulse.travisjr.net.GitHubEndpoint.CommonInterceptor;

/**
 * <p>This endpoint contract defines the subset of GitHub API services which are used.</p> 
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Deserialize(JSON)
@Config(ZombieConfig.class)
@Intercept(CommonInterceptor.class)
@Endpoint("https://api.github.com")
public interface GitHubEndpoint {
	
	class CommonInterceptor implements Interceptor {

		@Override
		public void intercept(InvocationContext context, HttpRequestBase request) {

			request.addHeader("User-Agent", "Travis-Jr");
			request.addHeader("Accept", "application/vnd.github.v3+json");
		}
	}
	
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
	@GET("/users/{user}")
	GitHubUser getUser(@PathParam("user") String user);
	
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
	@GET("/repos/{user}/{repo}")
	GitHubRepository getRepository(@PathParam("user") String user, @PathParam("repo") String repo);
}
