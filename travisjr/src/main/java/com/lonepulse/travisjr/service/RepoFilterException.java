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


import com.lonepulse.travisjr.TravisJrRuntimeException;
import com.lonepulse.travisjr.model.Repo;

/**
 * <p>This exception is thrown when a list of {@link Repo}s cannot be filtered 
 * given a set of filter-parameters.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class RepoFilterException extends TravisJrRuntimeException {


	private static final long serialVersionUID = -3991050493940230113L;
	
	
	/**
	 * <p>See {@link TravisJrRuntimeException#TravisJrRuntimeException()}.
	 * 
	 * @since 1.1.0
	 */
	public RepoFilterException() {}

	/**
	 * <p>Prints a detailed message with the GitHub username of the user whose 
	 * member {@link Repo}s failed to be filtered.
	 * 
	 * @since 1.1.0
	 */
	public RepoFilterException(String gitHubUsername) {
		super("Failed to filter member repo(s) for user " + gitHubUsername + ". ");
	}

	/**
	 * <p>See {@link TravisJrRuntimeException#TravisJrRuntimeException(Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public RepoFilterException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * <p>Prints a detailed message with the GitHub username of the user whose 
	 * member {@link Repo}s failed to be filtered and provides the root-cause.
	 * 
	 * @since 1.1.0
	 */
	public RepoFilterException(String gitHubUsername, Throwable throwable) {
		super("Failed to filter member repo(s) for user " + gitHubUsername + ". ", throwable);
	}
}
