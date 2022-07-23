package com.quarantinefriends.repository;

import com.quarantinefriends.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface HobbyRepository extends JpaRepository<Hobby, Long> {
}
