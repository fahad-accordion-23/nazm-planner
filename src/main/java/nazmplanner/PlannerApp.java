package nazmplanner;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * <h2>PlannerApp</h2>
 * 
 * <p>Entry point for the app.</p>
 */
public class PlannerApp extends Application
{
    private Parent createContent()
    {
        return new StackPane(new Text("Hello, World!"));
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setScene(new Scene(createContent(), 300, 300));
        stage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
