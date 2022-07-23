package com.quarantinefriends.repository;

import com.quarantinefriends.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
