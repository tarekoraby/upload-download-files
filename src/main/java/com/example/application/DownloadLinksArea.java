package com.example.application;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DownloadLinksArea extends VerticalLayout {

    private final File uploadFolder;

    public DownloadLinksArea(File uploadFolder) {
        this.uploadFolder = uploadFolder;
        refreshFileLinks();
        setMargin(true);
    }

    public void refreshFileLinks() {
        removeAll();
        add(new H4("Download Links:"));

        for (File file : uploadFolder.listFiles()) {
            addLinkToFile(file);
        }
    }

    private void addLinkToFile(File file) {
        FileInputStream stream;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        StreamResource streamResource = new StreamResource(file.getName(), () -> stream);
        Anchor link = new Anchor(streamResource, String.format("%s (%d KB)", file.getName(),
                (int) file.length() / 1024));
        link.getElement().setAttribute("download", true);
        add(link);
    }
}
