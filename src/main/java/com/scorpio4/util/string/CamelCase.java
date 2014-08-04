package com.scorpio4.util.string;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
public class CamelCase  {

	public CamelCase()  {
	}
	
/**
	 * Converts the string with a camel case or with underscores and replace it 
	 * with spaces between each word, and underscores replaced by spaces.
	 * For example "javaProgramming" and "JAVA_PROGRAMMING"
	 * would both become Java Programming".
	 * @param str The String to convert
	 * @return The converted String
	 */

	public static String toTitleCase( String str ) {
		if( str == null || str.length() == 0 ) {
			return str;
		}
 
		StringBuffer result = new StringBuffer();
 
		/*
		 * Pretend space before first character
		 */
		char prevChar = ' ';
 
		/*
		 * Change underscore to space, insert space before capitals
		 */
		for( int i = 0; i < str.length(); i++ ) {
			char c = str.charAt( i );
			if( c == '_' ) {
				result.append( ' ' );
			}
			else if( prevChar == ' ' || prevChar == '_' ) {
				result.append( Character.toUpperCase( c ) );
			}
			else if( Character.isUpperCase( c ) && !Character.isUpperCase( prevChar ) ) {
				/*
				 * Insert space before start of word if camel case
				 */
				result.append( ' ' );
				result.append( Character.toUpperCase( c ) );
			}
			else {
				result.append( Character.toLowerCase(c) );
			}
 
			prevChar = c;
		}
 
		return result.toString();
	}

	public static void main(String[] args) {
		System.err.println("TEST #1 "+CamelCase.toTitleCase("hrDiskStorageMedia"));
	}
	
}

