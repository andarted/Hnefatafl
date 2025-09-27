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
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import org.andarted.hnefatafl.common.TraceLogger;

class SidePanel extends JPanel { // Listener Interface wird in der Methode setSideListener() "implementiert"
	private static final Dimension MODUL_MAX_DIMENSION = new Dimension(180, Integer.MAX_VALUE);
	private static final Dimension BUTTON_MAX_DIMENSION = new Dimension(Integer.MAX_VALUE, 24);
	
	private SidePanelListener listener;
	
	
	private JPanel debugPanel;
	private JPanel infoPanel;
	
    private JLabel activePlayerLabel;
    private String activePlayer = "nicht festgelegt";
    private JLabel mousePositionLabel = new JLabel("pos. -1 -1");
    private int mouseXAxis = -1;
    private int mouseYAxis = -1;
	
    
    public void setSidePanelListener(SidePanelListener listener){
    	this.listener = listener;
    }
    
    public SidePanel() {
    	// setPreferredSize(new Dimension(220,450));
    	setBackground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createTitledBorder("Operation Panel"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createRigidArea(new Dimension(0, 5)));
		addActivePlayerModul();
		addDebugModul();
		addInfoModul();
		addMouseModul();
    }
    
    public void setActivePlayerDisplay(String newActivePlayer) {
    	if(activePlayerLabel != null) {
    		activePlayerLabel.setText(newActivePlayer);
    		activePlayerLabel.revalidate();
    		activePlayerLabel.repaint();
    	}
    }
	
	// - - - ACTIVE PLAYER MODUL - - -
	
