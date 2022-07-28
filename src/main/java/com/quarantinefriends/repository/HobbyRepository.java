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
    @Query(value="select count(hobby_id) as number from user_hobby group by hobby_id", nativeQuery = true)
    List<Long> findHobbyStatistics();
}
