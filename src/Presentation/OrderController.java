package Presentation;

import BusinessLogic.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OrderController {
    public TextField idT;
    public ChoiceBox<Integer> clientT;
    public ChoiceBox<Integer> productT;
    public TextField quantityT;
    public TableView<Orders> table;
    public TableColumn<Orders, Integer> idColumn;
    public TableColumn<Orders, Integer> nameColumn;
    public TableColumn<Orders, Integer> emailColumn;
    public TableColumn<Orders, Integer> phoneColumn;
    private final ClientController clientController=new ClientController();
    private final ProductController productController=new ProductController();
    private final OrderController thisController=this;

    /**
     * Initialize stage
     */
    public void initialize(){
        getClientsID();
        getProductsID();
    }

    /**
     * Get ID's of the clients in the data base
     */
    public void getClientsID(){
        ObservableList<Integer> id= FXCollections.observableArrayList();
        ClientBLL clientBLL=new ClientBLL(clientController);
        List<Client> clients=clientBLL.getClients();
        for(Client client:clients){
            id.add(client.getID());
        }
        clientT.getItems().addAll(id);
    }

    /**
     * Get ID's of the products in the data base
     */
    public void getProductsID(){
        ObservableList<Integer> id= FXCollections.observableArrayList();
        ProductBLL productBLL=new ProductBLL(productController);
        List<Product> products=productBLL.getProducts();
        for(Product product:products){
            id.add(product.getID());
        }
        productT.getItems().addAll(id);
    }

    /**
     * Display error message
     * @param s heather text
     * @param s1 body text
     */
    public void showError(String s,String s1) {
        Alert.AlertType type = Alert.AlertType.ERROR;
        Alert alert = new Alert(type, "");
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
        Alert.AlertType type= Alert.AlertType.CONFIRMATION;
        Alert alert=new Alert(type,"");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText(s1);
        alert.getDialogPane().setHeaderText(s);
        alert.showAndWait();
    }

    /**
     * When button pressed insert order in the data base
     */
    public void insertButton() {
        String s=idT.getText();
        if(!s.equals("")){
            int id=Integer.parseInt(s);
            if(clientT.getValue()!=null) {
                int clientId = clientT.getValue();
                if(productT.getValue()!=null) {
                    int productId = productT.getValue();
                    if(quantityT.getText()!=null){
                        int quantity = Integer.parseInt(quantityT.getText());
                        Orders orders = new Orders(id, clientId, productId, quantity);
                        OrderBLL orderBLL = new OrderBLL(thisController);
                        orderBLL.insertOrder(orders);
                    }
                    else{
                        showError("No quantity selected","Select quantity.");
                    }
                }
                else{
                    showError("No product selected","Select product.");
                }
            }
            else{
                showError("No client selected","Select client.");
            }
        }
        else{
            showError("No ID introduced","Introduce ID");
        }
    }

    /**
     * When button pressed update order in the data base
     */
    public void updateButton() {
        String s=idT.getText();
        if(!s.equals("")){
            int id=Integer.parseInt(s);
            if(clientT.getValue()!=null) {
                int clientId = clientT.getValue();
                if(productT.getValue()!=null) {
                    int productId = productT.getValue();
                    if(quantityT.getText()!=null){
                        int quantity = Integer.parseInt(quantityT.getText());
                        Orders orders = new Orders(id, clientId, productId, quantity);
                        OrderBLL orderBLL = new OrderBLL(thisController);
                        orderBLL.updateOrder(orders);
                    }
                    else{
                        showError("No quantity selected","Select quantity.");
                    }
                }
                else{
                    showError("No product selected","Select product.");
                }
            }
            else{
                showError("No client selected","Select client.");
            }
        }
        else{
            showError("No ID introduced","Introduce ID");
        }
    }

    /**
     * When button pressed delete order from the data base
     */
    public void deleteButton() {
        String s=idT.getText();
        if(!s.equals("")){
            int id=Integer.parseInt(s);
            Orders orders =new Orders(id,0,0,0);
            OrderBLL orderBLL=new OrderBLL(thisController);
            orderBLL.deleteOrder(orders);
        }
        else{
            showError("No ID introduced","Introduce ID");
        }
    }

    /**
     * Generate a .txt of the file with the bill
     */
    public void generateBill(){
        String s=idT.getText();
        if(!s.equals("")){
            int id=Integer.parseInt(s);
            OrderBLL orderBLL=new OrderBLL(thisController);
            Orders o=orderBLL.findOrder(id);
            try{
                FileWriter myWriter=new FileWriter("Bill.txt",true);
                myWriter.write("Order number:"+o.getID()+"\n");
                ClientBLL cb=new ClientBLL(clientController);
                Client c=cb.findClient(o.getClientID());
                ProductBLL pb=new ProductBLL(productController);
                Product p=pb.findProduct(o.getProductID());
                myWriter.write("Client: "+c.getName()+"\n");
                myWriter.write("Product: "+p.getName()+"\n");
                myWriter.write("Price/buc: "+p.getPrice()+"\n");
                myWriter.write("Quantity: "+o.getQuantity()+"\n");
                myWriter.write("Final price: "+o.getQuantity()*p.getPrice()+"\n\n");
                myWriter.close();
                showFinalizationMessage("Bill generated successfully!","Open the file too see it's content.");
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            showError("No ID introduced","Introduce ID");
        }
    }

    /**
     * Display the table of orders
     */
    public void show() {
        OrderBLL orderBLL=new OrderBLL(thisController);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("ClientID"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        table.setItems(getObservableOrders());
    }

    /**
     * Generate observable list of orders
     * @return list
     */
    private ObservableList<Orders> getObservableOrders() {
        OrderBLL orderBLL=new OrderBLL(thisController);
        List<Orders> orders=orderBLL.getOrders();
        ObservableList<Orders> orders1= FXCollections.observableArrayList();
        for(Orders order:orders){
            orders1.add(new Orders(order.getID(),order.getClientID(),order.getProductID(),order.getQuantity()));
        }
        return orders1;
    }

    /**
     * Go back to main stage
     * @param event event of the action
     * @throws IOException exception of thr action
     */
    public void goToMainWindow(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}