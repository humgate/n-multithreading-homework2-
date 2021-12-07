/**
 * Запускает вселенную
 */
public class Main {
    //таймаут желания купить машину покупателем
    static final int BYER_TIMEOUT = 8000;

    public static void main(String[] args) {
        /*
         * Создаем сущности и существ
         */

        //да будет салон
        final CarRetailerExt dealer = new CarRetailerExt();

        //и первый менеджер в салоне - продает машины
        final ManagerExt manOne = new ManagerExt(dealer, "Вася-продавец");

        //и из ребра его второй менеджер в салоне - тоже продает машины
        final ManagerExt manTwo = new ManagerExt(dealer, "Лена-продавец");

        //а третий менеджер будет только работать с заводом по приемке машин
        final ManagerExt manThree = new ManagerExt(dealer, "Петя-приемщик");

        /*
         *Запускаем потоки нашей вселенной
         */

        //производитель совмещенный с приемщиком начинает произодвить-принимать машины
        new Thread(null, manThree::receiveCar, "Производитель авто").start();

        //приходят покупатели которые в сумме собираются купить 10 машин

        //подошедший к первому менеджеру по продажам
        new Thread(null, manOne::sellCar, "Покупатель 1. маш1").start();

        //эти 3 покупателя планируют купить каждый по 3 машины
        for (int i = 0; i < 3; i++) {
            //подошедшие к первому менеджеру по продажам
            new Thread(null, manOne::sellCar, "Покупатель 2. маш" + (i + 1)).start();

            //подошедшие к второму менеджеру по продажам
            new Thread(null, manTwo::sellCar, "Покупатель 3. маш" + (i + 1)).start();

            //подошедшие к второму менеджеру по продажам
            new Thread(null, manTwo::sellCar, "Покупатель 4. маш" + (i + 1)).start();

            try {
                Thread.sleep(BYER_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

