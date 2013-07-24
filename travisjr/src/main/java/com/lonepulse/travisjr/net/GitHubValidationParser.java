package com.lonepulse.travisjr.net;

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


import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.lonepulse.robozombie.core.response.parser.AbstractResponseParser;

/**
 * <p>Parses the content of the {@link HttpResponse} body to determine if valid 
 * JSON content is received for a GitHup API call of type <b>/user/:username</b>.
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class GitHubValidationParser extends AbstractResponseParser<Boolean> {
	

	@Override
	protected Boolean processResponse(HttpResponse httpResponse) throws Exception {
		
		String responseBody = EntityUtils.toString(httpResponse.getEntity());
		
		return responseBody.contains("\"message\": \"Not Found\"");
	}
	
	@Override
	protected Class<Boolean> getType() {
		
		return Boolean.class;
	}
}
