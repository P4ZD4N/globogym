module com.p4zd4n.globogym {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jfxtras.agenda;


    opens com.p4zd4n.globogym to javafx.fxml;
    opens com.p4zd4n.globogym.entities to javafx.base;
    exports com.p4zd4n.globogym;
    exports com.p4zd4n.globogym.controllers;
    opens com.p4zd4n.globogym.controllers to javafx.fxml;
}