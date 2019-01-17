package life.qbic.portal.view.Form;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class PersonForm extends VerticalLayout {

    private final Label title;
    private final Grid persons;

    public PersonForm(String title) {

        this.title = new Label("<b><u>" + title + "</u><b>", ContentMode.HTML);

        this.persons = new Grid();

        this.persons.addColumn("Last Name", String.class);
        this.persons.addColumn("First Name", String.class);
        this.persons.addColumn("Institution", String.class);
        this.persons.addColumn("City", String.class);

        this.persons.setEditorEnabled(true);
        this.persons.setSizeFull();
        this.persons.setHeightMode( HeightMode.ROW );
        this.persons.setHeightByRows(5);
        this.persons.setSelectionMode(Grid.SelectionMode.MULTI);

        this.addComponents(this.title, persons);
        this.setSpacing(true);



    }

    public void addRow(){//TODO whenever an applicant is successfully added to grid, then add new line
        this.persons.addRow("", "", "", "");
    }

    public Grid getPersons() {
        return persons;
    }
}
