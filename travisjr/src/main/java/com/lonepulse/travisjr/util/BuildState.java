package com.lonepulse.travisjr.util;

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


import com.lonepulse.travisjr.model.Build;

/**
 * <p>Represents the <b>states</b> which a {@link Build} can occupy.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public enum BuildState {

	
	/**
	 * <p>The {@link Build} has not yet terminated.
	 * 
	 * @since 1.1.0
	 */
	ONGOING,
	
	/**
	 * <p>The {@link Build} has terminated with an error.
	 * 
	 * @since 1.1.0
	 */
	ERRORED,
	
	/**
	 * <p>The {@link Build} has successfully terminated without any errors 
	 * or failed assertions.
	 * 
	 * @since 1.1.0
	 */
	PASSED,
	
	/**
	 * <p>The {@link Build} has terminated with failed assertions.
	 * 
	 * @since 1.1.0
	 */
	FAILED;
}
