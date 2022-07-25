package com.quarantinefriends.repository;

import com.quarantinefriends.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Query(value="select * from user where account_terminated=false",nativeQuery = true)
    List<User> findEnabledUsers();
}
