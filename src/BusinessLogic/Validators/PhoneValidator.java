package BusinessLogic.Validators;

import Model.Client;

public class PhoneValidator implements Validator<Client> {
    /**
     * Validate Phone number
     * @param t Client for which it is validated
     * @return if it is valid or not
     */
    public boolean validate(Client t) {
        if(t.getPhoneNumber().length()!=10){
            return true;
        }
        else {
            return !t.getPhoneNumber().matches("[0-9]+");
        }
    }
}
