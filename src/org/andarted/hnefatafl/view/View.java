package org.andarted.hnefatafl.view;

import org.andarted.hnefatafl.presenter.IPresenter;
import org.andarted.hnefatafl.presenter.Presenter;
import org.andarted.hnefatafl.common.Variant;
import org.andarted.hnefatafl.model.GameBoard;
import org.andarted.hnefatafl.model.PieceType;
import org.andarted.hnefatafl.model.SquareType;
import org.andarted.hnefatafl.common.QLog;
import org.andarted.hnefatafl.common.TraceLogger;


import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class View implements IView {
	
    private static final Color BASE_COLOR = Color.ORANGE;
	
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JPanel boardPanelWrapper;

	private SidePanel sidePanel;
	
    private JMenu newGame;
    private JMenu classicSetUp;
    private JMenu altSetUp;
    
    private JMenuItem exitItem;
    
    private JMenuItem sizeSevenItem;
    private JMenuItem sizeNineItem;
    private JMenuItem sizeElevenItem;
    private JMenuItem sizeThirdteenItem;
    
    private JMenuItem sizeSevenAltSetUpItem;
    private JMenuItem sizeNineAltSetUpItem;
    private JMenuItem sizeElevenAltSetUpItem;
    private JMenuItem sizeThirdteenAltSetUpItem;
	
    private JMenuItem setSpriteRenderer;
    private JMenuItem setSwingRenderer;
    private JMenuItem setIsoRenderer;
    
    private JLabel activePlayerLabel;
    
    private IPresenter presenter;
    private IRender renderer;
    
    private IRender currendRenderer;
    
    // private IRender rendererSprites101;
    
	private GameBoard gameBoard;
	private BoardPanel boardPanel;
	private int mouseHoverPosX = 0;
	private int mouseHoverPosY = 0;
	
	
	// - - - Konstruktor - - -
	
	public View() {
		currendRenderer = new RendererSwing();
	}
	
    // - - - VERBINDUNG ZUM PRESENTER - - -
    
    @Override
    public void initializePresenter(Presenter presenter) {
    	QLog.log("view", "initializePresenter", "view initialisiert presenter");
        this.presenter = presenter;
    }

	
	// - - - Methoden - - -
	
	public void initializeView() {
		QLog.log("view", "initializeView()", "-> view.createMainFrame()");
		createMainFrame();
		QLog.log("view", "initializeView()", "-> view.createMenuBar()");
		createMenuBar();
		QLog.log("view", "initializeView()", "-> view.createMainPanel()");
		createMainPanel();
		QLog.log("view", "initializeView()", "-> view.createBoardPanel()");
		createBoardPanel();
		QLog.log("view", "initializeView()", "-> view.createSidePanel()");
		createSidePanel();
		QLog.log("view", "initializeView()", "-> view.createBoardPanelWrapper()");
		createBoardPanelWrapper();
		QLog.log("view", "initializeView()", "-> view.assembleElements()");
		assembleElements();
		
		// initializeBoardPanelListener();
		QLog.log("view", "initializeView()", "-> view.mainFrame.setVisibile()");
        mainFrame.setVisible(true);
	}
	
	
	// - - - MENU BAR - - -
	
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("Datei");
		JMenu viewMenu = new JMenu("Ansicht");
		
		newGame = new JMenu("Neustart");
		classicSetUp = new JMenu("klassische Aufstellung");
		altSetUp = new JMenu("alternative Aufstellung");
		
		exitItem = new JMenuItem("Beenden");
		
		sizeSevenItem = new JMenuItem("7x7");
	    sizeNineItem = new JMenuItem("9x9");
	    sizeElevenItem = new JMenuItem("11x11");
	    sizeThirdteenItem = new JMenuItem("13x13");
	    
		sizeSevenAltSetUpItem = new JMenuItem("7x7");
	    sizeNineAltSetUpItem = new JMenuItem("9x9");
	    sizeElevenAltSetUpItem = new JMenuItem("11x11");
	    sizeThirdteenAltSetUpItem = new JMenuItem("13x13");
		
	    setSwingRenderer = new JMenuItem("View A");
	    setSpriteRenderer = new JMenuItem("View B");
	    setIsoRenderer = new JMenuItem("View C");
		
		fileMenu.add(newGame);
		fileMenu.add(exitItem);
		
		newGame.add(classicSetUp);
		newGame.add(altSetUp);
		
		classicSetUp.add(sizeSevenItem);
		classicSetUp.add(sizeNineItem);
		classicSetUp.add(sizeElevenItem);
		classicSetUp.add(sizeThirdteenItem);
		
		altSetUp.add(sizeSevenAltSetUpItem);
		altSetUp.add(sizeNineAltSetUpItem);
		altSetUp.add(sizeElevenAltSetUpItem);
		altSetUp.add(sizeThirdteenAltSetUpItem);
		
		viewMenu.add(setSwingRenderer);
		viewMenu.add(setSpriteRenderer);
		viewMenu.add(setIsoRenderer);
		
		
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		
		mainFrame.setJMenuBar(menuBar);
		
		// newGame.addActionListener(e -> presenter.handleNewGameItem())
		exitItem.addActionListener(e -> presenter.handleExitItem());
		sizeSevenItem.addActionListener(e -> presenter.handleNewGameItem(7, Variant.STANDARD));
		sizeNineItem.addActionListener(e -> presenter.handleNewGameItem(9, Variant.STANDARD));
		sizeElevenItem.addActionListener(e -> presenter.handleNewGameItem(11, Variant.STANDARD));
		sizeThirdteenItem.addActionListener(e -> presenter.handleNewGameItem(13, Variant.STANDARD));
		
		sizeSevenAltSetUpItem.addActionListener(e -> presenter.handleNewGameItem(7, Variant.ALTERNATIVE));
		sizeNineAltSetUpItem.addActionListener(e -> presenter.handleNewGameItem(9, Variant.ALTERNATIVE));
		sizeElevenAltSetUpItem.addActionListener(e -> presenter.handleNewGameItem(11, Variant.ALTERNATIVE));
		sizeThirdteenAltSetUpItem.addActionListener(e -> presenter.handleNewGameItem(13, Variant.ALTERNATIVE));
		
		setSpriteRenderer.addActionListener(e -> {
			IRender spriteRenderer101 = null;
			try {
				spriteRenderer101 = new RendererSprites("/sprites/spriteSheet00.png", 32, 32);
				currendRenderer = spriteRenderer101;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boardPanel.setRenderer(spriteRenderer101);
		});
		
		setSwingRenderer.addActionListener(e -> {
			IRender swingRenderer = new RendererSwing();
			currendRenderer = swingRenderer;
			boardPanel.setRenderer(swingRenderer);
		});
		
		setIsoRenderer.addActionListener(e -> {
			IRender isoRenderer = null;
			try {
				isoRenderer = new RendererIso("/sprites/spriteSheetIso00_64.png", 64, 64);
				currendRenderer = isoRenderer;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boardPanel.setRenderer(isoRenderer);
		});
		
	}
    
	
    // - - - MAIN FRAME - - - 
	
	private void createMainFrame() {
		mainFrame = new JFrame("Hnefatafl");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(new BorderLayout());
	}
	
	private void createMainPanel(){
		mainPanel = new JPanel(new BorderLayout(20,0));
		mainPanel.setBackground(BASE_COLOR);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
	}
	
	
	// - - - BOARD PANEL - - -
	
	private void createBoardPanelWrapper() {
		boardPanelWrapper = new JPanel();
		boardPanelWrapper.setLayout(new BoxLayout(boardPanelWrapper, BoxLayout.Y_AXIS));
		boardPanelWrapper.setOpaque(false);
		
		boardPanelWrapper.add(Box.createGlue());
		boardPanelWrapper.add(boardPanel);
		boardPanelWrapper.add(Box.createGlue());
	}
	
	private void createBoardPanel(){	
		renderer = currendRenderer;
		int boardSize = presenter.getBoardSize();
		boardPanel = new BoardPanel(boardSize, currendRenderer, BASE_COLOR);
		boardPanel.setOpaque(false);
		
		initializeBoardPanelListener();
	}
	
    private void initializeBoardPanelListener() {
    	if(boardPanel != null) {
    		this.boardPanel.setBoardPanelListener(new BoardPanelListener() {
    			@Override
    			public void onFieldClick(int row, int col) {
    				if (presenter != null) {
    					presenter.onSquareClicked(row,col);
    				}
    			}
    			@Override
    			public void delegateOnFieldHoverToView(int row, int col, int screenX, int screenY) {
    				if (presenter != null) {
    					TraceLogger.log("boardPanelListener [@view]", "delegateOnFieldHover [1/2]:", true, "view.passingOnUpdateHoverPosToSidePanel()");
    					passingOnUpdateHoverPosToSidePanel(screenX, screenY);
    					
    					TraceLogger.log("boardPanelListener [@view]", "delegateOnFieldHover [2/2]:", true, "delegateOnFieldHoverToPresenter()");
    					delegateOnFieldHoverToPresenter(row, col);
    					// presenter.onFieldHover(row, col);
    				}
    			}
    		});
    	}
    }
    
	
	// - - - SIDE PANEL - - -
	
	private void createSidePanel() {
		this.sidePanel = new SidePanel();
		
		initializeSidePanelListener();
	}
	
    private void initializeSidePanelListener() {
    	this.sidePanel.setSidePanelListener(new SidePanelListener() {
			@Override
			public void clickOnSkipButton() {
				presenter.handleDebugSkipButton();
			}
			@Override
			public void clickOnFreeMovementButton() {
				presenter.handleDebugFreeMovementButton();
			}
			@Override
			public void clickOnGetRoyalistButton() {
				presenter.handleDebugGetRoyalist();
			}
			@Override
			public void clickOnGetAnarchistButton() {
				presenter.handleDebugGetAnarchist();
			}
			@Override
			public void clickOnGetKingButton() {
				presenter.handleDebugGetKing();
			}
			@Override
			public void clickOnGetRemoveButton() {
				presenter.handleDebugGetRemove();
			}
			@Override
			public void clickOnShowRoyalistDeathZoneButton() {
				presenter.handleDebugShowRoyalistDeathZoneButton();
			}
			@Override
			public void clickOnShowAnarchistDeathZoneButton() {
				presenter.handleDebugShowAnarchistDeathZoneButton();
			}
		});
    }
	
	
    // - - - ASSEMBLE - - -
    
    private void assembleElements() {
    	mainPanel.add(boardPanelWrapper, BorderLayout.CENTER);
    	mainPanel.add(sidePanel, BorderLayout.EAST);
    	mainFrame.add(mainPanel, BorderLayout.CENTER);
    	mainFrame.pack();
    	mainFrame.setLocationRelativeTo(null);
    }
    
    
    // - - - METHODS FOR VIEW - - -
    
    int getBoardSize() {
    	return presenter.getBoardSize();
    }
    
    // - - - @OVERRIDE - - -
    
    
	@Override
	public void initializeNewGame(GameBoard gameBoard) {		// <- Löschen???
		// weg mit dem alten
		if (boardPanel != null && boardPanelWrapper != null) {
			boardPanelWrapper.remove(boardPanel);
			System.out.println("View initializeNewGame: altes Fenster wird gelöscht");
		}
		
		this.gameBoard = gameBoard;
		this.renderer = currendRenderer;
		this.boardPanel = new BoardPanel(presenter.getBoardSize(), currendRenderer, BASE_COLOR);
		
		System.out.println("View: [initializeNewGame]");
		
		// registriere neuen Listener
		initializeBoardPanelListener();
		
		// neues boardPanel ins layout adden & gesamtes GUI refreshen - boardPanelWrapper - Edition
		boardPanelWrapper.removeAll();
		boardPanelWrapper.add(Box.createGlue());
		boardPanelWrapper.add(boardPanel);
		boardPanelWrapper.add(Box.createGlue());
		mainPanel.revalidate();
		mainPanel.repaint();
		
		// fenstergröße Anpassen
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
	}
	
	

    
    
    /*
	@Override
	public void setAnarchist(int row, int col) {
    	gameBoard.setPieceAt(PieceType.ANARCHIST, row, col);
    	System.out.println("DEBUG Model: "+gameBoard.getPieceAt(row, col)); // => Muss ANARCHIST zeigen
    	boardPanel.repaint();
	}

	@Override
	public void setRoyalist(int row, int col) {
    	gameBoard.setPieceAt(PieceType.ROYALIST, row, col);
    	// System.out.println("View: "+gameBoard.getPieceAt(row, col));
    	boardPanel.repaint();
	}

	@Override
	public void setKing(int row, int col) {
    	gameBoard.setPieceAt(PieceType.KING, row, col);
    	// System.out.println("View: "+gameBoard.getPieceAt(row, col));
    	boardPanel.repaint();
	}

	@Override
	public void removePiece(int row, int col) {
    	gameBoard.setPieceAt(PieceType.NOBODY, row, col);
    	// System.out.println("View: "+gameBoard.getPieceAt(row, col));
    	boardPanel.repaint();
	}
	*/
	@Override
	public void setActivePlayerDisplay(String newActivePlayer) {
		if (activePlayerLabel != null) {
			activePlayerLabel.setText(newActivePlayer);
			activePlayerLabel.revalidate();
			activePlayerLabel.repaint();
		}
	}
	
	@Override
	public void updateDebugDisplay(String newActivePlayer) {
		if (sidePanel != null) {
			sidePanel.setActivePlayerDisplay(newActivePlayer);
		}
	}
	
	@Override
    public void congratWin(String string) {
        JOptionPane.showMessageDialog(mainFrame, string, "Spielende", JOptionPane.INFORMATION_MESSAGE);
    }
	
	@Override
    public void highlightReach(int originRow, int originCol, int fromRow, int toRow, int fromCol, int toCol) {
		gameBoard.paintReachMap(originRow, originCol, fromRow, toRow, fromCol, toCol);
		// renderer.renderCell();
    	boardPanel.repaint();
    }
    
	/*
    @Override
    public void delegateClearHighlight() {
    	gameBoard.ClearHighlight();
    	boardPanel.repaint();
    }
    
    @Override
    public void delegateSetHighlightAt(int row, int col) {
    	gameBoard.setHighlightAt(row, col);
    }
    
    @Override
    public void delegateClearHighlightAt(int row, int col) {
    	gameBoard.clearHighlightAt(row, col);
    }
    */
    
    public void delegateSetRenderer(IRender newRenderer) {
    	boardPanel.setRenderer(newRenderer);
    }
    /*
	private void set
    boardPanel.setBoardPanelListener(new BoardPanelListener(){
		@Override
		public void onMouseHover(int row, int col, int screenX, int screenY) {
			
		}
	});
	*/
    
	// - - - GETTER - - -
	public Color getBaseColor() {return BASE_COLOR;}

	
	// - - - SETTER - - -
	
	@Override
	public void setGameBoard() {
		this.gameBoard = presenter.getGameBoard();
		boardPanel.setGameBoard(gameBoard);
	}
	


	@Override
	public void onFieldHover(int row, int col, int screenX, int screenY) {
		if (presenter != null) {
			presenter.onFieldHover(row, col);
		}
		
	}

	@Override
	public void setMouseHoverPos(int row, int col) {
		this.mouseHoverPosX = row;
		this.mouseHoverPosY = col;
		renderer.clearMouseHoverIndicator();
		renderer.showMouseHoverIndicator(row, col);
	}


	@Override
	public void delegateRepaint() {
		boardPanel.repaint();
		TraceLogger.log("view", "delegateRepaint", true, "boardPanel.repaint()");
		System.out.println("END\n");
	}


	@Override
	public void delegateOnFieldHoverToPresenter(int row, int col) {
		TraceLogger.log("view", "delegateOnFieldHoverToPresenter:", true, "presenter.onFieldHover()");
		presenter.onFieldHover(row, col);
	}
	
	public void passingOnUpdateHoverPosToSidePanel(int screenX, int screenY) {
		TraceLogger.log("view", "delegateUpdateHoverPosToSidePanel:", true, "sidePanel.updateHoverPos()");
		sidePanel.updateHoverPos(screenX, screenY);
	}

	@Override
	public SquareType getSquareAt(int row, int col) {
		return presenter.getSquareAt(row, col);
	}

	@Override
	public PieceType getPieceAr(int row, int col) {
		return presenter.getPieceAr(row, col);
	}



    
}
