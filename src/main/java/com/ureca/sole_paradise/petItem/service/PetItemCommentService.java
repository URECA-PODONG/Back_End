package com.ureca.sole_paradise.petItem.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ureca.sole_paradise.petItem.db.dto.PetItemCommentDTO;
import com.ureca.sole_paradise.petItem.db.entity.PetItemCommentEntity;
import com.ureca.sole_paradise.petItem.db.entity.PetItemEntity;
import com.ureca.sole_paradise.petItem.db.repository.PetItemCommentRepository;
import com.ureca.sole_paradise.petItem.db.repository.PetItemRepository;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.user.db.repository.UserRepository;
import com.ureca.sole_paradise.util.NotFoundException;


@Service
public class PetItemCommentService {


	private final PetItemCommentRepository petItemCommentRepository;
	private final PetItemRepository petItemRepository;
	private final UserRepository userRepository;

	public PetItemCommentService(final PetItemCommentRepository petItemCommentRepository,
								 final PetItemRepository petItemRepository, final UserRepository userRepository) {
		this.petItemCommentRepository = petItemCommentRepository;
		this.petItemRepository = petItemRepository;
		this.userRepository = userRepository;
	}

	public List<PetItemCommentDTO> findAll() {
		final List<PetItemCommentEntity> petItemCommentEntites = petItemCommentRepository.findAll(Sort.by("petItemCommentId"));
		return petItemCommentEntites.stream()
				.map(petItemCommentEntity -> mapToDTO(petItemCommentEntity, new PetItemCommentDTO()))
				.toList();
	}

	public PetItemCommentDTO get(final Integer petItemCommentId) {
		return petItemCommentRepository.findById(petItemCommentId)
				.map(petItemCommentEntity -> mapToDTO(petItemCommentEntity, new PetItemCommentDTO()))
				.orElseThrow(NotFoundException::new);
	}

	public Integer create(final PetItemCommentDTO petItemCommentDTO) {
		final PetItemCommentEntity petItemCommentEntity = new PetItemCommentEntity();
		mapToEntity(petItemCommentDTO, petItemCommentEntity);
		return petItemCommentRepository.save(petItemCommentEntity).getPetItemCommentId();
	}

	public void update(final Integer petItemCommentId, final PetItemCommentDTO petItemCommentDTO) {
		final PetItemCommentEntity petItemCommentEntity = petItemCommentRepository.findById(petItemCommentId)
				.orElseThrow(NotFoundException::new);
		mapToEntity(petItemCommentDTO, petItemCommentEntity);
		petItemCommentRepository.save(petItemCommentEntity);
	}

	public void delete(final Integer petItemCommentId) {
		petItemCommentRepository.deleteById(petItemCommentId);
	}

	private PetItemCommentDTO mapToDTO(final PetItemCommentEntity petItemCommentEntity,
									   final PetItemCommentDTO petItemCommentDTO) {
		petItemCommentDTO.setPetItemCommentId(petItemCommentEntity.getPetItemCommentId());
		petItemCommentDTO.setComment(petItemCommentEntity.getComment());
		petItemCommentDTO.setPetItem(petItemCommentEntity.getPetItem() == null ? null : petItemCommentEntity.getPetItem().getPetItemId());
		petItemCommentDTO.setUser(petItemCommentEntity.getUser() == null ? null : petItemCommentEntity.getUser().getUserId());
		return petItemCommentDTO;
	}

	private PetItemCommentEntity mapToEntity(final PetItemCommentDTO petItemCommentDTO,
											 final PetItemCommentEntity petItemComment) {
		petItemComment.setComment(petItemCommentDTO.getComment());
		final PetItemEntity petItem = petItemCommentDTO.getPetItem() == null ? null : petItemRepository.findById(petItemCommentDTO.getPetItem())
				.orElseThrow(() -> new NotFoundException("petItem not found"));
		petItemComment.setPetItem(petItem);
		final UserEntity user = petItemCommentDTO.getUser() == null ? null : userRepository.findById(petItemCommentDTO.getUser())
				.orElseThrow(() -> new NotFoundException("user not found"));
		petItemComment.setUser(user);
		return petItemComment;
	}
}
