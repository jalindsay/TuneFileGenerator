package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.PrintWriter;
import java.util.stream.IntStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tune file generator");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        PrintWriter printWriter = new PrintWriter("test.tun", "UTF-8");
        printWriter.println("[Tuning]");
        String[] notes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

        //TODO: stop grid from moving
        //TODO: lay out button to separate grid

        Label[] labelNote = new Label[12];
        for (int i = 0; i < 12; i++) {
            labelNote[i] = new Label();
            labelNote[i].setAlignment(Pos.CENTER);
        }

        int[] centArray = new int[128];
        for(int i=0; i<128; i++){
            centArray[i] = i*100;
        }

        for (int i = 0; i < 12; i++) {
            labelNote[i].setText(notes[i]);
        }

        //initialize slider array
        Slider[] slider = new Slider[12];
        for (int i = 0; i < 12; i++) {
            slider[i] = new Slider(-50,50,0);
            slider[i].setOrientation(Orientation.VERTICAL);
        }

        //initialize label array
        Label[] labelValue = new Label[12];
        for (int i = 0; i < 12; i++) {
            labelValue[i] = new Label("0");
            labelValue[i].setAlignment(Pos.CENTER);
        }

        //initialize note value array
        int[] noteValue = new int[12];
        for (int i = 0; i < 12; i++) {
            noteValue[i] = 0;
        }

        Button printButton = new Button("PRINT");

        //add everything to grid
        for(int i=0; i<12; i++){
            grid.add(labelNote[i], i + 1, 1);
            grid.add(slider[i], i + 1, 2);
            grid.add(labelValue[i], i + 1, 3);
        }
        grid.add(printButton, 1, 4);

        IntStream.range(0, 12).forEach(i -> slider[i].valueProperty().addListener((observable, oldValue, newValue) -> {
            labelValue[i].setText(String.format("%2.0f", newValue));
            noteValue[i] = newValue.intValue();
        }));

        printButton.setOnAction(actionEvent -> {
            for (int i = 0; i < 128; i++) {
                int temp = centArray[i] + noteValue[i % 12];
                printWriter.println("note "+i+"="+temp);
            }
            printWriter.close();
        });

        grid.setAlignment(Pos.CENTER);

        Scene mainScene = new Scene(grid, 600, 300);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
