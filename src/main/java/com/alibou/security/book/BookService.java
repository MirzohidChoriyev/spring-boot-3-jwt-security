package com.alibou.security.book;

import com.alibou.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final UserRepository userRepository;

    public ResponseEntity<?> save(BookRequest request) {
        var book = Book.builder()
                .id(request.getId())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .createdBy(userRepository.findByEmail(request.getEmail()).get().getId())
                .build();
        return ResponseEntity.ok(repository.save(book));
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public ResponseEntity<?> delete(Integer id){
        Book book = repository.findById(id).orElse(null);
        if (book == null){
            return ResponseEntity.ok()
                    .header(HttpStatus.BAD_REQUEST.name())
                    .body("ID: " + id + " not found");
        }
        repository.deleteById(id);
        return ResponseEntity.ok(book);
    }
}
