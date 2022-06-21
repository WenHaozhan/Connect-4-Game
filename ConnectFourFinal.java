/*
	
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ConnectFourFinal {
	public static void main(String[] args) {
		
		GameBoard game = new GameBoard("Connect Four");
		Computer cpu = new Computer();
		
		game.setVisible(true);
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setSize(695, 680);
		game.setResizable(false);
		// Center window on screen
		game.setLocationRelativeTo(null);
	}
}

class GameBoard extends JFrame implements ActionListener {
	static final int WIDTH = 7;
	static final int HEIGHT = 6;
	
	int [][] board;
	boolean tie;
	boolean winFound;
	boolean multiplayerGame;
	boolean singlePlayerGame;
	int winner;
	int counter;
	int rowHold;
	int lastdropped;
	
	// GUI Variables
	JButton [] controls;
	JButton newGame;
	JButton menu;
	JButton start;
	JButton start2;
	JLabel header;
	JLabel [][] gridElement;
	JLabel status;
	JPanel controlsHolder;
	JPanel grid;
	JPanel south;
	JPanel statusHolder;
	JPanel menuHolder;
	Container c;
	
	// Buttons for Menu 
	JButton instructions;
	JButton hard;
	JButton easy;
	JButton medium;
	
	final ImageIcon headerImage = new ImageIcon("header.png");
	final ImageIcon square = new ImageIcon("square.png");
	final ImageIcon redPiece = new ImageIcon("red.png");
	final ImageIcon yellowPiece = new ImageIcon("yellow.png");
	
	public GameBoard(String title) {
		super(title);
		
		board = new int [HEIGHT][WIDTH];
		winner = 0;
		tie = false;
		counter = 1;
		winFound = false;
		multiplayerGame = false;
		singlePlayerGame = false;
		
		// GUI 
		c = getContentPane();
		
		// Connect 4 Playing Board
		controlsHolder = new JPanel();
		// Create a grid layout with 1 row, 7 columns
		controlsHolder.setLayout(new GridLayout(1, WIDTH));
		
		// Add control buttons to a the controlsHolder JPanel 
		controls = new JButton[WIDTH];
		for (int i = 0; i < WIDTH; i++) {
			controls[i] = new JButton("" + (i+1));
			controls[i].addActionListener(this);
			controlsHolder.add(controls[i]);
		}
		
		grid = new JPanel();
		grid.setLayout(new GridLayout(HEIGHT, WIDTH));
		//grid.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 0));

		gridElement = new JLabel[HEIGHT][WIDTH];
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				gridElement[row][col] = new JLabel();
				gridElement[row][col].setIcon(square);
				grid.add(gridElement[row][col]);
			}
		}
		
		// South JPanel components
		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		menu = new JButton("Return to Menu");
		menu.addActionListener(this);
		status = new JLabel("Red Player's Turn");
		
		// Add status JLabel to statusHolder JPanel with FlowLayout (to center the JLabel);
		statusHolder = new JPanel();
		statusHolder.setLayout(new FlowLayout());
		statusHolder.add(status);
		
		// Add components to South JPanel
		south = new JPanel();
		south.setLayout(new GridLayout(1, 4));
		south.add(newGame);
		south.add(statusHolder);
		south.add(menu);
		
		// Add the controlsHolder to the main container
		c.add(BorderLayout.NORTH, controlsHolder);
		// Add grid to main container
		c.add(BorderLayout.CENTER, grid);
		// Add bottom JPanel (South)
		c.add(BorderLayout.SOUTH, south);
		
		// Menu
		header = new JLabel();
		header.setIcon(headerImage);
		//header.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		
		// Multiplayer button start game 
		start = new JButton("Multiplayer");
		start.addActionListener(this);
		// Single Player button start game 
		start2 = new JButton("Single Player");
		start2.addActionListener(this);
		
		instructions = new JButton("Instructions");
		instructions.addActionListener(this);
		
		hard = new JButton("Hard");
		hard.addActionListener(this);
		
		medium = new JButton("Medium");
		medium.addActionListener(this);
		
		easy = new JButton("Easy");
		easy.addActionListener(this);
		
		// Add components to menuHolder JPanel
		menuHolder = new JPanel();
		menuHolder.setLayout(null);
		menuHolder.add(header);
		menuHolder.add(start);
		menuHolder.add(start2);
		menuHolder.add(instructions);
		
		menuHolder.add(hard);
		menuHolder.add(medium);
		menuHolder.add(easy);
		
		// Set background color to black
		menuHolder.setBackground(Color.BLACK);
		
		// Set font and color
		//start.setForeground(Color.BLUE);
		start.setBackground(Color.WHITE);
		start.setFont(new Font("Helvetica", Font.BOLD, 15));
		start2.setBackground(Color.WHITE);
		start2.setFont(new Font("Helvetica", Font.BOLD, 15));
		instructions.setBackground(Color.WHITE);
		instructions.setFont(new Font("Helvetica", Font.BOLD, 15));
		
		// Set header image location 
		header.setBounds(100, 100, 500, 270);
		
		// Set button locations (x, y) and button size (width, height)
		start.setBounds(190, 305, 150, 35);
		start2.setBounds(350, 305, 150, 35);
		instructions.setBounds(270, 349, 150, 35);
		c.add(menuHolder);
		
		hideGame();
		showMenu();
	}
	
	public void hideMenu() {
		menuHolder.setVisible(false);
	}
	
	public void hideGame() {
		controlsHolder.setVisible(false);
		grid.setVisible(false);
		south.setVisible(false);
	}
	
	public void showGame() {
		// Add the controlsHolder to the main container
		c.add(BorderLayout.NORTH, controlsHolder);
		// Add grid to main container
		c.add(BorderLayout.CENTER, grid);
		// Add bottom JPanel (South)
		c.add(BorderLayout.SOUTH, south);
		
		controlsHolder.setVisible(true);
		grid.setVisible(true);
		south.setVisible(true);
	}
	
	public void showMenu() {
		c.add(menuHolder);

		menuHolder.setVisible(true);
	}
	
	public void checkWin(){
		//checks rows
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 4; j++){
				if(board [i][j] == board [i] [j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3]&& board[i][j] != 0){
					winner = board[i][j];
					winFound = true;
					return;
				}
			}
		}
		
		//checks columns
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 3; j++){
				if(board [j][i] == board [j+1] [i] && board[j][i] == board[j+2][i] && board[j][i] == board[j+3][i]&& board[j][i] != 0){
					winner = board[j][i];
					winFound = true;
					return;
				}
			}
		}
		
		//check diagnols
		for(int i = 0; i <3; i++ ){
			for(int j = 0; j < 4; j++){
				if(board [i][j] == board [i+1] [j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3] && board[i][j] != 0){
					winner = board[i][j];
					winFound = true;
					return;
					
				}
			}
			for(int j = board.length -1; j > 2; j-- ){
				if(board [i][j] == board [i+1] [j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == board[i+3][j-3] && board[i][j] != 0){
					winner = board[i][j];
					winFound = true;
					return;
				}
			}
		}
		if(counter - 1 == WIDTH * HEIGHT){
			tie = true;
		}
	}
	
	public void processWin() {
		if (1 == winner) {
			JOptionPane.showMessageDialog(null, "Red Player WINS!", "WINNER", JOptionPane.PLAIN_MESSAGE);
			status.setText("Red Wins!");
		}
		else if (2 == winner){
			JOptionPane.showMessageDialog(null, "Yellow Player WINS!", "WINNER", JOptionPane.PLAIN_MESSAGE);
			status.setText("Yellow Wins!");
		}
		else if (tie){
			JOptionPane.showMessageDialog(null, "It's a TIE!", "TIE", JOptionPane.PLAIN_MESSAGE);
			status.setText("it's a Tie!");
		}
	}

	
	public void drop(int column, int player){
		for(int i = board.length - 1; i >= 0; i--){
			if(board[i][column] == 0){
				board[i][column] = player;
				counter++;
				rowHold = i;
				
				if (player == 1) {
					lastdropped = column;
				}
				return;
			}
		}
	}
	
	public boolean dropAvailable(int column) {
		for(int i = board.length -1; i >=0; i--){
			if(board[i][column] == 0) {
				return true;
			}
		}
		return false;
	}
	
	public void startNewGame() {
		winner = 0;
		tie = false;
		counter = 1;
		winFound = false;
		
		// Reset Board array and GUI grid
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				board[row][col] = 0;
				gridElement[row][col].setIcon(square);
			}
		}
		
		// Reset status text 
		status.setText("Red Player's Turn");
	}
	
	public void actionPerformed(ActionEvent e) {
		 
		for (int column = 0; column < WIDTH; column++) {
			
			// Code for multiplayer game
			if (e.getSource() == controls[column] && !winFound && multiplayerGame) {
				if (dropAvailable(column)) {
					// Player 2
					if (counter % 2 == 0) {
						// Drop yellow piece in virtual board
						drop(column, 2);
						
						// Change GUI for yellow drop
						gridElement[rowHold][column].setIcon(yellowPiece);
						status.setText("Red Player's Turn");
					}
					// Player 1
					else {
						// Drop red piece in virtual board
						drop(column, 1);
						
						// Change GUI for red drop
						gridElement[rowHold][column].setIcon(redPiece);
						status.setText("Yellow Player's Turn");
					}
					checkWin();
					processWin();
				}
				else {
					JOptionPane.showMessageDialog(null, "Can't Drop There", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		

		
		// Single player
		if (singlePlayerGame) {
			for (int column = 0; column < WIDTH; column++) {
				if (e.getSource() == controls[column] && !winFound && singlePlayerGame) {
					if (dropAvailable(column)) {
						// Player 1
						if (counter % 2 != 0) {
							// Drop red piece in virtual board
							drop(column, 1);
							
							// Change GUI for red drop
							gridElement[rowHold][column].setIcon(redPiece);
							status.setText("Yellow Player's Turn");
						}
						
						// Player 2
						// Drop yellow piece in virtual board
						Computer.move(3, this);
						// Change GUI for yellow drop
						System.out.println(Computer.whichcolumn);
						gridElement[rowHold][Computer.whichcolumn].setIcon(yellowPiece);
						status.setText("Red Player's Turn");
						for(int i =0; i < 6; i++){
							for(int j = 0; j < 7; j++){
								System.out.print(board[i][j] + " ");
							}
							System.out.println("");
						}
						checkWin();
						processWin();
					}
					else {
						JOptionPane.showMessageDialog(null, "Can't Drop There", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		
		}
		
		if (e.getSource() == newGame) {
			if (JOptionPane.showConfirmDialog(null, "Are You Sure?", "New Game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				startNewGame();
			}
		}
		
		if (e.getSource() == menu) {
			if (JOptionPane.showConfirmDialog(null, "Are You Sure?", "Return to Menu", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				hideGame();
				showMenu();
				
				multiplayerGame = false;
				singlePlayerGame = false;
			}
		}
		
		if (e.getSource() == start) {
			multiplayerGame = true;
			hideMenu();
			showGame();
			startNewGame();
		}
		
		if (e.getSource() == start2) {
			singlePlayerGame = true;
			hideMenu();
			showGame();
			startNewGame();
		}
		
		if (e.getSource() == instructions) {
			// Popup window showing the instructions
			JOptionPane.showMessageDialog(null, "To win, you must be the first player to get four of your colored pieces \nin a row either horizontally, vertically or diagonally on the game board. \n\nTo play, press the numbered button on top of the game board to drop \nyour piece in the corresponding column.", "Instructions", JOptionPane.PLAIN_MESSAGE);
			
		}
		
	}
}


class Computer{
	static int whichcolumn;
	//returns the column number the computer decides
	public static boolean isEmpty(int column, GameBoard b){
		for(int i = b.HEIGHT - 1; i >=0; i--){
			if(b.board[i][column] == 0){
				return true;
			}
		}
		return false;
	}
	public static void move(int difficulty, GameBoard b){
		//easy
		if(difficulty == 1){
			b.drop(easymove(b), 2);
		}else if(difficulty == 3){
			b.drop(hardmove(b), 2);
		}else{
			b.drop(mediummove(b),2);
		}
	}
	
	public static int easymove(GameBoard b){
		whichcolumn = (int)(Math.random() * b.WIDTH);
		if(isEmpty(whichcolumn, b)){
				return whichcolumn;
		}
		while(!isEmpty(whichcolumn, b)){
			whichcolumn = (int)(Math.random() * b.WIDTH);
			if(isEmpty(whichcolumn, b)){
				return whichcolumn;
			}

		}
		return 1;
	}
	
	public static int hardmove(GameBoard b){
		if(hasConnect3(b, 1)){
			return whichcolumn;
		}else if(hasDangerousConnect2(b, 1)){
			return whichcolumn;
		}else if(hasConnect3(b,2)){
			return whichcolumn;
		}else if(hasConnect2(b, 2)){
			return whichcolumn;
		}
		return playOn(b);
	}
	
	public static int mediummove(GameBoard b){
		int rnd = (int)(Math.random()*100) + 1;
		if(rnd >= 50){
			return easymove(b);
		}else{
			return hardmove(b);
		}
	}
	
	//when there isnt any good move, but a move has to be made
	public static int playOn(GameBoard b){
		if(isEmpty(b.lastdropped, b)){
			whichcolumn = b.lastdropped;
			return b.lastdropped;
		}else if(isEmpty(b.lastdropped + 1, b) && b.lastdropped != b.WIDTH-1 ){
			whichcolumn = b.lastdropped +1;
			return b.lastdropped + 1;
		}else if(isEmpty(b.lastdropped - 1, b) && b.lastdropped != 0 ){
			whichcolumn = b.lastdropped-1;
			return b.lastdropped - 1;
		}else{
			for(int i = 0; i < b.HEIGHT; i++){
				for(int j = 0; j < b.WIDTH; j++){
					if(b.board[i][j] == 0){
						whichcolumn = j;
						return j;
					}
				}
			}
		}
		return 1;
	}
	
	//checks for 3 in a row that could turn into a 4 in a row
	
	public static boolean hasConnect3(GameBoard b, int player){
		//checks rows
		for(int i = 0; i < b.HEIGHT; i++){
			for(int j = 0; j < b.WIDTH - 2; j++){
				if(b.board [i][j] == b.board [i][j+1] && b.board[i][j] == b.board[i][j+2] &&  b.board[i][j] == player){
					if(j != b.WIDTH - 3 && b.board[i][j+3] == 0){
						whichcolumn = j + 3;
						return true;
					}
					if(j != 0 && b.board[i][j-1] == 0){
						whichcolumn = j-1;
						return true;
					}
				}
			}
		}
		
		//checks columns
		for(int i = 0; i < b.WIDTH; i++){
			for(int j = 1; j < b.HEIGHT - 3; j++){
				if(b.board [j][i] == b.board [j+1] [i] && b.board[j][i] == b.board[j+2][i] &&  b.board[j][i] == player){
					if(b.board[j-1][i] == 0){
						whichcolumn = i;
						return true;
					}
		
				}
			}
		}
		
		//check diagnols
		for(int i = 0; i < b.HEIGHT - 2; i++ ){
			for(int j = 0; j < b.WIDTH - 2; j++){
				if(b.board [i][j] == b.board [i+1] [j+1] && b.board[i][j] == b.board[i+2][j+2] &&  b.board[i][j] == player){
					if(j != 0 && i != 0 && j != b.WIDTH - 3 && i != b.HEIGHT - 3){
						if(b.board[i-1][j-1] == 0){
							whichcolumn = j-1;
							return true;
						}
						if(b.board[i+3][j+3] == 0){
							whichcolumn = j+3;
							return true;
						}
					}else if((j != b.WIDTH - 3 && i == 0)){
						if(b.board[i+3][j+3] == 0){
							whichcolumn = j+3;
							return true;
						}
						
					}else if(j == 0 && i != b.HEIGHT - 3){
						if(b.board[i+3][j+3] == 0){
							whichcolumn = j+3;
							return true;
						}
					}
					else if((j == b.WIDTH - 3 && i != 0)){
						if(b.board[i-1][j-1] ==0){
							whichcolumn = j-1;
							return true;
						}
					}else if(j != 0 && i == b.HEIGHT - 3){
						if(b.board[i-1][j-1] ==0){
							whichcolumn = j-1;
							return true;
						}
					}
				}
			}
			for(int j = b.WIDTH -1; j > 1; j-- ){
				if(b.board [i][j] == b.board [i+1] [j-1] && b.board[i][j] == b.board[i+2][j-2] &&  b.board[i][j] == player){
					if((j != 2 && i == 0) ){
						if(b.board[i+3][j-3] == 0){
							whichcolumn = j-3;
							return true;
						}
						
					}else if((j == b.WIDTH-1 && i != b.HEIGHT - 3)){
						if(b.board[i+3][j-3] == 0){
							whichcolumn = j-3;
							return true;
						}
					}else if((j == 2 && i != 0) ){
						if(b.board[i-1][j+1] ==0){
							whichcolumn = j+1;
							return true;
						}
					}else if((j != b.WIDTH-1 && i == b.HEIGHT - 3)){
						if(b.board[i-1][j+1] ==0){
							whichcolumn = j+1;
							return true;
						}
					}else{
						if(b.board[i-1][j+1] == 0){
							whichcolumn = j+1;
							return true;
						}
						if(b.board[i+3][j-3] == 0){
							whichcolumn = j-3;
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	//checks for connect 2 that is unsurrounded by any pieces
	public static boolean hasDangerousConnect2(GameBoard b, int player){
		//checks rows
		for(int i = 0; i < b.HEIGHT; i++){
			for(int j = 1; j < b.WIDTH - 2; j++){
				if(b.board [i][j] == b.board [i][j+1] && b.board[i][j] == player){
					if(j == 1 && b.board[i][j+3] == 0 && b.board[i][j+2] == 0 && b.board[i][j-1]==0){
						whichcolumn = j + 2;
						return true;
					}else if(j == b.WIDTH - 3 && b.board[i][j-1]==0 && b.board[i][j+2]==0 && b.board[i][j-2]==0){
						whichcolumn = j-1;
						return true;
					}else if((b.board[i][j-1] == 0 && b.board[i][j+2]==0 )){ 
						if(b.board[i][j-2]==0){
							whichcolumn = j-1;
							return true;
						}else if(b.board[i][j+3]==0){
							whichcolumn = j+2;
							return true;
						}
					}
					
				}
			}
		}
		return false;
	}
	public static boolean hasConnect2(GameBoard b, int player){
		for(int i = 0; i < b.HEIGHT; i++){
			for(int j = 0; j < b.WIDTH - 1; j++){
				if(j == 0){
					if(b.board[i][j] == b.board[i][j+1] && b.board[i][j] == player && b.board[i][j+2] == 0){
						whichcolumn = j+2;
						return true;
					}
				}else if(j == b.WIDTH - 2){
					if(b.board[i][j] == b.board[i][j+1] && b.board[i][j] == player && b.board[i][j-1] == 0){
						whichcolumn = j-1;
						return true;
					}
				}else{
					if(b.board[i][j] == b.board[i][j+1] && b.board[i][j] == player){
						if(b.board[i][j+2]==0){
							whichcolumn = j+2;
							return true;
						}else if(b.board[i][j-1] == 0){
							whichcolumn = j-1;
							return true;
						}
					}
				}
			}
		}

		
		//check diagnols
		for(int i = 0; i < b.HEIGHT - 1; i++ ){
			for(int j = 0; j < b.WIDTH - 1; j++){
				if(b.board [i][j] == b.board [i+1] [j+1] && b.board[i][j] == player){
					if(j != 0 && i != 0 && j != b.WIDTH - 2 && i != b.HEIGHT - 2){
						if(b.board[i-1][j-1] == 0){
							whichcolumn = j-1;
							return true;
						}
						if(b.board[i+2][j+2] == 0){
							whichcolumn = j+2;
							return true;
						}
					}else if((j != b.WIDTH - 2 && i == 0)){
						if(b.board[i+2][j+2] == 0){
							whichcolumn = j+2;
							return true;
						}
						
					}else if(j == 0 && i != b.HEIGHT - 2){
						if(b.board[i+2][j+2] == 0){
							whichcolumn = j+2;
							return true;
						}
					}else if((j == b.WIDTH - 2 && i != 0)){
						if(b.board[i-1][j-1] ==0){
							whichcolumn = j-1;
							return true;
						}
					}else if((j != 0 && i == b.HEIGHT - 2)){
						if(b.board[i-1][j-1] ==0){
							whichcolumn = j-1;
							return true;
						}
					}
				}
			}
			for(int j = b.WIDTH -1; j > 0; j-- ){
				if(b.board [i][j] == b.board [i+1] [j-1] && b.board[i][j] == player){
					if((j != 1 && i == 0) && (j == b.WIDTH-1 && i != b.HEIGHT -2)){
						if(b.board[i+2][j-2] == 0){
							whichcolumn = j-2;
							return true;
						}
						
					}else if((j == 1 && i != 0) && (j != b.WIDTH-1 && i == b.HEIGHT - 2)){
						if(b.board[i-1][j+1] ==0){
							whichcolumn = j+1;
							return true;
						}
					}else{
						if(b.board[i-1][j+1] == 0){
							whichcolumn = j+1;
							return true;
						}
						if(b.board[i+2][j-2] == 0){
							whichcolumn = j-2;
							return true;
						}
					}
				}
			}
		}
		
				//checks columns
		for(int i = 0; i < b.WIDTH; i++){
			for(int j = 1; j < b.HEIGHT - 2; j++){
				if(b.board [j][i] == b.board [j+1] [i] &&  b.board[j][i] == player){
					if(b.board[j-1][i] == 0){
						whichcolumn = i;
						return true;
					}
		
				}
			}
		}
		return false;
	}
}
