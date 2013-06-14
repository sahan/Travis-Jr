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
import java.util.Arrays;

/**
 * <p>Represents a configuration for a single build. 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class Config implements Serializable {


	private static final long serialVersionUID = -7393979873013226080L;
	
	
	/**
	 * <p>The dominant programming language used to code the repository.
	 */
	private String language;
	
	/**
	 * <p>The script(s) to be run before the repository is installed.
	 */
	private String[] before_install;
	
	/**
	 * <p>The script which installs the repository.
	 */
	private String install;
	
	/**
	 * <p>The script(s) to be run before the build-script is executed.
	 */
	private String[] before_script;
	
	/**
	 * <p>The script which runs and tests the build.
	 */
	private String script;
	
	/**
	 * <p>The script(s) to be run after the build-script is executed.
	 */
	private String[] after_script;

	/**
	 * <p>Accessor for language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * <p>Mutator for language.
	 *
	 * @param language 
	 *			the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * <p>Accessor for before_install.
	 *
	 * @return the before_install
	 */
	public String[] getBefore_install() {
		return before_install;
	}

	/**
	 * <p>Mutator for before_install.
	 *
	 * @param before_install 
	 *			the before_install to set
	 */
	public void setBefore_install(String[] before_install) {
		this.before_install = before_install;
	}

	/**
	 * <p>Accessor for install.
	 *
	 * @return the install
	 */
	public String getInstall() {
		return install;
	}

	/**
	 * <p>Mutator for install.
	 *
	 * @param install 
	 *			the install to set
	 */
	public void setInstall(String install) {
		this.install = install;
	}

	/**
	 * <p>Accessor for before_script.
	 *
	 * @return the before_script
	 */
	public String[] getBefore_script() {
		return before_script;
	}

	/**
	 * <p>Mutator for before_script.
	 *
	 * @param before_script 
	 *			the before_script to set
	 */
	public void setBefore_script(String[] before_script) {
		this.before_script = before_script;
	}

	/**
	 * <p>Accessor for script.
	 *
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * <p>Mutator for script.
	 *
	 * @param script 
	 *			the script to set
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/**
	 * <p>Accessor for after_script.
	 *
	 * @return the after_script
	 */
	public String[] getAfter_script() {
		return after_script;
	}

	/**
	 * <p>Mutator for after_script.
	 *
	 * @param after_script 
	 *			the after_script to set
	 */
	public void setAfter_script(String[] after_script) {
		this.after_script = after_script;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(after_script);
		result = prime * result + Arrays.hashCode(before_install);
		result = prime * result + Arrays.hashCode(before_script);
		result = prime * result + ((install == null) ? 0 : install.hashCode());
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((script == null) ? 0 : script.hashCode());
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
		Config other = (Config) obj;
		if (!Arrays.equals(after_script, other.after_script))
			return false;
		if (!Arrays.equals(before_install, other.before_install))
			return false;
		if (!Arrays.equals(before_script, other.before_script))
			return false;
		if (install == null) {
			if (other.install != null)
				return false;
		} else if (!install.equals(other.install))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (script == null) {
			if (other.script != null)
				return false;
		} else if (!script.equals(other.script))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Config [language=");
		builder.append(language);
		builder.append(", before_install=");
		builder.append(Arrays.toString(before_install));
		builder.append(", install=");
		builder.append(install);
		builder.append(", before_script=");
		builder.append(Arrays.toString(before_script));
		builder.append(", script=");
		builder.append(script);
		builder.append(", after_script=");
		builder.append(Arrays.toString(after_script));
		builder.append("]");
		return builder.toString();
	}
}
