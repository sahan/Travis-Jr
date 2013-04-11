package com.lonepulse.travisjr.model;

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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lonepulse.travisjr.util.DateUtils;

/**
 * <p>This entity represents a single repository which is under 
 * continuous integration.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class Repo implements Serializable, Comparable<Repo> {


	private static final long serialVersionUID = 6970742314687312453L;

	/**
	 * <p>Unique identifier for each repository. 
	 */
	private long id;
	
	/**
	 * <p>Identifies a repo by associated it with the owner, 
	 * for example {@code sahan/IckleBot}. 
	 */
	private String slug;
	
	/**
	 * <p>A brief description of the repo. 
	 */
	private String description;
	
	/**
	 * <p>Identifies the last build uniquely.  
	 */
	private String last_build_id;
	
	/**
	 * <p>The total number of buils which were submitted for CI.
	 */
	private Long last_build_number;
	
	/**
	 * <p>Specifies whether the last build was <b>started</b> 
	 * or <b>finished</b>.
	 */
	private Short last_build_status;
	
	/**
	 * <p>Specifies whether the last build <b>passed</b> 
	 * or <b>failed</b>.
	 */
	private Short last_build_result;
	
	/**
	 * <p>The time taken for the last build in seconds. 
	 */
	private Integer last_build_duration;
	
	/**
	 * <p>The target language for the last build.
	 */
	private String last_build_language;
	
	/**
	 * <p>The timestamp of the last build initiation.
	 */
	private String last_build_started_at;
	
	/**
	 * <p>The timestamp of the last build termination.
	 */
	private String last_build_finished_at;
	
	/**
	 * <p>The set of all recent builds this repository. 
	 */
	private List<Build> builds = new ArrayList<Build>();
	
	
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
	 * <p>Accessor for slug.
	 *
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * <p>Mutator for slug.
	 *
	 * @param slug 
	 *			the slug to set
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * <p>Accessor for description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <p>Mutator for description.
	 *
	 * @param description 
	 *			the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * <p>Accessor for last_build_id.
	 *
	 * @return the last_build_id
	 */
	public String getLast_build_id() {
		return last_build_id;
	}

	/**
	 * <p>Mutator for last_build_id.
	 *
	 * @param last_build_id 
	 *			the last_build_id to set
	 */
	public void setLast_build_id(String last_build_id) {
		this.last_build_id = last_build_id;
	}

	/**
	 * <p>Accessor for last_build_number.
	 *
	 * @return the last_build_number
	 */
	public Long getLast_build_number() {
		return last_build_number;
	}

	/**
	 * <p>Mutator for last_build_number.
	 *
	 * @param last_build_number 
	 *			the last_build_number to set
	 */
	public void setLast_build_number(Long last_build_number) {
		this.last_build_number = last_build_number;
	}

	/**
	 * <p>Accessor for last_build_status.
	 *
	 * @return the last_build_status
	 */
	public Short getLast_build_status() {
		return last_build_status;
	}

	/**
	 * <p>Mutator for last_build_status.
	 *
	 * @param last_build_status 
	 *			the last_build_status to set
	 */
	public void setLast_build_status(Short last_build_status) {
		this.last_build_status = last_build_status;
	}

	/**
	 * <p>Accessor for last_build_result.
	 *
	 * @return the last_build_result
	 */
	public Short getLast_build_result() {
		return last_build_result;
	}

	/**
	 * <p>Mutator for last_build_result.
	 *
	 * @param last_build_result 
	 *			the last_build_result to set
	 */
	public void setLast_build_result(Short last_build_result) {
		this.last_build_result = last_build_result;
	}

	/**
	 * <p>Accessor for last_build_duration.
	 *
	 * @return the last_build_duration
	 */
	public Integer getLast_build_duration() {
		return last_build_duration;
	}

	/**
	 * <p>Mutator for last_build_duration.
	 *
	 * @param last_build_duration 
	 *			the last_build_duration to set
	 */
	public void setLast_build_duration(Integer last_build_duration) {
		this.last_build_duration = last_build_duration;
	}

	/**
	 * <p>Accessor for last_build_language.
	 *
	 * @return the last_build_language
	 */
	public String getLast_build_language() {
		return last_build_language;
	}

	/**
	 * <p>Mutator for last_build_language.
	 *
	 * @param last_build_language 
	 *			the last_build_language to set
	 */
	public void setLast_build_language(String last_build_language) {
		this.last_build_language = last_build_language;
	}

	/**
	 * <p>Accessor for last_build_started_at.
	 *
	 * @return the last_build_started_at
	 */
	public String getLast_build_started_at() {
		return last_build_started_at;
	}

	/**
	 * <p>Mutator for last_build_started_at.
	 *
	 * @param last_build_started_at 
	 *			the last_build_started_at to set
	 */
	public void setLast_build_started_at(String last_build_started_at) {
		this.last_build_started_at = last_build_started_at;
	}

	/**
	 * <p>Accessor for last_build_finished_at.
	 *
	 * @return the last_build_finished_at
	 */
	public String getLast_build_finished_at() {
		return last_build_finished_at;
	}

	/**
	 * <p>Mutator for last_build_finished_at.
	 *
	 * @param last_build_finished_at 
	 *			the last_build_finished_at to set
	 */
	public void setLast_build_finished_at(String last_build_finished_at) {
		this.last_build_finished_at = last_build_finished_at;
	}
	
	/**
	 * <p>Accessor for builds.
	 *
	 * @return the builds
	 */
	public List<Build> getBuilds() {
		return builds;
	}

	/**
	 * <p>Mutator for builds.
	 *
	 * @param builds 
	 *			the builds to set
	 */
	public void setBuilds(List<Build> builds) {
		this.builds = builds;
	}

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
		
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Repo other = (Repo) obj;
		
		if (id != other.id) return false;
		
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Repo [id=");
		builder.append(id);
		builder.append(", slug=");
		builder.append(slug);
		builder.append(", description=");
		builder.append(description);
		builder.append(", last_build_id=");
		builder.append(last_build_id);
		builder.append(", last_build_number=");
		builder.append(last_build_number);
		builder.append(", last_build_status=");
		builder.append(last_build_status);
		builder.append(", last_build_result=");
		builder.append(last_build_result);
		builder.append(", last_build_duration=");
		builder.append(last_build_duration);
		builder.append(", last_build_language=");
		builder.append(last_build_language);
		builder.append(", last_build_started_at=");
		builder.append(last_build_started_at);
		builder.append(", last_build_finished_at=");
		builder.append(last_build_finished_at);
		builder.append(", builds=");
		builder.append(builds);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * <p>Compares {@link Repo}s by their last-build start time.
	 * 
	 * @since 1.1.0
	 */
	@Override
	public int compareTo(Repo another) {

		if(another == null) return -1;
		
		Date thisTimestamp = DateUtils.parseFromISO8601(last_build_started_at);
		Date otherTimestamp = DateUtils.parseFromISO8601(another.last_build_started_at);
		
		return otherTimestamp.compareTo(thisTimestamp);
	}
}
