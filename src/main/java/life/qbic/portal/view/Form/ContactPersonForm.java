package life.qbic.portal.view.Form;

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

    public TextField getLastName() {
        return lastName;
    }

    public TextField getFirstName() {
        return firstName;
    }

    public TextField getInstitution() {
        return institution;
    }

    public TextField getCity() {
        return city;
    }

    public TextField getEmail() {
        return email;
    }

    public TextField getPhoneNumber() {
        return phoneNumber;
    }
}
