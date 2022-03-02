package Presentation;

import Model.Client;
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
import BusinessLogic.ClientBLL;
import java.io.IOException;
import java.util.List;

public class ClientController {
    public TextField idT;
    public TextField nameT;
    public TextField emailT;
    public TextField phoneT;
    public TableView<Client> table;
    public TableColumn<Client, Integer> idColumn;
    public TableColumn<Client, String> nameColumn;
    public TableColumn<Client, String> emailColumn;
    public TableColumn<Client, String> phoneColumn;
    private final ClientController thisController=this;

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
     * When button pressed insert client in the table
     */
    public void insertButton() {
        String s=idT.getText();
        if(!s.equals("")){
            int id=Integer.parseInt(s);
            Client client=new Client(id,nameT.getText(),emailT.getText(),phoneT.getText());
            ClientBLL clientBLL=new ClientBLL(thisController);
            clientBLL.insertClient(client);
        }
        else{
            showError("No ID introduced!","Introduce ID.");
        }
    }

    /**
     * When button pressed update client in the table
     */
    public void updateButton() {
        String s=idT.getText();
        if(!s.equals("")){
            int id=Integer.parseInt(s);
            Client client=new Client(id,nameT.getText(),emailT.getText(),phoneT.getText());
            ClientBLL clientBLL=new ClientBLL(thisController);
            clientBLL.updateClient(client);
        }
        else{
            showError("No ID introduced!","Introduce ID.");
        }
    }

    /**
     * When button pressed delete client in the table
     */
    public void deleteButton() {
        String s=idT.getText();
        if(!s.equals("")){
            int id=Integer.parseInt(s);
            Client client=new Client(id,nameT.getText(),emailT.getText(),phoneT.getText());
            ClientBLL clientBLL=new ClientBLL(thisController);
            clientBLL.deleteClient(client);
        }
        else{
            showError("No ID introduced!","Introduce ID.");
        }
    }

    /**
     *Display the table of clients
     */
    public void show() {
        ClientBLL clientBLL=new ClientBLL(thisController);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        table.setItems(getObservableClients());
        /*ClientBLL clientBLL=new ClientBLL(thisController);
        table=clientBLL.createTable();*/
    }

    /**
     * Create observable list of clients
     * @return the list
     */
    private ObservableList<Client> getObservableClients() {
        ClientBLL clientBLL=new ClientBLL(thisController);
        List<Client> clients=clientBLL.getClients();
        ObservableList<Client> clients1= FXCollections.observableArrayList();
        for(Client client:clients){
            clients1.add(new Client(client.getID(),client.getName(),client.getEmail(),client.getPhoneNumber()));
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
