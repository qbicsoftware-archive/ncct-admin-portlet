package life.qbic.portal.view.Form;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import life.qbic.portal.model.utils.MyException;

/**
 * @author fhanssen
 */


public class ContactPersonForm extends VerticalLayout {

    private final Label title;

    private final TextField lastName;
    private final TextField firstName;
    private final TextField institution;
    private final TextField city;
    private final TextField email;
    private final TextField phoneNumber;

    public ContactPersonForm() {

        this.title = new Label("<b><u>Contact person:</u></b>", ContentMode.HTML);

        HorizontalLayout layout = new HorizontalLayout();

        this.lastName = new TextField("Last Name");
        this.firstName = new TextField("First Name");
        this.institution = new TextField("Institution");
        this.city = new TextField("City");
        this.email = new TextField("E-mail address");
        this.phoneNumber = new TextField("Phone number");

        styleFields(lastName);
        styleFields(firstName);
        styleFields(institution);
        styleFields(city);
        styleFields(email);
        styleFields(phoneNumber);

        validateFields();

        layout.addComponents(lastName, firstName, institution, city, email, phoneNumber);
        layout.setSizeFull();

        this.addComponents(title, layout);
        this.setSpacing(true);
    }

    private void styleFields(TextField textField) {
        textField.addStyleName("corners");
        textField.setSizeFull();
        textField.setHeight(40, Unit.PIXELS);
    }

    private void validateFields() {

        this.lastName.setRequired(true);
        this.lastName.setMaxLength(100);

        this.firstName.setRequired(true);
        this.firstName.setMaxLength(100);

        this.institution.setRequired(true);
        this.institution.setMaxLength(100);

        this.city.setRequired(true);
        this.city.setMaxLength(50);

        this.email.setRequired(true);
        this.email.setMaxLength(100);
        this.email.setValidationVisible(true);
        this.email.setImmediate(true);
        this.email.addValidator(new EmailValidator("Invalid format"));

        this.phoneNumber.setRequired(true);
        this.phoneNumber.setValidationVisible(true);
        this.phoneNumber.setImmediate(true);
        this.phoneNumber.setMaxLength(15);
        this.phoneNumber.addValidator(new RegexpValidator("\\+(?:[0-9]?){6,14}[0-9]", "+4945611234"));

    }

    public String getLastNameValue() throws MyException{
        if (lastName.getValue().isEmpty()) {
            throw new MyException("No last name set.");

        }
        return lastName.getValue();
    }

    public String getFirstNameValue() throws MyException{

        if (firstName.getValue().isEmpty()) {
            throw new MyException("No first name set.");

        }return firstName.getValue();
    }

    public String getInstitutionValue() throws MyException {
        if (institution.getValue().isEmpty()) {
            throw new MyException("No institution set.");

        }
        return institution.getValue();
    }

    public String getCityValue() throws MyException {
        if (city.getValue().isEmpty()) {
            throw new MyException("No city set.");

        }
        return city.getValue();
    }

    public String getEmailValue() throws MyException {
        if (email.getValue().isEmpty()) {
            throw new MyException("No email set.");

        }
        return email.getValue();
    }

    public String getPhoneNumberValue() throws  MyException{
        if (phoneNumber.getValue().isEmpty()) {
            throw new MyException("No phone number set.");

        }
        return phoneNumber.getValue();
    }

    public void setLastName(String lastName){
        this.lastName.setValue(lastName);
    }

    public void setFirstName(String firstName){
        this.firstName.setValue(firstName);
    }

    public void setInstitution(String institution){
        this.institution.setValue(institution);
    }

    public void setCity(String city){
        this.city.setValue(city);
    }

    public void setEmail(String email){
        this.email.setValue(email);
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber.setValue(phoneNumber);
    }


}
