package life.qbic.portal.view.Overview;

import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author fhanssen
 */


public class Projects extends Grid {


    public Projects() {
        this.addColumn("DFG ID", String.class);
        this.addColumn("Title", String.class);
        this.addColumn("Contact Person", String.class);
        this.addColumn("Description", String.class);

        this.setEditorEnabled(false);
        this.setHeightMode(HeightMode.ROW);
        this.setHeightByRows(10);
        this.setSizeFull();

    }
}
