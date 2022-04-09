import java.util.Random;

public class Board {
    private int size;
    private Cell [][] cells;
    private int mineCount;
    private int uncoverNum;

    private Random random = new Random();

    public Board(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        initCells();
        seedMines();
        generateNumbers();
    }

    private void initCells() {
        cells = new Cell[size+2][size];
        for (int row=2; row<size+2; row++) { // row+2 for include the top bar
            for (int col=0; col<size; col++) {
                cells[row][col] = new Cell();
            }
        }
    }


    private void seedMines() {
        int seeded = 0;
        while (seeded < mineCount) {
            int row = 2 + random.nextInt(size);
            int col = random.nextInt(size);

            Cell cell = getCell(row, col);
            if (cell.isMine()) {
                continue;
            }
            cell.setMine(true);
            seeded++;
            
        }
    }

    public void uncover(int row, int col) {
        Cell cell = getCell(row, col);

        if (cell == null || !cell.isCovered()) {
            return; 
        }
        cell.setCovered(false);
        uncoverNum++;
        if (cell.getadjacentMines() == 0) {
            int [][] pairs = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, /* Cell */ {0, 1},
                {1, -1}, {1, 0}, {1, 1}
            };
            for (int[] item: pairs) {
                int x = item[0];
                int y = item[1];
                uncover(row + x, col + y);
            }
        }

        

    }

    private void generateNumbers() {
        for (int row=2; row<size+2; row++) { // row+2 for include the top bar
            for (int col=0; col<size; col++) {
                Cell cell = getCell(row, col);
                if (cell.isMine()) {
                    continue;
                }

                int [][] pairs = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1}, /* Cell */ {0, 1},
                    {1, -1}, {1, 0}, {1, 1}
                };
                int mineCount = 0;
                for (int[] item: pairs) {
                    int x = item[0];
                    int y = item[1];

                    Cell focusedCell = getCell(row + x, col + y);
                    if (focusedCell == null) {
                        continue;
                    }
                    if (focusedCell.isMine()) {
                        mineCount++;
                    }
                }
                cell.setadjacentMines(mineCount);
            }
        }
    }

    public boolean isAllSafeCellUncorvered() {
        if (uncoverNum == ((size*size) - mineCount)) {
            return true;
        }
        return false;
    }

    public boolean mineUncovered() {
        for (int row=2; row<size+2; row++) {// row+2 for include the top bar
            for (int col=0; col<size; col++) {
                Cell cell = getCell(row, col);
                if (!cell.isCovered() && cell.isMine()) {
                    return true;
                }
            }
        }
        return false;
    }


    public Cell getCell(int row, int col) {
        if (row < 2 || col < 0 || row >= size+2 || col >= size) {
            return null;
        }
        return cells[row][col];
    }
}
