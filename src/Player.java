import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Player {
    private int allDecks;
    private String name;
    private Ship[][] ships;
    private Field field;
    Random random = new Random();

    Player() {
        field = new Field(this);
        allDecks = 20;
        ships = new Ship[4][];
        for (int i = 0; i < 4; i++) {
            ships[i] = new Ship[4 - i];
        }
    }

    boolean shooting(Field field, boolean playerShooting) {
        int x;
        int y;
        boolean err;
        if (playerShooting) {
            do {
                err = false;
                System.out.println(this.name + ", тебе стрелять! Только бей по полю :) \n" +
                        "Например А3,буквы от а до j включительно,цифры от 1 до 10.");
                String string = ((new Scanner(System.in)).nextLine()).toLowerCase();
                x = string.charAt(0) - 97;
                y = string.charAt(1) - 49;
                if (string.length() > 2) y = (string.charAt(1) - 48) * 10 + string.charAt(2) - 49;
                if (x < 0 || x > 9 || y < 0 || y > 9) {
                    System.out.println("Не верные координаты. Цельтесь лучше. Ещё разок");
                    err = true;
                }
                if (field.getCells()[x][y].isShoot()) {
                    System.out.println("Нет смысла стрелять в этом направлении. Ещё разок");
                    err = true;
                }
            } while (err);
            if (field.getCells()[x][y].ship == null) {
                field.getCells()[x][y].setShoot(true);
                System.out.println("Мимо!!!");
                return false;
            } else {
                System.out.println("Наше ядро попало им прямо в борт!");
                int ld = field.getCells()[x][y].ship.getLiveDecks();
                ld--;
                field.getCells()[x][y].ship.setLiveDecks(ld);
                if (field.getCells()[x][y].ship.getLiveDecks() == 0) {
                    defeatСloud(x, y, field);
                    System.out.println("Капитан, вижу что это корыто уже больше никуда не уплывет!");
                }
                int ad = field.getPlayer().getAllDecks();
                ad--;
                field.getPlayer().setAllDecks(ad);
                field.getCells()[x][y].setShoot(true);
                return true;
            }
        } else {
            List<Cell> cells = halfLifeShip(field);
            if (cells.size() != 0) {
                Cell cell;
                System.out.println(cells.size());
                cell = cells.get(random.nextInt(cells.size()));
                field.getCells()[cell.getX()][cell.getY()].setShoot(true);
                if (field.getCells()[cell.getX()][cell.getY()].ship == null) {
                    return false;
                } else {
                    int ld = cell.ship.getLiveDecks();
                    ld--;
                    cell.ship.setLiveDecks(ld);
                    if (field.getCells()[cell.getX()][cell.getY()].ship.getLiveDecks() == 0) {
                        defeatСloud(cell.getX(), cell.getY(), field);
                    }
                    return true;
                }
            } else {
                while (true) {
                    int X = random.nextInt(field.SIZE);
                    int Y = random.nextInt(field.SIZE);
                    if (field.getCells()[X][Y].ship == null) {
                        field.getCells()[X][Y].setShoot(true);
                        return false;
                    } else {
                        int ld = field.getCells()[X][Y].ship.getLiveDecks();
                        ld--;
                        field.getCells()[X][Y].ship.setLiveDecks(ld);
                        if (field.getCells()[X][Y].ship.getLiveDecks() == 0) {
                            defeatСloud(X, Y, field);
                        }
                    }
                }
            }
        }
    }

    private void defeatСloud(int x, int y, Field field) {
        for (int i = 0; i < field.getCells()[x][y].ship.getNumDecks(); i++) {
            for (int j = field.getCells()[x][y].ship.getUpperLeftCell().getX() - 1;
                 j < field.getCells()[x][y].ship.getUpperLeftCell().getX() + 2; j++) {
                for (int k = field.getCells()[x][y].ship.getUpperLeftCell().getY() - 1;
                     k < field.getCells()[x][y].ship.getUpperLeftCell().getY() + 2; k++) {
                    try {
                        if (field.getCells()[x][y].ship.isHorizontal()) {
                            field.getCells()[j + i][k].setShoot(true);
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                    try {
                        if (!field.getCells()[x][y].ship.isHorizontal()) {
                            field.getCells()[j][k + i].setShoot(true);
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            }
        }
    }

    public List<Cell> halfLifeShip(Field field) {
        List<Cell> cells = new ArrayList<>();
        List<Cell> cellsForShoot = new ArrayList<>();
        int x = 0, y = 0;
        for (int i = 0; i < field.SIZE; i++) {
            for (int j = 0; j < field.SIZE; j++) {
                if ((field.getCells()[i][j].isShoot() && field.getCells()[i][j].ship != null &&
                        (field.getCells()[i][j].ship.getLiveDecks() != 0))) {
                    cells.add(field.getCells()[i][j]);
                }
            }
        }
        switch (cells.size()) {
            case 1 -> {
                x = cells.get(0).getX();
                y = cells.get(0).getY();
                if (x > 0 && !field.getCells()[x - 1][y].isShoot()) {
                    cellsForShoot.add(field.getCells()[x - 1][y]);
                }
                if (x < 9 && !field.getCells()[x + 1][y].isShoot()) {
                    cellsForShoot.add(field.getCells()[x + 1][y]);
                }
                if (y > 0 && !field.getCells()[x][y - 1].isShoot()) {
                    cellsForShoot.add(field.getCells()[x][y - 1]);
                }
                if (y < 9 && !field.getCells()[x][y + 1].isShoot()) {
                    cellsForShoot.add(field.getCells()[x][y + 1]);
                }
            }
            case 2 -> {
                int start, end;
                if (cells.get(0).getX() == cells.get(1).getX()) {
                    x = cells.get(0).getX();
                    if (cells.get(0).getY() > cells.get(1).getY()) {
                        end = cells.get(0).getY();
                        start = cells.get(1).getY();
                    } else {
                        end = cells.get(1).getY();
                        start = cells.get(0).getY();
                    }
                    if (start > 0 && !field.getCells()[x][start - 1].isShoot()) {
                        cellsForShoot.add(field.getCells()[x][start - 1]);
                    }
                    if (end < 9 && !field.getCells()[x][end + 1].isShoot()) {
                        cellsForShoot.add(field.getCells()[x][end + 1]);
                    }
                } else if (cells.get(0).getY() == cells.get(1).getY()) {
                    y = cells.get(0).getY();
                    if (cells.get(0).getX() > cells.get(1).getX()) {
                        end = cells.get(0).getX();
                        start = cells.get(1).getX();
                    } else {
                        end = cells.get(1).getX();
                        start = cells.get(0).getX();
                    }
                    if (start > 0 && !field.getCells()[start - 1][y].isShoot()) {
                        cellsForShoot.add(field.getCells()[start - 1][y]);
                    }
                    if (end < 9 && !field.getCells()[end + 1][y].isShoot()) {
                        cellsForShoot.add(field.getCells()[end + 1][y]);
                    }
                }
            }
            case 3 -> {
                if (cells.get(0).getX() == cells.get(1).getX() && cells.get(0).getX() == cells.get(2).getX()) {
                    x = cells.get(0).getX();
                    if (cells.get(0).getY() < cells.get(2).getY()) {
                        y = cells.get(0).getY();
                    }
                    if (y > 0 && !field.getCells()[x][y - 1].isShoot()) {
                        cellsForShoot.add(field.getCells()[x][y - 1]);
                    }
                    if (y < 7 && !field.getCells()[x][y + 3].isShoot()) {
                        cellsForShoot.add(field.getCells()[x][y + 3]);
                    }
                } else if (cells.get(0).getY() == cells.get(1).getY() && cells.get(0).getY() == cells.get(2).getY()) {
                    y = cells.get(0).getY();
                    if (cells.get(0).getX() < cells.get(2).getX()) {
                        x = cells.get(0).getX();
                    }
                    if (x > 0 && !field.getCells()[x - 1][y].isShoot()) {
                        cellsForShoot.add(field.getCells()[x - 1][y]);
                    }
                    if (x < 7 && !field.getCells()[x + 3][y].isShoot()) {
                        cellsForShoot.add(field.getCells()[x + 3][y]);
                    }
                }
            }
        }
        return cellsForShoot;
    }

    public void setAllDecks(int allDecks) {
        this.allDecks = allDecks;
    }

    public int getAllDecks() {
        return allDecks;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ship[][] getShips() {
        return ships;
    }

    public void setShips(Ship[][] ships) {
        this.ships = ships;
    }
}
