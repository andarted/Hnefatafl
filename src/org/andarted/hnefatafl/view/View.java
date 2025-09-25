package org.andarted.hnefatafl.view;

import org.andarted.hnefatafl.presenter.IPresenter;
import org.andarted.hnefatafl.presenter.Presenter;

import org.andarted.hnefatafl.common.GameBoard;
import org.andarted.hnefatafl.common.Variant;
import org.andarted.hnefatafl.common.SquareType;
import org.andarted.hnefatafl.common.PieceType;


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
		// this.gameBoard = new GameBoard(11);
		currendRenderer = new RendererSwing();
	}
	
	
	// - - - Methoden - - -
	
	public void initializeView() {
		createMainFrame();
		createMenuBar();
		createMainPanel();
		createBoardPanel();
		createSidePanel();
		createBoardPanelWrapper();
		assembleElements();
		
		initializeListener();
		
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
		boardPanel = new BoardPanel(gameBoard, currendRenderer, BASE_COLOR);
		boardPanel.setOpaque(false);
	}
    
	
	// - - - SIDE PANEL - - -
	
	private void createSidePanel() {
		sidePanel = new SidePanel();
		
		sidePanel.setSidePanelListener(new SidePanelListener() {
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
	
	void deligateStreamMouseXAxis(int row) {
		
	}
	
	void deligateStreamMouseYAxis(int col) {
		
	}
    
	
    // - - - ASSEMBLE - - -
    
    private void assembleElements() {
    	mainPanel.add(boardPanelWrapper, BorderLayout.CENTER);
    	mainPanel.add(sidePanel, BorderLayout.EAST);
    	mainFrame.add(mainPanel, BorderLayout.CENTER);
    	mainFrame.pack();
    	mainFrame.setLocationRelativeTo(null);
    }
    
    
    // - - - LISTENER - - -
    
    
    private void initializeListener() {
    	if(boardPanel != null) {
    		boardPanel.setBoardPanelListener(new BoardPanelListener() {
    			@Override
    			public void onFieldClick(int row, int col) {
    				if (presenter != null) {
    					presenter.onSquareClicked(row,col);
    				}
    			}
    			@Override
    			public void delegateOnFieldHover(int row, int col, int screenX, int screenY) {
    				if (presenter != null) {
    					sidePanel.updateHoverPos(screenX, screenY);
    					System.out.println("view initializeListener @O delegateOnFieldHover: check - übernehmen Sie sidePanel updateHoverPos.");
    					presenter.onFieldHover(row, col);
    					System.out.println("view initializeListener @O delegateOnFieldHover: check - übernehmen Sie presenter onFieldHover.");
    				}
    			}
    		});
    	}
    }
    
    /*
    private void initializeListener() {
    	this.presenter = presenter;
		if (presenter != null) {
			boardPanel.setBoardPanelListener((row,col) ->{
				presenter.onSquareClicked(row, col);
			});
		}
    }
    */
    
    // - - - VERBINDUNG ZUM PRESENTER - - -
    
    @Override
    public void initializePresenter(Presenter presenter) {
        this.presenter = presenter;
        
        /*
        if (boardPanel != null) {
        	boardPanel.setBoardPanelListener((row, col)->{ // Listener wird in die BoardPanel-Instanz 
        		if (presenter != null) {
        			presenter.onSquareClicked(row,col);
        		}
        	});
        }
        */
        
        
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
		this.boardPanel = new BoardPanel(gameBoard, currendRenderer, BASE_COLOR);
		
		System.out.println("View: [initializeNewGame]");
		
		// registriere neuen Listener
		initializeListener();
		
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
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
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
	public void setMouseHoverHighlight(int row, int col) {
		// renderer.showMouseHoverIndicator(row,col);
	}


	@Override
	public void delegateRepaint() {
		boardPanel.repaint();
		System.out.println("view delegateRepaint: check.");
	}

	/*
	@Override
	public void highlightOnHoverSquare(int row, int col) {
		// gameBoard.paintReachMap(originRow, originCol, fromRow, toRow, fromCol, toCol);
		// renderer.renderCell();
    	boardPanel.repaint();
		
	}
	*/
    
}
