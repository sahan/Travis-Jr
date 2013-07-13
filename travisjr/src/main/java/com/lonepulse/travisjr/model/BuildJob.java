package com.lonepulse.travisjr.model;

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


import java.io.Serializable;

/**
 * <p>Represents a single build job in the build matrix.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class BuildJob implements Serializable {


	private static final long serialVersionUID = -654655019370021900L;
	
	
	/**
	 * <p>Identifies a single build in the matrix uniquely.
	 */
	private long id;
	
	/**
	 * <p>The repository id which the build belongs to.
	 */
	private long repository_id;
	
	/**
	 * <p>The id of the build within the build matrix.
	 */
	private double number;
	
	/**
	 * <p>Whether the build script exited successfully or whether is failed.
	 */
	private int result;
	
	/**
	 * <p>The timestamp for build initiation.
	 */
	private String started_at;
	
	/**
	 * <p>The timestamp for build termination.
	 */
	private String finished_at;
	
	/**
	 * <p>A flag which determines if failures are in-fact allowed for the build script.
	 */
	private boolean allow_failure;

//	/**
//	 * <p>The configuration which was used to run this build.
//	 */
//	private Config config;
	
	
	/**
	 * <p>Accessor for id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * <p>Mutator for id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * <p>Accessor for repository_id.
	 *
	 * @return the repository_id
	 */
	public long getRepository_id() {
		return repository_id;
	}

	/**
	 * <p>Mutator for repository_id.
	 *
	 * @param repository_id 
	 *			the repository_id to set
	 */
	public void setRepository_id(long repository_id) {
		this.repository_id = repository_id;
	}

	/**
	 * <p>Accessor for number.
	 *
	 * @return the number
	 */
	public double getNumber() {
		return number;
	}

	/**
	 * <p>Mutator for number.
	 *
	 * @param number 
	 *			the number to set
	 */
	public void setNumber(double number) {
		this.number = number;
	}

	/**
	 * <p>Accessor for result.
	 *
	 * @return the result
	 */
	public int getResult() {
		return result;
	}

	/**
	 * <p>Mutator for result.
	 *
	 * @param result 
	 *			the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * <p>Accessor for started_at.
	 *
	 * @return the started_at
	 */
	public String getStarted_at() {
		return started_at;
	}

	/**
	 * <p>Mutator for started_at.
	 *
	 * @param started_at 
	 *			the started_at to set
	 */
	public void setStarted_at(String started_at) {
		this.started_at = started_at;
	}

	/**
	 * <p>Accessor for finished_at.
	 *
	 * @return the finished_at
	 */
	public String getFinished_at() {
		return finished_at;
	}

	/**
	 * <p>Mutator for finished_at.
	 *
	 * @param finished_at 
	 *			the finished_at to set
	 */
	public void setFinished_at(String finished_at) {
		this.finished_at = finished_at;
	}

	/**
	 * <p>Accessor for allow_failure.
	 *
	 * @return the allow_failure
	 */
	public boolean isAllow_failure() {
		return allow_failure;
	}

	/**
	 * <p>Mutator for allow_failure.
	 *
	 * @param allow_failure 
	 *			the allow_failure to set
	 */
	public void setAllow_failure(boolean allow_failure) {
		this.allow_failure = allow_failure;
	}
	
//	/**
//	 * <p>Accessor for config.
//	 *
//	 * @return the config
//	 */
//	public Config getConfig() {
//		return config;
//	}
//
//	/**
//	 * <p>Mutator for config.
//	 *
//	 * @param config 
//	 *			the config to set
//	 */
//	public void setConfig(Config config) {
//		this.config = config;
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildJob other = (BuildJob) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BuildJob [id=");
		builder.append(id);
		builder.append(", repository_id=");
		builder.append(repository_id);
		builder.append(", number=");
		builder.append(number);
		builder.append(", result=");
		builder.append(result);
		builder.append(", started_at=");
		builder.append(started_at);
		builder.append(", finished_at=");
		builder.append(finished_at);
		builder.append(", allow_failure=");
		builder.append(allow_failure);
		builder.append(", config=");
//		builder.append(config);
		builder.append("]");
		return builder.toString();
	}
}
