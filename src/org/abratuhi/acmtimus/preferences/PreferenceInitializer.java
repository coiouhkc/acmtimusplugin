package org.abratuhi.acmtimus.preferences;

import org.abratuhi.acmtimus.Activator;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;


/**
 * Class used to initialize default preference values.
 * 
 * @author Alexei Bratuhin
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		store.setDefault(PreferenceConstants.PREF_USER, "coiouhkc");
		store.setDefault(PreferenceConstants.PREF_PASS, "");
		store.setDefault(PreferenceConstants.PREF_WORKSPACE, "");
		store.setDefault(PreferenceConstants.PREF_PROJECT, "acm.timus.ru");
		store.setDefault(PreferenceConstants.PREF_PACKAGE, "");
	}

}
