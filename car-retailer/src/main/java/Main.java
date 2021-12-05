/**
 * Запускает вселенную
 */
public class Main {
     public static void main(String[] args) {
        //да будет вендор
        final CarVendor vendor = new CarVendor();

        //и салон
        final CarRetailer dealer = new CarRetailer();

        //и менеджер в салоне
        final Manager manOne = new Manager(dealer, "Вася");

        //и из ребра его второй менеджер в салоне
        final Manager manTwo = new Manager(dealer, "Лена");

        //и пусть вендор начнет производство автомобилей
        //new Thread(null, vendor::produceCar, "Производитель").start();


        //покупатели, которым понравился первый менеджер и они обратились к нему за покупкой
        new Thread(null, manOne::sellCar, "Покупатель 1").start();

        //покупатели, которым понравился второй менеджер и они обратились к нему за покупкой
        new Thread(null, manTwo::sellCar, "Покупатель 2").start();

        //примем что машины забирает только первый менеджер
        new Thread(null, manOne::receiveCar, "Производитель авто 1").start();

        //примем что машины забирает только первый менеджер
        new Thread(null, manOne::receiveCar, "Производитель авто 2").start();

    }
}

