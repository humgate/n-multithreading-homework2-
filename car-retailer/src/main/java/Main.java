/**
 * Запускает вселенную
 */
public class Main {
     public static void main(String[] args) {
        //да будет салон
        final CarRetailer dealer = new CarRetailer();

        //и первый менеджер в салоне
        final Manager manOne = new Manager(dealer, "Вася");

        //и из ребра его второй менеджер в салоне
        final Manager manTwo = new Manager(dealer, "Лена");

        //Запускаем нашу вселенную

        //покупатель подошедший к первому менеджеру
        new Thread(null, manOne::sellCar, "Покупатель 1").start();

        //покупатель подошедший к второму менеджеру
        new Thread(null, manTwo::sellCar, "Покупатель 2").start();

        //производитель
        new Thread(null, manOne::receiveCar, "Производитель авто").start();


    }
}

