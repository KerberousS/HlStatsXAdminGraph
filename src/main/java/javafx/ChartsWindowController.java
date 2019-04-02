package javafx;

import com.sun.javafx.charts.Legend;
import getinfo.SummarizeTime;
import hibernate.Admin;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.io.File;
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
    Text caption;

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
    private StackedBarChart barChart;

    @FXML
    private Button pieChartButton;

    @FXML
    private Button lineChartButton;

    @FXML
    private Button areaChartButton;

    @FXML
    private Button barChartButton;


    @FXML
    private DatePicker setDateFrom;

    @FXML
    private DatePicker setDateTo;

    @FXML
    private Button naturalColorButton;

    @FXML
    private Button randomColorButton;

    @FXML
    private Button defaultColorButton;

    private ProgressIndicator progressIndicator;

    private static List<Admin> adminsList;

    private static LocalDate dateFrom;
    private static LocalDate dateTo;

    private String adminsFXMLFile = "admins/Admins.fxml";

    @FXML
    protected void handleCloseButton(ActionEvent event) {
        this.changeScene(adminsFXMLFile, event);
    }

    @FXML
    protected void showPieChart(ActionEvent event) {
        setChartsVisibility(true, false, false, false);
        setColorButtonsDisableStatus(false, false, false);
    }

    @FXML
    protected void showLineChart(ActionEvent event) {
        setChartsVisibility(false, true, false, false);
        setColorButtonsDisableStatus(false, false, false);
    }

    @FXML
    protected void showAreaChart(ActionEvent event) {
        setChartsVisibility(false, false, true, false);
        setColorButtonsDisableStatus(true, true, false);
    }

    @FXML
    protected void showBarChart(ActionEvent event) {
        setChartsVisibility(false, false, false, true);
        setColorButtonsDisableStatus(false, false, false);
    }

    @FXML
    protected void handleSaveScreenshot(ActionEvent event) {
        //Create new filechooser
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PNG files (*.png)", "*.png");

        //Configure filechooser
        fileChooser.setTitle("Save as...");
        fileChooser.setInitialFileName("adminchart.png");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show filechooser
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        //Fix image DPI
        Double pixelScale = 2.0;
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setTransform(Transform.scale(pixelScale, pixelScale));

        if (file != null) {
            try {
                if (pieChart.isVisible()) {
                    WritableImage writableImage = new WritableImage((int)Math.rint(pixelScale*pieChart.getWidth()), (int)Math.rint(pixelScale*pieChart.getHeight()));
                    WritableImage image = pieChart.snapshot(snapshotParameters, writableImage);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                } else if (lineChart.isVisible()) {
                    WritableImage writableImage = new WritableImage((int)Math.rint(pixelScale*lineChart.getWidth()), (int)Math.rint(pixelScale*lineChart.getHeight()));
                    WritableImage image = lineChart.snapshot(snapshotParameters, writableImage);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                } else if (areaChart.isVisible()) {
                    WritableImage writableImage = new WritableImage((int)Math.rint(pixelScale*areaChart.getWidth()), (int)Math.rint(pixelScale*areaChart.getHeight()));
                    WritableImage image = areaChart.snapshot(snapshotParameters, writableImage);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                } else if (barChart.isVisible()) {
                    WritableImage writableImage = new WritableImage((int)Math.rint(pixelScale*barChart.getWidth()), (int)Math.rint(pixelScale*barChart.getHeight()));
                    WritableImage image = barChart.snapshot(snapshotParameters, writableImage);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                }
            } catch (Exception e) {
                Error.setText(e.getStackTrace().toString());
                Error.setFill(Color.RED);
            }
        }
    }

    @FXML
    protected void handleNaturalAdminColor(ActionEvent event) {
        setColorButtonsDisableStatus(true, false, false);
        if (pieChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
                    pieChart.getData().get(i).getNode().lookup(".chart-pie").setStyle("-fx-pie-color: " + "#" + adminsList.get(i).getAdminColor());
                }
                for (Node n : pieChart.getChildrenUnmodifiable()) {
                    if (n instanceof Legend) {
                        int count = 0;
                        for (Legend.LegendItem legendItem : ((Legend) n).getItems()) {
                            String[] currentAdminChartColorStyle = pieChart.getData().get(count).getNode().lookup(".chart-pie").getStyle().split(": ");
                            String currentAdminChartColor = currentAdminChartColorStyle[1];
                            legendItem.getSymbol().setStyle("-fx-background-color: " + currentAdminChartColor);
                            count++;
                        }
                    }
                }
            });
        } else if (lineChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
                    lineChart.getData().get(i).getNode().lookup(".chart-series-line").setStyle("-fx-stroke: " + "#" + adminsList.get(i).getAdminColor());
                }

                for (Node n : lineChart.getChildrenUnmodifiable()) {
                    if (n instanceof Legend) {
                        int count = 0;
                        for (Legend.LegendItem legendItem : ((Legend) n).getItems()) {
                            String[] currentAdminChartColorStyle = lineChart.getData().get(count).getNode().lookup(".chart-series-line").getStyle().split(": ");
                            String currentAdminChartColor = currentAdminChartColorStyle[1];
                            legendItem.getSymbol().setStyle("-fx-background-color: " + currentAdminChartColor);
                            count++;
                        }
                    }
                }
            });
