package com.silva.logservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class LogHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LogHandler.class);
    public static final File LOG_FILE = new File("logs/book-store.log");

    @Scheduled(fixedRate = 5000)
    public void handleLogs() {
        LOG.info("Handling logs...");
        long lastPosition = 0;

        try (RandomAccessFile reader = new RandomAccessFile(LOG_FILE, "r")) {

            long fileLength = LOG_FILE.length();
            if(fileLength< lastPosition){
                lastPosition = 0;
            }
            if(fileLength > lastPosition){
                reader.seek(lastPosition);
                String line;
                while ((line = reader.readLine()) != null) {
                    //At this point,  the logs could be sent to ELK, Splunk, or other aggregator
                    LOG.info(line);
                }
                lastPosition = reader.getFilePointer();
            }
        } catch (IOException e) {
            LOG.error("An error occurred while reading the log file: ", e);
        }
    }
}
