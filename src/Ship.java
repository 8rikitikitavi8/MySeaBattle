public class Ship {
    private int numDecks;
    private boolean isHorizontal;
    private Cell upperLeftCell;
    private Field field;
    private int liveDecks;

    public Ship(int numDecks, boolean isHorizontal,  Cell upperLeftCell, Field field) {
        this.numDecks = numDecks;
        this.isHorizontal = isHorizontal;
        liveDecks = numDecks;
        this.upperLeftCell = upperLeftCell;
        this.field = field;

        for (int i = 0; i < numDecks; i++) {
            if (isHorizontal) {
               field.getCells()[upperLeftCell.getX() + i][upperLeftCell.getY()].ship = this;
            } else {
               field.getCells()[upperLeftCell.getX()][upperLeftCell.getY() + i].ship = this;
            }
        }
    }

    public void setLiveDecks(int liveDecks) {
        this.liveDecks = liveDecks;
    }

    public int getLiveDecks() {
        return liveDecks;
    }

    public int getNumDecks() {
        return numDecks;
    }

    public Cell getUpperLeftCell() {
        return upperLeftCell;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }
}
