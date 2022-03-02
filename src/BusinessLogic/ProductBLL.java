package BusinessLogic;

import BusinessLogic.Validators.*;
import DataAccess.ProductDAO;
import Model.Product;
import java.util.ArrayList;
import java.util.List;
import Presentation.ProductController;

public class ProductBLL {
    private final List<Validator<Product>> validators;
    private final ProductDAO productDAO;
    private final ProductController productController;

    /**
     * Constructor
     * @param productController needed for displaying errors
     */
    public ProductBLL(ProductController productController) {
        validators = new ArrayList<>();
        validators.add(new PriceValidator());
        validators.add(new InStockValidator());
        productDAO = new ProductDAO();
        this.productController=productController;
    }

    /**
     * Check if id is unique
     * @param t - product for which id is validated
     * @return if it is unique or not
     */
    public boolean validateID(Product t) {
        List<Product> clients = productDAO.createObjects();
        for(Product client: clients){
            if(t.getID()==client.getID()){
                productController.showError("ID already selected for another product!","Select another ID.");
                return false;
            }
        }
        return true;
    }

    /**
     * Use the validators to check the product
     * @param t product to validate
     * @return status of validation
     */
    public boolean validateProduct(Product t) {
        if(t.getName().equals("")) {
            productController.showError("Field Name is mandatory!","Introduce Name.");
            return false;
        }
        if(validators.get(0).validate(t)){
            productController.showError("Price must be between 1 and 200!","Introduce another price!");
            return false;
        }
        if(validators.get(1).validate(t)){
            productController.showError("Stock must be a positive number!","Introduce another stock!");
            return false;
        }
        return true;
    }

    /**
     * validate and insert product
     * @param t inserted product
     */
    public void insertProduct(Product t) {
        if(validateProduct(t)&&validateID(t)){
            productDAO.insert(t);
            productController.showFinalizationMessage("Inserted was executed!","Press Show to see modifications.");
        }
    }

    /**
     * validate and update product
     * @param t updated product
     */
    public void updateProduct(Product t) {
        if(validateProduct(t)){
            productDAO.update(t);
            productController.showFinalizationMessage("Update was executed!","Press Show to see modifications.");
        }
    }

    /**
     * validate and delete product
     * @param t deleted product
     */
    public void deleteProduct(Product t) {
        productDAO.delete(t);
        productController.showFinalizationMessage("Delete was executed!","Press Show to see modifications.");
    }

    /**
     * Get the list of products
     * @return list
     */
    public List<Product> getProducts() {
        return productDAO.createObjects();
    }

    /**
     * Get product with specified id
     * @param id -id to look for
     * @return product
     */
    public Product findProduct(int id){
        return productDAO.findById(id);
    }
}
