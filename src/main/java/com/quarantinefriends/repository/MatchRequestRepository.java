package com.quarantinefriends.repository;

import com.quarantinefriends.dto.MatchRequestDTO;
import com.quarantinefriends.entity.MatchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MatchRequestRepository extends JpaRepository<MatchRequest, Long> {
    @Query(value="select * from match_request where to_user_id=:userId", nativeQuery = true)
    List<MatchRequest> findMatchRequestsForUser(Long userId);

    @Query(value="select * from match_request where from_user_id=:userId",nativeQuery = true)
    List<MatchRequest> findMatchRequestsFromUser(Long userId);
}
