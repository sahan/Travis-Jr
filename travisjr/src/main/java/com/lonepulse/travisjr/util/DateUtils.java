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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;

/**
 * <p>This utility class is used to perform common processing on timestamps.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public final class DateUtils {

	
	/**
	 * <p>Constructor visibility restricted. Instantiation is nonsensical.
	 */
	private DateUtils() {} 
	
	
	/**
	 * <p>Takes an ISO8601 formatted timestamp and converts it to 
	 * a readable format. 
	 * 
	 * @param iso8601Timestamp
	 * 			the ISO8601 timestamp to be formatted
	 * 
	 * @return the formatted timestamp
	 * 
	 * @since 1.1.0
	 */
	@SuppressLint("SimpleDateFormat") //using an alternate format
	public static String formatForDisplay(String iso8601Timestamp) {
		
		try {
			
			SimpleDateFormat sdfParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			sdfParser.setTimeZone(TimeZone.getDefault());
			
			SimpleDateFormat sdfFormatter 
				= new SimpleDateFormat("dd MMM', 'yyyy' at 'h:mm:ss a", Locale.getDefault());
			
			return sdfFormatter.format(sdfParser.parse(iso8601Timestamp));
		}
		catch(ParseException pe) { //perform a crude parse
			
			try {
			
				String date = iso8601Timestamp.substring(0, 10);
				String time = iso8601Timestamp.substring(11, 19);
				
				String[] dateSegments = date.split("-");
				String[] timeSegments = time.split(":");
				
				Calendar calendar = Calendar.getInstance();
				calendar.set(Integer.parseInt(dateSegments[0]), Integer.parseInt(dateSegments[1]), 
							 Integer.parseInt(dateSegments[2]), Integer.parseInt(timeSegments[0]), 
							 Integer.parseInt(timeSegments[1]), Integer.parseInt(timeSegments[2]));
				
				return calendar.getTime().toString();
			}
			catch(Exception e) {
				
				return iso8601Timestamp.replace('T', ' ').replace('Z', ' ');
			}
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
	 * @since 1.1.0
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date parseFromISO8601(String iso8601Timestamp) {
			
		try {
			
			SimpleDateFormat sdfParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			sdfParser.setTimeZone(TimeZone.getDefault());
			
			return sdfParser.parse(iso8601Timestamp);
		}
		catch(ParseException pe) { //perform a crude parse
			
			try {
			
				String date = iso8601Timestamp.substring(0, 10);
				String time = iso8601Timestamp.substring(11, 19);
				
				String[] dateSegments = date.split("-");
				String[] timeSegments = time.split(":");
				
				Calendar calendar = Calendar.getInstance();
				calendar.set(Integer.parseInt(dateSegments[0]), Integer.parseInt(dateSegments[1]), 
							 Integer.parseInt(dateSegments[2]), Integer.parseInt(timeSegments[0]), 
							 Integer.parseInt(timeSegments[1]), Integer.parseInt(timeSegments[2]));
				
				return calendar.getTime();
			}
			catch(Exception e) {
				
				return Calendar.getInstance().getTime();
			}
		}
	}
}
