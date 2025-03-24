package com.hanshul.blog.service.impl;

import com.hanshul.blog.dto.UserDetailDto;
import com.hanshul.blog.entities.RoleEntity;
import com.hanshul.blog.entities.UserEntity;
import com.hanshul.blog.exceptions.ResourceNotFoundException;
import com.hanshul.blog.payloads.UserDetailRequestModel;
import com.hanshul.blog.repositories.RoleRepository;
import com.hanshul.blog.repositories.UserRepository;
import com.hanshul.blog.service.UserService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    /////////////////////////////////////////////////
    ///// VARIABLES
    /////////////////////////////////////////////////
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private RoleRepository roleRepository;

    /////////////////////////////////////////////////
    /////// CONSTRUCTOR
    /////////////////////////////////////////////////

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository,RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /////////////////////////////////////////////////
    /////// METHODS
    /////////////////////////////////////////////////

    @Override
    public ResponseEntity<BlogAppResponse> registerUser(UserDetailRequestModel requestModel) {
        Instant startTime = Instant.now();
        UserEntity userEntity = this.modelMapper.map(requestModel, UserEntity.class);
        Optional<RoleEntity> role  = this.roleRepository.findById(102);
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userEntity.getRoles().add(role.get());
        UserEntity createdUserDetails = this.userRepository.save(userEntity);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().request(requestModel).build()).data(Map.of("message",
                        String.format("user create successfully with id %s", createdUserDetails.getId())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BlogAppResponse> createUser(UserDetailRequestModel userDto) {
        LOGGER.debug("Inside createUser() method of UserServiceImpl");
        Instant startTime = Instant.now();
        UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        UserEntity createdUserDetails = this.userRepository.save(userEntity);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().request(userDto).build()).data(Map.of("message",
                        String.format("user create successfully with id %s", createdUserDetails.getId())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BlogAppResponse> updateUser(UserDetailRequestModel userDto, Integer userId) {
        LOGGER.debug("Inside updateUser() method of UserServiceImpl");
        Instant startTime = Instant.now();
        this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        // userEntity.setName(userDto.getName());
        // userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        // userEntity.setAbout(userDto.getAbout());
        this.userRepository.save(userEntity);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().request(userDto).build())
                .data(Map.of("message", String.format("User details successfully updated with id %s", userId))).build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getUserById(Integer userId) {
        LOGGER.debug("Inside getUserById() method of UserServiceImpl");
        Instant startTime = Instant.now();
        UserEntity userEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        UserDetailDto userDetails = this.modelMapper.map(userEntity, UserDetailDto.class);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime).data(userDetails)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getAllUsers() {
        Instant startTime = Instant.now();
        List<UserEntity> userEntity = this.userRepository.findAll();
        /*
         * Type listType = new TypeToken<List<UserDetailDto>>() {}.getType();
         * List<UserDetailDto> response = this.modelMapper.map(userEntity,listType);
         */
        List<UserDetailDto> response = userEntity.stream().map(this::mapUserEntityToUserDetailDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(BlogAppResponse.builder().success(true).starTime(startTime).data(response)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).build());
    }

    @Override
    public void deleteUser(Integer userId) {
        LOGGER.debug("Inside deleteUser() method of UserServiceImpl, userId : {}", userId);
        this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepository.deleteById(userId);
    }

    ///////////////////////////////////////////////////
    /////// PRIVATE METHODS
    ///////////////////////////////////////////////////

    private UserDetailDto mapUserEntityToUserDetailDto(UserEntity userEntity) {
        return this.modelMapper.map(userEntity, UserDetailDto.class);
    }
}
