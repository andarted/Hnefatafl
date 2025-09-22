package org.andarted.hnefatafl.view;


import org.andarted.hnefatafl.common.SquareType;
import org.andarted.hnefatafl.common.PieceType;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

class BoardPanel extends JPanel{
	
	private IRender renderer;
	private GameBoard gameBoard;
	
	private final int boardSize;
	private final int cellSize = 40;
	private BoardPanelListener listener;
	
	
	// - - - Konstuktor - - -
	
	BoardPanel(GameBoard board, IRender renderer, Color baseColor) {
		/*
		// - - - gibt mir genauere Fehlermeldung aus - - -
		if(board == null) throw new IllegalArgumentException("GameBoard must not be null");
		if(renderer == null) throw new IllegalArgumentException("Renderer must not be null");
		*/
		this.gameBoard = board;
		this.renderer = renderer;
		this.boardSize = gameBoard.getBoardSize();
		int panelSize = board.getBoardSize() * cellSize;
		setPreferredSize(new Dimension(panelSize + 570, panelSize));
		addMouseListener(createMouseListener());
		
		setBackground(baseColor);
	}
	
	// - - - REGISTRIERUNG LISTENER - - -
	
	void setBoardPanelListener(BoardPanelListener listener) {
		this.listener = listener;
	}
	
	
	// - - - METHODEN - - - 

	private void paintBoard(Graphics2D g) {
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				
				SquareType square = gameBoard.getSquareAt(row, col);
				PieceType piece = gameBoard.getPieceAt(row, col);
				boolean highlight = gameBoard.isHighlighted(row, col);
				renderer.renderCell(g, row, col, cellSize, square, piece, highlight);
				
			}
		}
	}
	
	private MouseListener createMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClick(e.getX(),e.getY());
			}
		};
	}
	
	/*
	private void handleClick(int pixelX, int pixelY) {
		int col = pixelX / cellSize;
		int row = pixelY / cellSize;
		if(isInsideBoard(row,col) && listener != null) {
			listener.onFieldClick(row, col);
		}
	}
	*/
	
	private void handleClick(int screenX, int screenY) {
		Point gridCoordinates = renderer.screenToGrid(screenX, screenY, cellSize);
		int col = gridCoordinates.x;
		int row = gridCoordinates.y;
		if(isInsideBoard(row,col) && listener != null) {
			listener.onFieldClick(row, col);
		}
	}
	
	private boolean isInsideBoard(int row, int col) {
		return row >= 0 && row < boardSize
				&& col >= 0 && col < boardSize;
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
	
	// - - - OVERRIDES - - -
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintBoard((Graphics2D) g);
		// Eigenes Zeichnen hier
		
	}
	


}
