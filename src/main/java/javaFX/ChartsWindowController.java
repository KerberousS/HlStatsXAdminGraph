package javaFX;

import getInfo.AdminTimeCollector;
import getInfo.SummarizeTime;
import hibernate.Admin;
import hibernate.AdminOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChartsWindowController implements Initializable {

    @FXML
    private GridPane chartsWindow;

    @FXML
    private Text adminText;

    @FXML
    private TableView<Admin> adminsList;

    @FXML
    Label caption;

    @FXML
    private Text Error;

    @FXML
    private TableColumn columnAdminName;

    @FXML
    private TableColumn columnAdminLink;

    @FXML
    private TableColumn columnAdminColor;

    @FXML
    private PieChart pieChart;

    @FXML
    private CategoryAxis xLineAxis;

    @FXML
    private NumberAxis yLineAxis;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private AreaChart areaChart;

    @FXML
    private BubbleChart bubbleChart;

    @FXML
    private ScatterChart scatterChart;

    @FXML
    private BarChart barChart;


    public static String choosenServer;
    public static String chosenAdminName;
    public static String choosenAdminLink;
    public static String choosenAdminColor;
    Double max = 0.0;

//    @FXML
//    protected void handleSumTimeButton(ActionEvent event) {
//        String adminID = adminIDTextField.getText();
//
//        SummarizeTime st = new SummarizeTime();
//        String sumTime = st.sumTimeString(adminID);
//        actiontarget.setFill(Color.FIREBRICK);
//        actiontarget.setText(sumTime);
//    }

    @FXML
    protected void handleCloseButton(ActionEvent event) {
        try {
            Parent baseWindow = FXMLLoader.load(getClass().getResource("Admins/Admins.fxml"));
            chartsWindow.getChildren().setAll(baseWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void generatePieChart(ActionEvent event) {
        try {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            List<Admin> admins = AdminOperations.displayFullRecords(choosenServer);
            SummarizeTime sum = new SummarizeTime();

            for (Admin a : admins) {
                System.out.println(a.getAdminName() + "\n" + sum.sumTimeString(a.getAdminLink()));
                PieChart.Data data = new PieChart.Data(a.getAdminName() + " (" + sum.sumTimeString(a.getAdminLink()) + ")", sum.timesToSeconds(a.getAdminLink()));
                pieChartData.add(data);
                max += sum.timesToSeconds(a.getAdminLink());
            }
            pieChart.setData(pieChartData);
            lineChart.setVisible(false);
            pieChart.setVisible(true);
            pieChart.setTitle("Admins Times");
            final Double finalMax = max;

            for (PieChart.Data data : pieChart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                        e -> {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            double dataValue = data.getPieValue() / finalMax * 100;
                            String result = String.format("%.2f", dataValue);
                            caption.setText(result + "%");
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void generateLineChart(ActionEvent event) {
        //TODO: Fix this
        try {
            List<Admin> admins = AdminOperations.displayFullRecords(choosenServer);
            SummarizeTime sum = new SummarizeTime();

            for (Admin a : admins) {
                XYChart.Series<String, Number> lineChartData = new XYChart.Series<>();
                List<Integer> times = sum.last28TimesList(a.getAdminLink());
                List<String> dates = sum.last28DaysList();
                lineChartData.setName(a.getAdminName());
                for (int i = 0; i < times.size(); i++) {
                    lineChartData.getData().add(new XYChart.Data<>(dates.get(i), times.get(i)));
//                    System.out.println(times.get(i) + " // " + dates.get(i));
                }
                System.out.println(lineChartData.getData());
                lineChart.getData().add(lineChartData);
            }

        } catch (Exception e)
        {
        e.printStackTrace();
        }
        lineChart.setTitle("Admins Line Graph");
        pieChart.setVisible(false);
        lineChart.setVisible(true);
    }

    @FXML
    protected void generateAreaChart(ActionEvent event) {
        try {
            //TODO: Generate Area Chart
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void generateBubbleChart(ActionEvent event) {
        try {
            //TODO: Generate Bubble Chart
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void generateScatterChart(ActionEvent event) {
        try {
            //TODO: Generate Scatter Chart
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void generateBarChart(ActionEvent event) {
        try {
            //TODO: Generate Bar Chart
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert adminsList != null : "fx:id=\"adminsList\" was not injected: check your FXML file 'Admins.fxml'.";

        columnAdminName.setCellValueFactory(new PropertyValueFactory<Admin, String>("adminName"));
        columnAdminLink.setCellValueFactory(new PropertyValueFactory<Admin, String>("adminLink"));
        columnAdminColor.setCellValueFactory(new PropertyValueFactory<Admin, String>("adminColor"));

        adminText.setText("Choose admins from the list: ");
        choosenServer = BaseWindowController.choosenServer;
        //Initialize server list for server dropdown

        adminsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        adminsList.getItems().addAll(AdminOperations.displayFullRecords(choosenServer));

        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 24 arial;");
        }
    }