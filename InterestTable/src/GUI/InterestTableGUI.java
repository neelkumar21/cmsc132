package GUI;


import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane; 
import javafx.scene.layout.HBox;
import javafx.stage.*;
import model.Interest;
import javafx.scene.control.Slider; 
import javafx.scene.control.Label; 
import javafx.scene.control.TextArea; 
import javafx.scene.control.ScrollPane; 


// Harshil Chava - hchava
// Neel Kumar - nkumar21
// Kabir Sidhu - ksidhu
// Peter Hong - phong12

public class InterestTableGUI extends Application {
	
	public void start(Stage primaryStage) { 
		
		
		Label principalLabel = new Label("Principal:");
        TextField principalTextField = new TextField();
        HBox principal = new HBox();
		principal.getChildren().addAll(principalLabel,principalTextField);
		
		
        Label rateLabel = new Label("Rate(Percentage):");
        TextField rateTextField = new TextField();
		HBox rate = new HBox();
		rate.getChildren().addAll(rateLabel,rateTextField);
		
		
		HBox hbox1 = new HBox(); 
        hbox1.getChildren().add(principal);
        hbox1.getChildren().add(rate);
        hbox1.setAlignment(Pos.CENTER);
        
		
        Label sliderLabel = new Label("Number of Years:");
        Slider slider = new Slider(1, 25, 1);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(4);
        slider.setMinorTickCount(3);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
        
        HBox hbox2 = new HBox();
        hbox2.getChildren().add(sliderLabel);
        hbox2.getChildren().add(slider);
        hbox2.setAlignment(Pos.CENTER);
        
        
        Button simpleInterestButton = new Button("SimpleInterest");
        Button compoundInterestButton = new Button("CompoundInterest");
		Button bothInterestsButton = new Button("BothInterests");
        
		HBox hbox3 = new HBox();
	    hbox3.getChildren().add(simpleInterestButton);
	    hbox3.getChildren().add(compoundInterestButton);
	    hbox3.getChildren().add(bothInterestsButton);
	    hbox3.setAlignment(Pos.CENTER);
        
		
	    
		SplitPane pane = new SplitPane();	
		pane.setOrientation(Orientation.VERTICAL);
		
		GridPane topArea = new GridPane();
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setPrefWidth(700);
		ScrollPane scrollPane = new ScrollPane(textArea);
		
		topArea.add(scrollPane,0,0);
		
		
		GridPane bottomArea = new GridPane();
	
		bottomArea.setAlignment(Pos.CENTER);
        bottomArea.add(hbox1, 0, 0);
        bottomArea.add(hbox2, 0, 1);
        bottomArea.add(hbox3, 0, 2);
		
		pane.getItems().addAll(topArea, bottomArea);

	
		
        Scene scene = new Scene(pane, 700, 300);
        primaryStage.setTitle("Interest Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();	
        
        
        
        
        
        
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent e)
            {
            	textArea.clear();
            	double principal = Double.parseDouble(principalTextField.getText());
                double rate = Double.parseDouble(rateTextField.getText());
                double year = slider.getValue();
                textArea.setText("Principal: " + Interest.formattedValue(principal) + ", Rate: " + rate + "\nYear, Simple Interest Amount");
                
                for(int i = 1; i < year; i ++ ) {
                    textArea.appendText("\n" + i + "-->" + Interest.formattedValue(Interest.simpleInterest(principal, rate, i)));

                }
            }
        };
  
        simpleInterestButton.setOnAction(event);
        
        
        compoundInterestButton.setOnAction((e) -> {
        	textArea.clear();
        	double principal2 = Double.parseDouble(principalTextField.getText());
            double rate2 = Double.parseDouble(rateTextField.getText());
            double year2 = slider.getValue();
            textArea.setText("Principal: " + Interest.formattedValue(principal2) + ", Rate: " + rate2 + "\nYear, Compound Interest Amount");
            
            for(int i = 1; i < year2; i ++ ) {
                textArea.appendText("\n" + i + "-->" + Interest.formattedValue(Interest.compoundInterest(principal2, rate2, i)));

            }
        });

		
        class BothInterests implements EventHandler<ActionEvent>{
			@Override
			public void handle(ActionEvent e) {
				textArea.clear();
            	double principal = Double.parseDouble(principalTextField.getText());
                double rate = Double.parseDouble(rateTextField.getText());
                double year = slider.getValue();
                textArea.setText("Principal: " + Interest.formattedValue(principal) + ", Rate: " + rate + "\nYear, Simple Interest Amount, Compounds Interest Amount");
                
                for(int i = 1; i < year; i ++ ) {
                    textArea.appendText("\n" + i + "-->" + Interest.formattedValue(Interest.simpleInterest(principal, rate, i)) + "-->" + Interest.formattedValue(Interest.compoundInterest(principal, rate, i)));

                }
			}
        	
        }
        
        bothInterestsButton.setOnAction(new BothInterests());
        
	}
	


	public static void main(String[] args) {
		Application.launch(args); 
	}
}