//        } else if (areaChart.isVisible()) {
//            Platform.runLater(() -> {
//                for (int i = 0; i < adminsList.size(); i++) {
//                    //Get nodes
//                    StringBuilder lookupCSS = new StringBuilder()
//                            .append(".default-color")
//                            .append(i);
//
//                    Node adminAreaStrokeColor = areaChart.lookup(lookupCSS.append(".chart-series-area-line").toString());
//                    Node adminAreaSymbolColor = areaChart.lookup(lookupCSS.append(".chart-area-symbol").toString());
//                    Node adminAreaFillColor = areaChart.lookup(lookupCSS.append(".chart-series-area-fill").toString());
//                    Node adminLegendSymbol = areaChart.lookup(lookupCSS.append(".chart-legend-item-symbol").toString());
//
//                    //Get color
//                    String adminColor = java.awt.Color.decode("#" + adminsList.get(i).getAdminColor()).toString();
//                    System.out.println(adminColor);
//
//                    //Set new node styles
//                    adminAreaStrokeColor.setStyle("-fx-stroke: " + adminColor);
//                    adminAreaSymbolColor.setStyle("-fx-background-color: " + adminColor);
//                    adminAreaFillColor.setStyle("-fx-fill: " + adminColor); //TODO: ADD ALPHA TO COLOR
//                    adminLegendSymbol.setStyle("-fx-background-color: " + adminColor);
//                }
//            });
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
        rePopulateCharts();
    }

    @FXML
    protected void handleRandomAdminColor(ActionEvent event) {
        setColorButtonsDisableStatus(false, false, false);
        if (pieChart.isVisible()) {
            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
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

                    pieChart.getData().get(i).getNode().lookup(".chart-pie").setStyle("-fx-pie-color: " + newRGBColor);
                }

                for (Node n : pieChart.getChildrenUnmodifiable()) {
                    if (n instanceof Legend) {
                        int count = 0;
                        for (Legend.LegendItem legendItem : ((Legend) n).getItems()) {
                            String[] currentAdminRGBCStyle = pieChart.getData().get(count).getNode().lookup(".chart-pie").getStyle().split(": ");
                            String newRGBColor = currentAdminRGBCStyle[1];
                            legendItem.getSymbol().setStyle("-fx-background-color: " + newRGBColor);
                            count++;
                        }
                    }
                }
            });
        } else if (lineChart.isVisible()) {

            Platform.runLater(() -> {
                for (int i = 0; i < adminsList.size(); i++) {
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

                    lineChart.getData().get(i).getNode().lookup(".chart-series-line").setStyle("-fx-stroke: " + newRGBColor);
                }

                for (Node n : lineChart.getChildrenUnmodifiable()) {
                    if (n instanceof Legend) {
                        int count = 0;
                        for (Legend.LegendItem legendItem : ((Legend) n).getItems()) {
                            String[] currentAdminRGBCStyle = lineChart.getData().get(count).getNode().lookup(".chart-series-line").getStyle().split(": ");
                            String newRGBColor = currentAdminRGBCStyle[1];
                            legendItem.getSymbol().setStyle("-fx-background-color: " + newRGBColor);
                            count++;
                        }
                    }
                }
            });
        } else if (barChart.isVisible()) {
        Platform.runLater(() -> {
            for (int i = 0; i < adminsList.size(); i++) {

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
                for (Node n : barChart.lookupAll(".default-color" + i + ".chart-bar")) {
                    n.setStyle("-fx-bar-fill: " + newRGBColor);
                }
            }
        });
    }
}

    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        setDateFrom.setValue(LocalDate.now().minusDays(27));
        setDateTo.setValue(LocalDate.now());

        dateFrom = setDateFrom.getValue();
        dateTo = setDateTo.getValue();

        setDateFrom.valueProperty().addListener((observableValue, localDate, t1) -> {
            dateFrom = t1;
            Platform.runLater(() -> rePopulateCharts());
            setColorButtonsDisableStatus(false, false, true);
        });

        setDateTo.valueProperty().addListener((observableValue, localDate, t1) -> {
            dateTo = t1;
            Platform.runLater(() -> rePopulateCharts());
            setColorButtonsDisableStatus(false, false, true);
        });

        // Factory to create Cell of DatePicker
        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory();
        setDateFrom.setDayCellFactory(dayCellFactory);
        setDateTo.setDayCellFactory(dayCellFactory);

        adminsList = AdminsWindowController.selectedAdminsList;

        Platform.runLater(() -> rePopulateCharts());
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

    private void setPieChartData() {
        Platform.runLater(() -> {
            //Create new Chart series
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (Admin a : adminsList) {
                //Get admin data
                GetAdminPieDataService getData = new GetAdminPieDataService(a, dateFrom, dateTo);

                getData.restart();

                getData.setOnSucceeded(e ->  {
                    PieChart.Data data = new PieChart.Data(a.getAdminName() + " (" + SummarizeTime.sumTimeString(a.getAdminLink(), dateFrom, dateTo) + ")", getData.getValue());
                    pieChartData.add(data);
                    pieChart.setData(pieChartData);
                });
            }


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
//                            //TODO: CHECK THIS AFTER REWORK OF GUI
//                            double dataValue = data.getPieValue() / adminTimeSumFinal * 100;
//                            String result = String.format("%.2f", dataValue);
//                            caption.setText(result + "%");
//                        });
//            }
        });
    }

    private void setLineChartData() {
        Platform.runLater(() -> {
            for (Admin a : adminsList) {
                
                //Get admin data
                GetAdminChartDataService getData = new GetAdminChartDataService(a, dateFrom, dateTo);

                getData.restart();

                getData.setOnSucceeded(e -> {
                    //Create new Chart series
                    XYChart.Series<String, Number> lineChartData = new XYChart.Series<>();
                    List<LocalDateTime> dateTimes = getData.getValue();
                    lineChartData.setName(a.getAdminName());
                    for (int i = 0; i < dateTimes.size(); i++) {
                        lineChartData.getData().add(new XYChart.Data<>(dateTimes.get(i).toLocalDate().toString(), dateTimes.get(i).toLocalTime().toSecondOfDay()));
                    }
                    lineChart.getData().add(lineChartData);
                });
            }
        });
    }

    private void setAreaChartData() {
        Platform.runLater(() -> {
            for (Admin a : adminsList) {

                //Get admin data
                GetAdminChartDataService getData = new GetAdminChartDataService(a, dateFrom, dateTo);
                getData.restart();

                getData.setOnSucceeded(e -> {
                    //Create new Chart series
                    XYChart.Series<String, Number> areaChartData = new XYChart.Series<>();
                    List<LocalDateTime> dateTimes = getData.getValue();

                    //Set admins name as series title
                    areaChartData.setName(a.getAdminName());

                    //Convert all admin data to chart data
                    for (int i = 0; i < dateTimes.size(); i++) {
                        areaChartData.getData().add(new XYChart.Data<>(dateTimes.get(i).toLocalDate().toString(), dateTimes.get(i).toLocalTime().toSecondOfDay()));
                    }
                    //Add chart data to areaChart
                    areaChart.getData().add(areaChartData);
                });
            }
        });
    }


    private void setBarChartData() {
        Platform.runLater(() -> {
            for (Admin a : adminsList) {

                //Get admin data
                GetAdminChartDataService getData = new GetAdminChartDataService(a, dateFrom, dateTo);
                getData.restart();

                getData.setOnSucceeded(e -> {
                    //Create new Chart series
                    XYChart.Series<String, Number> barChartData = new XYChart.Series<>();
                    List<LocalDateTime> dateTimes = getData.getValue();

                    barChartData.setName(a.getAdminName());

                    for (int i = 0; i < dateTimes.size(); i++) {
                        barChartData.getData().add(new XYChart.Data<>(dateTimes.get(i).toLocalDate().toString(), dateTimes.get(i).toLocalTime().toSecondOfDay()));
                    }
                    barChart.getData().add(barChartData);
                });
            }
        });
    }


    private void clearChartData() {
        Platform.runLater(() -> {
            //Clear all datas
            pieChart.getData().clear();
            lineChart.getData().clear();
            areaChart.getData().clear();
            barChart.getData().clear();
        });
    }

    private void rePopulateCharts() {
        Platform.runLater(() -> {
        clearChartData();

        setLineChartData();
        setAreaChartData();
        setBarChartData();
        setPieChartData();
        });
    }
}