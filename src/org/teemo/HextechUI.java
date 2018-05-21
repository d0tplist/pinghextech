package org.teemo;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.HashMap;


public class HextechUI extends AnchorPane {

    private final ImageView topImage;
    private final ImageView tiltedTeemo;
    private final AnchorPane midLaneContainer;
    private final ChoiceBox<Server> serverChoice;
    private final Button reportButton;
    private final Label labelPing;
    private final LineChart<String, Number> lineChart;
    private final NumberAxis numberAxis;
    private final CategoryAxis categoryAxis;
    private final VBox reportJG;
    private final PingLogic logic;
    private final HashMap<Server, XYChart.Series<String, Number>> data;
    private final Button github;

    private boolean pentaPlaying = false;
    private final Application application;

    public HextechUI(Application application) {
        this.application = application;


        github = new Button("!");
        data = new HashMap<>();
        topImage = new ImageView();
        tiltedTeemo = new ImageView();

        serverChoice = new ChoiceBox<>();
        serverChoice.getItems().addAll(Server.values());

        midLaneContainer = new AnchorPane();
        reportButton = new Button("Check Ping");
        labelPing = new Label("GG <3");

        numberAxis = new NumberAxis();
        categoryAxis = new CategoryAxis();
        lineChart = new LineChart<>(categoryAxis, numberAxis);

        lineChart.setLegendVisible(false);
        lineChart.setTitle("Ping History");

        categoryAxis.setTickLabelsVisible(false);
        tiltedTeemo.setImage(new Image(HextechUI.class.getResourceAsStream("/org/teemo/media/dogem7.png")));
        Image timo = new Image(HextechUI.class.getResourceAsStream("/org/teemo/media/teemo.gif"));

        reportJG = new VBox();
        reportJG.setSpacing(20.0);
        reportJG.setAlignment(Pos.CENTER);
        reportJG.setPrefWidth(220);

        reportJG.getChildren().add(labelPing);
        reportJG.getChildren().add(serverChoice);
        reportJG.getChildren().add(reportButton);

        getChildren().add(topImage);
        getChildren().add(midLaneContainer);
        getChildren().add(lineChart);

        Label disclaimer = new Label("This is not an app from Rito Games");
        disclaimer.setFont(new Font(10));
        disclaimer.setAlignment(Pos.CENTER);

        midLaneContainer.getChildren().add(reportJG);
        midLaneContainer.getChildren().add(tiltedTeemo);
        midLaneContainer.getChildren().add(github);
        midLaneContainer.getChildren().add(disclaimer);

        Tooltip tooltip = new Tooltip("See this awesome app on github!");
        github.setTooltip(tooltip);

        AnchorPane.setBottomAnchor(disclaimer, 2.0);
        AnchorPane.setLeftAnchor(disclaimer, 10.0);

        tiltedTeemo.setFitHeight(188);
        tiltedTeemo.setFitWidth(211);
        tiltedTeemo.setPreserveRatio(true);

        serverChoice.setPrefWidth(200);
        serverChoice.getSelectionModel().selectFirst();

        AnchorPane.setTopAnchor(github, 5.0);
        AnchorPane.setLeftAnchor(github, 5.0);

        AnchorPane.setTopAnchor(reportJG, 0.0);
        AnchorPane.setLeftAnchor(reportJG, 0.0);
        AnchorPane.setBottomAnchor(reportJG, 0.0);

        AnchorPane.setTopAnchor(tiltedTeemo, 0.0);
        AnchorPane.setRightAnchor(tiltedTeemo, 0.0);

        AnchorPane.setTopAnchor(topImage, 0.0);
        AnchorPane.setRightAnchor(topImage, 0.0);
        AnchorPane.setLeftAnchor(topImage, 0.0);

        AnchorPane.setBottomAnchor(lineChart, 0.0);
        AnchorPane.setRightAnchor(lineChart, 0.0);
        AnchorPane.setLeftAnchor(lineChart, 0.0);
        AnchorPane.setTopAnchor(lineChart, 339.0);

        AnchorPane.setTopAnchor(midLaneContainer, 147.0);
        AnchorPane.setLeftAnchor(midLaneContainer, 0.0);
        AnchorPane.setRightAnchor(midLaneContainer, 0.0);
        AnchorPane.setBottomAnchor(midLaneContainer, 265.0);

        midLaneContainer.setStyle("-fx-base: #0272B7; -fx-background-color: #0272B7;");
        midLaneContainer.setEffect(new DropShadow());

        labelPing.setFont(new Font(22));
        labelPing.setStyle("-fx-text-fill: #3fbf2e");

        logic = new PingLogic(this);

        reportButton.setOnAction(event -> logic.start());

        tiltedTeemo.setOnMouseReleased(event ->{

            if(pentaPlaying){
                return;
            }

            pentaPlaying = true;
            topImage.setImage(new Image(HextechUI.class.getResourceAsStream("/org/teemo/media/pentaFeed.png")));
            Platform.runLater(() -> PentakillSound.play(getServer() != Server.LAN));
            ScaleTransition scale = new ScaleTransition(Duration.millis(1000), topImage);
            scale.setToX(2);
            scale.setToY(2);
            scale.setCycleCount(2);
            scale.setAutoReverse(true);
            scale.play();

            scale.setOnFinished(event1 -> {
                pentaPlaying = false;
                topImage.setImage(timo);
            });
        });

        serverChoice.setOnAction(event -> {
            if(getServer() == Server.LAN){
                reportButton.setText("Reportar al Jungla");
                lineChart.setTitle("Ping del cyber");
            }else{
                reportButton.setText("Check Ping");
                lineChart.setTitle("Ping History");
            }
        });

        tiltedTeemo.setCursor(Cursor.HAND);
        topImage.setImage(timo);

        github.setOnAction(event -> {
            application.getHostServices().showDocument("https://d0tplist.github.io/pinghextech/");
        });

    }

    public XYChart.Series<String, Number> getSerie(Server server){
        if(data.containsKey(server)){
            return data.get(server);
        }

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName(server.toString());

        data.put(server, serie);
        lineChart.getData().add(serie);

        if(lineChart.getData().size() > 1){
            lineChart.setLegendVisible(true);
        }

        return serie;
    }

    public Label getLabelPing() {
        return labelPing;
    }

    public Button getReportButton() {
        return reportButton;
    }

    public ChoiceBox<Server> getServerChoice() {
        return serverChoice;
    }

    public Server getServer(){
        return serverChoice.getValue();
    }

    public LineChart<String, Number> getLineChart() {
        return lineChart;
    }
}
