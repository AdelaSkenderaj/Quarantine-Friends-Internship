package com.quarantinefriends.repository;

import com.quarantinefriends.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
}
