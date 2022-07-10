package com.fmi.wdj.booklibrary;

import com.fmi.wdj.booklibrary.model.book.BookGenre;
import com.fmi.wdj.booklibrary.model.book.Genre;
import com.fmi.wdj.booklibrary.model.user.User;
import com.fmi.wdj.booklibrary.model.user.UserInfo;
import com.fmi.wdj.booklibrary.repository.book.BookGenreRepository;
import com.fmi.wdj.booklibrary.repository.user.UserRepository;
import com.fmi.wdj.booklibrary.security.PasswordEncoderConfig;
import com.fmi.wdj.booklibrary.security.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class BookLibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookLibraryApplication.class, args);
    }

    // CHANGE LATER !!!!!!
    // initialize the genres with all values of the Genre enum
    @Autowired
    private BookGenreRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    @PostConstruct
    void init() {
        if (bookRepository.findAll().isEmpty()) {
            Arrays.stream(Genre.values()).forEach(g -> bookRepository.save(new BookGenre(g)));
        }
        if (!userRepository.existsById("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoderConfig.passwordEncoder().encode("pass"));
            admin.setRole(Role.ADMIN);
            admin.setIsEnabled(true);
            UserInfo adminInfo = new UserInfo();
            adminInfo.setEmail("admin@admin.admin");
            admin.setInfo(adminInfo);
            userRepository.save(admin);
        }
    }
}
