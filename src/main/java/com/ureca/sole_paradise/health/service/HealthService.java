package com.ureca.sole_paradise.health.service;

import java.util.List;
import com.ureca.sole_paradise.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ureca.sole_paradise.health.db.dto.HealthDTO;
import com.ureca.sole_paradise.health.db.entity.HealthEntity;
import com.ureca.sole_paradise.health.db.repository.HealthRepository;
import com.ureca.sole_paradise.pet.db.entity.PetEntity;
import com.ureca.sole_paradise.pet.db.repository.PetRepository;

@Service
public class HealthService {
	
	  private final HealthRepository healthRepository;
	    private final PetRepository petRepository;

	    public HealthService(final HealthRepository healthRepository,
	            final PetRepository petRepository) {
	        this.healthRepository = healthRepository;
	        this.petRepository = petRepository;
	    }

	    public List<HealthDTO> findAll() {
	        final List<HealthEntity> healths = healthRepository.findAll(Sort.by("healthId"));
	        return healths.stream()
	                .map(health -> mapToDTO(health, new HealthDTO()))
	                .toList();
	    }

	    public HealthDTO get(final Integer healthId) {
	        return healthRepository.findById(healthId)
	                .map(health -> mapToDTO(health, new HealthDTO()))
	                .orElseThrow(NotFoundException::new);
	    }

	    public Integer create(final HealthDTO healthDTO) {
	        final HealthEntity health = new HealthEntity();
	        mapToEntity(healthDTO, health);
	        return healthRepository.save(health).getHealthId();
	    }

	    public void update(final Integer healthId, final HealthDTO healthDTO) {
	        final HealthEntity health = healthRepository.findById(healthId)
	                .orElseThrow(NotFoundException::new);
	        mapToEntity(healthDTO, health);
	        healthRepository.save(health);
	    }

	    public void delete(final Integer healthId) {
	        healthRepository.deleteById(healthId);
	    }

	    private HealthDTO mapToDTO(final HealthEntity health, final HealthDTO healthDTO) {
	        healthDTO.setHealthId(health.getHealthId());
	        healthDTO.setVisitedDate(health.getVisitedDate());
	        healthDTO.setNotes(health.getNotes());
	        healthDTO.setHealthDate(health.getHealthDate());
	        healthDTO.setNextCheckupDate(health.getNextCheckupDate());
	        healthDTO.setCreatedAt(health.getCreatedAt());
	        healthDTO.setUpdatedAt(health.getUpdatedAt());
	        healthDTO.setAlarmStatus(health.getAlarmStatus());
	        healthDTO.setPet(health.getPet() == null ? null : health.getPet().getPetId());
	        return healthDTO;
	    }

	    private HealthEntity mapToEntity(final HealthDTO healthDTO, final HealthEntity health) {
	        health.setVisitedDate(healthDTO.getVisitedDate());
	        health.setNotes(healthDTO.getNotes());
	        health.setHealthDate(healthDTO.getHealthDate());
	        health.setNextCheckupDate(healthDTO.getNextCheckupDate());
	        health.setCreatedAt(healthDTO.getCreatedAt());
	        health.setUpdatedAt(healthDTO.getUpdatedAt());
	        health.setAlarmStatus(healthDTO.getAlarmStatus());
	        final PetEntity pet = healthDTO.getPet() == null ? null : petRepository.findById(healthDTO.getPet())
	                .orElseThrow(() -> new NotFoundException("pet not found"));
	        health.setPet(pet);
	        return health;
	    }

	}
