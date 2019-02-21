package com.ibm.mentor.trainingservice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.mentor.trainingservice.model.Mentor;
import com.ibm.mentor.trainingservice.model.TrainingStatus;
import com.ibm.mentor.trainingservice.model.Trainings;
import com.ibm.mentor.trainingservice.repository.MentorRepository;
import com.ibm.mentor.trainingservice.repository.TrainingRepository;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
	@Autowired
	TrainingRepository trainingRepository;	
	
	@Autowired
	MentorRepository mentorRepository;	
//	0 0 12 * * ?
	@Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void scheduleTaskWithCronExpression() {
        logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        List<Trainings> trainingList = trainingRepository.findByStatus(TrainingStatus.STARTED);
        logger.info("Cron Task :: Found trainings in progress - {}", trainingList.size());
        for (Trainings trainings : trainingList) {
        	if (trainings.getProgress() < 100) {
        		trainings.setProgress(trainings.getProgress() + 5);
        		trainingRepository.save(trainings);
        		if (trainings.getProgress() == 100) {
    				//send payment to mentor
            		trainings.setStatus(TrainingStatus.COMPLETED);
            		trainingRepository.save(trainings);
            		Mentor mentor = mentorRepository.getOne(trainings.getMentor().getId());
            		mentor.setTrainingsDelivered(mentor.getTrainingsDelivered() + 1);
            		mentorRepository.save(mentor);
            	} else if (trainings.getProgress() == 25 || trainings.getProgress() == 50 || trainings.getProgress() == 75) {
            		//send payment to mentor
            	}
        	}      	
		}
    }

}
