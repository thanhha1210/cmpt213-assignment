module cmpt213.asn5.client.client {
    requires javafx.controls;
    requires com.google.gson;

    exports cmpt213.asn5.client.ui;
    opens cmpt213.asn5.client.models to javafx.base, com.google.gson;
}