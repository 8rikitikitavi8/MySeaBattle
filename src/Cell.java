public class Cell {
    private int x;
    private int y;
    private TypeCellField typeCellField;
    Ship ship;
    private boolean isShoot;

    public Cell(int x, int y, TypeCellField typeCellField, Ship ship, boolean isShoot) {
        this.x = x;
        this.y = y;
        this.typeCellField = typeCellField;
        this.ship = ship;
        this.isShoot = isShoot;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public TypeCellField getTypeCellField() {
        return typeCellField;
    }

    public void setTypeCellField(TypeCellField typeCellField) {
        this.typeCellField = typeCellField;
    }

    public boolean isShoot() {
        return isShoot;
    }

    public void setShoot(boolean shoot) {
        isShoot = shoot;
    }
}
