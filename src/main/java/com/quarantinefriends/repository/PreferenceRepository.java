package com.quarantinefriends.repository;

import com.quarantinefriends.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    @Query(value="select count(preference_id) as number from user_preference group by preference_id", nativeQuery = true)
    List<Long> findPreferenceStatistics();
}
