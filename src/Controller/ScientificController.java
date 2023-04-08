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

import javax.swing.plaf.ColorUIResource;

public class ScientificController {

    //fxml variables
    @FXML
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnPlus,btnMul,btnMinus,btnDiv,btnEqual,btnPercent,btnLeftBracket,btnRightBracket,btnClear,btnDot;
    @FXML
    private Button btnCE,btnBackSpace,btnSin,btnCos,btnTan,btnCot,btnLog,btnPow2,btnFact,btnSqrt,btnSecond;
    @FXML
    private Label lblCurrent,lblResult;
    @FXML
    private VBox menu;
    @FXML
    private JFXButton btnSimple,btnCloseMenu,btnExit;
    @FXML
    private AnchorPane anchScientific;
    @FXML
    private StackPane stackPane;

    //my varibles
    Calculator calculator = new Calculator();
    String current = "";
    boolean menudown = false;


    public void initialize()
    {
        if(!WelcomeController.notification)
        {
            WelcomeController.notification = true;
            showAlarm("Sorry, for the purpose of this project\nPaPa is not able to calculate decimal\nnumbers but it does big ones ;)");
        }
    }//end initialize


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
        label.setStyle("-fx-font-family: 'Agency FB'; -fx-font-size: 20;");
        dialogLayout.setHeading(label);
        dialogLayout.setActions(btnDialog);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event) -> {
            anchScientific.setEffect(null);
        });

        anchScientific.setEffect(blur);

    }//end showAlarm


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


    private void equalClicked()
    {
        current = "";
        String result = calculator.calculate(lblCurrent.getText());
        lblCurrent.setText(result);
        lblResult.setText("");
    }


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
        else if(event.getSource()==btnBackSpace)
            setCurrent(current.substring(0,current.length()-1));
        else if(event.getSource()==btnSin)
            setCurrent(current + btnSin.getText() +"(");
        else if(event.getSource()==btnCos)
            setCurrent(current + btnCos.getText() +"(");
        else if(event.getSource()==btnTan)
            setCurrent(current + btnTan.getText() +"(");
        else if(event.getSource()==btnCot)
            setCurrent(current + btnCos.getText() +"(");
        else if(event.getSource()==btnLog)
            setCurrent(current + btnLog.getText() +"(");
        else if(event.getSource()==btnPow2)
            setCurrent(current + "^2");
        else if(event.getSource()==btnFact)
            setCurrent(current + "!");
        else if(event.getSource()==btnSqrt)
            setCurrent(current + "sqrt(");
        else if(event.getSource()==btnCE)
            setCurrent(calculator.btnClearToFirstOperatorPressed(current));
        else if(event.getSource()==btnEqual)
            equalClicked();
        else if(event.getSource()==btnSecond)
            secondMode();

    }//end handleClick


    private void secondMode()
    {
        if(btnSin.getText().equals("Sin"))
        {
            btnSin.setText("asin");
            btnCos.setText("acos");
            btnTan.setText("atan");
            btnCot.setText("acot");
            btnLog.setText("ln");
            btnSecond.setText("1st");
        }
        else
        {
            btnSin.setText("Sin");
            btnCos.setText("cos");
            btnTan.setText("tan");
            btnCot.setText("cot");
            btnLog.setText("log");
            btnSecond.setText("2st");
        }
    }


    @FXML
    private void handleControl(MouseEvent event)
    {
        if(event.getSource()==btnCloseMenu)
            dropdown();
        else if(event.getSource()==btnExit)
            System.exit(0);
        else if(event.getSource()==btnSimple)
        {
            dropdown();
            loadSimple();
        }
    }//end handleControl


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

    private void loadSimple()
    {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), anchScientific);
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), anchScientific);
            scaleTransition.setToX(0.05);
            scaleTransition.setToY(0.05);
            rotateTransition.setByAngle(360);
            scaleTransition.play();
            rotateTransition.play();

            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../Scene/Simple.fxml"));
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
                }
            });
    }


}
