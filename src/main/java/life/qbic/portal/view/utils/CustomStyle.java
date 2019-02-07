package life.qbic.portal.view.utils;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public final class CustomStyle {


    public static void styleTextArea(TextArea textArea){
        textArea.addStyleName("corners");
        textArea.setSizeFull();
        textArea.setWordwrap(true);
    }


    public static void styleTextField(TextField textField){
        textField.addStyleName("corners");
        textField.setHeight(40, Sizeable.Unit.PIXELS);
    }


    public static void addTextFieldSettings(TextField textField, String validationRegex, String errorMessage) {
        textField.setRequired(true);
        textField.setValidationVisible(true);
        textField.setImmediate(true);
        textField.addValidator(new RegexpValidator(validationRegex, true, errorMessage));
    }

    public static void addComboboxSettings(ComboBox comboBox) {
        comboBox.setFilteringMode(FilteringMode.CONTAINS);
        comboBox.setImmediate(true);
        comboBox.setValidationVisible(true);
        comboBox.addValidator(new StringLengthValidator("Select value",1, Integer.MAX_VALUE, false));

    }


    public static void addGridSettings(Grid grid) {
        grid.setSizeFull();
        grid.setHeightMode(HeightMode.ROW);
        grid.setHeightByRows(5);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setEditorEnabled(true);
    }
}
