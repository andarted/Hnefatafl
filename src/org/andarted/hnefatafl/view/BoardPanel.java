package org.andarted.hnefatafl.view;



import org.andarted.hnefatafl.common.TraceLogger;
import org.andarted.hnefatafl.model.GameBoard;
import org.andarted.hnefatafl.model.SquareType;
import org.andarted.hnefatafl.model.PieceType;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

class BoardPanel extends JPanel{
	
	private IRender renderer;
	private GameBoard gameBoard;
	
	private final int boardSize;
	private final int cellSize = 40;
	private BoardPanelListener listener;
	
	/*
	private int mouseOverRow = -1;
	private int mouseOverCol = -1;
	*/
	
	// - - - Konstuktor - - -
	
	BoardPanel(int boardSize, IRender renderer, Color baseColor) {
		/*
		// - - - gibt mir genauere Fehlermeldung aus - - -
		if(board == null) throw new IllegalArgumentException("GameBoard must not be null");
		if(renderer == null) throw new IllegalArgumentException("Renderer must not be null");
		*/
		// this.gameBoard = gameBoard;
		this.renderer = renderer;
		this.boardSize = boardSize;
		int panelSize = boardSize * cellSize;
		setPreferredSize(new Dimension(panelSize + 570, panelSize));
		addMouseListener(createMouseListener());
		addMouseMotionListener(createMouseMotionListener());
		
		setBackground(baseColor);
	}
	
	
	// - - - REGISTRIERUNG LISTENER - - -
	
	void setBoardPanelListener(BoardPanelListener listener) {
		this.listener = listener;
	}
	
	
	// - - - METHODEN - - - 

