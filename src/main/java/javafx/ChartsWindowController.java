package javafx;

import timecollector.SummarizeTime;
import hibernate.Admin;
import hibernate.DBOperations;
import hibernate.Server;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ChartsWindowController implements Initializable {

    @FXML
    private BorderPane chartsWindow;

    @FXML
    private Text caption;

    @FXML
    private Text Error;

    @FXML
    private StackPane chartStackPane;

    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private AreaChart<String, Number> areaChart;

    @FXML
    private StackedBarChart<String, Number> barChart;

    @FXML
    private DatePicker setDateFrom;

    @FXML
    private DatePicker setDateTo;

    @FXML
    private ComboBox<String> chartsComboBox;

    @FXML
    private ComboBox<String> colorComboBox;

    @FXML
    private ProgressIndicator chartsProgressIndicator;

    @FXML
    private ProgressIndicator adminsListProgressIndicator;

    @FXML
    private ListView<Admin> adminsListView;

    @FXML
    private Text adminError;

    private Server chosenServer;

    static Admin chosenAdmin;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    private ObservableList<Admin> adminObservableList;

    public static HostServices hostServices;

    @FXML
    protected void handleCloseButton(ActionEvent event) {
        String baseWindowFXMLFile = "BaseWindow.fxml";
        this.changeScene(baseWindowFXMLFile, event);
    }

    @FXML
    private void showPieChart() {
        pieChart.setTitle("Admin Pie Chart (From: " + dateFrom + " | To : " + dateTo + ")");
        setChartsVisibility(true, false, false, false);
    }

    @FXML
    private void showLineChart() {        //TODO: CHECK IF ANIMATION WORKS AFTER FIRST ADMIN HAS BEEN ADDED
        lineChart.setTitle("Admin Line Chart (From: " + dateFrom + " | To : " + dateTo + ")");
        setChartsVisibility(false, true, false, false);
    }

    @FXML
    private void showAreaChart() {
        areaChart.setTitle("Admin Area Chart (From: " + dateFrom + " | To : " + dateTo + ")");
        setChartsVisibility(false, false, true, false);
    }

    @FXML
    private void showBarChart() {
        barChart.setTitle("Admin Bar Chart (From: " + dateFrom + " | To : " + dateTo + ")");
        setChartsVisibility(false, false, false, true);
    }

    @FXML
    protected void handleSaveScreenshot(ActionEvent event) {

        //Fix scene resize bug
        chartsWindow.setPrefSize(chartsWindow.getWidth(), chartsWindow.getHeight());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //Create new filechooser
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PNG files (*.png)", "*.png");

        //Configure filechooser
        fileChooser.setTitle("Save as...");
        if (pieChart.isVisible()) {
            fileChooser.setInitialFileName("adminpiechart.png");
        } else if (lineChart.isVisible()) {
            fileChooser.setInitialFileName("adminlinechart.png");
        } else if (areaChart.isVisible()) {
            fileChooser.setInitialFileName("adminareachart.png");
        } else if (barChart.isVisible()) {
            fileChooser.setInitialFileName("adminbarchart.png");
        } else {
            fileChooser.setInitialFileName("adminchart.png");
        }
        fileChooser.getExtensionFilters().add(extFilter);

        //Show filechooser
        File file = fileChooser.showSaveDialog(stage);

        //Fix image DPI
        double pixelScale = 2.0;
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setTransform(Transform.scale(pixelScale, pixelScale));

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) Math.round(chartsWindow.getWidth() * 2), (int) Math.round(chartsWindow.getHeight() * 2));
                WritableImage image = chartsWindow.snapshot(snapshotParameters, writableImage);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (Exception e) {
                Error.setText(Arrays.toString(e.getStackTrace()));
                Error.setFill(Color.RED);
            }
        }
    }

