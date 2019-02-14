package life.qbic.portal.view.Overview;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import life.qbic.portal.model.db.Project;

public class InformationForm  extends Window {

    Button download;

    public InformationForm(Project p){

        download = new Button();
        download.addStyleName("corners");
        download.setIcon(FontAwesome.DOWNLOAD);


        this.setCaption(p.getTitle());

        this.setWidth("800");
        this.setHeight("550");
        VerticalLayout subContent = new VerticalLayout();
        subContent.setSpacing(true);
        subContent.setMargin(true);
        this.setContent(subContent);

        Grid infoGrid = new Grid();
        infoGrid.addColumn("Information", String.class);
        infoGrid.addColumn("Value", String.class);

        infoGrid.setSelectionMode(Grid.SelectionMode.NONE);
        infoGrid.setEditorEnabled(false);
        infoGrid.setHeightMode(HeightMode.ROW);
        infoGrid.setHeightByRows(7);
        infoGrid.setSizeFull();

        infoGrid.addRow("Contact", p.getContactPerson().getFirstName().concat(" ").concat(p.getContactPerson().getLastName()));
        infoGrid.addRow("DFG ID", p.getDfgID());
        infoGrid.addRow("QBiC ID", p.getQbicID());
        infoGrid.addRow("Sequencing Aim", p.getSequencingAim());
        infoGrid.addRow("Topical Assignment", p.getTopicalAssignment());
        infoGrid.addRow("Keywords", p.getKeywords());
        infoGrid.addRow("Classification", p.getClassification());


        subContent.addComponent(infoGrid);
        Label l = new Label("Download Declaration of Intent: ");
        HorizontalLayout downloadLayout = new HorizontalLayout(l,download);
        downloadLayout.setSpacing(true);
        subContent.addComponent(downloadLayout);
        subContent.setComponentAlignment(downloadLayout, Alignment.BOTTOM_RIGHT);

    }

    public Button getDownload() {
        return download;
    }
}
