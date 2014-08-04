package com.scorpio4.util;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 * 1st May 2014 - Licensed to Apscore
 */
import java.util.*;

public final class Steps {

	private final List<String> steps = new ArrayList();

	public Steps() {
	}

	public Steps(String path) {
		parse(path);
	}

	public Steps(String path, String separators) {
		if (path==null||path.equals("")) throw new NullPointerException("Steps is missing path");
		int ix = path.indexOf("?"); // remove parameters
		if (ix>0) path = path.substring(0,ix);

		ix = path.indexOf("#"); // remove fragments
		if (ix>0) path = path.substring(0,ix);
		parse(path, separators);
	}

	public void parse(String path) {
		parse(path, ":/\\!|"); // split various separators
	}

	public void parse(String path, String delims) {
		if (path==null||path.equals("")) throw new NullPointerException("Steps can't parse an empty path fragment");
		steps.clear();
		StringTokenizer tox = new StringTokenizer(path,delims);
		while(tox.hasMoreTokens()) {
			steps.add(tox.nextToken());
		}
//		if (isEmpty()) throw new com.IQKernel.core.KernelException("Insufficient steps");
	}

    public Steps stripExtension() {
        int lastPos = steps.size()-1;
        if (lastPos>=0) {
            String path = steps.get(lastPos);
            int dot = path.lastIndexOf(".");
            if (dot>=0) steps.set(lastPos, path.substring(0,dot));
        }
        return this;
    }

	public boolean isEmpty() {
		return (steps.size()<1);
	}

	public int size() {
		return steps.size();
	}

	public String step(int step) {
		return (String)steps.get(step);
	}

	public String add(String step) {
		steps.add(step);
		return toString();
	}

	public Steps back() {
		if (size()<1) return this;
		steps.remove(size()-1);
		return this;
	}

	public String local() {
		return isEmpty()?null:(String)steps.get(size()-1);
	}

	public String extension() {
		return toExtension(local());
	}

	public static String toExtension(String string) {
        if (string==null) return null;
		int q = string.indexOf("?");
		if (q>0) string = string.substring(0,q);
		int dot = string.lastIndexOf(".");
		if (dot>=0) return string.substring(dot+1);
		else return null;
	}

	public String root() {
		if (size()<2) return null;
		return (String)steps.get(1);
	}

	public static String localize(java.io.File root, java.io.File file) {
		return localize("urn:", root, file);
	}

	public static String localize(String prefix, java.io.File root, java.io.File file) {
		if (root==null || file == null || !file.getAbsolutePath().startsWith(root.getAbsolutePath())) return null;
		if (root.equals(file)) return ".";
		String path = file.getAbsolutePath().substring(root.getAbsolutePath().length()+1);
		Steps steps = new Steps(path);
		return prefix+steps.toString();
	}

	public String toPath() {
		return toString(1, java.io.File.separator);
	}

	public String toString(int start, String separator) {
		StringBuffer path = new StringBuffer();
		for (int i=start;i<size();i++) {
			if (i>start) path.append(separator);
			path.append(steps.get(i));
		}
		return path.toString();
	}

	public String toString(int start, int end, String separator) {
		if (end<1) end = size();
		StringBuffer path = new StringBuffer();
		for (int i=start;i<end;i++) {
			if (i>start) path.append(separator);
			path.append(steps.get(i));
		}
		return path.toString();
	}

	public String toString(int start) {
		return toString(start,0,":");
	}

	public String toString(String separator) {
		return toString(0,0,separator);
	}

	public String toString() {
		return toString(":");
	}

	public static String translate(String path, String newSeparator, int start) {
		return new Steps(path).toString(start, newSeparator);
	}

	public static String local(String path) {
		return new Steps(path).local();
	}

	public static String localize(String prefix, String local) {
		if (!local.startsWith(prefix)) return null;
		return local.substring(prefix.length());
	}
}
