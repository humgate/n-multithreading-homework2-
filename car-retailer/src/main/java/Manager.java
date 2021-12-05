/**
 * Менеджер автосалона.
 * Продает автомобиль клиенту.
 * Получает новый автомобиль от производителя.
 */
public class Manager {
    //
    private String name;

    //время в миллисекундах на приемку нового авто от производителя
    private final int NEW_CAR_GETTING_TIME = 1000;

    //время оформления продажи автомобиля
    private final int CAR_ORDER_TIME = 1000;

    //салон в котором работает менеджер
    private CarRetailer carRetailer;

    public Manager(CarRetailer carRetailer, String name) {
        this.carRetailer = carRetailer;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Менеджер " + name;
    }

    /**
     * Получение нового автомобиля от производителя и помещение его на склад салона
     * synchronized по менеджеру, так как менеджер не может одновременно делать более одной операции
     */
    public synchronized void receiveCar() {
        try {
            System.out.println(this + ": Принимаю автомобиль");
            Thread.sleep(NEW_CAR_GETTING_TIME);
            carRetailer.receiveCar(new Car());
            System.out.println(this + ": Прием автомобиля завершен");
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sellCar() {
        try {
            System.out.println(this + ": Смотрю наличие доступных машин на складе");
            while (carRetailer.getFromStore() == null) {
                System.out.println(this + ": Нет машин на складе");
                wait();
            }
            Thread.sleep(CAR_ORDER_TIME);
            System.out.println(this + ": Продал машину со склада");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
