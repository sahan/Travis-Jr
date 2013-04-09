package com.lonepulse.travisjr.net;

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


import com.lonepulse.robozombie.core.annotation.Endpoint;
import com.lonepulse.robozombie.core.annotation.Param;
import com.lonepulse.robozombie.core.annotation.Parser;
import com.lonepulse.robozombie.core.annotation.Request;
import com.lonepulse.travisjr.model.Repo;

/**
 * <p>This endpoint contract defines the remote services which are used 
 * by Travis Jr.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Endpoint(scheme = "https", value = "api.travis-ci.org")
@Parser(Parser.PARSER_TYPE.JSON)
public interface TravisCIEndpoint {
	
	/**
	 * <p>Takes a GitHub username and retrieves the repositories which 
	 * that user is a member of.
	 *
	 * @param member
	 * 			the GitHub username of the target member
	 * 			 
	 * @return the set of repositories which the user is a member of
	 * 
	 * @since 1.1.0
	 */
	@Request(path = "repos")
	Repo[] getReposByMember(@Param("member") String member);
}
