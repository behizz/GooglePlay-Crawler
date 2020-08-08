package com.behizz.googleplaycrawler;

import com.behizz.googleplaycrawler.internal.CSVPersistenceService;
import com.behizz.googleplaycrawler.internal.Fetcher;
import com.behizz.googleplaycrawler.internal.GooglePlayCrawler;
import com.behizz.googleplaycrawler.internal.Parser;

public class Main {

    public static void main(String[] args) throws Exception {
        new GooglePlayCrawler(new Fetcher(), new Parser(), new CSVPersistenceService())
                .run(args);
    }
}
