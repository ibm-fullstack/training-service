package com.ibm.mentor.trainingservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibm.mentor.trainingservice.model.TrainingStatus;
import com.ibm.mentor.trainingservice.model.Trainings;

@Repository
public interface TrainingRepository extends JpaRepository<Trainings, Long> {
    //List<Mentor> findByMentors(List skill);
	List<Trainings> findByMentorId(long mentorid);
	List<Trainings> findByUserId(long userid);
	List<Trainings> findByStatus(TrainingStatus status);
}
