package ni.edu.uam.practicahilos.controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
public class ProcesadorController {
    // Controles Clientes
    @FXML private Label lblEstadoClientes;
    @FXML private Label lblPorcentajeClientes;
    @FXML private ProgressBar barraClientes;
    @FXML private Button btnIniciarClientes;
    @FXML private Button btnDetenerClientes;

    // Controles Productos
    @FXML private Label lblEstadoProductos;
    @FXML private Label lblPorcentajeProductos;
    @FXML private ProgressBar barraProductos;
    @FXML private Button btnIniciarProductos;
    @FXML private Button btnDetenerProductos;

    // Hilos independientes
    private Thread hiloClientes;
    private Thread hiloProductos;

    // --- LÓGICA PARA CLIENTES (100 Registros) ---
    @FXML
    private void iniciarClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) return; // Evita doble ejecución

        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblEstadoClientes.setText("Procesando clientes...");
        btnIniciarClientes.setDisable(true);
        btnDetenerClientes.setDisable(false);

        Runnable tareaClientes = () -> {
            try {
                for (int i = 1; i <= 100; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                    Thread.sleep(80); // Tiempo de ejecución diferente (rápido)

                    final int avance = i;
                    Platform.runLater(() -> {
                        barraClientes.setProgress(avance / 100.0);
                        lblPorcentajeClientes.setText(avance + "%");
                    });
                }
                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Proceso de clientes finalizado");
                    btnIniciarClientes.setDisable(false);
                    btnDetenerClientes.setDisable(true);
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Los 100 clientes han sido procesados.");
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Proceso de clientes detenido");
                    btnIniciarClientes.setDisable(false);
                    btnDetenerClientes.setDisable(true);
                });
            }
        };

        hiloClientes = new Thread(tareaClientes);
        hiloClientes.setDaemon(true);
        hiloClientes.start();
    }

    @FXML
    private void detenerClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            btnDetenerClientes.setDisable(true);
            lblEstadoClientes.setText("Solicitando detención de clientes...");
            hiloClientes.interrupt();
        }
    }

    // --- LÓGICA PARA PRODUCTOS (50 Registros) ---
    @FXML
    private void iniciarProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) return;

        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0%");
        lblEstadoProductos.setText("Procesando productos...");
        btnIniciarProductos.setDisable(true);
        btnDetenerProductos.setDisable(false);

        Runnable tareaProductos = () -> {
            try {
                for (int i = 1; i <= 50; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                    Thread.sleep(150); // Tiempo de ejecución diferente (más lento)

                    final int avance = i;
                    Platform.runLater(() -> {
                        barraProductos.setProgress(avance / 50.0);
                        lblPorcentajeProductos.setText((avance * 2) + "%");
                    });
                }
                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Proceso de productos finalizado");
                    btnIniciarProductos.setDisable(false);
                    btnDetenerProductos.setDisable(true);
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Los 50 productos han sido procesados.");
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Proceso de productos detenido");
                    btnIniciarProductos.setDisable(false);
                    btnDetenerProductos.setDisable(true);
                });
            }
        };

        hiloProductos = new Thread(tareaProductos);
        hiloProductos.setDaemon(true);
        hiloProductos.start();
    }

    @FXML
    private void detenerProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) {
            btnDetenerProductos.setDisable(true);
            lblEstadoProductos.setText("Solicitando detención de productos...");
            hiloProductos.interrupt();
        }
    }

    // --- FUNCIONES GENERALES ---
    @FXML
    private void reiniciarTodo() {
        detenerClientes();
        detenerProductos();

        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0%");
        lblEstadoClientes.setText("Esperando inicio...");
        btnIniciarClientes.setDisable(false);

        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0%");
        lblEstadoProductos.setText("Esperando inicio...");
        btnIniciarProductos.setDisable(false);
    }

    public void detenerAlCerrar() {
        if (hiloClientes != null && hiloClientes.isAlive()) hiloClientes.interrupt();
        if (hiloProductos != null && hiloProductos.isAlive()) hiloProductos.interrupt();
    }

    // Regla de arquitectura: Centralización de notificaciones[cite: 3]
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