	private void paintBoard(Graphics2D g) {
		
		if(gameBoard == null) {
		    System.out.println("gameBoard ist NULL bei paintBoard! Überprüfung nötig!");
		    return; // oder: throw new IllegalStateException(...)
		}
		
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				
				SquareType squareType = gameBoard.getSquareAt(row, col);
				PieceType pieceType = gameBoard.getPieceAt(row, col);
				boolean highlight = gameBoard.isHighlighted(row, col);
				
				SquareAppearance squareAppearance = SquareTypeAppearanceMapper.getAppearance(squareType);
				PieceAppearance pieceAppearance =
						(pieceType == PieceType.NOBODY) ? null :PieceTypeAppearanceMapper.getAppearance(pieceType);
						// Bei NOBODY ist pieceAppearance null, damit der Render weiß, dass da nix hingehört.
				
				// if (squareAppearance == null) continue; // Defensive: Feld wird nicht gerendert
				
				if (squareType == null) System.out.println("squareType null bei " + row + "," + col);
				if (squareAppearance == null) System.out.println("squareAppearance null bei sqType " + squareType + " @ " + row + "," + col);
				if (pieceAppearance == null && pieceType != PieceType.NOBODY) System.out.println("pieceAppearance null trotz pieceType=" + pieceType + " bei " + row + "," + col);
				
				renderer.renderCell(g, row, col, cellSize, squareAppearance, pieceAppearance, highlight);
				
			}
		}
	}
	
	// - - - METHODS / LISTENER - - - 
	
	private MouseListener createMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClick(e.getX(),e.getY());
			}

			/*
			@Override
			public void mouseEntered(MouseEvent e) {
				handleMouseEntered(e.getX(),e.getY());
			}
			*/
			@Override
			public void mouseExited(MouseEvent e) {
				handleMouseExited(e.getX(),e.getY());
			}
			
		};
	}
	
	private MouseMotionListener createMouseMotionListener() {
		return new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				System.out.println("- - - USER HOVERS MOUSE OVER SQUARE - - -");
				System.out.println(" CLASS                          METHOD                               CHECK                    METHOD CALL");
				TraceLogger.log("MouseMotionAda… [@boardPanel]", "mouseMoved:", true, "boardPanel.handleMouseMoved()");
				handleMouseMoved(e.getX(),e.getY());
			}
		};
	}
	
	
	private void handleClick(int screenX, int screenY) {
		Point gridCoordinates = renderer.screenToGrid(screenX, screenY, cellSize);
		int col = gridCoordinates.x;
		int row = gridCoordinates.y;
		System.out.println("BaordPanel handleClick: MouseClick registered at " + screenX + " / " + screenY);
		if(isInsideBoard(row,col) && listener != null) {
			System.out.println("BaordPanel handleClick: MouseClick registered on " + row + " / " + col);
			listener.onFieldClick(row, col);
		}
	}
	
	private boolean isInsideBoard(int row, int col) {
		return row >= 0 && row < boardSize
				&& col >= 0 && col < boardSize;
	}
	
	
	public void handleMouseMoved(int screenX, int screenY) {
		TraceLogger.log("boardPanel", "handleMouseMoved[1/2]:", true, "renderer.screendToGrid()");
		Point gridCoordinates = renderer.screenToGrid(screenX, screenY, cellSize);
		int col = gridCoordinates.x;
		int row = gridCoordinates.y;
		
		if(isInsideBoard(row,col) && listener != null) {
			TraceLogger.log("boardPanel", "handleMouseMoved[2/2]:", true, "listener.delegateOnFieldHoverToView()");
			// System.out.println("BoardPanel handleMouseMoved: check - BoardPanelListener überbringen Sie folgendes: übernehmen Sie View onMouseHover.");
			listener.delegateOnFieldHoverToView(row, col, screenX, screenY);
			/*
			gameBoard.setHighlightAt(row, col);
			repaint();
			*/
		}
	}
	
	/*
	public void handleMouseEntered(int screenX, int screenY) {
		Point gridCoordinates = renderer.screenToGrid(screenX, screenY, cellSize);
		int col = gridCoordinates.x;
		int row = gridCoordinates.y;
		if(isInsideBoard(row,col) && listener != null) {
			listener.onFieldClick(row, col);
			System.out.println("BaordPanel handleMouseEntered: MouseEntered registered on " + row + " / " + col);
		}
		System.out.println("BaordPanel handleMouseEntered: MouseEntered registered at " + screenX + " / " + screenY);
	}
	*/
	
	public void handleMouseExited(int screenX, int screenY) {
		listener.delegateOnFieldHoverToView(-1, -1, -1, -1);
		TraceLogger.log("boardPanel", "handleMouseExited:", true, "- - -");
		System.out.println("- - - MouseExited registered at " + screenX + " / " + screenY + "\n");
		
		// Point gridCoordinates = renderer.screenToGrid(screenX, screenY, cellSize);
		
		/*
		int col = gridCoordinates.x;
		int row = gridCoordinates.y;
		if(!isInsideBoard(row,col) && listener != null) {
			listener.onMouseHover(-1, -1, -1, -1);
			// System.out.println("BaordPanel handleMouseExited: MouseExited registered on " + row + " / " + col);
		}
		*/
	}
	
	
	
	private void paintGrid(Graphics2D g) {
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				paintCell(g, row, col);
			}
		}
	}
	
	private void paintCell(Graphics2D g, int row, int col) {
		int x = col*cellSize;
		int y = row*cellSize;
		g.setColor(Color.WHITE);
		g.fillRect(x,y,cellSize,cellSize);
		g.setColor(Color.BLACK);
		g.drawRect(x,y,cellSize, cellSize);
	}

	
		// - - - SETTER - - -
	
	public void setRenderer(IRender newRenderer) {
		this.renderer = newRenderer;
		repaint();
	}
	
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
		repaint();
	}
	
	
	// - - - OVERRIDES - - -
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (gameBoard == null) {
	        System.out.println("paintComponent: gameBoard ist null -> KEIN Drawing");
	        return;
	    }
		
		paintBoard((Graphics2D) g);
		// Eigenes Zeichnen hier
		
	}
	


	/*
	public void setBoardPanelListener(Object listener2) {
		// 
		
	}
	*/


}
