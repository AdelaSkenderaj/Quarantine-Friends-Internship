package com.quarantinefriends.repository;

import com.quarantinefriends.dto.MessageDTO;
import com.quarantinefriends.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value="select * from message where from_user_id=:friendOneId and to_user_id=:friendTwoId or from_user_id=:friendTwoId and to_user_id=:friendOneId order by id", nativeQuery = true)
    List<Message> findMessagesForFriends(Long friendOneId, Long friendTwoId);
}
