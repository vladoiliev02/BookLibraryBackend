package com.fmi.wdj.booklibrary.repository.user;

import com.fmi.wdj.booklibrary.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
