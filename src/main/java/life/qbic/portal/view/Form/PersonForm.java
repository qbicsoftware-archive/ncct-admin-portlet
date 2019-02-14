package life.qbic.portal.view.Form;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author fhanssen
 */


public class PersonForm extends VerticalLayout {

    private final Label title;
    private final Grid persons;

    private final TextField lastName;
    private final TextField firstName;
    private final TextField institution;
    private final TextField city;


    public PersonForm(String title) {

        this.title = new Label("<b><u>" + title + "</u><b>", ContentMode.HTML);

        this.persons = new Grid();

        this.persons.addColumn("Last Name", String.class);
        this.persons.addColumn("First Name", String.class);
        this.persons.addColumn("Institution", String.class);
        this.persons.addColumn("City", String.class);

        this.persons.setEditorEnabled(true);
        this.persons.setSizeFull();
        this.persons.setHeightMode(HeightMode.ROW);
        this.persons.setHeightByRows(5);
        this.persons.setSelectionMode(Grid.SelectionMode.MULTI);

        this.addComponents(this.title, persons);
        this.setSpacing(true);


        this.lastName = new TextField();
        lastName.addStyleName("corners");
        lastName.setMaxLength(100);
        lastName.setRequired(true);
        this.persons.getColumn("Last Name").setEditorField(lastName);

        this.firstName = new TextField();
        firstName.addStyleName("corners");
        firstName.setMaxLength(100);
        firstName.setRequired(true);
        this.persons.getColumn("First Name").setEditorField(firstName);

        this.institution = new TextField();
        institution.addStyleName("corners");
        institution.setMaxLength(100);
        institution.setRequired(true);
        this.persons.getColumn("Institution").setEditorField(institution);

        this.city = new TextField();
        city.addStyleName("corners");
        city.setMaxLength(50);
        city.setRequired(true);
        this.persons.getColumn("City").setEditorField(city);

    }

    public void addRow() {//TODO whenever an applicant is successfully added to grid, then add new line
        this.persons.addRow("", "", "", "");
    }

    public Grid getPersons() {
        return persons;
    }
}
