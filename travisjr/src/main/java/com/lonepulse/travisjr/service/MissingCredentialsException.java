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


import com.lonepulse.travisjr.TravisJrException;
import com.lonepulse.travisjr.TravisJrRuntimeException;

/**
 * <p>This exception is thrown when credentials requested in the 
 * absence of an account.  
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class MissingCredentialsException extends TravisJrRuntimeException {


	private static final long serialVersionUID = 3763250715035656907L;

	
	/**
	 * <p>See {@link TravisJrException#TravisJrRuntimeException()}.
	 * 
	 * @since 1.1.0
	 */
	public MissingCredentialsException() {
		
		this("The requested credentials cannot be found. " + 
			 "Please create an account before accessing credentials. ");
	}

	/**
	 * <p>See {@link TravisJrException#TravisJrRuntimeException(String)}.
	 * 
	 * @since 1.1.0
	 */
	public MissingCredentialsException(String detailMessage) {
		
		super(detailMessage);
	}

	/**
	 * <p>See {@link TravisJrException#TravisJrRuntimeException(Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public MissingCredentialsException(Throwable throwable) {
		
		super(throwable);
	}

	/**
	 * <p>See {@link TravisJrException#TravisJrRuntimeException(String, Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public MissingCredentialsException(String detailMessage, Throwable throwable) {
		
		super(detailMessage, throwable);
	}
}
