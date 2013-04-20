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


import android.app.Activity;

import com.lonepulse.travisjr.TravisJrRuntimeException;

/**
 * <p>This exception is thrown when a {@link TabSwipeListener} cannot be initialized 
 * on an {@link Activity}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class TabSwipeListenerException extends TravisJrRuntimeException {


	private static final long serialVersionUID = 2716924294460417858L;

	
	/**
	 * <p>See {@link TravisJrRuntimeException#TravisJrRuntimeException()}.
	 * 
	 * @since 1.1.0
	 */
	public TabSwipeListenerException() {}

	/**
	 * <p>Prints a detailed message with the class name of {@link Activity} 
	 * for which a {@link TabSwipeListener} failed be initialized.
	 * 
	 * @param activityClassName
	 * 			the {@link Class} name of the {@link Activity} on which a 
	 * 			{@link TabSwipeListener} failed to be initialized
	 * 
	 * @since 1.1.0
	 */
	public TabSwipeListenerException(String activityClassName) {
		super("Failed to initialize a TabSwipeListener on " + activityClassName + ". ");
	}
	
	/**
	 * <p>Prints a detailed message with the class name of {@link Activity} 
	 * for which a {@link TabSwipeListener} failed be initialized along with 
	 * the reason for failure.
	 * 
	 * @param activityClassName
	 * 			the {@link Class} name of the {@link Activity} on which a 
	 * 			{@link TabSwipeListener} failed to be initialized
	 * 
	 * @param reason
	 * 			the reason the {@link TabSwipeListener} initailization failed
	 * 
	 * @since 1.1.0
	 */
	public TabSwipeListenerException(String activityClassName, String reason) {
		super("Failed to initialize a TabSwipeListener on " + activityClassName + ". " + reason);
	}

	/**
	 * <p>See {@link TravisJrRuntimeException#TravisJrRuntimeException(Throwable)}.
	 * 
	 * @since 1.1.0
	 */
	public TabSwipeListenerException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * <p>Prints a detailed message with the class name of {@link Activity} 
	 * for which a {@link TabSwipeListener} failed be initialized along with 
	 * the root cause.
	 * 
	 * @param activityClassName
	 * 			the {@link Class} name of the {@link Activity} on which a 
	 * 			{@link TabSwipeListener} failed to be initialized
	 * 
	 * @param throwable
	 * 			the root cause
	 * 
	 * @since 1.1.0
	 */
	public TabSwipeListenerException(String activityClassName, Throwable throwable) {
		super("Failed to initialize a TabSwipeListener on " + activityClassName + ". ", throwable);
	}
}
