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

    @Query(value="SELECT (SELECT COUNT(preference_id) FROM user_preference WHERE user_preference.preference_id = preference.id) AS TOT FROM preference", nativeQuery = true)
    List<Long> findPreferenceStatistics();
}
