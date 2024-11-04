package com.ureca.sole_paradise.missing.service;

import java.util.List;

import com.ureca.sole_paradise.walkRoute.db.entity.WalkRouteEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ureca.sole_paradise.missing.db.dto.MissingDTO;
import com.ureca.sole_paradise.missing.db.entity.MissingEntity;
import com.ureca.sole_paradise.missing.db.repository.MissingRepository;
import com.ureca.sole_paradise.pet.db.entity.PetEntity;
import com.ureca.sole_paradise.pet.db.repository.PetRepository;
import com.ureca.sole_paradise.util.NotFoundException;
import com.ureca.sole_paradise.walkRoute.db.repository.WalkRouteRepository;

@Service
public class MissingService {
	private final MissingRepository missingRepository;
    private final PetRepository petRepository;
    private final WalkRouteRepository walkRouteRepository;

    public MissingService(final MissingRepository missingRepository,
            final PetRepository petRepository, final WalkRouteRepository walkRouteRepository) {
        this.missingRepository = missingRepository;
        this.petRepository = petRepository;
        this.walkRouteRepository = walkRouteRepository;
    }

    public List<MissingDTO> findAll() {
        final List<MissingEntity> missings = missingRepository.findAll(Sort.by("missingId"));
        return missings.stream()
                .map(missing -> mapToDTO(missing, new MissingDTO()))
                .toList();
    }

    public MissingDTO get(final Integer missingId) {
        return missingRepository.findById(missingId)
                .map(missing -> mapToDTO(missing, new MissingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MissingDTO missingDTO) {
        final MissingEntity missing = new MissingEntity();
        mapToEntity(missingDTO, missing);
        return missingRepository.save(missing).getMissingId();
    }

    public void update(final Integer missingId, final MissingDTO missingDTO) {
        final MissingEntity missing = missingRepository.findById(missingId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(missingDTO, missing);
        missingRepository.save(missing);
    }

    public void delete(final Integer missingId) {
        missingRepository.deleteById(missingId);
    }

    private MissingDTO mapToDTO(final MissingEntity missing, final MissingDTO missingDTO) {
        missingDTO.setMissingId(missing.getMissingId());
        missingDTO.setAlarmName(missing.getAlarmName());
        missingDTO.setLocation(missing.getLocation());
        missingDTO.setAlertRadiusKm(missing.getAlertRadiusKm());
        missingDTO.setMissingDate(missing.getMissingDate());
        missingDTO.setMissingDetails(missing.getMissingDetails());
        missingDTO.setMissingStatus(missing.getMissingStatus());
        missingDTO.setAlarmPicture(missing.getAlarmPicture());
        missingDTO.setContactNumber(missing.getContactNumber());
        missingDTO.setContactNumber(missing.getContactNumber());
        missingDTO.setPet(missing.getPet() == null ? null : missing.getPet().getPetId());
        missingDTO.setWalkroute(missing.getWalkroute() == null ? null : missing.getWalkroute().getWalkrouteId());
        return missingDTO;
    }

    private MissingEntity mapToEntity(final MissingDTO missingDTO, final MissingEntity missing) {
        missing.setAlarmName(missingDTO.getAlarmName());
        missing.setLocation(missingDTO.getLocation());
        missing.setAlertRadiusKm(missingDTO.getAlertRadiusKm());
        missing.setMissingDate(missingDTO.getMissingDate());
        missing.setMissingDetails(missingDTO.getMissingDetails());
        missing.setContactNumber(missingDTO.getContactNumber());
        missing.setMissingStatus(missingDTO.getMissingStatus());
        missing.setAlarmPicture(missingDTO.getAlarmPicture());
        missing.setContactNumber(missingDTO.getContactNumber());
        final PetEntity pet = missingDTO.getPet() == null ? null : petRepository.findById(missingDTO.getPet())
                .orElseThrow(() -> new NotFoundException("pet not found"));
        missing.setPet(pet);
        final WalkRouteEntity walkroute = missingDTO.getWalkroute() == null ? null : walkRouteRepository.findById(missingDTO.getWalkroute())
                .orElseThrow(() -> new NotFoundException("walkroute not found"));
        missing.setWalkroute(walkroute);
        return missing;
    }

}