import java.util.ArrayList;
import java.util.List;

/**
 * Автосалон.
 * - помещает новый автомобиль на склад
 * - выдает автомобиль со склада
 * - учитывает автомобили в наличии на складе
 */

public class CarRetailer {
    //склад автомобилей в салоне, доступных доя продажи
    List<Car> cars = new ArrayList<>();

    /**
     * Пополняет склад доступных для продажи автомобилей салона новым автомобилем.
     * synchronized на объекте салона (и тем самым на объекте склада) для того, что-бы обеспечить
     * корректную работу со складом, если понадобиться привлечь более одного менеджера.
     * Здесь наверное лучше применить потокобезопасную коллекцию, но я ее еще не проходил поэтому так
     *
     * @param car - автомобиль для добавления на склад салона
     */
    public synchronized void receiveCar(Car car) {
        cars.add(car);
        System.out.println("Склад: машин на складе "+ cars.size());
    }

    /**
     * Выдача автомобиля со склада.
     * Извлекает автомобиль со склада салона.
     * synchronized на объекте салона (и тем самым на объекте склада), для того что-бы обеспечить
     * корректную работу со складом если понадобиться привлечь более одного менеджера
     * Здесь наверное лучше применить потокобезопасную коллекцию, но я ее еще не проходил поэтому так
     * @return - автомобиль для продажи если они есть на складе, иначе null
     */
    public synchronized Car getFromStore() {
        System.out.println("Склад: машин на складе " + cars.size());
        return (cars.size() != 0) ? cars.remove(0) : null;
    }
}