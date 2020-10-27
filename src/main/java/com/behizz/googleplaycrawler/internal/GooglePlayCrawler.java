package com.behizz.googleplaycrawler.internal;


import com.behizz.googleplaycrawler.internal.model.Comment;
import com.behizz.googleplaycrawler.internal.model.ParserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GooglePlayCrawler {
    private static final Logger logger = LoggerFactory.getLogger(GooglePlayCrawler.class);
    private final Fetcher fetcher;
    private final Parser parser;
    private final PersistenceInterface persistenceService;

    public GooglePlayCrawler(Fetcher fetcher, Parser parser,
                             PersistenceInterface persistenceService) {
        this.fetcher = fetcher;
        this.parser = parser;
        this.persistenceService = persistenceService;
    }

    public void run(String[] args) {
        if (!isInputDataValid(args)) {
            return;
        }

        logger.info("Started crawling for app {}", args[0]);

        // init fetcher and persistence service
        fetcher.init(args[0]);
        persistenceService.init(args[0], args[1]);

        List<Comment> commentList = new ArrayList<>();
        IntStream.range(1, 6).forEach(
                rate -> {
                    String pageIdentifier = "null";
                    while (pageIdentifier != null) {
                        // fetch data from Google Play
                        String result = fetcher.sendCommentRequest(rate, pageIdentifier);
                        try {
                            // parse results
                            ParserResponse parserResponse = parser.parseComments(result);

                            // buffer comments for final persistence
                            commentList.addAll(parserResponse.getComments());
                            logger.info("Fetched {} comments", commentList.size());

                            // set page identifier for the next request
                            pageIdentifier = parserResponse.getPageIdentifier();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        // persist data
        persistenceService.save(commentList);

    }

    private boolean isInputDataValid(String[] args) {
        if (args.length == 0) {
            logger.error("Please provide app package name and output dir!");
            return false;
        } else if (args.length == 1) {
            logger.error("Please provide output dir!");
            return false;
        }

        return true;
    }
}
