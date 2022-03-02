package BusinessLogic.Validators;

import Model.Product;

public class InStockValidator implements Validator<Product> {
    /**
     * Validate Stock
     * @param t Product for which it is validated
     * @return if it is valid or not
     */
    public boolean validate(Product t) {
        return t.getStock() < 0;
    }
}
