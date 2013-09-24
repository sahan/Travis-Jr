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


import android.app.Activity;
import android.content.Context;

import com.lonepulse.icklebot.annotation.inject.Pojo;
import com.lonepulse.travisjr.AuthenticationActivity;
import com.lonepulse.travisjr.model.GitHubUser;
import com.lonepulse.travisjr.view.MissingViewException;

/**
 * <p>This contract specifies the services offered for managing the 
 * user's account.</p>
 * 
 * @version 1.2.1
 * <br><br>
 * @since 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Pojo(BasicAccountService.class)
public interface AccountService {

	
	/**
	 * <p>Determines if the given {@link Activity} context has a <b>transient user</b> 
	 * as a result of an external invocation via a URI.
	 *
	 * @param activity
	 * 			the {@link Activity} {@link Context} to look for a transient {@link GitHubUser}
	 * 			in the current session context
	 * 
	 * @return {@code true} if the given {@link Activity} has a transient user 
	 * 
	 * @since 1.1.0
	 */
	boolean hasTransientUser(Activity activity);
	
	/**
	 * <p>Attaches the given {@link GitHubUser} to the specified given {@link Activity}.</p>
	 * 
	 * <p>User {@link #getTransientUser(Activity)} to retrieve this {@link GitHubUser}.</p>
	 *
	 * @param username
	 * 			the {@link GitHubUser} to attach
	 * 
	 * @param activity
	 * 			the {@link Activity} to which the given {@link GitHubUser} is attached 
	 * 
	 * @since 1.1.0
	 */
	void setTransientUser(GitHubUser gitHubUser, Activity activity);
	
	/**
	 * <p>Retrieves the transient {@link GitHubUser} which is attached to the given {@link Activity}.</p>
	 *
	 * @param activity
	 * 			the {@link Activity} whose transient {@link GitHubUser} is retrieved
	 * 
	 * @return the attached {@link GitHubUser}; else {@code null} if no transient user was found 
	 * 
	 * @since 1.1.0
	 */
	GitHubUser getTransientUser(Activity activity);
	
	/**
	 * <p>Retrieves the saved GitHub username from the user credentials.</p>
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
	 * <p>Saves the given GitHub username in the user credentials.</p>
	 * 
	 * @param username
	 * 			the username to save in credentials
	 * 
	 * @since 1.1.0
	 */
	void setGitHubUsername(String username);
	
	/**
	 * <p>If there is a transient {@link GitHubUser} in the given context, the associated 
	 * username is retrieved; else retrieves the username from the user credentials.</p>
	 * 
	 * @param activity
	 * 			the {@link Activity} {@link Context} to look for a transient {@link GitHubUser}
	 * 			in the current session context
	 * 
	 * @return the saved GitHub username
	 * 
	 * @throws MissingViewException
	 * 			if no GitHUb username was previously saved   
	 * 
	 * @since 1.1.0
	 */
	String getGitHubUsername(Activity activity) throws MissingCredentialsException;
	
	/**
	 * <p>Indicates whether the user is to be treated as a member or an organization.</p>
	 * 
	 * @return the {@link UserMode} associated with the current user; else 
	 * 		   {@link UserMode#ORGANIZATION} if the user mode failed to be determined
	 * 
	 * @since 1.1.0
	 */
	UserMode getUserMode();
	
	/**
	 * <p>Update the current user's {@link UserMode} to the given instance.
	 * 
	 * @param userMode
	 * 			the updates the user mode to the given instance of {@link UserMode} 
	 * 
	 * @since 1.1.0
	 */
	void setUserMode(UserMode userMode);
	
	/**
	 * <p>If there is a {@link GitHubUser} in the given context, the associated {@link UserMode} 
	 * is retrieved; else retrieves the {@link UserMode} in the saved credentials.</p>
	 * 
	 * @param activity
	 * 			the {@link Activity} {@link Context} to look for a transient {@link GitHubUser}
	 * 			in the current session context
	 * 
	 * @return the {@link UserMode} associated with the current user; else {@link UserMode#ORGANIZATION} 
	 * 		   if the user mode for the current user in context failed to be determined
	 * 
	 * @since 1.1.0
	 */
	UserMode getUserMode(Activity activity);
	
	/**
	 * <p>If there is a {@link GitHubUser} in the given context, with a <b>missing or invalid</b> 
	 * {@link UserMode}, the user is fetched from the GitHub API and the {@link GitHubUser#getType()} is 
	 * resolved to a instance of {@link UserMode} which is subsequently returned.</p>
	 * 
	 * @param activity
	 * 			the {@link Activity} {@link Context} to look for a transient {@link GitHubUser}
	 * 			in the current session context
	 * 
	 * @return the {@link UserMode} associated with the current user - fetched from the GitHub API if 
	 * 		   missing or invalid; else {@link UserMode#ORGANIZATION} if the transient user is missing 
	 * 		   altogether in the given context or if no such user is found
	 * 
	 * @since 1.1.0
	 */
	UserMode fetchUserMode(Activity activity);
	
	/**
	 * <p>Retrieves the GitHub username which was saved in the account created by the official GitHub app.</p>
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
	 * <p>Indicates whether the user had already setup account credentials. This can be used before 
	 * {@link #getGitHubUsername()} to circumvent a {@link MissingViewException}.</p>
	 * 
	 * @return {@code true} if credentials are already saved
	 * 
	 * @since 1.1.0
	 */
	boolean areCredentialsAvailable();
	
	/**
	 * <p>Clears all saved credentials and account details; navigates 
	 * to the {@link AuthenticationActivity}.
	 * 
	 * @param context
	 * 			the {@link Context} from which the user's 
	 * 			account is purged
	 *
	 * @since 1.1.0
	 */
	void purgeAccount(Context context);
}
