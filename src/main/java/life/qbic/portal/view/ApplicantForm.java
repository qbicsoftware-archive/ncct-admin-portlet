package life.qbic.portal.view;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;

public class ApplicantForm extends Grid {


    public ApplicantForm() {
        this.addColumn("Last Name", String.class);
        this.addColumn("First Name", String.class);
        this.addColumn("Institution", String.class);
        this.addColumn("City", String.class);

        this.setEditorEnabled(true);
        this.setCaption("Applicants");
        this.setSizeFull();
        this.setHeightMode( HeightMode.ROW );
        this.setHeightByRows(5);
        this.setSelectionMode(SelectionMode.MULTI);


    }


    public void addRow(){//TODO whenever an applicant is successfully added to grid, then add new line
        this.addRow("", "", "", "");
    }

}
