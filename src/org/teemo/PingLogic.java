package org.teemo;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.teemo.api.Executor;
import org.teemo.api.Extractor;
import org.teemo.api.MacPing;
import org.teemo.api.WindowsPing;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PingLogic {

    private final HextechUI ui;
    private boolean checking = false;
    private int count;
    private final ArrayList<Double> pings;
    private final DecimalFormat formatter;
    private double average;
    private final Extractor extractor;

    public PingLogic(HextechUI ui) {
        this.ui = ui;
        this.formatter = new DecimalFormat("#,###.### ms");
        this.pings = new ArrayList<>();
        this.extractor = System.getProperty("os.name").startsWith("windows") ? new WindowsPing() : new MacPing();
    }

    public final void start() {
        if (checking) {
            return;
        }

        average = 0.0;
        pings.clear();
        final Server server = ui.getServer();

        final var serie = ui.getSerie(server);
        serie.getData().clear();

        new Thread(() -> {

            checking = true;
            count = 0;

            Executor.execute(extractor, server, (result) -> {

                pings.add(result);

                pings.stream().mapToDouble(r -> r).average().ifPresent(l -> average = l);

                Platform.runLater(() -> {
                    var data = new XYChart.Data<String, Number>("" + count, result);
                    serie.getData().add(data);
                    ui.getLabelPing().setText(formatter.format(average));
                    count++;
                    if (average > 110) {
                        ui.getLabelPing().setStyle("-fx-text-fill: red");
                    } else {
                        ui.getLabelPing().setStyle("-fx-text-fill: #3fbf2e");
                    }

                    Tooltip tooltip = new Tooltip(result + " ms");
                    Tooltip.install(data.getNode(), tooltip);
                });

                System.out.println(result + " ms");
            });

            checking = false;

        }).start();

    }
}
