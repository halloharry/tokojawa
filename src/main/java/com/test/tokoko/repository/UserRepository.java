package com.test.tokoko.repository;

import com.test.tokoko.model.Product;
import com.test.tokoko.model.User;
import com.test.tokoko.module.user.payload.AuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByIdAndIsDeleted(Long id, Integer isDeleted);

}
