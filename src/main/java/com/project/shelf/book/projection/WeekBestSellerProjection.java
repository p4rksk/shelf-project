package com.project.shelf.book.projection;

public interface WeekBestSellerProjection {
    Integer getId();
    String getBookImagePath();
    String getBookTitle();
    String getAuthor();
    Long getReadCount();
}
