package org.andarted.hnefatafl;

import javax.swing.SwingUtilities;

import org.andarted.hnefatafl.presenter.Presenter;
import org.andarted.hnefatafl.view.View;



public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> startApplication());
	}
	
	private static void startApplication() {
		System.out.println("Hnefatafl startet!");
        
		View view = new View();
		// Presenter presenter = new Presenter(view);
		Presenter presenter = new Presenter(view);
		view.initializePresenter(presenter); // Verbindung herstellen
	}

}
