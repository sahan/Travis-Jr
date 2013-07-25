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
 * <p>This exception is thrown when a GitHub user failed to be authenticated using the 
 * API at {@code https://api.github.com/users}).  
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class UserAuthenticationFailedException extends TravisJrRuntimeException {

	
	private static final long serialVersionUID = -7087904713673704796L;
	

	/**
	 * <p>Prints a detailed message using the {@link Uri} which failed to be resolved 
	 * to an authentic GitHub user.
	 * 
	 * @param uri
	 * 			the {@link Uri} which failed to be resolved to a GitHub user
	 * 
	 * @since 1.1.0
	 */
	public UserAuthenticationFailedException(Uri uri) {
		
		this("A GitHub user failed to be resolved for the url " + 
			 ((uri == null)? "<null>" :uri.toString()));
	}
	
	/**
	 * <p>Prints a detailed message using the {@link Uri} which failed to be resolved 
	 * to an authentic GitHub user.
	 * 
	 * @param uri
	 * 			the {@link Uri} which failed to be resolved to a GitHub user
	 * 
	 * @param rootCause
	 * 			the root cause which caused a failure in repository resolution
	 * 
	 * @since 1.1.0
	 */
	public UserAuthenticationFailedException(Uri uri, Throwable rootCause) {
		
		this("A GitHub user failed to be resolved for the url " + 
				((uri == null)? "<null>" :uri.toString()), rootCause);
	}

	/**
	 * <p>Prints a detailed with the username which failed to be authenticated.
	 * 
	 * @param user
	 * 			the username which failed to be authenticated
	 * 
	 * @since 1.1.0
	 */
	public UserAuthenticationFailedException(String user) {
		
		super("The user " + user + " failed to be authenticated. ");
	}

	/**
	 * <p>See {@link TravisJrException#TravisJrRuntimeException(Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public UserAuthenticationFailedException(Throwable throwable) {
		
		super(throwable);
	}

	/**
	 * <p>Prints a detailed with the username which failed to be authenticated.
	 * 
	 * @param user
	 * 			the username which failed to be authenticated
	 * 
	 * @param rootCause
	 * 			the root cause which caused a failure in repository resolution
	 * 
	 * @since 1.1.0
	 */
	public UserAuthenticationFailedException(String user, Throwable rootCause) {
		
		super("The user " + user + " failed to be authenticated. ", rootCause);
	}
}
