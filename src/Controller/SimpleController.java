package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jdk.internal.util.xml.impl.Input;

import javax.swing.plaf.ColorUIResource;

public class SimpleController {

    //fxml variables
    @FXML
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnPlus,btnMul,btnMinus,btnDiv,btnEqual,btnPercent,btnLeftBracket,btnRightBracket,btnClear,btnDot;
    @FXML
    private Label lblCurrent,lblResult;
    @FXML
    private AnchorPane anchSimple;
    @FXML
    private VBox menu;
    @FXML
    private JFXButton btnScientific,btnCloseMenu,btnExit;
    @FXML
    private StackPane stackPane;


    //my variables
    Calculator calculator = new Calculator();
    boolean menudown = false;
    private double anchX, anchY;
    Stage stage;
    String current = "";


    public void initialize()
    {
        makeDraggable();
    }//end initialize

    private void setCurrent(String current)
    {
        if(current.equals("C"))
        {
            this.current = "";
            lblCurrent.setText("0.0");
        }
        else
        {
            this.current = current;
            lblCurrent.setText(current);
            String result = calculator.calculate(current);
            if(result.startsWith("Sorry"))
            {
                setCurrent("C");
                showAlarm(result);
            }
            else
                lblResult.setText(result);
        }
    }//end setCurrent


    private void showAlarm(String message)
    {

        BoxBlur blur = new BoxBlur(3, 3, 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton btnDialog = new JFXButton("OK");
        btnDialog.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        btnDialog.setPrefSize(60,30);
        btnDialog.setStyle("-fx-background-color: #7fffd4; -fx-background-radius: 5; -fx-text-fill: #f84100; -fx-font-family: 'Agency FB'; -fx-font-size: 23;");
        JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        btnDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            dialog.close();
        });//end ClickEvent

        Label label = new Label(message);
        label.setStyle("-fx-font-family: 'Agency FB'; -fx-font-size: 17;");
        dialogLayout.setHeading(label);
        dialogLayout.setActions(btnDialog);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event) -> {
            anchSimple.setEffect(null);
        });

        anchSimple.setEffect(blur);

    }//end showAlarm


    @FXML
    private void handleClick(MouseEvent event)
    {
       if(event.getSource()==btn0)
            setCurrent(current + "0");
       else if(event.getSource()==btn1)
           setCurrent(current + "1");
       else if(event.getSource()==btn2)
           setCurrent(current + "2");
       else if(event.getSource()==btn3)
           setCurrent(current + "3");
       else if(event.getSource()==btn4)
           setCurrent(current + "4");
       else if(event.getSource()==btn5)
           setCurrent(current + "5");
       else if(event.getSource()==btn6)
           setCurrent(current + "6");
       else if(event.getSource()==btn7)
           setCurrent(current + "7");
       else if(event.getSource()==btn8)
           setCurrent(current + "8");
       else if(event.getSource()==btn9)
           setCurrent(current + "9");
       else if(event.getSource()==btnPlus)
           setCurrent(current + "+");
       else if(event.getSource()==btnMinus)
           setCurrent(current + "-");
       else if(event.getSource()==btnMul)
           setCurrent(current + "*");
       else if(event.getSource()==btnDiv)
           setCurrent(current + "/");
       else if(event.getSource()==btnClear)
           setCurrent("C");
       else if(event.getSource()==btnRightBracket)
           setCurrent(current + ")");
       else if(event.getSource()==btnLeftBracket)
           setCurrent(current + "(");
       else if(event.getSource()==btnPercent)
           setCurrent(current + "%");
       else if(event.getSource()==btnDot)
           setCurrent(current + ".");
       else if(event.getSource()==btnEqual)
           equalClicked();

    }//end handleClick


    @FXML
    private void handleControl(MouseEvent event)
    {
        if(event.getSource()==btnCloseMenu)
            dropdown();
        else if(event.getSource()==btnExit)
            System.exit(0);
        else if(event.getSource()==btnScientific)
        {
            dropdown();
            loadScientific();
        }
    }//end handleControl


    private void equalClicked()
    {
        current = "";
        String result = calculator.calculate(lblCurrent.getText());
        lblCurrent.setText(result);
        lblResult.setText("");
    }


    private void makeDraggable()
    {
        anchSimple.setOnMousePressed( (event -> {
            anchX = event.getSceneX();
            anchY = event.getSceneY();
        }));

        anchSimple.setOnMouseDragged( (event -> {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getSceneX() - anchX);
            stage.setY(event.getSceneY() - anchY);
        }));
    }//end makeDraggable


    private void loadScientific()
    {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), anchSimple);
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), anchSimple);
        scaleTransition.setToX(0.05);
        scaleTransition.setToY(0.05);
        rotateTransition.setByAngle(360);
        scaleTransition.play();
        rotateTransition.play();

        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../Scene/Scientific.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        scene.setFill(Color.TRANSPARENT);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setScene(scene);
                        stage.show();

                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }

                    //the old code which added fxml to stackpane
                    /*
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../Scene/Scientific.fxml"));
                    Scene thisScene = anchSimple.getScene();
                    StackPane stackPane = (StackPane) thisScene.getRoot();
                    stackPane.getChildren().add(root);
                    stackPane.getChildren().remove(anchSimple);



                    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), anchSimple);
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.6), anchSimple);
                    scaleTransition.setToX(20);
                    scaleTransition.setToY(20);
                    rotateTransition.setByAngle(360);
                    scaleTransition.play();
                    rotateTransition.play();

                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                     */
            }
        });

    }//end loadScientific


    @FXML
    private void dropdown()
    {
         if(!menudown)
         {
             menudown = true;
             TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.7), menu);
             translateTransition.setAutoReverse(true);
             translateTransition.setCycleCount(1);
             translateTransition.setByY(150);
             translateTransition.play();
         }
         else
         {
             menudown = false;
             TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), menu);
             translateTransition.setAutoReverse(true);
             translateTransition.setCycleCount(1);
             translateTransition.setByY(-150);
             translateTransition.play();
         }
    }//end dropdown

}//end class
