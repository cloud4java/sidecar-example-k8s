package com.silva.logservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class LogHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LogHandler.class);
    public static final File LOG_FILE = new File("/logs/book-store.log");

    @Scheduled(fixedRate = 5000)
    public void handleLogs() {
        LOG.info("Handling logs...");

        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //At this point,  the logs could be sent to ELK, Splunk, or other aggregator
                LOG.info(line);
            }
        } catch (IOException e) {
            LOG.error("An error occurred while reading the log file: ", e);
        }

    }
}
