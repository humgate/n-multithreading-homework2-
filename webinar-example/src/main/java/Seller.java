/**
 * Seller – работник магазина. Он осуществляет все процессы по продаже и приемке
 * товара.
 */
public class Seller {
    private Shop shop;
    public Seller(Shop shop) {
        this.shop = shop;
    }

    public synchronized void receiveBread() {
        try {
            System.out.println("Продавец: Принимаю товар");
            Thread.sleep(3000);
            shop.getBreads().add(new Bread());
            System.out.println("Продавец: Прием товара завершен");
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized Bread sellBread() {
        try {
            System.out.println("Продавец: Продаю хлеб");
            while (shop.getBreads().size() == 0) {
                System.out.println("Продавец: Не могу продать - хлеба нет!");
                wait();
            }
            Thread.sleep(1000);
            System.out.println("Продавец: Продано");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return shop.getBreads().remove(0);
    }
}
