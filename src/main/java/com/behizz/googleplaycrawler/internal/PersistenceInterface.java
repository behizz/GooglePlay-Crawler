package com.behizz.googleplaycrawler.internal;

import com.behizz.googleplaycrawler.internal.model.Comment;

import java.util.List;

public interface PersistenceInterface {
    /**
     * Init method for specifying output file
     * @param appId
     * @param outputPath
     */
    void init(String appId, String outputPath);

    /**
     * Save comments with the desired method
     * @param comments
     */
    void save(List<Comment> comments);
}
