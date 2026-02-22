package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.dto.responseDto.BorrowResponseDto;
import project.entity.BookEntity;
import project.entity.BorrowEntity;
import project.entity.UserEntity;
import project.enums.BorrowStatus;
import project.exception.AppBadException;
import project.mapper.BorrowMapper;
import project.repository.BookRepository;
import project.repository.BorrowRepository;
import project.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository repository;

    @Autowired
    private BorrowMapper mapper;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public Page<BorrowResponseDto> getAllBorrowByPage(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("borrow_date").ascending());
        Page<BorrowEntity> borrowPage = repository.findAll(pageRequest);
        return borrowPage.map(mapper::toBorrowResponseDto);
    }

    public BorrowResponseDto findById(Integer id){
        Optional<BorrowEntity> optionalEntity = repository.findById(id);
        if(optionalEntity.isEmpty()){
            throw new AppBadException("Borrow record not found");
        }
        return mapper.toBorrowResponseDto(optionalEntity.get());
    }

    public BorrowResponseDto borrowBook(Integer userId,Integer bookId){
        Optional<UserEntity> entity = userRepository.findById(userId);

        if(entity.isEmpty()){
            throw new AppBadException("User not found");
        }

        Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        if(optionalBookEntity.isEmpty()){
            throw new AppBadException("Book not found");
        }
        Optional<BorrowEntity> optionalBorrowEntity = repository.findByUserEntity_IdAndBookEntity_IdAndStatus(userId,bookId,BorrowStatus.BORROWED);
        if (optionalBorrowEntity.isPresent()) {
              throw new AppBadException("This book is already borrowed and not yet returned");
            }

        BookEntity bookEntity = optionalBookEntity.get();
        Integer availableCopies = bookEntity.getAvailableCopies();
        if(availableCopies == 0){
            throw  new AppBadException("No available copies");
        }
        bookEntity.setAvailableCopies(availableCopies - 1);

        BorrowEntity borrowEntity = new BorrowEntity();
        borrowEntity.setBorrowDate(LocalDate.now());
        borrowEntity.setReturnDate(null);
        borrowEntity.setStatus(BorrowStatus.BORROWED);
        borrowEntity.setBookEntity(bookEntity);
        borrowEntity.setUserEntity(entity.get());
        BorrowEntity savedEntity= repository.save(borrowEntity);
        bookRepository.save(bookEntity);
        return mapper.toBorrowResponseDto(savedEntity);
    }

    public BorrowResponseDto returnBook(Integer userId,Integer bookId){
        Optional<UserEntity> entity = userRepository.findById(userId);
        if(entity.isEmpty()){
            throw new AppBadException("User not found");
        }
        Optional<BorrowEntity> optionalBorrowEntity = repository.findByUserEntity_IdAndBookEntity_IdAndStatus(userId,bookId,BorrowStatus.BORROWED);
        if (optionalBorrowEntity.isEmpty()) {
            throw new AppBadException("This book was not borrowed or has already been returned");
        }
                BookEntity bookEntity = optionalBorrowEntity.get().getBookEntity();
                bookEntity.setAvailableCopies( bookEntity.getAvailableCopies() + 1);
                bookRepository.save(bookEntity);
                BorrowEntity borrowEntity = optionalBorrowEntity.get();
                borrowEntity.setReturnDate(LocalDate.now());
                borrowEntity.setStatus(BorrowStatus.RETURNED);
        BorrowEntity savedEntity= repository.save(borrowEntity);
        return mapper.toBorrowResponseDto(savedEntity);

    }
}
