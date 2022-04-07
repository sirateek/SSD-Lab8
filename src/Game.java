import java.awt.*;
import javax.swing.*;
import java.awt.event.*;



public class Game extends JFrame{
    private Board board;
    private int boardSize = 20;
    private int mineCount = 40;
    private GridUI gridUI;
    public static Color[] NUMBER_COLOR = {Color.black, Color.blue, Color.green, Color.red, Color.magenta};

    public Game() {
        board = new Board(boardSize, mineCount);
        gridUI = new GridUI();
        add(gridUI);
        pack();

        // board.getCell(2, 3).setFlagged(true);

    }

    public void start() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
    }


    class GridUI extends JPanel {
        public static final int CELL_PIXEL_SIZE = 30;
        private Image imageCell;
        private Image imageFlag;
        private Image imageMine;


        public GridUI() {
            setPreferredSize(new Dimension(boardSize * CELL_PIXEL_SIZE, (boardSize+2) * CELL_PIXEL_SIZE));
            imageCell = new ImageIcon("imgs/Cell.png").getImage();
            imageFlag = new ImageIcon("imgs/Flag.png").getImage();
            imageMine = new ImageIcon("imgs/Mine.png").getImage();

            addMouseListener(new MouseAdapter() {

                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        

            
                        int row = e.getY() / CELL_PIXEL_SIZE;
                        int col = e.getX() / CELL_PIXEL_SIZE;
                        Cell cell = board.getCell(row, col);

                        if (row == 0 || row ==1) {
                            return;
                        }
                        if (!cell.isCovered()) {
                            return;
                        } 
                        if (SwingUtilities.isRightMouseButton(e)) {
                            if (cell.isFlagged()){
                                mineCount ++;
                                cell.setFlagged(!cell.isFlagged());
                            }
                            else {
                                if (mineCount != 0) {
                                    mineCount--;
                                    cell.setFlagged(!cell.isFlagged());
                                }
                            }

                        } else {
                            if (!cell.isFlagged()) {    
                                board.uncover(row, col);
                                if (board.mineUncovered()) {
                                    JOptionPane.showMessageDialog(Game.this, "Loose!", "Kaboom!", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                        repaint();
                    }   
            });
        }


        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int row = 2; row< boardSize+2; row++) { // row+2 for include the top bar
                for (int col= 0 ; col<boardSize; col++) {
                    paintCell(g, row, col);
                }
            }
            paintTopBar(g);
        }
    

        public void paintCell(Graphics g, int row, int col) {
            int x = col * CELL_PIXEL_SIZE;
            int y = row * CELL_PIXEL_SIZE;

            Cell cell = board.getCell(row, col);
            
            if (cell.isCovered()) {
                g.drawImage(imageCell, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, Color.black, null);
            } else {
                g.setColor(Color.gray);
                g.fillRect(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
                g.setColor((Color.lightGray));
                g.fillRect(x+1, y+1, CELL_PIXEL_SIZE-2, CELL_PIXEL_SIZE-2);
                if (cell.isMine()) {
                    g.drawImage(imageMine, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null);
                }
                
                
                if (cell.getadjacentMines() > 0) {
                    if (cell.getadjacentMines() > NUMBER_COLOR.length) {
                        g.setColor(NUMBER_COLOR[NUMBER_COLOR.length - 1]);
                    } else {
                        g.setColor(NUMBER_COLOR[cell.getadjacentMines()]);
                    }
                    
                    g.drawString(cell.getadjacentMines() + "", x + (int)(CELL_PIXEL_SIZE * 0.35),  y + (int)(CELL_PIXEL_SIZE * 0.5));
                }
            }

            
            if (cell.isFlagged()) {
                g.drawImage(imageFlag, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }

        }

        public void paintTopBar(Graphics g) {
            g.setColor(Color.gray);
            g.drawRect(0, 0, 600, 50);
            g.fillRect(0,0,600, 50);
            g.drawImage(imageFlag, 0, 0, 50, 50, null, null);
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString(String.valueOf(mineCount), 40, 35);
        }
    }


    public static void main(String[] agrs) {
        Game game = new Game();
        game.start();
    }
}
