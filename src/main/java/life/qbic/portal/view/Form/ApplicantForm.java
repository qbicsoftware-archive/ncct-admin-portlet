package life.qbic.portal.view.Form;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ApplicantForm extends VerticalLayout {

    private final Label title;
    private final Grid applicants;

    public ApplicantForm() {

        this.title = new Label("Applicants",ContentMode.HTML);
        this.applicants = new Grid();

        this.applicants.addColumn("Last Name", String.class);
        this.applicants.addColumn("First Name", String.class);
        this.applicants.addColumn("Institution", String.class);
        this.applicants.addColumn("City", String.class);

        this.applicants.setEditorEnabled(true);
        this.applicants.setSizeFull();
        this.applicants.setHeightMode( HeightMode.ROW );
        this.applicants.setHeightByRows(5);
        this.applicants.setSelectionMode(Grid.SelectionMode.MULTI);

        this.addComponents(title, applicants);
        this.setSpacing(true);

    }


    public void addRow(){//TODO whenever an applicant is successfully added to grid, then add new line
        this.applicants.addRow("", "", "", "");
    }

}
