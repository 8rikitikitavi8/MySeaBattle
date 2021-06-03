import java.util.Random;
import java.util.Scanner;

public class Game {
    Field field = new Field();
    boolean player1shoot;
    Player player1 = new Player();
    Player player2 = new Player();

    public void startGame() {
        System.out.println("Тысяча чертей!!! Свистать всех наверх!!! Вижу нового капитана. Напишите имя");
        player1.setName((new Scanner(System.in)).nextLine());
        player2.setName("computer");
        System.out.println("Многие приезжали на остров черепа в поисках золота, но пока нико не возвращался!!!\n" +
                "Посмотрим какая ждет тебя судьба, " + player1.getName()+ "\n");
//        placeShipsRandomly();
        placeShips();
        player1shoot = (new Random()).nextBoolean();
        while (player1.getAllDecks() != 0 && player2.getAllDecks() != 0) {
            if (player1shoot) {
                if (!player1.shooting(player2.getField(), true)) {
                    player1shoot = !player1shoot;
                    System.out.println("\n" + "Выстрел от " + player1.getName());
                }
            } else {
                if (!player2.shooting(player1.getField(), false)) {
                    player1shoot = !player1shoot;
                    System.out.println("\n" + "Выстрел от " + player2.getName());
                }
            }
            field.showAllField(player1, player2, true, false);
        }
        if (player1.getAllDecks() == 0) {
            System.out.println(player2.getName() + ",твой флот победил. Теперь ты будешь адмиралом!");
        } else {
            System.out.println(player1.getName() + ",твой флот победил. Теперь ты будешь адмиралом!");
        }
    }

    private boolean placeShipsRandomly() {
        System.out.println("Капитан " + player1.getName() + ", если сам хочешь расставить свой флот нажми 1, если нет - любую кнопку");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equals("1")) {
            return false;
        }
        return true;
    }

    void placeShips() {
        field.showAllField(player1, player2, true, false);
        if (!placeShipsRandomly()) {
            player1.getField().locateShipsByPlayer(player1.getShips(), player1, player2);
        } else {
        player1.getField().locateShipsRandom(player1.getShips());
        }
        player2.getField().locateShipsRandom(player2.getShips());
        field.showAllField(player1, player2, true, false);
    }
}
