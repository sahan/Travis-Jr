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
 * <p>This model represents a GitHub user. This could be an individual or an organization.</p>
 * 
 * <p><b>Note</b> that this is a partial representation of the complete User model (available 
 * via the service {@code https://api.github.com/users}) which is used for authentication and 
 * for retrieving the login name in its proper case. 
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class GitHubUser implements Serializable {


	private static final long serialVersionUID = 5687172223807519601L;
	
	/**
	 * <p>Identifies this GitHub user uniquely.
	 */
	private String id;
	
	/**
	 * <p>The login name of this GitHub user <b>in its proper case</b>.
	 */
	private String login;
	
	/**
	 * <p>A message which describes an API request failure.
	 */
	private String message;
	
	
	/**
	 * <p>Accessor for id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * <p>Mutator for id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * <p>Accessor for login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * <p>Mutator for login.
	 *
	 * @param login 
	 *			the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * <p>Accessor for message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * <p>Mutator for message.
	 *
	 * @param message 
	 *			the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GitHubUser other = (GitHubUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GitHubUser [id=");
		builder.append(id);
		builder.append(", login=");
		builder.append(login);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}
