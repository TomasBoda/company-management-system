package com.app.controllers.log;

import com.app.model.Log;
import com.app.router.generic.Component;
import com.app.utils.datetime.TimeUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LogCardController extends Component<Log> {

    @FXML
    private Label title;
    @FXML
    private Label datetime;
    @FXML
    private Label description;

    @Override
    public void onData(Log log) {
        title.setText(log.getTitle());
        datetime.setText(TimeUtil.toString(log.getTimestamp()));
        description.setText(log.getDescription());
    }
}
