package com.lonepulse.travisjr.util;

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


import com.lonepulse.travisjr.app.TravisJr;

/**
 * <p>This utility wraps the resource of the application for access via 
 * fluent facade.  
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public final class Resources {
	
	
	/**
	 * <p>Constructor visibility restricted. Instantiation 
	 * is non-sensical.
	 */
	private Resources() {}
	
	/**
	 * <p>Retrieves the {@link String} value for a <b>key</b> 
	 * given the resource id.
	 * 
	 * @param id
	 * 			the string resource id of the key
	 * 
	 * @since 1.1.0
	 */
	public static String key(int id) {
		
		return TravisJr.Application.CONTEXT.getString(id);
	}
	
	/**
	 * <p>Retrieves the {@link String} value for an <b>error</b> 
	 * given the resource id.
	 * 
	 * @param id
	 * 			the string resource id of the error
	 * 
	 * @since 1.1.0
	 */
	public static String error(int id) {
		
		return TravisJr.Application.CONTEXT.getString(id);
	}
}
