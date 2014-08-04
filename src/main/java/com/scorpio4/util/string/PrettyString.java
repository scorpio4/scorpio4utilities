package com.scorpio4.util.string;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
import java.util.*;

public class PrettyString  {

	public PrettyString()  {
	}
	
	public static String humanize(String label) {
		return prettySafe(unCamelCase(label));
	}

	public static String pretty(String org_string) {
		return camelCase(org_string.replaceAll("[^A-Za-z0-9]+", " "));
	}

	public static String prettySafe(String org_string) {
		return org_string.replaceAll("[^A-Za-z0-9 ]+", "");
	}

	public static String sanitize(Object org_string) {
		return org_string.toString().trim().replaceAll("[^A-Za-z0-9]+", "_");
	}

	public static String sanitizeUC(Object org_string) {
		return sanitize(org_string).toUpperCase();
	}

	public static String unCamelCase(String label) {
		if (label==null) return "";
		return label.replaceAll(String.format("%s|%s|%s|%s","(?<=[A-Z])(?=[A-Z][a-z])","(?<=[^A-Z])(?=[A-Z])","(?<=[A-Za-z])(?=[^A-Za-z])","(?<=[A-Za-z])(?=[^A-Za-z])")," ");
	}

	public static String lamaCase(String clean_string) {
		clean_string = unCamelCase(clean_string);
	    StringTokenizer words = new StringTokenizer(clean_string, " ");
	    StringBuilder camelCase = new StringBuilder();
	    boolean first = true;
	    while (words.hasMoreTokens()) {
	        String currentToken = sanitize(words.nextToken());
	        if (first) currentToken = currentToken.toLowerCase();
	        else currentToken = capitalise(currentToken);
	        camelCase.append(currentToken);
	        first = false;
	    }
	    return camelCase.toString().trim();
	}

	public static String camelCase(String clean_string) {
	    StringTokenizer words = new StringTokenizer(clean_string, " ");
	    StringBuilder camelCase = new StringBuilder();
	    while (words.hasMoreTokens()) {
	        String currentToken = words.nextToken();
	        currentToken = capitalise(currentToken);
	        camelCase.append(currentToken);
	    }
	    return camelCase.toString().trim();
	}

	public static String wikize(String clean_string) {
	    StringTokenizer words = new StringTokenizer(clean_string, " ");
	    StringBuilder camelCase = new StringBuilder();
	    while (words.hasMoreTokens()) {
	        String currentToken = words.nextToken();
	        currentToken = capitalise(currentToken);
	        camelCase.append(currentToken).append(" ");
	    }
	    return sanitize(camelCase.toString().trim());
	}
	
	public static String capitalise(String word) {
		return word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();
	}

    public static String slice(String word, String sep) {
        int ix = word.lastIndexOf(sep);
        if (ix<0) return word;
        return word.substring(0,ix);
    }

    public static String between(String word, String first, String last) {
        int ix_first = word.indexOf(first);
        int len_first = first.length();
        if (ix_first<0) return word;
        if (last != null) {
            int ix_last = word.lastIndexOf(last);
            if (ix_last<0) return word.substring(ix_first+len_first);
            if (ix_first>ix_last) return word;
            return word.substring(ix_first+len_first,ix_last);
        }
        return word.substring(ix_first+len_first);
    }

    public static String normalizeWhitespace(String str) {
		return str.replaceAll("\\s+", " ").trim();
	}
}

