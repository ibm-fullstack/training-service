package com.ibm.mentor.trainingservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibm.mentor.trainingservice.model.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
    Optional<Mentor> findByUsername(String username);
    Boolean existsByUsername(String username);
    List<Mentor> findBySkillsNameContainingAndStartTimeLessThanAndEndTimeGreaterThan(String name, int startTime, int endTime);
    
}
