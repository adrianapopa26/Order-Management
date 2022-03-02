package BusinessLogic;

import BusinessLogic.Validators.EmailValidator;
import BusinessLogic.Validators.PhoneValidator;
import BusinessLogic.Validators.Validator;
import Model.Client;
import DataAccess.ClientDAO;
import Presentation.ClientController;

import java.util.ArrayList;
import java.util.List;

public class ClientBLL {
    private final List<Validator<Client>> validators;
    private final ClientDAO clientDAO;
    private final ClientController clientController;

    /**
     * Constructor
     * @param clientController needed for error messages
     */
    public ClientBLL(ClientController clientController) {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        validators.add(new PhoneValidator());
        clientDAO = new ClientDAO();
        this.clientController=clientController;
    }

    /**
     * Check if id is unique
     * @param t - client for which id is validated
     * @return if it is unique or not
     */
    public boolean validateID(Client t) {
        List<Client> clients = clientDAO.createObjects();
        for(Client client: clients){
            if(t.getID()==client.getID()){
                clientController.showError("ID already selected for another client!","Select another ID.");
                return false;
            }
        }
        return true;
    }

    /**
     * Use the validators to check the client
     * @param t Client to validate
     * @return status of validation
     */
    public boolean validateClient(Client t) {
        if(t.getName().equals("")) {
            clientController.showError("Field Name is mandatory!","Introduce Name.");
            return false;
        }
        if(validators.get(0).validate(t)){
            clientController.showError("Email not valid!","Try another Email.");
            return false;
        }
        if(validators.get(1).validate(t)){
            clientController.showError("Phone Number not valid!","Try another Phone Number.");
            return false;
        }
        return true;
    }

    /**
     * validate and insert client
     * @param t inserted client
     */
    public void insertClient(Client t) {
        if(validateClient(t)&&validateID(t)){
            clientDAO.insert(t);
            clientController.showFinalizationMessage("Inserted was executed!","Press Show to see modifications.");
        }
    }

    /**
     * validate and update client
     * @param t updated client
     */
    public void updateClient(Client t) {
        if(validateClient(t)){
            clientDAO.update(t);
            clientController.showFinalizationMessage("Update was executed!","Press Show to see modifications.");
        }
    }

    /**
     * validate and delete client
     * @param t deleted client
     */
    public void deleteClient(Client t) {
        clientDAO.delete(t);
        clientController.showFinalizationMessage("Delete was executed!","Press Show to see modifications.");
    }

    /**
     * Get the list of clients
     * @return list
     */
    public List<Client> getClients() {
        return clientDAO.createObjects();
    }

    /*public TableView<Client> createTable() {
        return clientDAO.createTable(clientDAO.createClientsList());
    }*/

    /**
     * Get client with specified id
     * @param id -id to look for
     * @return client
     */
    public Client findClient(int id){
        return clientDAO.findById(id);
    }
}
