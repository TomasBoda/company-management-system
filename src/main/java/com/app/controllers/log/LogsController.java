package com.app.controllers.log;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Log;
import com.app.model.Team;
import com.app.router.generic.LoaderPage;
import com.app.utils.Dialog;
import com.app.utils.NodeUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class LogsController extends LoaderPage<Log> implements Initializable {

    @FXML
    private VBox logsList;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label totalCount;

    private boolean sortTitleReversed = false;
    private boolean sortDateReversed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        renderData(logsList, "/components/log-card.fxml");
        NodeUtil.initScrollPane(scrollPane);

        totalCount.setText(getData().length + " total logs");
        sortByDate();
        sortByDate();
    }

    @Override
    public Log[] loadData() {
        Response<Log[]> response = Api.logs().getAll();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    @FXML
    private void sortByTitle() {
        Log[] logs = getData();

        if (sortTitleReversed) {
            Arrays.sort(logs, Collections.reverseOrder(Comparator.comparing(Log::getTitle)));
        } else {
            Arrays.sort(logs, Comparator.comparing(Log::getTitle));
        }

        setData(logs);
        sortTitleReversed = !sortTitleReversed;
        rerender();
    }

    @FXML
    private void sortByDate() {
        Log[] logs = getData();

        if (sortDateReversed) {
            Arrays.sort(logs, Collections.reverseOrder(Comparator.comparing(Log::getTimestamp)));
        } else {
            Arrays.sort(logs, Comparator.comparing(Log::getTimestamp));
        }

        setData(logs);
        sortDateReversed = !sortDateReversed;
        rerender();
    }
}
