package org.andarted.hnefatafl;

import javax.swing.SwingUtilities;

import org.andarted.hnefatafl.presenter.Presenter;
import org.andarted.hnefatafl.model.Model;
import org.andarted.hnefatafl.view.View;



public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> startApplication());
	}
	
	private static void startApplication() {
		System.out.println("Hnefatafl startet!");
        
		// - - - VERKABELN - - -
		
		View view = new View();									// Anlegen View
		Model model = new Model();								// Anlegen Model
		
		// Presenter presenter = new Presenter(view);
		Presenter presenter = new Presenter(view, model);		// Anlegen Presenter für View & Model
		
		view.initializePresenter(presenter);					// View zurück zu Presenter verbinden
		model.initializePresenter(presenter);			 		// Model zurück zu Presenter verbinden
		
		// - - - BOOTEN - - - 
		
		view.initializeView();									// GUI und alles erstellen
	}

}
