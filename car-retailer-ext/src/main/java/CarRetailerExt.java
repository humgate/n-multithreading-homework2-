import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Автосалон.
 * - помещает новый автомобиль на склад
 * - выдает автомобиль со склада
 * - учитывает автомобили в наличии на складе
 */

public class CarRetailerExt {
    //склад автомобилей в салоне, доступных доя продажи
    List<Car> cars = new ArrayList<>();

    /*
    * Интерфейс Lock не предоставляет возможности указать объект монитора, так как это позволяет сделать synchronized.
    * А нам надо, согласно принятому дизайну, чтобы все потоки синхронизировались по именно складу (ритейлеру).
    * Поэтому нам придется объявить Lock внутри нашего салона и наружу (менеджерам) выдать методы-адаптеры, которые
    * будут лочить, эвейтить и нотифаить
    */
    private final ReentrantLock lock = new ReentrantLock(true); //честный
    private final Condition condition = lock.newCondition();

    protected void doLock() {
        lock.lock();
    }

    protected void doUnlock() {
        lock.unlock();
    }

    protected void doAwait() throws InterruptedException {
        condition.await();
    }

    protected void doNotifyAll() {
        condition.signalAll();
    }

    /**
     * Пополняет склад доступных для продажи автомобилей салона новым автомобилем.
     * @param car - автомобиль для добавления на склад салона
     */
    public void receiveCar(Car car) {
        cars.add(car);
        System.out.println("Склад: машин на складе "+ cars.size());
    }

    /**
     * Выдача автомобиля со склада.
     * Извлекает автомобиль со склада салона.
     * @return - автомобиль для продажи если они есть на складе, иначе null
     */
    public Car getFromStore() {
        System.out.println("Склад: машин на складе " + cars.size());
        return (cars.size() != 0) ? cars.remove(0) : null;
    }
}