//    @FXML
//    protected void handleNaturalAdminColor(ActionEvent event) {
//        if (pieChart.isVisible()) {
//            Platform.runLater(() -> {
//                for (int i = 0; i < pieChart.getData().size(); i++) {
//                    pieChart.getData().get(i).getNode().lookup(".chart-pie").setStyle("-fx-pie-color: " + "#" + pieChart.getData().get(i));
//                }
//                for (Node n : pieChart.getChildrenUnmodifiable()) {
//                    if (n instanceof Legend) {
//                        int count = 0;
//                        for (Legend.LegendItem legendItem : ((Legend) n).getItems()) {
//                            String[] currentAdminChartColorStyle = pieChart.getData().get(count).getNode().lookup(".chart-pie").getStyle().split(": ");
//                            String currentAdminChartColor = currentAdminChartColorStyle[1];
//                            legendItem.getSymbol().setStyle("-fx-background-color: " + currentAdminChartColor);
//                            count++;
//                        }
//                    }
//                }
//            });
//        } else if (lineChart.isVisible()) {
//            Platform.runLater(() -> {
//                for (int i = 0; i < lineChart.getData().size(); i++) {
//                    lineChart.getData().get(i).getNode().lookup(".chart-series-line").setStyle("-fx-stroke: " + "#" + lineChart.getData().get(i));
//                }
//
//                for (Node n : lineChart.getChildrenUnmodifiable()) {
//                    if (n instanceof Legend) {
//                        int count = 0;
//                        for (Legend.LegendItem legendItem : ((Legend) n).getItems()) {
//                            String[] currentAdminChartColorStyle = lineChart.getData().get(count).getNode().lookup(".chart-series-line").getStyle().split(": ");
//                            String currentAdminChartColor = currentAdminChartColorStyle[1];
//                            legendItem.getSymbol().setStyle("-fx-background-color: " + currentAdminChartColor);
//                            count++;
//                        }
//                    }
//                }
//            });
//            //Area charts color would require lots of additional work, since it isnt possible to getNodes in it, for the time being i'm skipping it
//        } else if (barChart.isVisible()) {
//            Platform.runLater(() -> {
//                for (int i = 0; i < barChart.getData().size(); i++) {
//
//                    //Get admin color
//                    String adminColor = barChart.getData().get(i).toString();
//
//                    //Set new node styles
//                    for (Node n : barChart.lookupAll(".default-color" + i + ".chart-bar")) {
//                        n.setStyle("-fx-bar-fill: " + "#" + adminColor);
//                    }
//                }
//            });
//        }
//    }
//
//    //TODO: FIX COLORS
//    @FXML
//    protected void handleDefaultChartColor(ActionEvent event) {
//        rePopulateCharts();
//    }
//
//    @FXML
//    protected void handleRandomAdminColor(ActionEvent event) {
//        if (pieChart.isVisible()) {
//            Platform.runLater(() -> {
//                for (int i = 0; i < pieChart.getData().size(); i++) {
//                    //Generate random color
//                    StringBuilder newRGBColor = new StringBuilder()
//                            .append("rgb")
//                            .append("(")
//                            .append(randomInt(255))
//                            .append(", ")
//                            .append(randomInt(255))
//                            .append(", ")
//                            .append(randomInt(255))
//                            .append(")");
//
//                    pieChart.getData().get(i).getNode().lookup(".chart-pie").setStyle("-fx-pie-color: " + newRGBColor);
//                }
//
//                for (Node n : pieChart.getChildrenUnmodifiable()) {
//                    if (n instanceof Legend) {
//                        int count = 0;
//                        for (Legend.LegendItem legendItem : ((Legend) n).getItems()) {
//                            String[] currentAdminRGBCStyle = pieChart.getData().get(count).getNode().lookup(".chart-pie").getStyle().split(": ");
//                            String newRGBColor = currentAdminRGBCStyle[1];
//                            legendItem.getSymbol().setStyle("-fx-background-color: " + newRGBColor);
//                            count++;
//                        }
//                    }
//                }
//            });
//        } else if (lineChart.isVisible()) {
//
//            Platform.runLater(() -> {
//                for (int i = 0; i < lineChart.getData().size(); i++) {
//                    //Generate random color
//                    StringBuilder newRGBColor = new StringBuilder()
//                            .append("rgb")
//                            .append("(")
//                            .append(randomInt(255))
//                            .append(", ")
//                            .append(randomInt(255))
//                            .append(", ")
//                            .append(randomInt(255))
//                            .append(")");
//
//                    lineChart.getData().get(i).getNode().lookup(".chart-series-line").setStyle("-fx-stroke: " + newRGBColor);
//                }
//
//                for (Node n : lineChart.getChildrenUnmodifiable()) {
//                    if (n instanceof Legend) {
//                        int count = 0;
//                        for (Legend.LegendItem legendItem : ((Legend) n).getItems()) {
//                            String[] currentAdminRGBCStyle = lineChart.getData().get(count).getNode().lookup(".chart-series-line").getStyle().split(": ");
//                            String newRGBColor = currentAdminRGBCStyle[1];
//                            legendItem.getSymbol().setStyle("-fx-background-color: " + newRGBColor);
//                            count++;
//                        }
//                    }
//                }
//            });
//        } else if (barChart.isVisible()) {
//            Platform.runLater(() -> {
//                for (int i = 0; i < barChart.getData().size(); i++) {
//
//                    //Generate random color
//                    StringBuilder newRGBColor = new StringBuilder()
//                            .append("rgb")
//                            .append("(")
//                            .append(randomInt(255))
//                            .append(", ")
//                            .append(randomInt(255))
//                            .append(", ")
//                            .append(randomInt(255))
//                            .append(")");
//
//                    //Set new node styles
//                    for (Node n : barChart.lookupAll(".default-color" + i + ".chart-bar")) {
//                        n.setStyle("-fx-bar-fill: " + newRGBColor);
//                    }
//                }
//            });
//        }
//    }

    public class AdminListCellFactory implements Callback<ListView<Admin>, ListCell<Admin>> {
        @Override
        public ListCell<Admin> call(ListView<Admin> adminListView) {
            return new AdminListViewCell((admin, value) -> rePopulateCharts());
        }
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        //Setup Admin List View
        adminObservableList = FXCollections.observableArrayList();

        adminsListView.setCellFactory(new AdminListCellFactory());
        adminsListView.setItems(adminObservableList);

        //Initialize server list for server dropdown
        chosenServer = BaseWindowController.chosenServer;

        //Set up progress bars
        adminsListProgressIndicator.progressProperty().bind(getAdmins.progressProperty());
        adminsListProgressIndicator.setVisible(true);
        populateAdminsList();

        chartsComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            if (t1.contains("Pie")) {
                showPieChart();
            } else if (t1.contains("Line")) {
                showLineChart();
            } else if (t1.contains("Area")) {
                showAreaChart();
            } else if (t1.contains("Bar")) {
                showBarChart();
            }
        });

