package javafx;

import getinfo.SummarizeTime;
import hibernate.Admin;
import hibernate.DBOperations;
import hibernate.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
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
    private DatePicker setdateFrom;

    @FXML
    private DatePicker setdateTo;

    private Server chosenServer;
    public static List<Admin> adminsList; //TODO: MAKE THIS WORK

    private LocalDate dateFrom;
    private LocalDate dateTo;


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
        Double max = 0.0;
        try {
            dateFrom = setdateFrom.getValue();
            dateTo = setdateTo.getValue();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            List<Admin> admins = DBOperations.displayAdminRecords(chosenServer.getServerName());

            Duration duration;
            for (Admin a : admins) {
                duration = SummarizeTime.adminTimesDurationTotal(a.getAdminLink(), dateFrom, dateTo);
                Long durationTime = duration.getSeconds();
                System.out.println(a.getAdminName() + "\n" + SummarizeTime.adminTimesDurationTotal(a.getAdminLink(), dateFrom, dateTo));
                PieChart.Data data = new PieChart.Data(a.getAdminName() + " (" + SummarizeTime.durationToLocalTime(duration) + ")", durationTime);
                pieChartData.add(data);
                max += durationTime;
            }

            //
            pieChart.setData(pieChartData);

            chartVisibility(true, false, false, false);

            final Double finalMax = max;

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
        } catch (Exception e) {
            e.printStackTrace();
            Error.setText("Something went wrong");
            Error.setFill(Color.RED);
        }
    }

    @FXML
    protected void generateLineChart(ActionEvent event) {
        lineChart.getData().clear();
        try {
            dateFrom = setdateFrom.getValue();
            dateTo = setdateTo.getValue();

            List<Admin> admins = DBOperations.displayAdminRecords(chosenServer.getServerName());


            for (Admin a : admins) {
                XYChart.Series<String, Number> lineChartData = new XYChart.Series<>();

                List<Duration> times = SummarizeTime.getTimesListFromPeriod(a.getAdminLink(), dateFrom, dateTo);
                List<LocalDate> dates = SummarizeTime.getDaysListFromPeriod(dateFrom, dateTo);
                lineChartData.setName(a.getAdminName());
                for (int i = 0; i < dates.size(); i++) {
                    System.out.println("date: " + dates.get(i).toString() + "time: " + times.get(i).toMinutes());
                    lineChartData.getData().add(new XYChart.Data<>(dates.get(i).toString(), times.get(i).toMinutes()));
//                    System.out.println(times.get(i) + " // " + dates.get(i));
                }
                System.out.println(lineChartData.getData());
                lineChart.getData().add(lineChartData);
            }

        } catch (Exception e) {
        e.printStackTrace();
        Error.setText("Something went wrong");
        Error.setFill(Color.RED);
        }

        chartVisibility(false, true, false, false);
    }

    @FXML
    protected void generateAreaChart(ActionEvent event) {
        areaChart.getData().clear();

            try {
                dateFrom = setdateFrom.getValue();
                dateTo = setdateTo.getValue();

                List<Admin> admins = DBOperations.displayAdminRecords(chosenServer.getServerName());

                for (Admin a : admins) {

                    XYChart.Series<String, Number> areaChartData = new XYChart.Series<>();

                    List<LocalDate> dates = SummarizeTime.getDaysListFromPeriod(dateFrom, dateTo);
                    List<Duration> times = SummarizeTime.getTimesListFromPeriod(a.getAdminLink(), dateFrom, dateTo);

                    areaChartData.setName(a.getAdminName());
                    for (int i = 0; i < times.size(); i++) {
                        areaChartData.getData().add(new XYChart.Data<>(dates.get(i).toString(), times.get(i).toMinutes()));
                    }

                    System.out.println(areaChartData.getData());
                    areaChart.getData().add(areaChartData);

                }

            } catch (Exception e)
            {
                e.printStackTrace();
                Error.setText("Something went wrong");
                Error.setFill(Color.RED);
            }
            chartVisibility(false, false, true, false);
        }

    @FXML
    protected void generateBarChart(ActionEvent event) {
        barChart.getData().clear();
        barChart.setCategoryGap(5);
        try {
            dateFrom = setdateFrom.getValue();
            dateTo = setdateTo.getValue();

            List<Admin> admins = DBOperations.displayAdminRecords(chosenServer.getServerName());

            List<LocalDate> dates = SummarizeTime.getDaysListFromPeriod(dateFrom, dateTo);

            for (Admin a : admins) {
                XYChart.Series<String, Number> barChartData = new XYChart.Series<>();
                barChartData.setName(a.getAdminName());
                List<Duration> times = SummarizeTime.getTimesListFromPeriod(a.getAdminLink(), dateFrom, dateTo);
                for (int i = 0; i < times.size(); i++) {
                    barChartData.getData().add(new XYChart.Data<>(dates.get(i).toString(), times.get(i).toMinutes()));
                }
                System.out.println(barChartData.getData());
                barChart.getData().add(barChartData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Error.setText("Something went wrong");
            Error.setFill(Color.RED);
        }
        chartVisibility(false, false, false, true);
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert chartsWindow != null : "fx:id=\"chartsWindow\" was not injected: check your FXML file 'charts.fxml'.";

        adminText.setText("Choose admins from the list: ");
        chosenServer = BaseWindowController.chosenServer;
        //Initialize server list for server dropdown

        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 24 arial;");

        //TODO: ADD COLOR FUNCTIONALITY
        //TODO: ADD SAVE TO SCREENSHOT BUTTON
        //TODO: ADD MORE ADMINS TO CHECK CHARTS (ESPECIALLY BARCHART)
        //TODO: ADD DATE FROM TO FUNCTIONALITY
        //TODO: REWORK TIMES ON DURATIONS
        //TODO: ADD NOW DATE/CLOCK IN APPLICATION
        }

        public void chartVisibility (Boolean pieChartVisibility, Boolean lineChartVisibility, Boolean areaChartVisibility, Boolean barChartVisibility) {
        pieChart.setVisible(pieChartVisibility);
        lineChart.setVisible(lineChartVisibility);
        areaChart.setVisible(areaChartVisibility);
        barChart.setVisible(barChartVisibility);
        }
    }