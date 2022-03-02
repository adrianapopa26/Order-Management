package Presentation;

import BusinessLogic.ProductBLL;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class ProductController {
    public TextField idT;
    public TextField nameT;
    public TextField priceT;
    public TextField stockT;
    public TableView<Product> table;
    public TableColumn<Product, Integer> idColumn;
    public TableColumn<Product, String> nameColumn;
    public TableColumn<Product, Double> priceColumn;
    public TableColumn<Product, Integer> stockColumn;
    private final ProductController thisController=this;

    /**
     * Display error message
     * @param s heather text
     * @param s1 body text
     */
    public void showError(String s,String s1){
        Alert.AlertType type= Alert.AlertType.ERROR;
        Alert alert=new Alert(type,"");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText(s1);
        alert.getDialogPane().setHeaderText(s);
        alert.showAndWait();
    }

    /**
     * Display success message
     * @param s heather text
     * @param s1 body text
     */
    public void showFinalizationMessage(String s,String s1){
        Alert.AlertType type= Alert.AlertType.INFORMATION;
        Alert alert=new Alert(type,"");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText(s1);
        alert.getDialogPane().setHeaderText(s);
        alert.showAndWait();
    }

    /**
     * When button pressed insert product in the data base
     */
    public void insertButton() {
        String s=idT.getText();
        if(!s.equals("")){
            int id=Integer.parseInt(s);
            if(!priceT.getText().equals("")){
                double price=Double.parseDouble(priceT.getText());
                if(!stockT.getText().equals("")) {
                    int stock = Integer.parseInt(stockT.getText());
                    Product product = new Product(id, nameT.getText(), price, stock);
                    ProductBLL productBLL = new ProductBLL(thisController);
                    productBLL.insertProduct(product);
                }
                else
                {
                    showError("Stock mandatory!","Introduce Stock.");
                }
            }
            else{
                showError("Price mandatory!","Introduce Price.");
            }
        }
        else{
            showError("No ID introduced","Introduce ID");
        }
    }

    /**
     * When button pressed update product in the data base
     */
    public void updateButton() {
        String s=idT.getText();
        if(!s.equals("")){
            int id=Integer.parseInt(s);
            if(!priceT.getText().equals("")){
                double price=Double.parseDouble(priceT.getText());
                if(!stockT.getText().equals("")) {
                    int stock = Integer.parseInt(stockT.getText());
                    Product product = new Product(id, nameT.getText(), price, stock);
                    ProductBLL productBLL = new ProductBLL(thisController);
                    productBLL.updateProduct(product);
                }
                else
                {
                    showError("Stock mandatory!","Introduce Stock.");
                }
            }
            else{
                showError("Price mandatory!","Introduce Price.");
            }
        }
        else{
            showError("No ID introduced","Introduce ID");
        }
    }

    /**
     * When button pressed delete product from the data base
     */
    public void deleteButton() {
        String s=idT.getText();
        if(!s.equals("")){
            int id=Integer.parseInt(s);
            Product product=new Product(id,nameT.getText(),0.0,0);
            ProductBLL productBLL=new ProductBLL(thisController);
            productBLL.deleteProduct(product);
        }
        else{
            showError("No ID introduced","Introduce ID");
        }
    }

    /**
     * Display the table of products
     */
    public void show() {
        ProductBLL productBLL=new ProductBLL(thisController);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        table.setItems(getObservableProducts());
    }

    /**
     * Get observable list of products
     * @return the list
     */
    private ObservableList<Product> getObservableProducts() {
        ProductBLL productBLL=new ProductBLL(thisController);
        List<Product> products=productBLL.getProducts();
        ObservableList<Product> clients1= FXCollections.observableArrayList();
        for(Product product:products){
            clients1.add(new Product(product.getID(),product.getName(),product.getPrice(),product.getStock()));
        }
        return clients1;
    }

    /**
     * Go back to main stage
     * @param event event of the action
     * @throws IOException exception of thr action
     */
    public void goToMainWindow(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
