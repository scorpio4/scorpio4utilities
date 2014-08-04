package com.scorpio4.util.map;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
import java.util.*;

/**
 * @author lee
 * Date: 9/11/12
 */
public class Factualize {
	/**
	 * Facts provides utility methods for dealing with simple fact Maps.
	 */

	private Factualize() {
	}

	public static Map pivots(Map<String, Object> model) {
		Map map  = new HashMap();
		for (Map.Entry item: model.entrySet()) {
			if (!isLocal((String)item.getKey()) && isVector((String)item.getKey(), model) ) {
				map .put(item.getKey(), item.getValue());
			}
		}
		return map ;
	}

	public static Map facts(Map<String, Object> model) {
		Map map = new HashMap();
		for (Map.Entry item: model.entrySet()) {
			if (!isLocal((String)item.getKey()) && !isVector((String)item.getKey(), model) ) {
				map .put(item.getKey(), item.getValue());
			}
		}
		return map ;
	}

	public static List vectors(Map<String, Object> model) {
		List<String> vectors = new ArrayList<String>();

		for (String key : model.keySet()) {
			if (!isLocal(key) && isVector(key, model) ) {
				vectors.add(key);
			}
		}
		return vectors;
	}

	public static List scalars(Map<String, Object> model) {
		List<String> scalars = new ArrayList<String>();

		for (String key : model.keySet()) {
			if (!isLocal(key) && !isVector(key, model) ) {
				scalars.add(key);
			}
		}
		return scalars;
	}

	public static Map local(Map<String, Object> model) {
		return local(model,new <String, Object> HashMap());
	}

	public static Map local(Map<String, Object> model, Map<String, Object> new_model) {
		for (String key : model.keySet()) {
			if (isLocal(key)) {
				new_model.put(key, model.get(key));
			}
		}
		return new_model;
	}

	public static Map global(Map<String, Object> model) {
		return global(model, new <String, Object>HashMap());
	}

	public static Map global(Map<String, Object> model, Map<String, Object> new_model) {
		for (String key : model.keySet()) {
			if (isGlobal(key)) {
				new_model.put(key, model.get(key));
			}
		}
		return new_model;
	}

	public static Map localize(String namespace, Map<String, Object> model) {
		return localize(namespace, model, new <String, Object>HashMap());
	}

	public static Map localize(String namespace, Map<String, Object> model, Map<String, Object> new_model) {
		int ns_len = namespace.length();

		for (String key : model.keySet()) {
			if (key.startsWith(namespace)) {
				new_model.put(key.substring(ns_len), model.get(key));
			} else {
				new_model.put(key, model.get(key));
			}
		}

		return new_model;
	}

	public static String localName(String ns, String key) {
		if (key!=null && key.startsWith(ns)) {
			return key.substring(ns.length());
		}
		return null;
	}

	public static Map globalize(String namespace, Map<String, Object> model) {
		return globalize(namespace, model, new <String, Object>HashMap());
	}

	public static Map globalize(String namespace, Map<String, Object> model, Map<String, Object> new_model) {
		for (String key : model.keySet()) {
			if (isLocal(key) && !key.equals("this")) {
				new_model.put(namespace+key, model.get(key));
			} else {
				new_model.put(key, model.get(key));
			}
		}

		return new_model;
	}

	public static boolean isLocal(String key) {
		return (key.indexOf(":"))<0;
	}

	public static boolean isGlobal(String key) {
		return (key.indexOf(":"))>=0;
	}

	public static boolean isVector(String key, Map model) {
		return model.get(key) !=null &&
			(model.get(key) instanceof List || model.get(key) instanceof Map);
	}

}
