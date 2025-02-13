package com.project.shelf.user;

import com.project.shelf._core.enums.Avatar;
import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.util.ApiUtil;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf._core.util.NaverToken;
import com.project.shelf.book.BookResponseRecord.RankResponseDTO;
import com.project.shelf.payment.Payment;
import com.project.shelf.payment.PaymentRepository;
import com.project.shelf.user.UserRequestRecord.LoginReqDTO;
import com.project.shelf.user.UserResponseRecord.*;
import com.project.shelf._core.erros.exception.Exception401;
import com.project.shelf.book.Book;
import com.project.shelf.book.BookRepository;
import com.project.shelf.book_history.BookHistoryRepository;
import com.project.shelf.wishlist.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookHistoryRepository bookHistoryRepository;
    private final NaverToken naverToken;
    private final PaymentRepository paymentRepository;

    //회원가입
    @Transactional
    public User join(UserRequest.JoinDTO reqDTO) {
        Optional<User> userOp = userRepository.findByEmail(reqDTO.getEmail());

        if (userOp.isPresent()) {
            throw new Exception400("중복된 이메일이 존재합니다.");
        }
        User user = userRepository.save(User.builder()
                .email(reqDTO.getEmail())
                .password(reqDTO.getPassword())
                .nickName(reqDTO.getNickName())
                .avatar(Avatar.AVATAR01)
                .status(false)
                .createdAt(LocalDateTime.now())
                .build());
        return user;
    }

    @Transactional
    public LoginRespDTO login(LoginReqDTO reqDTO) {
        User user = userRepository.findByEmail(reqDTO.email())
                .orElseThrow(() -> new Exception400("등록된 정보를 찾을 수 없습니다."));
        log.info("유저 정보: {}", user);

        return LoginRespDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .status(user.getStatus())
                .avatar(user.getAvatar().getValue())
                .createdAt(user.getCreatedAt())
                .build();
    }

    //메인페이지
    public MainDTO main(SessionUser sessionUser) {
        // 1. 베스트 셀러 정보 DTO 매핑
        List<MainDTO.BestSellerDTO> bestSeller = IntStream.range(0, 10)
                .mapToObj(i -> {
                    Book book = bookRepository.findBooksByHistory().get(i);
                    return MainDTO.BestSellerDTO.builder()
                            .id(book.getId())
                            .bookImagePath(book.getPath())
                            .bookTitle(book.getTitle())
                            .author(book.getAuthor().getName())
                            .rankNum(i + 1) // 순위 추가
                            .build();
                })
                .collect(Collectors.toList());

        //2. 이어보기 정보 DTO 매핑
        List<MainDTO.BookHistoryDTO> bookHistories = bookHistoryRepository.findBookHistoryByUserId(sessionUser.getId()).stream().map(
                bookHistory -> MainDTO.BookHistoryDTO.builder()
                        .userId(sessionUser.getId())
                        .bookId(bookHistory.getBook().getId())
                        .bookImagePath(bookHistory.getBook().getPath())
                        .bookTitle(bookHistory.getBook().getTitle())
                        .pageCount(bookHistory.getBook().getPageCount())
                        .lastReadPage(bookHistory.getLastReadPage())
                        .build()).collect(Collectors.toList());


        LocalDate today = LocalDate.now();

        // 3. 주간 베스트 셀러 DTO 매핑
        List<MainDTO.WeekBestSellerDTO> weekBestSeller = IntStream.range(0, getWeeklyBestSellers(today).size())
                .mapToObj(i -> {
                    Book book = getWeeklyBestSellers(today).get(i);
                    return MainDTO.WeekBestSellerDTO.builder()
                            .id(book.getId())
                            .bookImagePath(book.getPath())
                            .bookTitle(book.getTitle())
                            .author(book.getAuthor().getName())
                            .rankNum(i + 1) // 순위 추가
                            .build();
                })
                .collect(Collectors.toList());

        //4, 일간 베스트 셀러 정보 DTO 매핑
        Book book = getDailyBestSellers(today);
        MainDTO.DayBestSellerDTO dayBestSeller = MainDTO.DayBestSellerDTO.builder()
                .id(book.getId())
                .bookTitle(book.getTitle())
                .bookImagePath(book.getPath())
                .bookIntro(book.getBookIntro())
                .author(book.getAuthor().getName())
                .build();


        return MainDTO.builder()
                .bestSellerDTOS(bestSeller)
                .bookHistoryDTOS(bookHistories)
                .weekBestSellerDTOS(weekBestSeller)
                .dayBestSellerDTO(dayBestSeller)
                .build();
    }

    //주간 베스트 셀러 날짜 구하는 메서드
    public List<Book> getWeeklyBestSellers(LocalDate date) {
        LocalDateTime startOfWeek = date.with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = date.with(DayOfWeek.SUNDAY).atTime(LocalTime.MAX);

        System.out.println(startOfWeek + " 찾아라 " + endOfWeek);
        return bookRepository.findWeekBestSellers(startOfWeek, endOfWeek);
    }


    //일별 베스트셀러 날짜 구하는 메서드
    public Book getDailyBestSellers(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay(); // 하루의 시작 시간
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 하루의 끝 시간
        Pageable pageable = PageRequest.of(0, 1);
        Page<Book> page = bookRepository.findTopDayBestSeller(startOfDay, endOfDay, pageable);
        return page.getContent().get(0);
    }

    //네이버 오어스
    @Transactional
    public String oauthNaver(String naverAccessToken) {
        // 1. RestTemplate 객체 생성
        RestTemplate rt = new RestTemplate();

        // 2. 토큰으로 사용자 정보 받기 (PK, Email)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + naverAccessToken);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(headers);

        ResponseEntity<NaverRespDTO.NaverUserDTO> response = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                request,
                NaverRespDTO.NaverUserDTO.class);

        // 3. 해당정보로 DB조회 (있을수, 없을수)
        String username = "naver_" + response.getBody().response().email();
        User userPS = userRepository.findByEmail(username)
                .orElse(null);

        // 4. 있으면? - 조회된 유저정보 리턴
        if (userPS != null) {
            return AppJwtUtil.create(userPS);
        } else {
            // 5. 없으면? - 강제 회원가입
            User user = User.builder()
                    .password(UUID.randomUUID().toString())
                    .email(response.getBody().response().email())
                    .provider("naver")
                    .build();
            User returnUser = userRepository.save(user);
            return AppJwtUtil.create(returnUser);
        }
    }


    // 사용자 마이 페이지
    public UserResponse.MyPageDTO MyPage(SessionUser sessionUser) {
        // 사용자 정보 불러오기 ( 세션 )
        Payment payment = paymentRepository.findLastPaymentById(sessionUser.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new Exception401("❗로그인 되지 않았습니다❗"));

        // 문자열을 long 으로
        String orderDateStr = payment.getOrderDate();
        long unixTimestamp = Long.parseLong(orderDateStr);

        // unixTimestamp -> LocalDateTime으로 변환
        LocalDateTime subStartDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp), ZoneId.systemDefault());
        LocalDate subStartDateLD = subStartDateTime.toLocalDate().minusDays(1);;

        // 구독 종료일 및 다음 결제일 계산
        LocalDate subEndDateLD = subStartDateLD.plusMonths(1).minusDays(1);
        LocalDate nextPaymentDayLD = subStartDateLD.plusMonths(1);
        // String 문자열로 포맷
        String subStartDate = subStartDateLD.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        String subEndDate = subEndDateLD.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        String nextPaymentDate = nextPaymentDayLD.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        // 구독기간 생성
        String subPeriod = subStartDate + " ~ " + subEndDate;

        return new UserResponse.MyPageDTO(subPeriod, nextPaymentDate);
    }

    // 사용자 개인 정보
    public UserResponse.MyInfoDTO MyInfo(SessionUser sessionUser) {
        // 사용자 정보 불러오기 ( 세션 )
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception401("❗로그인 되지 않았습니다❗"));

        return new UserResponse.MyInfoDTO(user);
    }

    // 사용자 정보 수정
    @Transactional
    public UserResponse.UpdateInfoDTO UpdateInfo(SessionUser sessionUser, UserRequest.UpdateInfoDTO reqDTO) {
        // 사용자 정보 불러오기 ( 세션 )
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception401("❗로그인 되지 않았습니다❗"));

        // 사용자 정보 업데이트
        user.setAvatar(reqDTO.getAvatar());
        user.setNickName(reqDTO.getNickName());
        user.setPassword(reqDTO.getPassword());
        user.setPhone(reqDTO.getPhone());
        user.setAddress(reqDTO.getAddress());

        return new UserResponse.UpdateInfoDTO(user);
    }


    //내 서재 페이지
    public MyLibraryResponseDTO myLibrary(SessionUser sessionUser) {
        //1. 이어보기 정보 DTO 매핑
        List<MyLibraryResponseDTO.BookListDTO.HistoryDTO> bookHistories = bookHistoryRepository.findBookHistoryByUserId(sessionUser.getId()).stream().map(
                bookHistory -> MyLibraryResponseDTO.BookListDTO.HistoryDTO.builder()
                        .id(bookHistory.getBook().getId())
                        .imagePath(bookHistory.getBook().getPath())
                        .bookTitle(bookHistory.getBook().getTitle())
                        .pageCount(bookHistory.getBook().getPageCount())
                        .lastReadPage(bookHistory.getLastReadPage())
                        .build()).collect(Collectors.toList());

        //2. 전체 도서 DTO 매핑
        List<MyLibraryResponseDTO.BookListDTO.AllBookDTO> allBook = bookHistoryRepository.findBookListByUserId(sessionUser.getId()).stream().map(
                bookHistory -> MyLibraryResponseDTO.BookListDTO.AllBookDTO.builder()
                        .id(bookHistory.getBook().getId())
                        .bookImagePath(bookHistory.getBook().getPath())
                        .bookTitle(bookHistory.getBook().getTitle())
                        .author(bookHistory.getBook().getAuthor().getName())
                        .build()).collect(Collectors.toList());

        //3. 책 목록 DTO 매핑
        List<MyLibraryResponseDTO.BookListDTO> bookList = new ArrayList<>();
        bookList.add(MyLibraryResponseDTO.BookListDTO.builder()
                .historyList(bookHistories)
                .allBook(allBook)
                .build());

        //4. 위시리스트 DTO 매핑
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<MyLibraryResponseDTO.WishListDTO> wishList = wishlistRepository.findWishlistByByUserId(sessionUser.getId()).stream().map(
                wishlist -> MyLibraryResponseDTO.WishListDTO.builder()
                        .id(wishlist.getId())
                        .bookId(wishlist.getBook().getId())
                        .bookImagePath(wishlist.getBook().getPath())
                        .bookTitle(wishlist.getBook().getTitle())
                        .author(wishlist.getBook().getAuthor().getName())
                        .createdAt(wishlist.getCreatedAt().format(formatter))
                        .build()).collect(Collectors.toList());

        return MyLibraryResponseDTO.builder()
                .bookList(bookList)
                .wishList(wishList)
                .build();

    }

    // 중복확인
    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);

    }

    public boolean checkNickNameDuplicate(String nickName) {
        return userRepository.existsByNickName(nickName);
    }


}
