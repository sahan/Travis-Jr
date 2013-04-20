package com.lonepulse.travisjr.view;

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


import android.view.View;

import com.lonepulse.travisjr.TravisJrException;
import com.lonepulse.travisjr.TravisJrRuntimeException;

/**
 * <p>This exception is thrown a required {@link View} is missing from the root content.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class MissingViewException extends TravisJrRuntimeException {

	
	private static final long serialVersionUID = 639339573088864399L;
	

	/**
	 * <p>See {@link TravisJrException#TravisJrRuntimeException()}.
	 * 
	 * @since 1.1.0
	 */
	public MissingViewException() {
		
		this("A required view was found missing.");
	}

	/**
	 * <p>Prints a detailed message with the ID of the missing {@link View}.
	 * 
	 * @param missingViewId
	 * 			the ID of the missing {@link View}
	 * 
	 * @since 1.1.0
	 */
	public MissingViewException(String missingViewId) {
		
		super("Required view with ID " + missingViewId + " is missing.");
	}

	/**
	 * <p>See {@link TravisJrException#TravisJrRuntimeException(Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public MissingViewException(Throwable throwable) {
		
		super(throwable);
	}

	/**
	 * <p>Prints a detailed message with the ID of the missing {@link View} 
	 * and the root cause.
	 * 
	 * @param missingViewId
	 * 			the ID of the missing {@link View}
	 * 
	 * @param throwable
	 * 			the root cause
	 * 
	 * @since 1.1.0
	 */
	public MissingViewException(String missingViewId, Throwable throwable) {
		
		super(missingViewId, throwable);
	}
}
