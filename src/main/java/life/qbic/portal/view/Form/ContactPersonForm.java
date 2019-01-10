package life.qbic.portal.view.Form;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContactPersonForm extends VerticalLayout {

    private final Label title;

    private final HorizontalLayout horizontalLayout;
    private final TextField lastName;
    private final TextField firstName;
    private final TextField institution;
    private final TextField city;
    private final TextField email;
    private final TextField phoneNumber;

    public ContactPersonForm(){

        this.title = new Label("Contact person", ContentMode.HTML);

        this.horizontalLayout = new HorizontalLayout();

        this.lastName = new TextField("Last Name");
        this.firstName = new TextField("First Name");
        this.institution = new TextField("Institution");
        this.city = new TextField("City");
        this.email = new TextField("E-mail address");
        this.phoneNumber = new TextField("Phone number");

        styleFields();

        this.horizontalLayout.addComponents(lastName, firstName, institution, city, email, phoneNumber);
        this.horizontalLayout.setSizeFull();

        this.addComponents(title, horizontalLayout);
        this.setSpacing(true);
    }

    private void styleFields(){
        this.lastName.addStyleName("corners");
        this.firstName.addStyleName("corners");
        this.institution.addStyleName("corners");
        this.city.addStyleName("corners");
        this.email.addStyleName("corners");
        this.phoneNumber.addStyleName("corners");

        this.lastName.setSizeFull();
        this.firstName.setSizeFull();
        this.institution.setSizeFull();
        this.city.setSizeFull();
        this.email.setSizeFull();
        this.phoneNumber.setSizeFull();



    }
}
