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


import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.app.TravisJr;

/**
 * <p>This utility class is used to perform common processing on text.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public final class TextUtils {

	
	/**
	 * <p>Constructor visibility restricted. Instantiation is nonsensical.
	 */
	private TextUtils() {} 
	
	
	/**
	 * <p>Take an object and determines if it's data is available. If the 
	 * data is available it is returned, else the default unavailability 
	 * message is returned.
	 * 
	 * @param object
	 * 			the object whose availability is to be checked
	 * 
	 * @return the available string content, else the default 
	 * 		   unavailability message
	 * 
	 * @since 1.1.0
	 */
	public static String isAvailable(Object object) {
		
		return isAvailable(object, TravisJr.Application.CONTEXT.getString(R.string.not_available));
	}
	
	/**
	 * <p>Take an object and determines if it's data is available. If the 
	 * data is available it is returned, else the given unavailability 
	 * message is returned.
	 * 
	 * @param object
	 * 			the object whose availability is to be checked
	 *
	 * @param ifNotAvailable
	 * 			the string to return if the content is unavailable
	 * 
	 * @return the available string content, else the default 
	 * 		   unavailability message
	 * 
	 * @since 1.1.0
	 */
	public static String isAvailable(Object object, String ifNotAvailable) {
		
		if(object != null) {
			
			if(object instanceof String) {
				
				return (object.equals(""))? ifNotAvailable :(String)object;
			}
				
			return String.valueOf(object);
		}
		else {
			
			return ifNotAvailable;
		}
		
		
	}
}
