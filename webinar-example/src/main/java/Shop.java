import java.util.ArrayList;
import java.util.List;

/**
 * Shop – магазин, который может либо продавать хлеб покупателю, либо принимать
 * новый завоз от хлебопекарни.
 *
 */
public class Shop {
    // Продавец
    Seller seller = new Seller(this);
    List<Bread> breads = new ArrayList<>(10);

    public Bread sellBread() {
        return seller.sellBread();
    }

    public void acceptBread() {
        seller.receiveBread();
    }

    List<Bread> getBreads() {
        return breads;
    }
}
