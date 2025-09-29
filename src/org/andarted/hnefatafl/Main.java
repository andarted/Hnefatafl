package org.andarted.hnefatafl;

import javax.swing.SwingUtilities;

import org.andarted.hnefatafl.presenter.Presenter;
import org.andarted.hnefatafl.common.QLog;
import org.andarted.hnefatafl.model.Model;
import org.andarted.hnefatafl.view.View;



public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> startApplication());
	}
	
	private static void startApplication() {
		System.out.println("Hnefatafl startet!");
        
		// - - - VERKABELN - - -
		
		Model model = new Model();								// Anlegen Model
		View view = new View();									// Anlegen View
		
		Presenter presenter = new Presenter(view, model);		// Anlegen Presenter für View & Model
		
		view.initializePresenter(presenter);				// View zurück zu Presenter verbinden
		model.initializePresenter(presenter);		
		
		// - - - BOOTEN - - - 
		
		QLog.log("main", "", "-> view.initializeView()");
		view.initializeView();
		QLog.log("main", "", "-> view.setGameBoard()");
		view.setGameBoard(); // Model zurück zu Presenter verbinden
		// presenter.startDefaultGame();
		// presenter.deligateInitializeView();		// GUI und alles erstellen
		QLog.log("main", "", "- - - MAIN IST DURCH - - -");
	}

}