//        colorComboBox.valueProperty().addListener((observableValue, o, t1) -> {
//            if (t1.contains("Natural")) {
//                //TODO: SHOW NATURAL ADMIN COLORS
//            } else if (t1.contains("Random")) {
//                //TODO: SHOW RANDOM COLORS
//            } else if (t1.contains("Default")) {
//                //TODO: SHOW DEFAULT COLORS
//            }
//        });

        setDateFrom.setValue(LocalDate.now().minusDays(27));
        setDateTo.setValue(LocalDate.now());

        dateFrom = setDateFrom.getValue();
        dateTo = setDateTo.getValue();

        setDateFrom.valueProperty().addListener((observableValue, localDate, t1) -> {
            dateFrom = t1;
            Platform.runLater(this::rePopulateCharts);
        });

        setDateTo.valueProperty().addListener((observableValue, localDate, t1) -> {
            dateTo = t1;
            Platform.runLater(this::rePopulateCharts);
        });

        // Factory to create Cell of DatePicker
        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
        setDateFrom.setDayCellFactory(dayCellFactory);
        setDateTo.setDayCellFactory(dayCellFactory);
    }

    // Factory to create Cell of DatePicker
    private Callback<DatePicker, DateCell> getDayCellFactory() {

        return new Callback<>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Disable days before 28 days from now and any date that is after todays date
                        Platform.runLater(() -> {
                            if (item.isBefore(LocalDate.now().minusDays(27)) || item.isAfter(LocalDate.now())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        });
                    }
                };
            }
        };
    }

    private void changeScene(String windowFXMLFile, ActionEvent event) {
        Parent window = null;
        try {
            window = FXMLLoader.load(getClass().getResource(windowFXMLFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = null;
        if (window != null) {
            scene = new Scene(window);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void setChartsVisibility(Boolean pieChartVisibility, Boolean lineChartVisibility, Boolean areaChartVisibility, Boolean barChartVisibility) {
        pieChart.setVisible(pieChartVisibility);
        lineChart.setVisible(lineChartVisibility);
        areaChart.setVisible(areaChartVisibility);
        barChart.setVisible(barChartVisibility);
    }

//    private static Integer randomInt(Integer scopeInclusive) {
//        Random randomGenerator = new Random();
//        int randomInt = randomGenerator.nextInt(scopeInclusive) + 1;
//        return randomInt;
//    }
//
//
//            Double adminTimeSum = 0.0;
//            for (PieChart.Data data : pieChart.getData()) {
//                adminTimeSum += data.getPieValue();
//            }
//
//            final Double adminTimeSumFinal = adminTimeSum;
//
//            for (PieChart.Data data : pieChart.getData()) {
//                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
//                        e -> {
//                            caption.setTranslateX(e.getSceneX());
//                            caption.setTranslateY(e.getSceneY());
//                            double dataValue = data.getPieValue() / adminTimeSumFinal * 100;
//                            String result = String.format("%.2f", dataValue);
//                            caption.setText(result + "%");
//                        });
//            }


    private Boolean isNewData(Admin a) {
        return a.getAdminTimeList() == null;
    }

    private void setChartData(Admin a) {
        List<LocalDateTime> adminDateTimes;

        if (!dateFrom.equals(LocalDate.now().minusDays(27)) | !dateTo.equals(LocalDate.now())) {
            adminDateTimes = SummarizeTime.cutTimesListToPeriod(a.getAdminTimeList(), dateFrom, dateTo);
        } else {
            adminDateTimes = a.getAdminTimeList();
        }

        //Create new Chart series
        XYChart.Series<String, Number> lineChartData = new XYChart.Series<>();
        XYChart.Series<String, Number> areaChartData = new XYChart.Series<>();
        XYChart.Series<String, Number> barChartData = new XYChart.Series<>();

        //Set admins name as series title
        lineChartData.setName(a.getAdminName());
        areaChartData.setName(a.getAdminName());
        barChartData.setName(a.getAdminName());

        //Convert all admin data to chart data
        for (LocalDateTime at : adminDateTimes) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(at.toLocalDate().toString(), (at.toLocalTime().getHour() * 60) + (at.toLocalTime().getMinute()));
            lineChartData.getData().add(data);
            areaChartData.getData().add(data);
            barChartData.getData().add(data);
        }

        PieChart.Data pieChartData = new PieChart.Data(a.getAdminName() + " (" + SummarizeTime.sumTimeString(adminDateTimes) + ")", SummarizeTime.adminTimesDurationTotal(adminDateTimes));

        //Add chart data to charts
        Platform.runLater(() -> pieChart.getData().add(pieChartData));
        Platform.runLater(() -> lineChart.getData().add(lineChartData));
        Platform.runLater(() -> areaChart.getData().add(areaChartData));
        Platform.runLater(() -> barChart.getData().add(barChartData));
    }


    private void clearChartData() {
        //Clear all datas
        pieChart.getData().clear();
        lineChart.getData().clear();
        areaChart.getData().clear();
        barChart.getData().clear();
    }

    private void rePopulateCharts() {
        chartsProgressIndicator.setVisible(true);
        Platform.runLater(() -> {
            clearChartData();
            for (Admin a : adminObservableList) {
                if (a.isSelected()) {
                    if (isNewData(a)) {
                        //Get admin data
                        GetAdminChartDataService getData = new GetAdminChartDataService(a, LocalDate.now().minusDays(27), LocalDate.now());
                        getData.restart();

                        getData.setOnFailed(e -> Error.setText("Get data failed " + getData.getMessage()));
                        getData.setOnSucceeded(e -> {
                            a.setAdminTimeList(getData.getValue());
                            setChartData(a);
                            chartsProgressIndicator.setVisible(false);
                        });
                    } else {
                        setChartData(a);
                        chartsProgressIndicator.setVisible(false);
                    }
                }
            }
        });
    }

    // ** ADMINS LIST CONTROLS ** //

    private void populateAdminsList() {

        adminsListProgressIndicator.setVisible(true);

        adminObservableList.clear();

        getAdmins.restart();

        //On fail
        getAdmins.setOnFailed(e -> {
            Error.setText("Something went terribly wrong, give this piece of code to person responsible for this: " + getAdmins.getException().toString());
            adminsListProgressIndicator.setVisible(false);
        });

        //On succeed
        getAdmins.setOnSucceeded(e -> {
            if (adminObservableList.isEmpty()) {
                adminsListView.setPlaceholder(new Label("There are no admins added, right click to add new admin"));
                adminsListProgressIndicator.setVisible(false);
            } else {
                adminsListProgressIndicator.setVisible(false);
                for (Admin a:adminObservableList) {
                    a.setSelected(false);
                }
            }
        });
    }

    private Service getAdmins = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    Platform.runLater(() -> {
                        adminObservableList.setAll(DBOperations.displayAdminRecords(chosenServer.getServerName()));
                    });
                    return null;
                }
            };
        }
    };

    @FXML
    protected void handleAdminAdd(ActionEvent event) {
        String addAdminFXMLFile = "admins/AddAdmin.fxml";

        // New window (Stage)
        Parent newWindow = null;
        try {
            newWindow = FXMLLoader.load(getClass().getResource(addAdminFXMLFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert newWindow != null;
        Scene secondScene = new Scene(newWindow, 450, 350); //Create a new scene in a newly created stackpane

        Stage addAdminStage = new Stage();

        addAdminStage.setScene(secondScene);
        addAdminStage.initModality(Modality.WINDOW_MODAL);
        addAdminStage.initOwner(chartsWindow.getScene().getWindow());

        addAdminStage.centerOnScreen();

        addAdminStage.show();

        addAdminStage.setOnHidden(ev -> populateAdminsList());
    }

    @FXML
    protected void handleAdminEdit() { //TODO: Make this appear in new small window, do the same with "add"
        if (adminsListView.getSelectionModel().getSelectedItem() == null) {
            adminError.setText("Please choose an Admin first!");
            adminError.setStyle("-fx-font-weight: bold;");
            adminError.setFill(Color.RED);
            Platform.runLater(this::clearAdminErrorText);
        } else {
            chosenAdmin = adminsListView.getSelectionModel().getSelectedItem();


            // New window (Stage)
            String editAdminFXMLFile = "admins/EditAdmin.fxml";

            Parent newWindow = null;
            try {
                newWindow = FXMLLoader.load(getClass().getResource(editAdminFXMLFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert newWindow != null;
            Scene secondScene = new Scene(newWindow, 450, 350); //Create a new scene in a newly created stackpane

            Stage editAdminStage = new Stage();

            editAdminStage.setScene(secondScene);
            editAdminStage.initModality(Modality.WINDOW_MODAL);
            editAdminStage.initOwner(chartsWindow.getScene().getWindow());

            editAdminStage.centerOnScreen();

            editAdminStage.show();

            editAdminStage.setOnHidden(ev -> populateAdminsList());
        }
    }

    @FXML
    protected void handleAdminDelete() {
        if (adminsListView.getSelectionModel().getSelectedItem() == null) {
            adminError.setText("Please choose an Admin first!");
            adminError.setFill(Color.RED);
            adminError.setStyle("-fx-font-weight: bold;");
            Platform.runLater(this::clearAdminErrorText);
        } else {
            chosenAdmin = adminsListView.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete admin");
            alert.setContentText("Are you sure you want to delete admin \"" + chosenAdmin.getAdminName() + "\"?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBOperations.deleteAdminRecord(chosenAdmin.getAdminID()); //Delete admin

                populateAdminsList(); //Repopulate admins list
            }
        }
    }

    private void clearAdminErrorText() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            adminError.setText("");
        });
        thread.start();
    }
}