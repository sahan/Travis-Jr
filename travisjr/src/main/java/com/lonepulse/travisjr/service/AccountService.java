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


import com.lonepulse.icklebot.annotation.inject.Pojo;
import com.lonepulse.travisjr.view.MissingViewException;

/**
 * <p>This contract specifies the services offered for managing the 
 * user's account. 
 * 
 * @version 1.1.1
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Pojo(BasicAccountService.class)
public interface AccountService {

	/**
	 * <p>Retrieves the saved GitHub username from the user credentials.
	 * 
	 * @return the saved GitHub username
	 * 
	 * @throws MissingViewException
	 * 			if no GitHUb username was previously saved   
	 * 
	 * @since 1.1.0
	 */
	String getGitHubUsername() throws MissingCredentialsException;
	
	/**
	 * <p>Saves the given GitHub username in the user credentials.
	 * 
	 * @param username
	 * 			the username to save in credentials
	 * 
	 * @since 1.1.0
	 */
	void setGitHubUsername(String username);
	
	/**
	 * <p>Retrieves the GitHub username which was saved in the account 
	 * created the official GitHub app.
	 * 
	 * @return the username linked to the official GitHub application
	 * 
	 * @throws MissingViewException
	 * 			if the original GitHUb application is not in use or if 
	 * 			its account credentials cannot be accessed
	 * 
	 * @since 1.1.0
	 */
	String queryGitHubAccount() throws MissingCredentialsException;
	
	/**
	 * <p>Indicates whether the user had already setup account credentials. 
	 * This can be used before {@link #getGitHubUsername()} to circumvent a 
	 * {@link MissingViewException}.
	 * 
	 * @return {@code true} if credentials are already saved
	 * 
	 * @since 1.1.1
	 */
	boolean areCredentialsAvailable();
	
	/**
	 * <p>Indicates whether the user is to be treated as a member or an 
	 * organization.
	 * 
	 * @return {@code true} if the user is to be treated as a member, 
	 * 		   {@code false} if the user is to be treated as a organization
	 * 
	 * @since 1.1.2
	 */
	boolean isUserModeOrganization();
}
