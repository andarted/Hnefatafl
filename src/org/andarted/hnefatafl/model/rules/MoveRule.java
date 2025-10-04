package org.andarted.hnefatafl.model.rules;

import org.andarted.hnefatafl.model.Model;

public class MoveRule implements IRule{
	
	public MoveRule() {
		
	}
	
	@Override
	public void apply(Model model) {
		System.out.println("MoveRule angewendet");
	}

}
