package com.ConnectASU.entities;

public class Post {
    private final int id;
    private final String content;
    private final String authorName;

    public Post(int id, String content, String authorName) {
        this.id = id;
        this.content = content;
        this.authorName = authorName;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthorName() {
        return authorName;
    }
}
