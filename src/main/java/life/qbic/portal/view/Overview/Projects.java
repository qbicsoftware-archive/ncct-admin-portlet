package life.qbic.portal.view.Overview;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;
import org.vaadin.gridutil.cell.GridCellFilter;


/**
 * @author fhanssen
 */


public class Projects extends Grid {


    final GridCellFilter filter;

    public Projects() {

        this.addColumn("DFG ID", String.class);
        this.addColumn("Title", String.class);
        this.addColumn("Contact Person", String.class);
        this.addColumn("Description", String.class);

        this.setEditorEnabled(false);
        this.setHeightMode(HeightMode.ROW);
        this.setHeightByRows(20);
        this.setSizeFull();

        filter = new GridCellFilter(this);

        // simple filters
        filter.setTextFilter("DFG ID", true, false);
        filter.setTextFilter("Title", true, false);
        filter.setTextFilter("Contact Person", true, false);
        filter.setTextFilter("Description", true, false);
    }

}
