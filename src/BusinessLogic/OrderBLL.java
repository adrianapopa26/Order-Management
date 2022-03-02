package BusinessLogic;

import BusinessLogic.Validators.QuantityValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.OrderDAO;
import DataAccess.ProductDAO;
import Model.Orders;
import Model.Product;
import Presentation.OrderController;
import java.util.ArrayList;
import java.util.List;

public class OrderBLL {
    private final List<Validator<Orders>> validators;
    private final OrderDAO orderDAO;
    private final OrderController orderController;

    /**
     * Constructor
     * @param orderController needed for error messages
     */
    public OrderBLL(OrderController orderController) {
        validators = new ArrayList<>();
        validators.add(new QuantityValidator());
        orderDAO = new OrderDAO();
        this.orderController=orderController;
    }

    /**
     * Check if id is unique
     * @param t - order for which id is validated
     * @return if it is unique or not
     */
    public boolean validateID(Orders t) {
        List<Orders> orders = orderDAO.createObjects();
        for(Orders order: orders){
            if(t.getID()==order.getID()){
                orderController.showError("ID already selected for another client!","Select another ID.");
                return false;
            }
        }
        return true;
    }

    /**
     * Use the validators to check the order
     * @param t order to validate
     * @return status of validation
     */
    public boolean validateOrder(Orders t) {
        if(validators.get(0).validate(t)){
            orderController.showError("Quantity not available!","Try a smaller quantity.");
            return false;
        }
        return true;
    }

    /**
     * validate and insert order and product stock
     * @param t inserted order
     */
    public void insertOrder(Orders t) {
        if(validateOrder(t)&&validateID(t)){
            orderDAO.insert(t);
            ProductDAO p =new ProductDAO();
            Product q = p.findById(t.getProductID());
            q.setStock(q.getStock()-t.getQuantity());
            p.update(q);
            orderController.showFinalizationMessage("Inserted was executed!","Press Show to see modifications.");
        }
    }

    /**
     * validate and update order and product stock
     * @param t updated order
     */
    public void updateOrder(Orders t) {
        if(validateOrder(t)){
            orderDAO.update(t);
            ProductDAO p =new ProductDAO();
            Product q = p.findById(t.getProductID());
            q.setStock(q.getStock()-t.getQuantity());
            p.update(q);
            orderController.showFinalizationMessage("Update was executed!","Press Show to see modifications.");
        }
    }

    /**
     * validate and delete order and product stock
     * @param t deleted order
     */
    public void deleteOrder(Orders t) {
        Orders o=orderDAO.findById(t.getID());
        ProductDAO p =new ProductDAO();
        Product q = p.findById(o.getProductID());
        q.setStock(q.getStock()+o.getQuantity());
        p.update(q);
        orderDAO.delete(o);
        orderController.showFinalizationMessage("Delete was executed!","Press Show to see modifications.");
    }

    /**
     * Get order with specified id
     * @param id -id to look for
     * @return order
     */
    public Orders findOrder(int id){
        return orderDAO.findById(id);
    }

    /**
     * Get the list of orders
     * @return list
     */
    public List<Orders> getOrders() {
        return orderDAO.createObjects();
    }
}
