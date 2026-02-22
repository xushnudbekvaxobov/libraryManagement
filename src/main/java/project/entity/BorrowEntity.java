package project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.enums.BorrowStatus;

import java.time.LocalDate;

@Entity
@Table(name = "borrow")
@Getter
@Setter
public class BorrowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BorrowStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity bookEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
}
