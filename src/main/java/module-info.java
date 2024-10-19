module cr.ac.una.mapp {
    requires javafx.controls;
      requires javafx.fxml;
        requires java.logging;
    requires java.base;
    opens cr.ac.una.mapp.controller to javafx.fxml;
    exports cr.ac.una.mapp;
}
