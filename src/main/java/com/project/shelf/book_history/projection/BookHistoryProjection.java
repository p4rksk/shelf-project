package com.project.shelf.book_history.projection;

public interface BookHistoryProjection {
    Integer getUserId();
    Integer getBookId();
    String getBookTitle();
    Integer getPageCount();
    Integer getLastReadPage();
    String getBookImagePath();
}