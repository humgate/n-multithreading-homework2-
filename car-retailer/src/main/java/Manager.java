/**
 * Менеджер автосалона.
 * Продает автомобиль клиенту.
 * Получает новый автомобиль поставленный производителем.
 */
public class Manager {
    //имя
    private String name;

    //время в миллисекундах на производство и получение менеджером нового авто от производителя
    private final int NEW_CAR_PROD_TIME = 2000;

    //время оформления продажи автомобиля
    private final int CAR_ORDER_TIME = 500;

    //количество машин которое должен продать салон
    private final int CARS_TO_SELL = 10;

    //салон в котором работает менеджер
    private final CarRetailer carRetailer;

    public Manager(CarRetailer carRetailer, String name) {
        this.carRetailer = carRetailer;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Менеджер " + name;
    }

    /**
     * Получает новый автомобиль от производителя и помещает его на склад салона.
     * Внутрь данной операции включено время производства производителем.
     * По другому можно сказать, что в нашей модели нет спец объекта завод-производитель. Считаем что на
     * заводе-изготовителе к моменту начала нашей задачи уже изготовлено достаточно автомобилей.
     *
     * synchronized не по менеджеру, а по салону (по складу салона).
     *
     * Логика здесь следующая: в нашем салоне будет работать более одного менеджера. Поэтому неправильно будет
     * синхронизировать потоки по менеджеру. Если так сделать, то поток-покупатель, наткнувшись на отсутствие машин,
     * будет ждать когда исключительно ТОТ ЖЕ менеджер, который занимался продажей ему, примет на склад авто от
     * производителя. А нам как раз это не нужно, мы наняли специально нескольких менеджеров чтобы любой из них мог
     * принимать новые машины.
     *
     * Объектом-монитором должен стать склад салона (в нашем случае сам салон).
     * Только один поток может продавать машину со склада или помещать ее туда от производителя.
     * Поскольку и операция продажи и операция пополнения склада требуют доступа к складу, который синхронизирован,
     * можно не бояться ситуации гонки потоков в отношении менеджера
     */
    public void receiveCar() {
        for (int i = 0; i < CARS_TO_SELL; i++) {
            try {
                Thread.sleep(NEW_CAR_PROD_TIME);
                System.out.println(Thread.currentThread().getName() + ": Произведен новый автомобиль");

                synchronized (carRetailer) {
                    carRetailer.receiveCar(new Car());
                    System.out.println(Thread.currentThread().getName() + ": автомобиль принят " + this);
                    carRetailer.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Продажа автомобиля со склада салона.
     *
     * synchronized не по менеджеру, а по салону (по складу салона).
     *
     * Логика здесь следующая: в нашем салоне будет работать более одного менеджера. Поэтому неправильно будет
     * синхронизировать потоки по менеджеру. Если так сделать, то поток-покупатель, наткнувшись на отсутствие машин,
     * будет ждать когда исключительно ТОТ ЖЕ менеджер, который занимался продажей ему, примет на склад авто от
     * производителя. А нам как раз это не нужно, мы наняли специально двух менеджеров чтобы любой из них мог
     * принимать новые машины.
     *
     * Объектом-монитором должен стать склад салона (в нашем случае сам салон).
     * Только один поток может продавать машину со склада или помещать ее туда от производителя.
     * Поскольку и операция продажи и операция пополнения склада требуют доступа к складу, который синхронизирован,
     * можно не бояться ситуации гонки потоков в отношении менеджера
     */
    public void sellCar() {
        System.out.println(Thread.currentThread().getName() + " пришел покупать авто");
        synchronized (carRetailer) {
            try {
                while (carRetailer.getFromStore() == null) {
                    System.out.println(this + ": Нет машин на складе");
                    carRetailer.wait();;
                }
                Thread.sleep(CAR_ORDER_TIME);
                System.out.println(this + ": Продал машину со склада");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
