import java.util.Random;
import java.util.Scanner;

public class Field {
    public static final int SIZE = 10;
    private Player player;
    private Cell[][] cells = new Cell[SIZE][SIZE];
    String[][] showOnePlayer = new String[SIZE + 1][SIZE + 1];
    String[][] showAllPlayers = new String[SIZE + 1][SIZE + SIZE + 5];

    public Field(Player player) {
        this.player = player;
        init();
    }

    public Field() {
    }

    private void init() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new Cell(i, j, TypeCellField.SEA, null, false);
            }
        }
    }

    public String[][] showField(Player player, boolean shipsIsVisible) {
        for (int i = 1; i < SIZE + 1; i++) {
            for (int j = 1; j < SIZE + 1; j++) {
                if (player.getField().getCells()[i - 1][j - 1].ship == null) {
                    if (player.getField().getCells()[i - 1][j - 1].isShoot()) {
                        showOnePlayer[j][i] = TypeCellField.MISS.getTypeCell();
                    } else {
                        showOnePlayer[j][i] = TypeCellField.SEA.getTypeCell();
                    }
                } else {
                    if (player.getField().getCells()[i - 1][j - 1].isShoot()) {
                        showOnePlayer[j][i] = TypeCellField.DAMAGE_SHIP.getTypeCell();
                    } else {
                        if (shipsIsVisible) {
                            showOnePlayer[j][i] = TypeCellField.SHIP.getTypeCell();
                        } else showOnePlayer[j][i] = TypeCellField.SEA.getTypeCell();

                    }
                    if (player.getField().getCells()[i - 1][j - 1].ship.getLiveDecks() == 0) {
                        showOnePlayer[j][i] = TypeCellField.KILL_SHIP.getTypeCell();
                    }
                }
            }
        }
        showOnePlayer[0][0] = "  ";
        for (int i = 0; i < SIZE; i++) {
            showOnePlayer[0][i + 1] = Character.toString((char) ('a' + i));
//            showOnePlayer[0][1] = TypeCellField.A.getTypeCell();
//            showOnePlayer[0][2] = TypeCellField.B.getTypeCell();
//            showOnePlayer[0][3] = TypeCellField.C.getTypeCell();
//            showOnePlayer[0][4] = TypeCellField.D.getTypeCell();
//            showOnePlayer[0][5] = TypeCellField.E.getTypeCell();
//            showOnePlayer[0][6] = TypeCellField.F.getTypeCell();
//            showOnePlayer[0][7] = TypeCellField.G.getTypeCell();
//            showOnePlayer[0][8] = TypeCellField.H.getTypeCell();
//            showOnePlayer[0][8] = TypeCellField.I.getTypeCell();
//            showOnePlayer[0][8] = TypeCellField.J.getTypeCell();
            showOnePlayer[1 + i][0] = i != 9 ? " " + Integer.toString(i + 1) : Integer.toString(i + 1);
        }
        return showOnePlayer;
    }

    public void showAllField(Player player1, Player player2, boolean player1isVisible, boolean player2isVisible) {
        for (int i = 0; i < SIZE + 1; i++) {
            for (int j = 0; j < SIZE + 1; j++) {
                showAllPlayers[i][j] = showField(player1, player1isVisible)[i][j];
            }
        }
        for (int i = 0; i < SIZE + 1; i++) {
            for (int j = SIZE + 4; j < SIZE * 2 + 5; j++) {
                showAllPlayers[i][j] = showField(player2, player2isVisible)[i][j - (SIZE + 4)];
            }
        }
        for (int i = 0; i < SIZE + 1; i++) {
            for (int j = SIZE + 1; j < SIZE + 4; j++) {
                showAllPlayers[i][j] = " ";
            }
        }
        System.out.println("         " + player1.getName() + "                    " + player2.getName());
        for (int i = 0; i < SIZE + 1; i++) {
            for (int j = 0; j < SIZE * 2 + 5; j++) {
                System.out.print(showAllPlayers[i][j] + " ");
            }
            System.out.println("");
        }
    }

    boolean checkEmptyField(int numDecks, Field field, int x, int y, boolean isHorizontal) {
        if (isHorizontal & (x + numDecks) > 10) {
            return false;
        }
        if (!isHorizontal & (y + numDecks) > 10) {
            return false;
        }
        for (int i = 0; i < numDecks; i++) {
            for (int j = x - 1; j < x + 2; j++) {
                for (int k = y - 1; k < y + 2; k++) {
                    try {
                        if (isHorizontal & field.cells[j + i][k].ship != null) {
                            return false;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    try {
                        if (!isHorizontal & field.cells[j][k + i].ship != null) {
                            return false;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }
        }
        return true;

    }

    public void locateShipsRandom(Ship[][] ships) {
        Random random = new Random();
        boolean isHorizontal;
        int upperLeftCellX;
        int upperLeftCellY;
        int numDecks;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                do {
                    upperLeftCellX = random.nextInt(10);
                    upperLeftCellY = random.nextInt(10);
                    numDecks = i + 1;
                    isHorizontal = random.nextBoolean();
                } while (!checkEmptyField(numDecks, this, upperLeftCellX, upperLeftCellY, isHorizontal));
                ships[i][j] = new Ship(numDecks, isHorizontal, this.cells[upperLeftCellX][upperLeftCellY], this);
            }
        }
    }

    public void locateShipsByPlayer(Ship[][] ships, Player player1, Player player2) {
        String isHorizontalString;
        int upperLeftX, upperLeftY, numDecks;
        boolean isHorizontal = false;
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                do {
                    boolean err;
                    numDecks = i + 1;
                    if (i > 0) {
                        do {
                            err = false;
                            System.out.println("Задайте ориентацию " + (j + 1) + " го " + numDecks + " палубного корабля. \n" +
                                    " Нажмите H если горизонтально и V если вертикально.");
                            isHorizontalString = (scanner.nextLine()).toLowerCase();
                            if (isHorizontalString.equals("h")) {
                                isHorizontal = true;
                            } else if (isHorizontalString.equals("v")) {
                                isHorizontal = false;
                            } else err = true;
                            if (err) {
                                System.out.println("Вы ввели не верный символ, повторите попытку...");
                            }
                        } while (err);
                    }
                    do {
                        err = false;
                        System.out.println("Введите координату верхней или левой клетки  " + (j + 1) + " го " + numDecks + " палубного корабля. \n" +
                                " (например А5)");
                        String upLeftCell = (scanner.nextLine()).toLowerCase();
                        upperLeftX = upLeftCell.charAt(0) - 97;
                        upperLeftY = upLeftCell.charAt(1) - 49;
                        if (upLeftCell.length() > 2) upperLeftY = (upLeftCell.charAt(1) - 48) * 10 + upLeftCell.charAt(2) - 49;
                        if ( upperLeftX < 0 || upperLeftX > 9 || upperLeftY < 0 || upperLeftY > 9) {
                            err = true;
                        }
                        if (err) {
                            System.out.println("Вы ввели не верное значение. (А-J)(1-10)");
                        }

                    } while (err);
                    if (!checkEmptyField(numDecks, this, upperLeftX, upperLeftY, isHorizontal)) {
                        System.out.println("Вы ввели не верные координаты корабля. \n " +
                                "Вероятнее всего он попадает на другой корабль или слишком близко к нему.");
                    }
                } while (!checkEmptyField(numDecks, this, upperLeftX, upperLeftY, isHorizontal));
                ships[i][j] = new Ship(numDecks, isHorizontal, this.cells[upperLeftX][upperLeftY], this);
                showAllField(player1, player2, true, false);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Cell[][] getCells() {
        return cells;
    }

}
