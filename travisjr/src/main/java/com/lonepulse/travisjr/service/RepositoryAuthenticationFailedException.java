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


import android.net.Uri;

import com.lonepulse.travisjr.TravisJrException;
import com.lonepulse.travisjr.TravisJrRuntimeException;

/**
 * <p>This exception is thrown when a GitHub repository failed to be authenticated using the 
 * API at {@code https://api.github.com/repos}).  
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class RepositoryAuthenticationFailedException extends TravisJrRuntimeException {

	
	private static final long serialVersionUID = -3484014330090316059L;
	

	/**
	 * <p>Prints a detailed message using the {@link Uri} which failed to be resolved 
	 * to an authentic GitHub repository.
	 * 
	 * @param uri
	 * 			the {@link Uri} which failed to be resolved to a GitHub repository
	 * 
	 * @since 1.1.0
	 */
	public RepositoryAuthenticationFailedException(Uri uri) {
		
		this("A GitHub repository failed to be resolved for the url " + 
			 ((uri == null)? "<null>" :uri.toString()));
	}
	
	/**
	 * <p>Prints a detailed message using the {@link Uri} which failed to be resolved 
	 * to an authentic GitHub repository and the root cause of this failure.
	 * 
	 * @param uri
	 * 			the {@link Uri} which failed to be resolved to a GitHub repository
	 * 
	 * @param rootCause
	 * 			the root cause which caused a failure in repository resolution
	 * 
	 * @since 1.1.0
	 */
	public RepositoryAuthenticationFailedException(Uri uri, Throwable rootCause) {
		
		this("A GitHub repository failed to be resolved for the url " + 
			 ((uri == null)? "<null>" :uri.toString()), rootCause);
	}

	/**
	 * <p>Prints a detailed with the "&lt;user&gt;/&lt;repo&gt;" identifier which failed to be authenticated.
	 * 
	 * @param user
	 * 			the username which failed to be authenticated
	 * 
	 * @since 1.1.0
	 */
	public RepositoryAuthenticationFailedException(String slug) {
		
		super("The repository " + slug + " failed to be authenticated. ");
	}

	/**
	 * <p>See {@link TravisJrException#TravisJrRuntimeException(Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public RepositoryAuthenticationFailedException(Throwable throwable) {
		
		super(throwable);
	}

	/**
	 * <p>Prints a detailed with the "&lt;user&gt;/&lt;repo&gt;" identifier which failed to be authenticated.
	 * 
	 * @param user
	 * 			the username which failed to be authenticated
	 * 
	 * @param rootCause
	 * 			the root cause which caused a failure in repository resolution 
	 * 
	 * @since 1.1.0
	 */
	public RepositoryAuthenticationFailedException(String slug, Throwable rootCause) {
		
		super("The repository " + slug + " failed to be authenticated. ", rootCause);
	}
}
