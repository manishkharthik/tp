package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of archived persons.
 */
public class ArchivedPersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml"; // Reuse same FXML
    private final Logger logger = LogsCenter.getLogger(ArchivedPersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code ArchivedPersonListPanel} with the given {@code ObservableList}.
     */
    public ArchivedPersonListPanel(ObservableList<Person> archivedPersonList) {
        super(FXML);
        personListView.setItems(archivedPersonList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }
}
