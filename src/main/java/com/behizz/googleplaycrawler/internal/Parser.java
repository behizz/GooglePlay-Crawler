package com.behizz.googleplaycrawler.internal;

import com.behizz.googleplaycrawler.internal.model.Comment;
import com.behizz.googleplaycrawler.internal.model.ParserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ParserResponse parseComments(String result) throws JsonProcessingException {
        // remove unwanted parts from response and just get the targeted line
        result = result.split("\n")[3];
        // remove the first extra [
        result = result.replaceAll("^\\[", "");

        // wrapper json object
        ObjectNode wrapperObject = objectMapper.readValue(fixJsonStructure(result), ObjectNode.class);

        // extract target data json
        String targetData = wrapperObject.get("key").get(2).toString();
        targetData = removeUnwantedChars(targetData);
        ObjectNode targetJsonObject = objectMapper.readValue(fixJsonStructure(targetData), ObjectNode.class);

        List<Comment> commentList = new ArrayList<>();
        // loop on each comment
        targetJsonObject.get("key").get(0).forEach(node -> {
            // author
            String author = node.get(1).get(0).toString();
            // rate
            int rate = node.get(2).asInt();
            // comment text
            String text = node.get(4).toString();
            // comment date
            long timestamp = node.get(5).get(0).asLong();
            LocalDateTime date = Instant.ofEpochMilli(timestamp * 1000)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            // on version
            String version = "";
            if (node.size() > 10) {
                version = node.get(10).toString();
            }

            Comment comment = Comment.CommentBuilder
                    .aComment()
                    .withAuthor(author)
                    .withText(text)
                    .withDate(date.toString())
                    .withVersion(version)
                    .withRate(rate)
                    .build();
            commentList.add(comment);
        });

        // page identifier
        String pageIdentifier = null;
        if (targetJsonObject.get("key").size() > 1) {
            pageIdentifier = textCleaner(targetJsonObject.get("key").get(1).get(1).toString());
        }

        return new ParserResponse(commentList, pageIdentifier);
    }

    private String fixJsonStructure(String input) {
        return "{ \"key\": " + input + "}";
    }

    private String removeUnwantedChars(String input){
        return input.replaceAll("^\"", "")
                .replaceAll("\"$", "")
                .replaceAll("\\\\n", "")
                .replaceAll("\\[\\\\\"", "\\[\"")
                .replaceAll("\\\\\"]", "\"]")
                .replaceAll(",\\\\*\"", ",\"")
                .replaceAll("\\\\*\",", "\",")
                .replaceAll("\\\\\"", "")
                .replaceAll("\\\\", "");
    }

    private String textCleaner(String input) {
        return input.replaceAll("\"", "");
    }

}
