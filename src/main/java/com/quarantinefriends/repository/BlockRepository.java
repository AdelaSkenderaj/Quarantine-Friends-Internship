package com.quarantinefriends.repository;

import com.quarantinefriends.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BlockRepository extends JpaRepository<Block, Long> {

}
