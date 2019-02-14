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
        // Create a header row to hold column filters
        HeaderRow filterRow = this.appendHeaderRow();

        // Set up a filter for all columns
        for (Object pid: this.getContainerDataSource().getContainerPropertyIds()) {
            HeaderCell cell = filterRow.getCell(pid);

            // Have an input field to use for filter
            TextField filterField = new TextField();
            filterField.addStyleName(ValoTheme.TEXTFIELD_TINY);
            filterField.setInputPrompt("Filter");

            // Set filter field width based on data type
            if (this.getContainerDataSource()
                    .getType(pid).equals(Integer.class)) {
                filterField.setColumns(5);
                cell.setStyleName("rightalign");
            } else
                filterField.setColumns(8);

            // Update filter When the filter input is changed
//            filterField.addTextChangeListener(change -> {
////                // Can't modify filters so need to replace
////                this.getContainerDataSource().removeContainerProperty(pid);
////
////                // (Re)create the filter if necessary
//                if (! change.getText().isEmpty())
////                   this.get
////                    this.sort(pid,
////                            new SimpleStringFilter(pid,
////                                   change.getText(), true, false));
//            });
//            cell.setComponent(filterField);
        }
    }
}
