package ni.edu.uam.practicahilos.application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ni.edu.uam.practicahilos.controller.ProcesadorController;
public class MainApp extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        // La ruta debe coincidir con la carpeta resources/ni/edu/uam/practicahilos/fxml/
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ni/edu/uam/practicahilos/fxml/fxml/procesador-view.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("NicaData Solutions - Procesamiento Concurrente");
        stage.setScene(scene);
        stage.show();

        // Obtener el controlador para gestionar el cierre seguro de los hilos
        ProcesadorController controller = loader.getController();
        stage.setOnCloseRequest(event -> controller.detenerAlCerrar());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
