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
 * <p>This exception is thrown when detailed build information cannot be retrieved 
 * for a certain build.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class BuildInfoUnavailableException extends TravisJrRuntimeException {


	private static final long serialVersionUID = -2436724976240029692L;
	

	/**
	 * <p>Prints a detailed message which specifies the ID of the {@link Repo} 
	 * whose builds were unavailable.
	 * 
	 * @param owner
	 * 			the name of the repository owner 
	 * 
	 * @param repo
	 * 			the name of the repository
	 * 
	 * @param buildId
	 * 			the ID of the build
	 * 
	 * @param rootCause
	 * 			the root cause of this exception
	 * 
	 * @since 1.1.0
	 */
	public BuildInfoUnavailableException(String owner, String repo, long buildId, Throwable rootCause) {
		
		this("No detailed buid information was available for buid with ID " + 
			 buildId + " on repository " + repo + " by " + owner + ". ");
	}

	/**
	 * <p>See {@link TravisJrRuntimeException#TravisJrRuntimeException(String)}.
	 * 
	 * @since 1.1.0
	 */
	public BuildInfoUnavailableException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * <p>See {@link TravisJrRuntimeException#TravisJrRuntimeException(Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public BuildInfoUnavailableException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * <p>See {@link TravisJrRuntimeException#TravisJrRuntimeException(String, Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public BuildInfoUnavailableException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
