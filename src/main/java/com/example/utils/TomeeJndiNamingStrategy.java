package com.example.utils;

import org.wicketstuff.javaee.naming.IJndiNamingStrategy;

public class TomeeJndiNamingStrategy implements IJndiNamingStrategy {
	private static final long serialVersionUID = 4496498533970047379L;

	private static String moduleName;
	public TomeeJndiNamingStrategy(String moduleName) {
		TomeeJndiNamingStrategy.moduleName = moduleName;
	}

	@Override
	public String calculateName(String ejbName, Class<?> ejbType) {
		String nameToUse = ejbName != null ? ejbName : ejbType.getSimpleName();
		return "java:global/"+moduleName+"/" + nameToUse + "!" + ejbType.getName();
	}

}
