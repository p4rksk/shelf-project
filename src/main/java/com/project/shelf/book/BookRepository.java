package com.project.shelf.book;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.shelf.book.projection.BestSellerProjection;
import com.project.shelf.book.projection.DayBestSellerProjection;
import com.project.shelf.book.projection.WeekBestSellerProjection;
import com.project.shelf.user.UserResponse.MainDTO.BestSellerDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // 한달 간의 신간 구하는 쿼리
    @Query("SELECT b FROM Book b JOIN FETCH b.author a WHERE b.registrationDate>= :startDate AND b.registrationDate <= :endDate GROUP BY b.id ORDER BY COUNT(b.id) DESC")
    List<Book> findByRegistrationMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // 베스트셀러 구하는 쿼리
    @Query(value = """
        SELECT 
            b.id AS id,
            b.path AS bookImagePath,
            b.title AS bookTitle,
            a.name AS author,
            COUNT(bh.id) AS readCount
        FROM book_history_tb bh
        JOIN book_tb b ON bh.book_id = b.id
        JOIN author_tb a ON b.author_id = a.id
        GROUP BY b.id, b.path, b.title, a.name
        ORDER BY readCount DESC
    """, nativeQuery = true)
    List<BestSellerProjection> findBestSellers();
    

    

    // 주간 베스트 셀러 구하는 쿼리
    @Query(value = """
        SELECT 
            b.id AS id,
            b.path AS bookImagePath,
            b.title AS bookTitle,
            a.name AS author,
            COUNT(bh.id) AS readCount
        FROM book_history_tb bh
        JOIN book_tb b ON bh.book_id = b.id
        JOIN author_tb a ON b.author_id = a.id
        WHERE bh.created_at BETWEEN :startOfWeek AND :endOfWeek
        GROUP BY b.id, b.path, b.title, a.name
        ORDER BY readCount DESC
        """, nativeQuery = true)
    List<WeekBestSellerProjection> findWeekBestSellersNative(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);
    

    // 일별 베스트 셀러 구하는 쿼리
    @Query(value = """
        SELECT 
            b.id AS id,
            b.title AS bookTitle,
            a.name AS author,
            b.book_intro AS bookIntro,
            b.path AS bookImagePath
        FROM book_history_tb bh
        JOIN book_tb b ON bh.book_id = b.id
        JOIN author_tb a ON b.author_id = a.id
        WHERE bh.created_at BETWEEN :startOfDay AND :endOfDay
        GROUP BY b.id, b.title, a.name, b.book_intro, b.path
        ORDER BY COUNT(bh.id) DESC
        LIMIT 1
    """, nativeQuery = true)
    DayBestSellerProjection findDayBestSellerNative(@Param("startOfDay") LocalDateTime startOfDay,
                                                    @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT b FROM Book b JOIN FETCH b.author a WHERE a.id = :authorId")
    List<Book> findByAuthorId(@Param("authorId") Integer authorId);

    @Query("SELECT b FROM Book b JOIN FETCH b.author a WHERE b.category = :category")
    List<Book> findByCategory(@Param("category") Book.Category category);


    @Query("SELECT count(*) FROM Book b where b.category = :category")
    Integer findByCategoryConut(@Param("category") Book.Category category);

    // 관리자 책 목록 보기
    @Query("SELECT b FROM Book b JOIN FETCH b.author a ORDER BY b.registrationDate DESC")
    List<Book> findAllWithAuthor();

    // 관리자 책 상세보기
    @Query("select b, a.name from Book b JOIN FETCH b.author a where b.id =:bookId")
    Optional<Book> findByBookId(@Param("bookId") Integer bookId);

    // 앱 책 정보 불러오기
    @Query("select b, a.name from Book b JOIN FETCH b.author a where b.id =:bookId")
    Optional<Book> appFindABook(@Param("bookId") Integer bookId);

    //책 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM Book b WHERE b.id = :bookId")
    void deleteByBookId(@Param("bookId") Integer bookId);


    @Query("select b from BookHistory bh JOIN bh.book b JOIN FETCH b.author a WHERE b.category = :category GROUP BY b.id ORDER BY COUNT(bh.id) DESC")
    List<Book> findBestSellersByCategory(@Param("category") Book.Category category);

}
