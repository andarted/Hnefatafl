package org.andarted.hnefatafl.model.rules;

import org.andarted.hnefatafl.model.Model;

public class BeispielRule implements IRule{
	
	public BeispielRule() {
		
	}

	@Override
	public void apply(Model model) {
		System.out.println("BeispielRule angewendet");
	}
	

}
