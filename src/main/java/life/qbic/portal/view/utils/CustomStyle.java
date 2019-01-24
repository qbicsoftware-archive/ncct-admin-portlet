package life.qbic.portal.view.utils;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public final class CustomStyle {

    public static void styleComboBox(ComboBox comboBox){
        comboBox.addStyleName("corners");
        comboBox.setFilteringMode(FilteringMode.CONTAINS);
    }

    public static void styleTextArea(TextArea textArea){
        textArea.addStyleName("corners");
        textArea.setSizeFull();
        textArea.setWordwrap(true);
    }


    public static void styleTextField(TextField textField){
        textField.addStyleName("corners");
        textField.setHeight(40, Sizeable.Unit.PIXELS);
    }
}