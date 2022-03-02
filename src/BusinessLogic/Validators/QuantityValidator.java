package BusinessLogic.Validators;

import DataAccess.ProductDAO;
import Model.Orders;
import Model.Product;

public class QuantityValidator implements Validator<Orders> {
    /**
     * Validate Quantity
     * @param t Order for which it is validated
     * @return if it is valid or not
     */
    public boolean validate(Orders t) {
        ProductDAO p =new ProductDAO();
        Product q = p.findById(t.getProductID());
        return t.getQuantity() > q.getStock();
    }
}
