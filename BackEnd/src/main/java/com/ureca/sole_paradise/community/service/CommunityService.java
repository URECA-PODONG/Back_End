package com.ureca.sole_paradise.community.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ureca.sole_paradise.community.db.dto.CommunityDTO;
import com.ureca.sole_paradise.community.db.entity.CommunityCommentEntity;
import com.ureca.sole_paradise.community.db.entity.CommunityEntity;
import com.ureca.sole_paradise.community.db.repository.CommunityCommentRepository;
import com.ureca.sole_paradise.community.db.repository.CommunityRepository;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.user.db.repository.UserRepository;
import com.ureca.sole_paradise.util.NotFoundException;
import com.ureca.sole_paradise.util.ReferencedWarning;



@Service
public class CommunityService {


    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CommunityCommentRepository communityCommentRepository;

    public CommunityService(final CommunityRepository communityRepository,
                            final UserRepository userRepository,
                            final CommunityCommentRepository communityCommentRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.communityCommentRepository = communityCommentRepository;
    }

    public List<CommunityDTO> findAll() {
        final List<CommunityEntity> communityEntites = communityRepository.findAll(Sort.by("postId"));
        return communityEntites.stream()
                .map(communityEntity -> mapToDTO(communityEntity, new CommunityDTO()))
                .toList();
    }

    public CommunityDTO get(final Integer postId) {
        return communityRepository.findById(postId)
                .map(communityEntity -> mapToDTO(communityEntity, new CommunityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CommunityDTO communityDTO) {
        final CommunityEntity community = new CommunityEntity();
        mapToEntity(communityDTO, community);
        return communityRepository.save(community).getPostId();
    }

    public void update(final Integer postId, final CommunityDTO communityDTO) {
        final CommunityEntity community = communityRepository.findById(postId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(communityDTO, community);
        communityRepository.save(community);
    }

    public void delete(final Integer postId) {
        communityRepository.deleteById(postId);
    }

    private CommunityDTO mapToDTO(final CommunityEntity communityEntity, final CommunityDTO communityDTO) {
        communityDTO.setPostId(communityEntity.getPostId());
        communityDTO.setTitle(communityEntity.getTitle());
        communityDTO.setContents(communityEntity.getContents());
        communityDTO.setImageUrl(communityEntity.getImageUrl());
        communityDTO.setGood(communityEntity.getGood());
        communityDTO.setCategory(communityEntity.getCategory());
        communityDTO.setUser(communityEntity.getUser() == null ? null : communityEntity.getUser().getUserId());
        return communityDTO;
    }

    private CommunityEntity mapToEntity(final CommunityDTO communityDTO, final CommunityEntity community) {
        community.setTitle(communityDTO.getTitle());
        community.setContents(communityDTO.getContents());
        community.setGood(communityDTO.getGood());
        community.setImageUrl(communityDTO.getImageUrl());
        community.setCategory(communityDTO.getCategory());
        final UserEntity user = communityDTO.getUser() == null ? null : userRepository.findById(communityDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        community.setUser(user);
        return community;
    }

    public ReferencedWarning getReferencedWarning(final Integer postId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final CommunityEntity community = communityRepository.findById(postId)
                .orElseThrow(NotFoundException::new);
        final CommunityCommentEntity postCommunityComment = communityCommentRepository.findFirstByPost(community);
        if (postCommunityComment != null) {
            referencedWarning.setKey("community.communityComment.post.referenced");
            referencedWarning.addParam(postCommunityComment.getCommunityCommentId());
            return referencedWarning;
        }
        return null;
    }


}
