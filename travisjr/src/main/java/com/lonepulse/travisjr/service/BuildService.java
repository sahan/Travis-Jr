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
import java.util.Map;

import com.lonepulse.icklebot.annotation.inject.Pojo;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.model.BuildInfo;
import com.lonepulse.travisjr.model.BuildJob;
import com.lonepulse.travisjr.model.Repo;

/**
 * <p>This contract specifies the service offered on the {@link Build}s for a {@link Repo}.
 * 
 * @version 1.1.2
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
	
	/**
	 * <p>Takes the repository name and owner name together with the id of the information 
	 * file and retrieves an instance of {@link BuildInfo}.
	 *
	 * @param owner
	 * 			the GitHub username of the repository owner
	 * 
	 * @param repository
	 * 			the name of the repository
	 * 
	 * @param buildId
	 * 			the id of the build whose information is to be retrieved
	 * 
	 * @return the instance of {@link BuildInfo} which contains detailed 
	 * 		   information about the build
	 * 
	 * @since 1.1.1
	 */
	BuildInfo getBuildInfo(String owner, String repository, long buildId);
	
	/**
	 * <p>Takes the {@link BuildInfo} and retrieves the log for each of the {@link BuildJob}s.
	 *
	 * @param buildId
	 * 			the {@link BuildInfo} containing the {@link BuildJob}s whose logs are to be fetched
	 * 
	 * @return a {@link Map} which maps {@link BuildJob}s to their logs  
	 * 
	 * @since 1.1.2
	 */
	Map<BuildJob, StringBuilder> getJobLogs(BuildInfo buildInfo);
}
