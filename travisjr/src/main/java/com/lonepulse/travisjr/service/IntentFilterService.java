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


import android.content.IntentFilter;
import android.net.Uri;

/**
 * <p>This contract offers services for working with {@link Uri} data filtered through an 
 * {@link IntentFilter}. Only {@link Uri}s whose host is <b>travis-ci.org</b> will be processed.
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public interface IntentFilterService {

	/**
	 * <p>Determines if the given {@link Uri} points to a <b>user</b> on <b>travis-ci.org</b>. 
	 *
	 * @param uri
	 * 			the {@link Uri} to be checked if it points to a user
	 * 
	 * @return the username of the GitHub user in its proper case 
	 * 
	 * @throws UserAuthenticationFailedException
	 * 			if a valid GitHub user failed to be resolved using the given {@link Uri}
	 * 
	 * @since 1.1.0
	 */
	String resolveUser(Uri uri) throws UserAuthenticationFailedException;
	
	/**
	 * <p>Determines if the given {@link Uri} points to a <b>repository</b> on <b>travis-ci.org</b>. 
	 *
	 * @param uri
	 * 			the {@link Uri} to be checked if it points to a repository
	 * 
	 * @return {@code true} if the given {@link Uri} points to a repository, else {@code false} if 
	 * 		   the {@link Uri} does not point to a repository or if an error occurred during validation
	 * 
	 * @return the GitHub repository it its proper case, in the format &lt;user&gt;/&lt;repo&gt;
	 * 
	 * @since 1.1.0
	 */
	String resolveRepository(Uri uri) throws RepositoryAuthenticationFailedException;
}
