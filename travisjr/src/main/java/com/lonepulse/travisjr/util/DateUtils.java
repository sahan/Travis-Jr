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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p>This utility class is used to perform common processing on timestamps.
 * 
 * @version 1.1.1
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public final class DateUtils {

	
	/**
	 * <p>Constructor visibility restricted. Instantiation is nonsensical.
	 */
	private DateUtils() {} 
	
	
	/**
	 * <p>Takes an ISO8601 timestamp string and converts it to 
	 * a readable date-time format. 
	 * 
	 * @param iso8601Timestamp
	 * 			the ISO8601 timestamp to be formatted
	 * 
	 * @return the formatted timestamp string or an empty string 
	 * 		   if formatting failed
	 * 
	 * @since 1.1.0
	 */
	public static String formatDateTimeForDisplay(String iso8601Timestamp) {
		
		try {
			
			SimpleDateFormat sdfFormatter 
				= new SimpleDateFormat("dd MMM', 'yyyy' at 'h:mm:ss a", Locale.getDefault());
			
			return sdfFormatter.format(DateUtils.parseFromISO8601(iso8601Timestamp));
		}
		catch(Exception e1) {
			
			return "";
		}
	}
	
	/**
	 * <p>Takes an ISO8601 timestamp string and converts it to 
	 * a readable date format. 
	 * 
	 * @param iso8601Timestamp
	 * 			the ISO8601 timestamp to be formatted
	 * 
	 * @return the formatted date string or an empty string 
	 * 		   if formatting failed
	 * 
	 * @since 1.1.1
	 */
	public static String formatDateForDisplay(String iso8601Timestamp) {
		
		try {
			
			SimpleDateFormat sdfFormatter = new SimpleDateFormat("dd MMM', 'yyyy", Locale.getDefault());
			return sdfFormatter.format(DateUtils.parseFromISO8601(iso8601Timestamp));
		}
		catch(Exception e) {
			
			return "";
		}
	}
	
	/**
	 * <p>Takes an ISO8601 timestamp string and converts it to 
	 * a readable time format. 
	 * 
	 * @param iso8601Timestamp
	 * 			the ISO8601 timestamp to be formatted
	 * 
	 * @return the formatted time string or an empty string 
	 * 		   if formatting failed
	 * 
	 * @since 1.1.1
	 */
	public static String formatTimeForDisplay(String iso8601Timestamp) {
		
		try {
			
			SimpleDateFormat sdfFormatter = new SimpleDateFormat("h:mm:ss a", Locale.getDefault());
			return sdfFormatter.format(DateUtils.parseFromISO8601(iso8601Timestamp));
		}
		catch(Exception e) {
			
			return "";
		}
	}
		
	/**
	 * <p>Parses a given ISO8601 timestamp to its corresponding {@link Date}. 
	 *
	 * @param iso8601Timestamp
	 * 			the ISO8601 timestamp to be parsed
	 * 
	 * @return the parsed {@link Date} instance
	 * 
	 * @throws ParseException
	 * 			if the timestamp cannot be parsed into a {@link Date}
	 * 
	 * @throws NullPointerException
	 * 			if the given ISO8601 timestamp is {@code null}
	 * 
	 * @since 1.1.0
	 */
	public static Date parseFromISO8601(String iso8601Timestamp) throws ParseException {
			
		SimpleDateFormat sdfParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
		return sdfParser.parse(iso8601Timestamp);
	}
}
