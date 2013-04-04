package com.lonepulse.travisjr;

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


/**
 * <p>This exception is thrown due to erroneous conditions which <b>cannot</b> 
 * be recovered from or circumvented.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class TravisJrRuntimeException extends RuntimeException {


	private static final long serialVersionUID = -473535522588147013L;
	

	/**
	 * <p>See {@link RuntimeException#RuntimeException()}.
	 * 
	 * @since 1.1.0
	 */
	public TravisJrRuntimeException() {}

	/**
	 * <p>See {@link RuntimeException#RuntimeException(String)}.
	 * 
	 * @since 1.1.0
	 */
	public TravisJrRuntimeException(String detailMessage) {
		
		super(detailMessage);
	}

	/**
	 * <p>See {@link RuntimeException#RuntimeException(Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public TravisJrRuntimeException(Throwable throwable) {
		
		super(throwable);
	}

	/**
	 * <p>See {@link RuntimeException#RuntimeException(String, Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public TravisJrRuntimeException(String detailMessage, Throwable throwable) {
		
		super(detailMessage, throwable);
	}
}
