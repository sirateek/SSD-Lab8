import java.awt.*;
import javax.swing.*;
import java.awt.event.*;



public class Game extends JFrame{
    private Board board;
    private int boardSize = 20;
    private int mineCount = 40;
    private GridUI gridUI;

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
            setPreferredSize(new Dimension(boardSize * CELL_PIXEL_SIZE, boardSize * CELL_PIXEL_SIZE));
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

                        if (!cell.isCovered()) {
                            return;
                        } 
                        if (SwingUtilities.isRightMouseButton(e)) {
                             cell.setFlagged(!cell.isFlagged());
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
            paintCell(g, 0, 0);
            paintCell(g, 1, 1);

            for (int row = 0; row< boardSize; row++) {
                for (int col= 0 ; col<boardSize; col++) {
                    paintCell(g, row, col);
                }
            }
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
                    g.setColor(Color.black);
                    g.drawString(cell.getadjacentMines() + "", x + (int)(CELL_PIXEL_SIZE * 0.35),  y + (int)(CELL_PIXEL_SIZE * 0.5));
                }
            }

            
            if (cell.isFlagged()) {
                g.drawImage(imageFlag, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }

        }
    }


    public static void main(String[] agrs) {
        Game game = new Game();
        game.start();
    }
}
