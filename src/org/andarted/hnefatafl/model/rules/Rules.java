package org.andarted.hnefatafl.model.rules;

import java.util.ServiceLoader;

import org.andarted.hnefatafl.model.Model;


public class Rules{
	
	private final Iterable<IRule> allRules;
	
	public Rules() {
		
		allRules = ServiceLoader.load(IRule.class);
	}
	
	public void applyAll(Model model) {
		// ServiceLoader<IRule> loader = ServiceLoader.load(IRule.class, getClass().getClassLoader()); // <- Diese Zeile sorgt dafür, dass der ServiceLoader die Rule-Klassen laden kann, auch wenn sie package-private sind.
		for (IRule rule : allRules) {
			rule.apply(model);
		}
	}

}
