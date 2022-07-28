package com.quarantinefriends.repository;

import com.quarantinefriends.dto.UserDTO;
import com.quarantinefriends.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Query(value="select * from user where account_terminated=false and role_id=1",nativeQuery = true)
    List<User> findEnabledUsers();

    @Query(value="select count(id) as number from user where account_terminated=true", nativeQuery = true)
    Long findNrBannedUsers();

    @Query(value="select count(friend_one_id) as number from friendships",nativeQuery = true)
    Long findNrOfFriendships();

    @Query(value="select * from user where account_terminated=true",nativeQuery = true)
    List<User> findBannedUsers();

    @Modifying
    @Query(value="delete from user_block where blocked_user_id=:userId", nativeQuery = true)
    void removeFromBlockedUsers(Long userId);

    @Query(value="select user.id from match_request inner join user on user.id=match_request.to_user_id where from_user_id=:userId", nativeQuery = true)
    List<User> findRequestedUsers(Long userId);

    @Modifying
    @Query(value="delete from friendships where friend_one_id=:userId or friend_two_id=:userId", nativeQuery = true)
    void removeFromFriendships(Long userId);

    @Query(value="select user.id from user_block inner join user on user.id=user_block.user_id where blocked_user_id=:userId",nativeQuery = true)
    List<User> findBlockingUsers(Long userId);
}
