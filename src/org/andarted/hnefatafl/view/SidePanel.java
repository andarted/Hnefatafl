package org.andarted.hnefatafl.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import org.andarted.hnefatafl.common.QLog;
import org.andarted.hnefatafl.common.TraceLogger;

class SidePanel extends JPanel { // Listener Interface wird in der Methode setSideListener() "implementiert"
	private static final Dimension MODUL_MAX_DIMENSION = new Dimension(180, Integer.MAX_VALUE);
	private static final Dimension BUTTON_MAX_DIMENSION = new Dimension(Integer.MAX_VALUE, 24);
	
	private SidePanelListener listener;
	
	private JPanel displayPanel;
	private JPanel debugPanel;
	private JPanel infoPanel;
	private JPanel mousePosPanel;
	
    private JLabel activePlayerLabel;
    private String activePlayer = "nicht festgelegt";
    private JLabel mousePosLabel = new JLabel(" -1 -1");
    private int mouseXAxis = -1;
    private int mouseYAxis = -1;
	
    
    public void setSidePanelListener(SidePanelListener listener){
    	this.listener = listener;
    }
    
    public SidePanel() {
    	// setPreferredSize(new Dimension(220,450));
    	setBackground(Color.ORANGE);
		// setBorder(BorderFactory.createTitledBorder("Operation Panel"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// add(Box.createRigidArea(new Dimension(0, 5)));
		addDisplayModul();
		add(Box.createRigidArea(new Dimension(0, 8)));
		addDebugModul();
		add(Box.createRigidArea(new Dimension(0, 8)));
		addInfoModul();
		add(Box.createRigidArea(new Dimension(0, 8)));
		addMouseModul();
    }
    
    public void setActivePartyDisplay(String newActivePlayer) {
    	QLog.log("sidePanel", "setActiveDisplay()", "Setzte Display auf " + newActivePlayer);
    	if(activePlayerLabel != null) {
    		activePlayerLabel.setText(" " + newActivePlayer);
    		activePlayerLabel.revalidate();
    		activePlayerLabel.repaint();
    	}
    }
	
	// - - - ACTIVE PLAYER MODUL - - -
	
	private void addDisplayModul() {
		displayPanel = new JPanel();
		
		displayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		displayPanel.setMaximumSize(MODUL_MAX_DIMENSION);
		displayPanel.setBorder(BorderFactory.createTitledBorder("active player"));
				
	    displayPanel.setLayout(new BorderLayout());
	    activePlayerLabel = new JLabel(activePlayer);
	    activePlayerLabel.setFont(new Font("Arial", Font.BOLD, 10));
	    displayPanel.add(activePlayerLabel, BorderLayout.NORTH);
	    
	    add(displayPanel);
        // add(Box.createRigidArea(new Dimension(0, 10))); // Abstand
	}
	
	// - - - DEBUG MODUL - - -
	
