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
import com.lonepulse.travisjr.model.BuildJob;
import com.lonepulse.travisjr.model.Repo;

/**
 * <p>This exception is thrown when the log of a {@link BuildJob} cannot be retrieved.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class JobLogUnavailableException extends TravisJrRuntimeException {


	private static final long serialVersionUID = -4339685342342947170L;

	
	/**
	 * <p>Prints a detailed message which specifies the ID of the {@link Repo} 
	 * whose builds were unavailable.
	 * 
	 * @param buildJob
	 * 			the {@link BuildJob} whose log failed to be retrieved
	 * 
	 * @param rootCause
	 * 			the root cause of this exception
	 * 
	 * @since 1.1.0
	 */
	public JobLogUnavailableException(BuildJob buildJob, Throwable rootCause) {
		
		this("Failed to fetch the log for the build job with id" + buildJob.getId() + ". ", rootCause);
	}

	/**
	 * <p>See {@link TravisJrRuntimeException#TravisJrRuntimeException(String)}.
	 * 
	 * @since 1.1.0
	 */
	public JobLogUnavailableException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * <p>See {@link TravisJrRuntimeException#TravisJrRuntimeException(Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public JobLogUnavailableException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * <p>See {@link TravisJrRuntimeException#TravisJrRuntimeException(String, Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public JobLogUnavailableException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
