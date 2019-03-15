package javafx;

import getinfo.SummarizeTime;
import hibernate.Admin;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class ChartsWindowController implements Initializable {

    @FXML
    private GridPane chartsWindow;

    @FXML
    private Text adminText;

    @FXML
    Label caption;

    @FXML
    private Text Error;

    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private AreaChart areaChart;

    @FXML
    private BarChart barChart;

    @FXML
    private Button pieChartButton;

    @FXML
    private Button lineChartButton;

    @FXML
    private Button areaChartButton;

    @FXML
    private Button barChartButton;


    @FXML
    private DatePicker setdateFrom;

    @FXML
    private DatePicker setdateTo;

    @FXML
    private Button naturalColorButton;

    @FXML
    private Button randomColorButton;

    @FXML
    private Button defaultColorButton;

    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private ProgressBar progressBar;

    public static List<Admin> adminsList;
    private static List<String> defaultColorsList = new ArrayList<>();

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Integer workToDo;

    @FXML
    protected void handleCloseButton(ActionEvent event) {

        try {
            Parent baseWindow = FXMLLoader.load(getClass().getResource("admins/admins.fxml"));
            chartsWindow.getChildren().setAll(baseWindow);
        } catch (IOException e) {
            e.printStackTrace();
            Error.setText("Something went wrong");
            Error.setFill(Color.RED);
        }
    }

    @FXML
    protected void generatePieChart(ActionEvent event) {
        setChartsVisiblity(true, false, false, false);
        setChartButtonsDisableStatus(true, false, false, false);
        setColorButtonsDisableStatus(false, false, true);
    }

    @FXML
    protected void generateLineChart(ActionEvent event) {
        setChartButtonsDisableStatus(false, true, false, false);
        setChartsVisiblity(false, true, false, false);
        setColorButtonsDisableStatus(false, false, true);
    }

    @FXML
    protected void generateAreaChart(ActionEvent event) {
        areaChart.getData().clear();
        try {
            dateFrom = setdateFrom.getValue();
            dateTo = setdateTo.getValue();

            for (Admin a : adminsList) {

                XYChart.Series<String, Number> areaChartData = new XYChart.Series<>();

                List<LocalDateTime> dateTimes = SummarizeTime.getTimesListFromPeriod(a.getAdminLink(), dateFrom, dateTo);

                areaChartData.setName(a.getAdminName());
                for (int i = 0; i < dateTimes.size(); i++) {
                    areaChartData.getData().add(new XYChart.Data<>(dateTimes.get(i).toLocalDate().toString(), dateTimes.get(i).toLocalTime().toSecondOfDay()));
                }

                System.out.println(areaChartData.getData());
                areaChart.getData().add(areaChartData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Error.setText("Something went wrong");
            Error.setFill(Color.RED);
        }
        setChartsVisiblity(false, false, true, false);
    }

    @FXML
    protected void generateBarChart(ActionEvent event) {
        barChart.getData().clear();
        barChart.setCategoryGap(5);
        try {
            dateFrom = setdateFrom.getValue();
            dateTo = setdateTo.getValue();

            for (Admin a : adminsList) {
                XYChart.Series<String, Number> barChartData = new XYChart.Series<>();
                barChartData.setName(a.getAdminName());
                List<LocalDateTime> dateTimes = SummarizeTime.getTimesListFromPeriod(a.getAdminLink(), dateFrom, dateTo);
                for (int i = 0; i < dateTimes.size(); i++) {
                    barChartData.getData().add(new XYChart.Data<>(dateTimes.get(i).toLocalDate().toString(), dateTimes.get(i).toLocalTime().toSecondOfDay()));
                }
                System.out.println(barChartData.getData());
                barChart.getData().add(barChartData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Error.setText("Something went wrong");
            Error.setFill(Color.RED);
        }
        setChartsVisiblity(false, false, false, true);
    }

    @FXML
    protected void handleSaveScreenshot(ActionEvent event) {
        //TODO: SAVE SCREENSHOT
    }
    @FXML
    protected void handleNaturalAdminColor(ActionEvent event) {
        setColorButtonsDisableStatus(true, false, false);
        if (pieChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
                    //Get nodes
                    Node adminPieChartColor = pieChart.lookup(".default-color" + i + ".chart-pie");
                    Node adminLegendSymbol = pieChart.lookup(".default-color" + i + ".chart-legend-item-symbol");

                    //Set new style
                    adminPieChartColor.setStyle("-fx-pie-color: " + "#" + adminsList.get(i).getAdminColor());
                    adminLegendSymbol.setStyle("-fx-background-color: " + "#" + adminsList.get(i).getAdminColor());
                }
            });
        } else if (lineChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
                    //Get nodes
                    Node adminLineStrokeColor = lineChart.lookup(".default-color" + i + ".chart-series-line");
                    Node adminLineSymbolColor = lineChart.lookup(".default-color" + i + ".chart-line-symbol");
                    Node adminLegendSymbol = lineChart.lookup(".default-color" + i + ".chart-legend-item-symbol");

                    //Set new node styles
                    adminLineStrokeColor.setStyle("-fx-stroke: " + "#" + adminsList.get(i).getAdminColor());
                    adminLineSymbolColor.setStyle("-fx-background-color: " + "#" + adminsList.get(i).getAdminColor());
                    adminLegendSymbol.setStyle("-fx-background-color: " + "#" + adminsList.get(i).getAdminColor());
                }
            });
        }
    }

    @FXML
    protected void handleDefaultChartColor(ActionEvent event) {
        setColorButtonsDisableStatus(false, false, true);
        if (pieChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
                    //Get nodes
                    Node adminPieChartColor = pieChart.lookup(".default-color" + i + ".chart-pie");
                    Node adminLegendSymbol = pieChart.lookup(".default-color" + i + ".chart-legend-item-symbol");

                    //Get default color
                    String newRGBColor = defaultColorsList.get(i);

                    //Set new style
                    adminPieChartColor.setStyle("-fx-pie-color: " + newRGBColor);
                    adminLegendSymbol.setStyle("-fx-background-color: " + newRGBColor);
                }
            });
        } else if (lineChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
                    //Get nodes
                    Node adminLineStrokeColor = lineChart.lookup(".default-color" + i + ".chart-series-line");
                    Node adminLineSymbolColor = lineChart.lookup(".default-color" + i + ".chart-line-symbol");
                    Node adminLegendSymbol = lineChart.lookup(".default-color" + i + ".chart-legend-item-symbol");

                    //Get default color
                    String newRGBColor = defaultColorsList.get(i);

                    //Set new node styles
                    adminLineStrokeColor.setStyle("-fx-stroke: " + newRGBColor);
                    adminLineSymbolColor.setStyle("-fx-background-color: " + newRGBColor);
                    adminLegendSymbol.setStyle("-fx-background-color: " + newRGBColor);
                }
            });
        }
    }

    @FXML
    protected void handleRandomAdminColor(ActionEvent event) {
        setColorButtonsDisableStatus(false, false, false);
        if (pieChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
                    //Get nodes
                    Node adminPieChartColor = pieChart.lookup(".default-color" + i + ".chart-pie");
                    Node adminLegendSymbol = pieChart.lookup(".default-color" + i + ".chart-legend-item-symbol");

                    //Generate random color
                    StringBuilder newRGBColor = new StringBuilder()
                            .append("rgb")
                            .append("(")
                            .append(randomInt(255))
                            .append(", ")
                            .append(randomInt(255))
                            .append(", ")
                            .append(randomInt(255))
                            .append(")");

                    //Set new style
                    adminPieChartColor.setStyle("-fx-pie-color: " + newRGBColor);
                    adminLegendSymbol.setStyle("-fx-background-color: " + newRGBColor);
                }
            });
        } else if (lineChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
                    //Get nodes
                    Node adminLineStrokeColor = lineChart.lookup(".default-color" + i + ".chart-series-line");
                    Node adminLineSymbolColor = lineChart.lookup(".default-color" + i + ".chart-line-symbol");
                    Node adminLegendSymbol = lineChart.lookup(".default-color" + i + ".chart-legend-item-symbol");

                    //Generate random color
                    StringBuilder newRGBColor = new StringBuilder()
                            .append("rgb")
                            .append("(")
                            .append(randomInt(255))
                            .append(", ")
                            .append(randomInt(255))
                            .append(", ")
                            .append(randomInt(255))
                            .append(")");

                    //Set new node styles
                    adminLineStrokeColor.setStyle("-fx-stroke: " + newRGBColor);
                    adminLineSymbolColor.setStyle("-fx-background-color: " + newRGBColor);
                    adminLegendSymbol.setStyle("-fx-background-color: " + newRGBColor);
                }
            });
        }
    }
    private void rePopulateCharts() {
        setChartButtonsDisableStatus(true, true, true, true);
        setColorButtonsDisableStatus(true, true, true);

        clearChartData.restart();

        clearChartData.setOnSucceeded(e-> {
                    populatePieChart.restart();
                    populateLineChart.restart();
                });

        populatePieChart.setOnSucceeded(e-> pieChartButton.setDisable(false));
        populateLineChart.setOnSucceeded(e-> lineChartButton.setDisable(false));
        populatePieChart.setOnSucceeded(e-> setColorButtonsDisableStatus(false, false, true));
    }

    private void populateCharts() {
        populatePieChart.restart();
        populateLineChart.restart();

        populatePieChart.setOnSucceeded(e-> pieChartButton.setDisable(false));
        populateLineChart.setOnSucceeded(e-> lineChartButton.setDisable(false));
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert chartsWindow != null : "fx:id=\"chartsWindow\" was not injected: check your FXML file 'charts.fxml'.";

        adminText.setText("Choose admins from the list: ");

        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 24 arial;");

        setdateFrom.setValue(LocalDate.now().minusDays(27));
        setdateTo.setValue(LocalDate.now());

        setdateFrom.valueProperty().addListener((observableValue, localDate, t1) -> {
            rePopulateCharts();
            setColorButtonsDisableStatus(false, false, true);
        });

        setdateTo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                rePopulateCharts();
                setColorButtonsDisableStatus(false, false, true);
            }
        });

        // Factory to create Cell of DatePicker
        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
        setdateFrom.setDayCellFactory(dayCellFactory);
        setdateTo.setDayCellFactory(dayCellFactory);

        adminsList = AdminsWindowController.selectedAdminsList;

        setChartButtonsDisableStatus(true, true, true, true);
        setColorButtonsDisableStatus(true, true, true);

        //Set up progress bars
        progressBar.setProgress(0);
        progressIndicator.setProgress(0);

        progressBar.progressProperty().unbind();
        progressIndicator.progressProperty().unbind();

        progressBar.progressProperty().bind(populatePieChart.progressProperty());
        progressIndicator.progressProperty().bind(populatePieChart.progressProperty());

        populateCharts();
        addDefaultColorsToList();
        String defaultChartColors = getClass().getResource("chartsDefaultColors.css").toExternalForm();
        chartsWindow.getStylesheets().add(defaultChartColors);



        //TODO: ADD MORE ADMINS TO CHECK CHARTS (ESPECIALLY BARCHART)
        //TODO: GET EVERYTHING INTO NEW THREADS SO APP WONT FREEZE
        //TODO: ADD LOADING BARS EVERYWHERE WHERE NEEDED
        //TODO: MAKE ALL DATA APPEAR WHEN LOADING WINDOW
    }

    private void addDefaultColorsToList() {
        defaultColorsList.add("#e6194B");
        defaultColorsList.add("#3cb44b");
        defaultColorsList.add("#4363d8");
        defaultColorsList.add("#911eb4");
        defaultColorsList.add("#f58231");
        defaultColorsList.add("#bfef45");
        defaultColorsList.add("#42d4f4");
        defaultColorsList.add("#ffe119");
        defaultColorsList.add("#f032e6");
        defaultColorsList.add("#aaffc3");
        defaultColorsList.add("#9A6324");
        defaultColorsList.add("#800000");
        defaultColorsList.add("#ffd8b1");
        defaultColorsList.add("#e6beff");
        defaultColorsList.add("#469990");
    }

    private void setChartsVisiblity(Boolean pieChartVisibility, Boolean lineChartVisibility, Boolean areaChartVisibility, Boolean barChartVisibility) {
        pieChart.setVisible(pieChartVisibility);
        lineChart.setVisible(lineChartVisibility);
        areaChart.setVisible(areaChartVisibility);
        barChart.setVisible(barChartVisibility);
    }

    private void setChartButtonsDisableStatus(Boolean pieChartButtonDisable, Boolean lineChartButtonDisable, Boolean areaChartButtonDisable, Boolean barChartButtonDisable) {
        pieChartButton.setDisable(pieChartButtonDisable);
        lineChartButton.setDisable(lineChartButtonDisable);
        areaChartButton.setDisable(areaChartButtonDisable);
        barChartButton.setDisable(barChartButtonDisable);
    }

    private void setColorButtonsDisableStatus(Boolean naturalColorButtonDisable, Boolean randomColorButtonDisable, Boolean defaultColorButtonDisable) {
        naturalColorButton.setDisable(naturalColorButtonDisable);
        randomColorButton.setDisable(randomColorButtonDisable);
        defaultColorButton.setDisable(defaultColorButtonDisable);
    }

    private static Integer randomInt(Integer scopeInclusive) {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(scopeInclusive) + 1;
        return randomInt;
    }

    // Factory to create Cell of DatePicker
    private Callback<DatePicker, DateCell> getDayCellFactory() {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Disable days before 28 days from now and any date that is after todays date
                        if (item.isBefore(LocalDate.now().minusDays(27)) || item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    Service populateLineChart = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    dateFrom = setdateFrom.getValue();
                    dateTo = setdateTo.getValue();

                    for (Admin a : adminsList) {
                        XYChart.Series<String, Number> lineChartData = new XYChart.Series<>();

                        List<LocalDateTime> dateTimes = SummarizeTime.getTimesListFromPeriod(a.getAdminLink(), dateFrom, dateTo);

                        lineChartData.setName(a.getAdminName());
                        Platform.runLater(() -> {
                            for (int i = 0; i < dateTimes.size(); i++) {
                                //System.out.println("date: " + dateTimes.get(i).toLocalDate().toString() + "time: " + dateTimes.get(i).toLocalTime().toSecondOfDay());
                                lineChartData.getData().add(new XYChart.Data<>(dateTimes.get(i).toLocalDate().toString(), dateTimes.get(i).toLocalTime().toSecondOfDay()));
                            }
                            lineChart.getData().add(lineChartData);
                        });
                    }
                    return null;
                }
            };
        }
    };



    Service populatePieChart = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    Double max = 0.0;
                    dateFrom = setdateFrom.getValue();
                    dateTo = setdateTo.getValue();
                    workToDo = adminsList.size();
                    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

                    int progress = 0;
                    Integer duration;
                    for (Admin a : adminsList) {
                        duration = SummarizeTime.adminTimesDurationTotal(a.getAdminLink(), dateFrom, dateTo);
                        //System.out.println(a.getAdminName() + "\n" + SummarizeTime.adminTimesDurationTotal(a.getAdminLink(), dateFrom, dateTo));
                        PieChart.Data data = new PieChart.Data(a.getAdminName() + " (" + SummarizeTime.sumTimeString(a.getAdminLink(), dateFrom, dateTo) + ")", duration);
                        pieChartData.add(data);
                        max += duration;
                        this.updateProgress(progress, workToDo);
                    }

                    //TODO: ADD PLATFORM RUNLATER
                    Platform.runLater(() -> pieChart.setData(pieChartData));

                    final Double finalMax = max;
                    Platform.runLater(() -> {
                        for (PieChart.Data data : pieChart.getData()) {
                            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                                    e -> {
                                        caption.setTranslateX(e.getSceneX());
                                        caption.setTranslateY(e.getSceneY());
                                        //TODO: CHECK THIS AFTER REWORK OF GUI
                                        double dataValue = data.getPieValue() / finalMax * 100;
                                        String result = String.format("%.2f", dataValue);
                                        caption.setText(result + "%");
                                    });
                        }
                    });
                    return null;
                }
            };
        }
    };

    Service clearChartData = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    Platform.runLater(() -> {
                        pieChart.getData().clear();
                        lineChart.getData().clear();
                    });
                    if (!lineChart.getData().isEmpty()) {
                        try {
                            Thread.sleep(1000); //FIXME, I cant get this to work in any other way other than that...
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            };
        }
    };
}