package com.project.shelf.book.projection;

public interface BestSellerProjection { //"인터페이스 기반 Projection은 Native Query나 JPQL을 통해 조회 시,쿼리 결과가 getter 메서드 명과 자동 매핑된다."
    Integer getId();
    String getBookImagePath();
    String getBookTitle();
    String getAuthor();
    Long getReadCount();
}
