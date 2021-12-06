/**
 * Запускает вселенную
 */
public class Main {
     public static void main(String[] args) {
        //да будет салон
        final CarRetailer dealer = new CarRetailer();

        //и первый менеджер в салоне - продает машины
        final Manager manOne = new Manager(dealer, "Вася-продавец");

        //и из ребра его второй менеджер в салоне - тоже продает машины
        final Manager manTwo = new Manager(dealer, "Лена-продавец");

        //а третий менеджер будет только работать с заводом по приемке машин
        final Manager manThree = new Manager(dealer, "Петя-приемщик");

        //Запускаем нашу вселенную
        //производитель
        new Thread(null, manThree::receiveCar, "Производитель авто").start();

        //покупатель подошедший к первому менеджеру
        new Thread(null, manOne::sellCar, "Покупатель 1").start();

        //покупатель подошедший к второму менеджеру
        new Thread(null, manTwo::sellCar, "Покупатель 2").start();
    }
}

