public class Cell {
    private boolean mine;
    private boolean flagged;
    private boolean covered;
    private int adjacentMines;

    public Cell() {
        setMine(false);
        setFlagged(false);
        setCovered(true);
        setadjacentMines(0);
    }

    public int getadjacentMines() {
        return adjacentMines;
    }

    public void setadjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public boolean isCovered() {
        return covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    
}
