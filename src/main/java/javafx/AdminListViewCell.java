package javafx;

import hibernate.Admin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class AdminListViewCell extends ListCell<Admin> {

    @FXML
    private GridPane adminCellGridPane;

    @FXML
    private CheckBox adminSelect;

    @FXML
    private Label adminName;

    @FXML
    private Label adminLink;

    @FXML
    private Rectangle adminColor;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Admin a, boolean empty) {
        super.updateItem(a, empty);
        if(empty || a == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/javafx/admins/AdminCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            adminName.setText(a.getAdminName());
            String [] linkParts = a.getAdminLink().split("player=");
            adminLink.setText(linkParts[1]);

            adminColor.setFill(Color.valueOf(a.getAdminColor()));
            a.setSelected(false);
            adminSelect.selectedProperty().addListener((ov, old_val, new_val) -> {
                a.setSelected(new_val);
                super.updateItem(a, false);
            });

            setText(null);
            setGraphic(adminCellGridPane);
        }

    }
}