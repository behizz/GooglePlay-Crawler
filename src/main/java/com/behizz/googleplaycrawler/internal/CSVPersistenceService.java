package com.behizz.googleplaycrawler.internal;

import com.behizz.googleplaycrawler.internal.model.Comment;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class CSVPersistenceService implements PersistenceInterface {
    private static final Logger logger = LoggerFactory.getLogger(CSVPersistenceService.class);
    private String outputFile;

    @Override
    public void init(String appId, String outputPath) {
        this.outputFile = new File(outputPath, appId + "-" + "comments.csv").toString();
    }

    @Override
    public void save(List<Comment> comments) {
        try {
            Writer writer = new FileWriter(outputFile, true);
            StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write(comments);
            logger.info("wrote {} comments in {}", comments.size(), outputFile);
            writer.close();
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
