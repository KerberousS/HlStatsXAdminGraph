module administratorgraph {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;
    requires java.xml.bind;
    requires java.xml;
    requires java.xml.crypto;
    requires com.sun.xml.bind;
    requires org.hibernate.orm.core;
    requires junit;
    requires java.sql;
    requires java.persistence;
    requires org.jsoup;
    requires org.joda.time;
    requires javafx.controls;
    requires java.naming;
    requires net.bytebuddy;
    exports javaFX;
    opens hibernate;
    opens javaFX;
}