	private void addActivePlayerModul() {
		JPanel activePlayerPanel = new JPanel();
		
		activePlayerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		activePlayerPanel.setMaximumSize(MODUL_MAX_DIMENSION);
		activePlayerPanel.setBorder(BorderFactory.createTitledBorder("aktive player"));
				
	    activePlayerPanel.setLayout(new BorderLayout());
	    activePlayerLabel = new JLabel(activePlayer);
	    activePlayerLabel.setFont(new Font("Arial", Font.BOLD, 10));
	    activePlayerPanel.add(activePlayerLabel, BorderLayout.NORTH);
	    
	    add(activePlayerPanel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Abstand
	}
	
	// - - - DEBUG MODUL - - -
	
	private void addDebugModul() {
		debugPanel = new JPanel();
		
		debugPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		debugPanel.setMaximumSize(MODUL_MAX_DIMENSION);
		debugPanel.setBorder(BorderFactory.createTitledBorder("debug modul"));
		
		debugPanel.setLayout(new BoxLayout(debugPanel, BoxLayout.Y_AXIS));
		
		addSkipToPlayerButton();
		addFreeMovement();
		addImportUnit();
		addImportKingButton();
		addExportUnit();
				
		add(debugPanel);
		// add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	private void addSkipToPlayerButton() {
		JButton skipToButton = new JButton("skip to player");
		
		skipToButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		skipToButton.setMaximumSize(BUTTON_MAX_DIMENSION);
		skipToButton.setFont(new Font("Arial", Font.BOLD, 10));
		skipToButton.setHorizontalAlignment(SwingConstants.CENTER);
		skipToButton.setFocusPainted(false);
		skipToButton.addActionListener(e -> {
	    	if (listener != null) listener.clickOnSkipButton();
	    	System.out.println("SidePanel: aktuelle Partei setzt aus");
	    	// presenter.handleDebugSkipButton(); <- habe extra einen Listener eingerichtet, damit das hier über die View läuft, Junge. 
		});
		
		
		debugPanel.add(skipToButton);
		// sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	private void addFreeMovement() {
		JToggleButton freeMovementButton = new JToggleButton("aktivate Free Movement");
		
		freeMovementButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		freeMovementButton.setMaximumSize(BUTTON_MAX_DIMENSION);
		freeMovementButton.setFont(new Font("Arial", Font.BOLD, 10));
		freeMovementButton.setHorizontalAlignment(SwingConstants.CENTER);
		freeMovementButton.setFocusPainted(false);
		freeMovementButton.addActionListener(e -> {
			if (listener != null) listener.clickOnFreeMovementButton();
				System.out.println("View: Free Movement Aktivated");
				// presenter.handleDebugFreeMovementButton();
		});
		
		// debugPanel.add(freeMovementButton);
		// sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	private void addImportUnit() {
		JPanel importUnitPanel = new JPanel();
		
		importUnitPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		importUnitPanel.setMaximumSize(BUTTON_MAX_DIMENSION);
		
		importUnitPanel.setLayout(new BoxLayout(importUnitPanel, BoxLayout.X_AXIS));

		
		JButton importAnarchistButton = new JButton("A");
		importAnarchistButton.setFont(new Font("Arial", Font.BOLD, 10));
		importAnarchistButton.setFocusPainted(false);
		importAnarchistButton.addActionListener(e -> {
			if (listener != null) listener.clickOnGetAnarchistButton();
				System.out.println("View: new Anarchist may arrive");
				// presenter.handleDebugGetAnarchist();
		});
		
		JButton importRoyalistButton = new JButton("R");
		importRoyalistButton.setFont(new Font("Arial", Font.BOLD, 10));
		importRoyalistButton.setFocusPainted(false);
		importRoyalistButton.addActionListener(e -> {
			if (listener != null) listener.clickOnGetRoyalistButton();
				System.out.println("View: new Royalist may arrive");
				// presenter.handleDebugGetRoyalist();
		});
		
		
		importUnitPanel.add(Box.createRigidArea(new Dimension(6, 10)));
		importUnitPanel.add(importRoyalistButton);
		importUnitPanel.add(Box.createRigidArea(new Dimension(6, 10)));
		importUnitPanel.add(importAnarchistButton);
		importUnitPanel.add(Box.createRigidArea(new Dimension(6, 10)));
		
		debugPanel.add(importUnitPanel);
		// sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}

	private void addImportKingButton() {
		JButton importKingButton = new JButton("lang lebe der König");
		
		importKingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		importKingButton.setMaximumSize(BUTTON_MAX_DIMENSION);
		importKingButton.setFocusPainted(false);
		importKingButton.setFont(new Font("Arial", Font.BOLD, 10));
		importKingButton.addActionListener(e -> {
			if (listener != null) listener.clickOnGetKingButton();
				System.out.println("View: new Kings may arrive");
				// presenter.handleDebugGetKing();
		});
		
		debugPanel.add(importKingButton);
		
	}
	
	private void addExportUnit() {
		JButton exportUnitPanel = new JButton("verbanne Einheit");
		
		exportUnitPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		exportUnitPanel.setMaximumSize(BUTTON_MAX_DIMENSION);
		exportUnitPanel.setFocusPainted(false);
		exportUnitPanel.setFont(new Font("Arial", Font.BOLD, 10));
		exportUnitPanel.addActionListener(e -> {
			if (listener != null) listener.clickOnGetRemoveButton();
				System.out.println("View: Einheit fängt ein neues Leben an");
				// presenter.handleDebugGetRemove();
		});
		
		debugPanel.add(exportUnitPanel);
		// sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
	}
		
	// - - - INFO MODUL - - -
	
	private void addInfoModul() {
		infoPanel = new JPanel();
		
		infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoPanel.setMaximumSize(MODUL_MAX_DIMENSION);
		infoPanel.setBorder(BorderFactory.createTitledBorder("info"));
		
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		
		JToggleButton showRoyalistDeathZone = new JToggleButton("Royalist Death-Zone");
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

		JToggleButton showAnarchistDeathZone = new JToggleButton("Anarchist Death-Zone");
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
		add(Box.createRigidArea(new Dimension(0, 5)));
		
	}
	
	
	// – – – MOUSE MODUL - - -
	
	private void addMouseModul() {
		JPanel mousePositionPanel = new JPanel();
		
		mousePositionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mousePositionPanel.setMaximumSize(MODUL_MAX_DIMENSION);
		mousePositionPanel.setBorder(BorderFactory.createTitledBorder("aktive player"));
				
		mousePositionPanel.setLayout(new BorderLayout());
		// mousePositionLabel = new JLabel(mousePosition);
		mousePositionLabel.setFont(new Font("Arial", Font.BOLD, 10));
		mousePositionPanel.add(mousePositionLabel, BorderLayout.NORTH);
	    
	    add(mousePositionLabel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Abstand
	}
	
	public void updateHoverPos(int screenX, int screenY) {
		mouseXAxis = screenX;
		mouseYAxis = screenY;
		TraceLogger.log("sidePanel", "updateHoverPos:", true, "- - -");
		mousePositionLabel.setText("pos. " + mouseXAxis + " " + mouseYAxis);
	}
	
	
	// - - - METHODS - - - 
	
	public void streamMouseXAxis(int screenX) {
		mouseXAxis = screenX;
	};
	
	public void streamMouseYAxis(int screenY) {
		mouseYAxis = screenY;
	};
	
	/*
	private void streamMousePosition(int screenX, int screenY) {	
	}
	*/
}
