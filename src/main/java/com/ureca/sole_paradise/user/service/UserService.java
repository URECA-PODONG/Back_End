package com.ureca.sole_paradise.user.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.ureca.sole_paradise.user.config.ReferencedWarning;
import com.ureca.sole_paradise.user.db.dto.KakaoResponse;
import com.ureca.sole_paradise.user.db.dto.SocialUserDTO;
import com.ureca.sole_paradise.user.db.dto.UserDTO;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.user.db.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> findAll() {
        final List<UserEntity> users = userRepository.findAll(Sort.by("userId"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Integer userId) throws NotFoundException {
        return userRepository.findById(userId)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserDTO userDTO) {
        final UserEntity user = new UserEntity();
        mapToEntity(userDTO, user);
        log.info("Creating user with email: {}", user.getAccountEmail());
        return userRepository.save(user).getUserId();
    }

    //반환타입                       // 재료
    public SocialUserDTO getSocialInfo(KakaoResponse kakaoResponse) {
        SocialUserDTO kakaoInfoRes = new SocialUserDTO();
        kakaoInfoRes.setSocialEmail(kakaoResponse.getEmail());
        return kakaoInfoRes;
    }

    public void update(final Integer userId, final UserDTO userDTO) throws NotFoundException {
        final UserEntity user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        log.info("Updating user with ID: {}", userId);
        userRepository.save(user);
    }

    public void delete(final Integer userId) {
        log.info("Deleting user with ID: {}", userId);
        userRepository.deleteById(userId);
    }

    private UserDTO mapToDTO(final UserEntity user, final UserDTO userDTO) {
        userDTO.setUserId(user.getUserId());
        userDTO.setAccountEmail(user.getAccountEmail());
        userDTO.setProfileNickname(user.getProfileNickname());
        userDTO.setUserPicture(user.getUserPicture());
        userDTO.setNickname(user.getNickname());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }

    private UserEntity mapToEntity(final UserDTO userDTO, final UserEntity user) {
        user.setAccountEmail(userDTO.getAccountEmail());
        user.setProfileNickname(userDTO.getProfileNickname());
        user.setUserPicture(userDTO.getUserPicture());
        user.setNickname(userDTO.getNickname());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setHealth(userDTO.isHealth());
        user.setPetCare(userDTO.isPetCare());
        user.setMissing(userDTO.isMissing());
        return user;
    }

    public ReferencedWarning getReferencedWarning(final Integer userId) throws NotFoundException {
        final UserEntity user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        // 경고 로직 구현 또는 필요 없다면 제거
        return null;
    }
}
