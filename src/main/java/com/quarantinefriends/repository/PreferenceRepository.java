package com.quarantinefriends.repository;

import com.quarantinefriends.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
