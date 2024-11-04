package com.ureca.sole_paradise.community.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ureca.sole_paradise.community.db.dto.CommunityCommentDTO;
import com.ureca.sole_paradise.community.db.entity.CommunityCommentEntity;
import com.ureca.sole_paradise.community.db.entity.CommunityEntity;
import com.ureca.sole_paradise.community.db.repository.CommunityCommentRepository;
import com.ureca.sole_paradise.community.db.repository.CommunityRepository;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.user.db.repository.UserRepository;
import com.ureca.sole_paradise.util.NotFoundException;



@Service
public class CommunityCommentService {

    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    public CommunityCommentService(final CommunityCommentRepository communityCommentRepository,
                                   final CommunityRepository communityRepository, final UserRepository userRepository) {
        this.communityCommentRepository = communityCommentRepository;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }

    public List<CommunityCommentDTO> findAll() {
        final List<CommunityCommentEntity> communityCommentEntites = communityCommentRepository.findAll(Sort.by("commentId"));
        return communityCommentEntites.stream()
                .map(communityCommentEntity -> mapToDTO(communityCommentEntity, new CommunityCommentDTO()))
                .toList();
    }

    public CommunityCommentDTO get(final Integer commentId) {
        return communityCommentRepository.findById(commentId)
                .map(communityCommentEntity -> mapToDTO(communityCommentEntity, new CommunityCommentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CommunityCommentDTO communityCommentDTO) {
        final CommunityCommentEntity communityCommentEntity = new CommunityCommentEntity();
        mapToEntity(communityCommentDTO, communityCommentEntity);
        return communityCommentRepository.save(communityCommentEntity).getCommunityCommentId();
    }

    public void update(final Integer commentId, final CommunityCommentDTO communityCommentDTO) {
        final CommunityCommentEntity communityCommentEntity = communityCommentRepository.findById(commentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(communityCommentDTO, communityCommentEntity);
        communityCommentRepository.save(communityCommentEntity);
    }

    public void delete(final Integer commentId) {
        communityCommentRepository.deleteById(commentId);
    }

    private CommunityCommentDTO mapToDTO(final CommunityCommentEntity communityCommentEntity,
                                         final CommunityCommentDTO communityCommentDTO) {
        communityCommentDTO.setCommunityCommentId(communityCommentEntity.getCommunityCommentId());
        communityCommentDTO.setComment(communityCommentEntity.getComment());
        communityCommentDTO.setPost(communityCommentEntity.getPost() == null ? null : communityCommentEntity.getPost().getPostId());
        communityCommentDTO.setUser(communityCommentEntity.getUser() == null ? null : communityCommentEntity.getUser().getUserId());
        return communityCommentDTO;
    }

    private CommunityCommentEntity mapToEntity(final CommunityCommentDTO communityCommentDTO,
                                               final CommunityCommentEntity communityCommentEntity) {
        communityCommentEntity.setComment(communityCommentDTO.getComment());
        final CommunityEntity post = communityCommentDTO.getPost() == null ? null : communityRepository.findById(communityCommentDTO.getPost())
                .orElseThrow(() -> new NotFoundException("post not found"));
        communityCommentEntity.setPost(post);
        final UserEntity user = communityCommentDTO.getUser() == null ? null : userRepository.findById(communityCommentDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        communityCommentEntity.setUser(user);
        return communityCommentEntity;
    }

}
