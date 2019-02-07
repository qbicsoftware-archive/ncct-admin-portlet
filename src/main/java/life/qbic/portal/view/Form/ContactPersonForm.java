package life.qbic.portal.view.Form;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContactPersonForm extends VerticalLayout {

    private final Label title;

    private final TextField lastName;
    private final TextField firstName;
    private final TextField institution;
    private final TextField city;
    private final TextField email;
    private final TextField phoneNumber;

    public ContactPersonForm(){

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

    private void styleFields(TextField textField){
        textField.addStyleName("corners");
        textField.setSizeFull();
        textField.setHeight(40, Unit.PIXELS);
    }

    private void validateFields(){

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

        this.lastName.setValue("Test");
        this.firstName.setValue("test");
        this.city.setValue("Tübigen");
        this.institution.setValue("Tübingen");
        this.phoneNumber.setValue("+6912381239");
        this.email.setValue("123@web.de");
    }

    public String getLastNameValue() {
        return lastName.getValue();
    }

    public String getFirstNameValue() {
        return firstName.getValue();
    }

    public String getInstitutionValue() {
        return institution.getValue();
    }

    public String getCityValue() {
        return city.getValue();
    }

    public String getEmailValue() {
        return email.getValue();
    }

    public String getPhoneNumberValue() {
        return phoneNumber.getValue();
    }
}
