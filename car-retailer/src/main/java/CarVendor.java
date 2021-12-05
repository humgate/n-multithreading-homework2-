import java.util.ArrayList;
import java.util.List;

/**
 * Производитель авто
 */
public class CarVendor {
    //Время производства автомобиля
    final int CAR_PRODUCTION_TIME = 5000;

    //Емкость завода, или количество автомобилей к производству
    final int CAR_PRODUCTION_AMOUNT = 10;

    //склад автомобилей у вендора, доступных для поставки продавцу
    private final List<Car> vendorCars = new ArrayList<>();

    public void produceCar() {
        while (vendorCars.size() < 10) {
            try {
                Thread.sleep(CAR_PRODUCTION_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            vendorCars.add(new Car());
            System.out.println("Вендор: произведена новая машина. На складе вендора: " + vendorCars.size());
        }
    }

    /**
     * Выдача автомобиля со склада вендора.
     * Извлекает автомобиль со склада салона.
     * synchronized на объекте вендора (и тем самым на объекте склада), для того что-бы обеспечить
     * корректную работу со складом если понадобиться привлечь более одного менеджера со стороны салона
     * Здесь наверное лучше применить потокобезопасную коллекцию, но я ее еще не проходил поэтому так
     * @return - автомобиль для продажи если они есть на складе, иначе null
     */
    public synchronized Car getFromVendorStore() {
        System.out.println("Склад вендора: машин на складе вендора " + vendorCars.size());
        return (vendorCars.size() != 0) ? vendorCars.remove(0) : null;
    }
}
