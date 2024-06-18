module com.p4zd4n.globogym {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jfxtras.agenda;


    opens com.p4zd4n.globogym to javafx.fxml;
    exports com.p4zd4n.globogym;
    exports com.p4zd4n.globogym.controller;
    opens com.p4zd4n.globogym.controller to javafx.fxml;
}