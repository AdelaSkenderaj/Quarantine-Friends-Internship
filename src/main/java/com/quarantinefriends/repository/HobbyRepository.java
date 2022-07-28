package com.quarantinefriends.repository;

import com.quarantinefriends.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    @Query(value="SELECT (SELECT COUNT(hobby_id) FROM user_hobby WHERE user_hobby.hobby_id = hobby.id) AS TOT FROM hobby", nativeQuery = true)
    List<Long> findHobbyStatistics();
}
