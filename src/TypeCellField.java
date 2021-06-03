public enum TypeCellField {
    SEA("."),
//    SEA("\uD83C\uDF0A"),
    SHIP("0"),
//    SHIP("\uD83D\uDEE5"),
    DAMAGE_SHIP("X"),
//    DAMAGE_SHIP("\uD83D\uDCA5"),
    KILL_SHIP("$"),
//    KILL_SHIP("☠"),
    MISS("*");
//    MISS("\uD83D\uDD73"),

//    VOID(" "),
//    A("\uDB40\uDC41"),B("\uDB40\uDC42"),C("\uD83C\uDDE8"),D("\uD83C\uDDE9"),E("\uD83C\uDDEA"),F("\uD83C\uDDEB"),
//    G("\uD83C\uDDEC"),H("\uD83C\uDDED"),I("\uD83C\uDDEE"),J("\uD83C\uDDEF"),
//    ONE("1"),TWO("2"),THREE("3"),FOUR("4"),FIVE("5"),SIX("6"),SEVEN("7"),EIGHT("8"),NINE("9"), TEN("10");

    TypeCellField(String typeCell) {
        this.typeCell = typeCell;
    }

    private String typeCell;

    public String getTypeCell() {
        return typeCell;
    }

    public void setTypeCell(String typeCell) {
        this.typeCell = typeCell;
    }
}
