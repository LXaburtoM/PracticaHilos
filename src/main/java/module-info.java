module ni.edu.uam.practicahilos {
    requires javafx.controls;
    requires javafx.fxml;

    // Exportar el paquete de arranque
    exports ni.edu.uam.practicahilos.application;

    // Abrir el paquete del controlador a javafx.fxml
    opens ni.edu.uam.practicahilos.controller to javafx.fxml;
}