public class Main {
    public static void main(String[] args) {
        final Shop shop = new Shop();

        /* Покупатель, чья работа заключается в том, чтобы купить хлеб
         * - shop.sellBread()
         */
        new Thread(null, () -> shop.sellBread(), "Покупатель").start();

        /* Водитель хлебопекарни, чья работа заключается в том, чтобы привезти хлеб
        * - shop.acceptBread()
        */
        new Thread(null, shop::acceptBread, "Водитель хлебопекарни").start();
    }
}
