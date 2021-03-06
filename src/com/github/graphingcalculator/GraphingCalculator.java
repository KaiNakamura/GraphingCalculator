package com.github.graphingcalculator;

import com.github.graphingcalculator.expression.Expression;
import com.github.graphingcalculator.expressionparser.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GraphingCalculator extends Application {
	protected static final int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 500;
	protected static final double MIN_X = -10, MAX_X = 10, DELTA_X = 0.01;
	protected static final double MIN_Y = -10, MAX_Y = 10;
	protected static final double GRID_INTERVAL = 5;
	protected static final String EXAMPLE_EXPRESSION = "0.125*x^3-0.5*x^2+3";
	protected final ExpressionParser expressionParser = new SimpleExpressionParser();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Graphing Calculator");

		final Pane queryPane = new HBox();
		final Label label = new Label("y=");
		final TextField textField = new TextField(EXAMPLE_EXPRESSION);
		final Button button = new Button("Graph");
		queryPane.getChildren().add(label);
		queryPane.getChildren().add(textField);

		final Pane graphPane = new Pane();
		final LineChart<Number, Number> chart = new LineChart<>(
			new NumberAxis(MIN_X, MAX_X, GRID_INTERVAL),
			new NumberAxis(MIN_Y, MAX_Y, GRID_INTERVAL)
		);
		chart.setLegendVisible(false);
		chart.setCreateSymbols(false);
		graphPane.getChildren().add(chart);
		button.setOnMouseClicked(e -> {
			try {
				final Expression expression = expressionParser.parse(textField.getText());
				final XYChart.Series series = new XYChart.Series();
				for (double x = MIN_X; x <= MAX_X; x += DELTA_X) {
					final double y = expression.evaluate(x);
					series.getData().add(new XYChart.Data(x, y));
				}
				chart.getData().clear();
				chart.getData().addAll(series);
			} catch (ExpressionParseException epe) {
				textField.setStyle("-fx-text-fill: red");
			}
		});
		queryPane.getChildren().add(button);

		textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));
		
		final BorderPane root = new BorderPane();
		root.setTop(queryPane);
		root.setCenter(graphPane);

		final Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
