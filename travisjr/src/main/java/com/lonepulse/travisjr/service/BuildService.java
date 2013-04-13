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


import java.util.List;

import com.lonepulse.icklebot.annotation.inject.Pojo;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.model.Repo;

/**
 * <p>This contract specifies the service offered on the {@link Build}s for 
 * a {@link Repo}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Pojo(BasicBuildService.class)
public interface BuildService {

	/**
	 * <p>Retrieves the set of recent {@link Build}s for a given {@link Repo}.
	 * 
	 * @param repoId
	 * 			the ID of the {@link Repo} whose {@link Build}s are to fetched
	 * 
	 * @return all recent {@link Build}s for the given {@link Repo}
	 * 
	 * @throws BuildsUnavailableException
	 * 			if no {@link Build}s are found for the given {@link Repo}
	 * 
	 * @since 1.1.0
	 */
	List<Build> getRecentBuilds(long repoId);
}
