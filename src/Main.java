/*
Игра морской бой с "умным" компьютером,который при ранении корабля бьет разумно по соседним клеткам пока не потопит корабль.
После потопления корабля появляется "облако" куда стрелять нет смысла.
Есть автоматическая и ручная расстановка кораблей
 */

public class Main {
    public static void main(String[] args) {
    Game game = new Game();
    game.startGame();
    }
}
