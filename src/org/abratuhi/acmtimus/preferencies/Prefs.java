package org.abratuhi.acmtimus.preferencies;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.osgi.service.prefs.Preferences;

import acmtimuseclipseplugin.Activator;

public class Prefs {
	
	public static final String PREF_USER = "user";
	public static final String PREF_PASS = "pass";
	public static final String PREF_WORKSPACE = "wspace";
	public static final String PREF_PROJECT = "project";
	public static final String PREF_PACKAGE = "package";
	
	public static final Map<String, String> DEFAULTS = new TreeMap<String, String>();
	
	static {
		DEFAULTS.put(PREF_USER, "coiouhkc");
		DEFAULTS.put(PREF_PASS, "");
		DEFAULTS.put(PREF_WORKSPACE, "");
		DEFAULTS.put(PREF_PROJECT, "acm.timus.ru");
		DEFAULTS.put(PREF_PACKAGE, "");
	}
	
	
	public final static String get(final String pref) {
		IPreferencesService ps = Platform.getPreferencesService();
		Preferences root = ps.getRootNode();
		Preferences prefs = root.node(Activator.PLUGIN_ID);
		return prefs.get(pref, DEFAULTS.get(pref));
	}

}
