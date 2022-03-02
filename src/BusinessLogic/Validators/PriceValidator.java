package BusinessLogic.Validators;

import Model.Product;

public class PriceValidator implements Validator<Product> {
    /**
     * Validate Price
     * @param t Product for which it is validated
     * @return if it is valid or not
     */
    public boolean validate(Product t) {
        if (t.getPrice()<0) {
            return true;
        }
        if(t.getPrice()>200) {
            return true;
        }
        return false;
    }
}
