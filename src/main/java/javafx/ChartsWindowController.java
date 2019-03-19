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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class ChartsWindowController implements Initializable {

    @FXML
    private BorderPane chartsWindow;

    @FXML
    Label caption;

    @FXML
    private Text Error;

    @FXML
    private StackPane chartStackPane;

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

    private ProgressBar progressBar;

    private static List<Admin> adminsList;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    private String adminsFXMLFile = "admins/Admins.fxml";

    @FXML
    protected void handleCloseButton(ActionEvent event) {
            this.changeScene(adminsFXMLFile, event);
    }

    @FXML
    protected void showPieChart(ActionEvent event) {
        setChartsVisibility(true, false, false, false);
    }

    @FXML
    protected void showLineChart(ActionEvent event) {
        setChartsVisibility(false, true, false, false);
    }

    @FXML
    protected void showAreaChart(ActionEvent event) {
        setChartsVisibility(false, false, true, false);
    }

    @FXML
    protected void showBarChart(ActionEvent event) {
        setChartsVisibility(false, false, false, true);
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

                    //Get color
                    String adminColor = adminsList.get(i).getAdminColor();

                    //Set new style
                    adminPieChartColor.setStyle("-fx-pie-color: " + "#" + adminColor);
                    adminLegendSymbol.setStyle("-fx-background-color: " + "#" + adminColor);
                }
            });
        } else if (lineChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
                    //Get nodes
                    Node adminLineStrokeColor = lineChart.lookup(".default-color" + i + ".chart-series-line");
                    Node adminLineSymbolColor = lineChart.lookup(".default-color" + i + ".chart-line-symbol");
                    Node adminLegendSymbol = lineChart.lookup(".default-color" + i + ".chart-legend-item-symbol");

                    //Get color
                    String adminColor = adminsList.get(i).getAdminColor();

                    //Set new node styles
                    adminLineStrokeColor.setStyle("-fx-stroke: " + "#" + adminColor);
                    adminLineSymbolColor.setStyle("-fx-background-color: " + "#" + adminColor);
                    adminLegendSymbol.setStyle("-fx-background-color: " + "#" + adminColor);
                }
            });
        } else if (areaChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
                    //Get nodes
                    Node adminAreaStrokeColor = areaChart.lookup(".default-color" + i + ".chart-series-area-line");
                    Node adminAreaSymbolColor = areaChart.lookup(".default-color" + i + ".chart-area-symbol");
                    Node adminAreaFillColor = areaChart.lookup(".default-color" + i + ".chart-series-area-fill");
                    Node adminLegendSymbol = areaChart.lookup(".default-color" + i + ".chart-legend-item-symbol");

                    //Get color
                    String adminColor = adminsList.get(i).getAdminColor();

                    //Set new node styles
                    adminAreaStrokeColor.setStyle("-fx-stroke: " + "#" + adminColor);
                    adminAreaSymbolColor.setStyle("-fx-background-color: " + "#" + adminColor);
                    adminAreaFillColor.setStyle("-fx-fill: " + "#" + adminColor); //TODO: ADD ALPHA TO COLOR
                    adminLegendSymbol.setStyle("-fx-background-color: " + "#" + adminColor);
                }
            });
        } else if (barChart.isVisible()) {
            Platform.runLater(() -> {
                    for (int i = 0; i < adminsList.size(); i++) {

                        //Get admin color
                        String adminColor = adminsList.get(i).getAdminColor();

                        //Set new node styles
                        for (Node n : barChart.lookupAll(".default-color" + i + ".chart-bar")) {
                            n.setStyle("-fx-bar-fill: " + "#" + adminColor);
                        }
                    }
            });
        }
    }

    @FXML
    protected void handleDefaultChartColor(ActionEvent event) {
        setColorButtonsDisableStatus(false, false, true);
        rePopulateCharts();
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

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
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

        rePopulateCharts();

        //TODO: CHECK LOADING BAR AFTER GRAPHICAL REWORK
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
        return dayCellFactory;
    }

    private void changeScene(String windowFXMLFile, ActionEvent event) {
        Parent window = null;
        try {
            window = FXMLLoader.load(getClass().getResource(windowFXMLFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(window);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void setChartsVisibility(Boolean pieChartVisibility, Boolean lineChartVisibility, Boolean areaChartVisibility, Boolean barChartVisibility) {
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



    Service populatePieChart = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    Double max = 0.0;
                    dateFrom = setdateFrom.getValue();
                    dateTo = setdateTo.getValue();
                    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

                    Integer duration;
                    for (Admin a : adminsList) {
                        duration = SummarizeTime.adminTimesDurationTotal(a.getAdminLink(), dateFrom, dateTo);
                        //System.out.println(a.getAdminName() + "\n" + SummarizeTime.adminTimesDurationTotal(a.getAdminLink(), dateFrom, dateTo));
                        PieChart.Data data = new PieChart.Data(a.getAdminName() + " (" + SummarizeTime.sumTimeString(a.getAdminLink(), dateFrom, dateTo) + ")", duration);
                        pieChartData.add(data);
                        max += duration;
                    }

                    final Double finalMax = max;
                    Platform.runLater(() -> {
                        pieChart.setData(pieChartData);
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
                        //System.out.println(a.getAdminName()); Debug line
                        lineChartData.setName(a.getAdminName());
                        Platform.runLater(() -> {
                            for (int i = 0; i < dateTimes.size(); i++) {
                                //System.out.println("date: " + dateTimes.get(i).toLocalDate().toString() + "time: " + dateTimes.get(i).toLocalTime().toSecondOfDay()); Debug
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


    Service populateAreaChart = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    dateFrom = setdateFrom.getValue();
                    dateTo = setdateTo.getValue();

                    for (Admin a : adminsList) {

                        XYChart.Series<String, Number> areaChartData = new XYChart.Series<>();

                        List<LocalDateTime> dateTimes = SummarizeTime.getTimesListFromPeriod(a.getAdminLink(), dateFrom, dateTo);

                        areaChartData.setName(a.getAdminName());
                        Platform.runLater(() -> {
                            for (int i = 0; i < dateTimes.size(); i++) {
                                areaChartData.getData().add(new XYChart.Data<>(dateTimes.get(i).toLocalDate().toString(), dateTimes.get(i).toLocalTime().toSecondOfDay()));
                            }
                            areaChart.getData().add(areaChartData);
                        });
                    }
                    return null;
                }
            };
        }
    };

    Service populateBarChart = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    dateFrom = setdateFrom.getValue();
                    dateTo = setdateTo.getValue();

                    for (Admin a : adminsList) {
                        XYChart.Series<String, Number> barChartData = new XYChart.Series<>();
                        barChartData.setName(a.getAdminName());
                        List<LocalDateTime> dateTimes = SummarizeTime.getTimesListFromPeriod(a.getAdminLink(), dateFrom, dateTo);

                        Platform.runLater(() -> {
                            for (int i = 0; i < dateTimes.size(); i++) {
                                barChartData.getData().add(new XYChart.Data<>(dateTimes.get(i).toLocalDate().toString(), dateTimes.get(i).toLocalTime().toSecondOfDay()));
                            }
                            //System.out.println(barChartData.getData());
                            barChart.getData().add(barChartData);
                        });
                    }
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
                        areaChart.getData().clear();
                        barChart.getData().clear();
                        try {
                            Thread.sleep(1000); //FIXME: This is additional time for chart to actually clear the values from both the Data and Styles
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    return null;
                }
            };
        }
    };


    private void rePopulateCharts() {
        setChartButtonsDisableStatus(true, true, true, true);
        setColorButtonsDisableStatus(true, true, true);

        progressBar = new ProgressBar();
        progressBar.progressProperty().bind(populateLineChart.progressProperty());
        chartStackPane.getChildren().add(progressBar);
        progressBar.setVisible(true);
        clearChartData.restart();

        //On succeed
        clearChartData.setOnSucceeded(e -> {
            populatePieChart.restart();
            populateLineChart.restart();
            populateAreaChart.restart();
            populateBarChart.restart();
        });

        //On failed
        populatePieChart.setOnFailed(e -> {
            Error.setText(this.getClass().getName() + "has failed, reason: " + populatePieChart.getException().toString());
            Error.setFill(Color.RED);
        });
        populateLineChart.setOnFailed(e -> {
            Error.setText(this.getClass().getName() + "has failed, reason: " + populateLineChart.getException().toString());
            Error.setFill(Color.RED);
        });
        populateAreaChart.setOnFailed(e -> {
            Error.setText(this.getClass().getName() + "has failed, reason: " + populateAreaChart.getException().toString());
            Error.setFill(Color.RED);
        });
        populateBarChart.setOnFailed(e -> {
            Error.setText(this.getClass().getName() + "has failed, reason: " + populateBarChart.getException().toString());
            Error.setFill(Color.RED);
        });


        //If succeeded
        populatePieChart.setOnSucceeded(e -> pieChartButton.setDisable(false));
        populateLineChart.setOnSucceeded(e -> lineChartButton.setDisable(false));
        populateAreaChart.setOnSucceeded(e -> areaChartButton.setDisable(false));
        populateBarChart.setOnSucceeded(e -> {
            barChartButton.setDisable(false);
            setColorButtonsDisableStatus(false, false, true);
            progressBar.setVisible(false);
        });
    }
}