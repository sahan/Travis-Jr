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
import com.lonepulse.robozombie.core.annotation.Parser;
import com.lonepulse.robozombie.rest.annotation.PathParam;
import com.lonepulse.robozombie.rest.annotation.Rest;
import com.lonepulse.travisjr.model.BuildJob;

/**
 * <p>This endpoint contract defines the remote services which are used 
 * for accessing the Amazon S3 cloud storage.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Endpoint("s3.amazonaws.com/archive.travis-ci.org")
public interface AmazonS3Endpoint {
	
	/**
	 * <p>Takes the job ID of a {@link BuildJob} in the build matrix and retrieves its log. 
	 *
	 * @param jobId
	 * 			the job ID whose log is to be retrieved 
	 * 
	 * @return the log of the build job
	 * 
	 * @since 1.1.0
	 */
	@Rest(path = "/jobs/:job_id/log.txt")
	@Parser(Parser.PARSER_TYPE.STRING)
	String getJobLog(@PathParam("job_id") String jobId);
}