	private void addDebugModul() {
		debugPanel = new JPanel();
		
		debugPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		debugPanel.setMaximumSize(MODUL_MAX_DIMENSION);
		debugPanel.setBorder(BorderFactory.createTitledBorder("debug modul"));
		
		debugPanel.setLayout(new BoxLayout(debugPanel, BoxLayout.Y_AXIS));
		
		addSkipToPlayerButton();
		debugPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		debugPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		addDebugModeTButton();
		addImportUnit1of2();
		addImportUnit2of2();
		
		add(debugPanel);
		// add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	private void addSkipToPlayerButton() {
		JButton skipToButton = new JButton("toggle active party");
		
		skipToButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		skipToButton.setMaximumSize(BUTTON_MAX_DIMENSION);
		skipToButton.setFont(new Font("Arial", Font.BOLD, 10));
		skipToButton.setHorizontalAlignment(SwingConstants.CENTER);
		skipToButton.setFocusPainted(false);
		skipToButton.addActionListener(e -> {
	    	if (listener != null) listener.clickOnSkipButton();
	    	// System.out.println("SidePanel: aktuelle Partei setzt aus");
	    	QLog.log("sidePanelLis[…]", "clickOnSkipButton()", "[über sidePanel.addSkipTpPlayerButton() gesetzt.");
	    	// presenter.handleDebugSkipButton(); <- habe extra einen Listener eingerichtet, damit das hier über die View läuft, Junge. 
		});
		debugPanel.add(skipToButton);
		// sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}

	
	private void addDebugModeTButton() {
		JToggleButton debugModeTButton = new JToggleButton("debug mode");
		
		debugModeTButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		debugModeTButton.setMaximumSize(BUTTON_MAX_DIMENSION);
		debugModeTButton.setFont(new Font("Arial", Font.BOLD, 10));
		debugModeTButton.setHorizontalAlignment(SwingConstants.CENTER);
		debugModeTButton.setFocusPainted(false);
		debugModeTButton.addActionListener(e -> {
			if (listener != null) listener.clickOnDebugModeTButton();
				System.out.println("Clicked on DebugModeTButton");
				// presenter.handleDebugFreeMovementButton();
		});
		
		
		debugPanel.add(debugModeTButton);
	}
	
	
	private void addImportUnit1of2() {
		JPanel importUnitPanel = new JPanel();
		
		importUnitPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		importUnitPanel.setMaximumSize(BUTTON_MAX_DIMENSION);
		
		importUnitPanel.setLayout(new BoxLayout(importUnitPanel, BoxLayout.X_AXIS));

		

		
		JButton importRoyalistButton = new JButton("R");
		importRoyalistButton.setFont(new Font("Arial", Font.BOLD, 10));
		importRoyalistButton.setFocusPainted(false);
		importRoyalistButton.addActionListener(e -> {
			if (listener != null) listener.clickOnGetRoyalistButton();
				System.out.println("View: new Royalist may arrive");
				// presenter.handleDebugGetRoyalist();
		});
		
		JButton importAnarchistButton = new JButton("A");
		importAnarchistButton.setFont(new Font("Arial", Font.BOLD, 10));
		importAnarchistButton.setFocusPainted(false);
		importAnarchistButton.addActionListener(e -> {
			if (listener != null) listener.clickOnGetAnarchistButton();
				System.out.println("View: new Anarchist may arrive");
				// presenter.handleDebugGetAnarchist();
		});
		
		importUnitPanel.add(Box.createRigidArea(new Dimension(6, 10)));
		importUnitPanel.add(importRoyalistButton);
		importUnitPanel.add(Box.createRigidArea(new Dimension(6, 10)));
		importUnitPanel.add(importAnarchistButton);
		importUnitPanel.add(Box.createRigidArea(new Dimension(6, 10)));
		
		debugPanel.add(importUnitPanel);
		// sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	private void addImportUnit2of2() {
		JPanel importUnitPanel = new JPanel();
		
		importUnitPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		importUnitPanel.setMaximumSize(BUTTON_MAX_DIMENSION);
		
		importUnitPanel.setLayout(new BoxLayout(importUnitPanel, BoxLayout.X_AXIS));
		
		JButton importKingButton = new JButton("K");
		importKingButton.setFont(new Font("Arial", Font.BOLD, 10));
		importKingButton.setFocusPainted(false);
		importKingButton.addActionListener(e -> {
			if (listener != null) listener.clickOnGetKingButton();
				System.out.println("View: new Kings may arrive");
				// presenter.handleDebugGetKing();
		});
		
		JButton exportUnitPanel = new JButton("BAN");
		exportUnitPanel.setFont(new Font("Arial", Font.BOLD, 10));
		exportUnitPanel.setFocusPainted(false);
		exportUnitPanel.addActionListener(e -> {
			if (listener != null) listener.clickOnGetRemoveButton();
				System.out.println("View: Einheit fängt ein neues Leben an");
				// presenter.handleDebugGetRemove();
		});
		
		importUnitPanel.add(Box.createRigidArea(new Dimension(6, 10)));
		importUnitPanel.add(importKingButton);		
		importUnitPanel.add(Box.createRigidArea(new Dimension(6, 10)));
		importUnitPanel.add(exportUnitPanel);
		importUnitPanel.add(Box.createRigidArea(new Dimension(6, 10)));
		
		debugPanel.add(importUnitPanel);
		// sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));		
	}
		
	
	// - - - INFO MODUL - - -
	
	private void addInfoModul() {
		infoPanel = new JPanel();
		
		infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoPanel.setMaximumSize(MODUL_MAX_DIMENSION);
		infoPanel.setBorder(BorderFactory.createTitledBorder("info"));
		
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		
		JToggleButton showRoyalistDeathZone = new JToggleButton("royalist death zone");
		showRoyalistDeathZone.setMaximumSize(BUTTON_MAX_DIMENSION);
		showRoyalistDeathZone.setFont(new Font("Arial", Font.BOLD, 10));
		showRoyalistDeathZone.setHorizontalAlignment(SwingConstants.CENTER);
		showRoyalistDeathZone.setFocusPainted(false);
		showRoyalistDeathZone.addActionListener(e -> {
			if (listener != null) listener.clickOnShowRoyalistDeathZoneButton();
				System.out.println("View: zeige Royalist Death Zone");
				// presenter.handleDebugShowRoyalistDeathZoneButton();
		});
		
		infoPanel.add(showRoyalistDeathZone);

		JToggleButton showAnarchistDeathZone = new JToggleButton("anarchist death zone");
		showAnarchistDeathZone.setMaximumSize(BUTTON_MAX_DIMENSION);
		showAnarchistDeathZone.setFont(new Font("Arial", Font.BOLD, 10));
		showAnarchistDeathZone.setHorizontalAlignment(SwingConstants.CENTER);
		showAnarchistDeathZone.setFocusPainted(false);
		showAnarchistDeathZone.addActionListener(e -> {
			if (listener != null) listener.clickOnShowAnarchistDeathZoneButton();
				System.out.println("View: zeige Anarchist Death Zone");
				// presenter.handleDebugShowAnarchistDeathZoneButton();
		});
		
		infoPanel.add(showAnarchistDeathZone);
		
		add(infoPanel);

		// add(infoPanel);
		// add(Box.createRigidArea(new Dimension(0, 5)));
		
	}
	
	
	
	// – – – MOUSE MODUL - - -
	
	private void addMouseModul() {
		mousePosPanel = new JPanel();
		
		mousePosPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mousePosPanel.setMaximumSize(MODUL_MAX_DIMENSION);
		mousePosPanel.setBorder(BorderFactory.createTitledBorder("mouse pos"));
				
		mousePosPanel.setLayout(new BorderLayout());
		mousePosLabel.setFont(new Font("Arial", Font.BOLD, 10));
		mousePosPanel.add(mousePosLabel, BorderLayout.NORTH);
		add(mousePosPanel);
	    
        // add(Box.createRigidArea(new Dimension(0, 10))); // Abstand
	}
	
	
	void updateHoverPos(int screenX, int screenY) {
		mouseXAxis = screenX;
		mouseYAxis = screenY;
		TraceLogger.log("sidePanel", "updateHoverPos:", true, "- - -");
		mousePosLabel.setText(" " + mouseXAxis + " " + mouseYAxis);
	}
	
	
	// - - - METHODS - - - 
	
	/*
	public void streamMouseXAxis(int screenX) {
		mouseXAxis = screenX;
	};
	
	public void streamMouseYAxis(int screenY) {
		mouseYAxis = screenY;
	};
	*/
	
	/*
	private void streamMousePosition(int screenX, int screenY) {	
	}
	*/
}
