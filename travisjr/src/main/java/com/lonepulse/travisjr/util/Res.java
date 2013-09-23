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


import android.content.res.Resources;

import com.lonepulse.travisjr.app.TravisJr;

/**
 * <p>This utility provides a concise contract for accessing application resources by wrapping 
 * an instance of {@link Resources}.</p>   
 * 
 * <br><br>
 * @since 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public final class Res {
	
	
	private static final Resources resources = TravisJr.Application.getContext().getResources();
	
	/**
	 * <p>Constructor visibility restricted. Instantiation is nonsensical.</p>
	 */
	private Res() {}
	
	/**
	 * <p>Retrieves the {@link String} resource value for the given {@code int} ID. This is a 
	 * convinence method for {@link Context#getResources()#string(int)}.</p>
	 * 
	 * @param id
	 * 			the {@code int} ID of the String resource
	 * 
	 * @since 1.1.0
	 */
	public static String string(int id) {
		
		return resources.getString(id);
	}
}
