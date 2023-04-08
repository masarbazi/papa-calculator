package Controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class WelcomeController {

    //fxml variables
    @FXML
    private Pane paneLogo, paneWelcome, paneTo;
    @FXML
    public StackPane stackPane;
    @FXML
    private AnchorPane anchWelcome;
    @FXML
    private Group animationGroup;
    @FXML
    private ImageView imgP1,imgP2,imgP3,imgP4;

    //my variables
    @FXML
    private SimpleController simpleController;
    public static boolean notification = false;


    public void initialize()
    {
        System.out.println("Starting welcome");
        Image p1 = new Image("images/p1.png");
        Image p2 = new Image("images/a1.png");
        Image p3 = new Image("images/p2.png");
        Image p4 = new Image("images/a2.png");
        imgP1.setImage(p1);
        imgP2.setImage(p2);
        imgP3.setImage(p3);
        imgP4.setImage(p4);
        animationSetup();
        dropdownWithFade(paneWelcome, 1.5, 50, 0);
    }

    private void animationSetup()
    {
        //animation variables
        Image rabit1 = new Image("images/1.png");
        Image rabit2 = new Image("images/2.png");
        Image rabit3 = new Image("images/3.png");
        Image rabit4 = new Image("images/4.png");
        Image rabit5 = new Image("images/5.png");
        Image rabit6 = new Image("images/6.png");
        Image rabit7 = new Image("images/7.png");
        Image rabit8 = new Image("images/8.png");
        //animation imageviews
        final ImageView imgRabit1 = new ImageView(rabit1);
        final ImageView imgRabit2 = new ImageView(rabit2);
        final ImageView imgRabit3 = new ImageView(rabit3);
        final ImageView imgRabit4 = new ImageView(rabit4);
        final ImageView imgRabit5 = new ImageView(rabit5);
        final ImageView imgRabit6 = new ImageView(rabit6);
        final ImageView imgRabit7 = new ImageView(rabit7);
        final ImageView imgRabit8 = new ImageView(rabit8);
        //timeline
        Timeline t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);

        animationGroup.getChildren().add(imgRabit1);

        //Add images into the timeline
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(150),
                (ActionEvent event) -> {
                    animationGroup.getChildren().setAll(imgRabit2);
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(300),
                (ActionEvent event) -> {
                    animationGroup.getChildren().setAll(imgRabit3);
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(450),
                (ActionEvent event) -> {
                    animationGroup.getChildren().setAll(imgRabit4);
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(600),
                (ActionEvent event) -> {
                    animationGroup.getChildren().setAll(imgRabit5);
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(750),
                (ActionEvent event) -> {
                    animationGroup.getChildren().setAll(imgRabit6);
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(900),
                (ActionEvent event) -> {
                    animationGroup.getChildren().setAll(imgRabit7);
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1050),
                (ActionEvent event) -> {
                    animationGroup.getChildren().setAll(imgRabit8);
                }
        ));

        t.play();

    }//end animationSetup

    private void dropdownWithFade(Node node, double duration, double size, int level)
    {
        level++;
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), node);

        translateTransition.setByY(size);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        translateTransition.play();
        fadeTransition.play();

        int finalLevel = level;
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(finalLevel ==1)
                    dropdownWithFade(paneTo, 0.8, size, finalLevel);
                else if(finalLevel ==2)
                    dropdownWithFade(paneLogo, 1.8, 90, finalLevel);
                else
                    loadCalculator();
            }
        });

    }//end dropdownWithFade

    private void loadCalculator()
    {
        System.out.println("i've been called");
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("../Scene/Simple.fxml"));
            stackPane.getChildren().add(root);

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), root);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();
            fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    stackPane.getChildren().remove(anchWelcome);
                }
            });
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }//end loadCalculator


    public void setStackPaneWidth(double width)
    {
        stackPane.setPrefWidth(width);
        System.out.println("set done");
    }


}//end class
