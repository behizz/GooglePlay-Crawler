package com.behizz.googleplaycrawler.internal.model;

import java.util.List;

public class ParserResponse {
    private List<Comment> comments;
    private String pageIdentifier;

    public ParserResponse(List<Comment> comments, String pageIdentifier) {
        this.comments = comments;
        this.pageIdentifier = pageIdentifier;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getPageIdentifier() {
        return pageIdentifier;
    }
}
