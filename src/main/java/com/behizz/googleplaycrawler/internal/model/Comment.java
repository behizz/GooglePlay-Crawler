package com.behizz.googleplaycrawler.internal.model;

import com.opencsv.bean.CsvBindByName;

public class Comment {
    @CsvBindByName
    private String author;
    @CsvBindByName
    private String text;
    @CsvBindByName
    private String date;
    @CsvBindByName
    private String version;
    @CsvBindByName
    private int rate;

    public static final class CommentBuilder {
        private String author;
        private String text;
        private String date;
        private String version;
        private int rate;

        private CommentBuilder() {
        }

        public static CommentBuilder aComment() {
            return new CommentBuilder();
        }

        public CommentBuilder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public CommentBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public CommentBuilder withDate(String date) {
            this.date = date;
            return this;
        }

        public CommentBuilder withVersion(String version) {
            this.version = version;
            return this;
        }

        public CommentBuilder withRate(int rate) {
            this.rate = rate;
            return this;
        }

        public Comment build() {
            Comment comment = new Comment();
            comment.date = this.date;
            comment.version = this.version;
            comment.text = this.text;
            comment.author = this.author;
            comment.rate = this.rate;
            return comment;
        }
    }
}
