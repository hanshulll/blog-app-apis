package com.hanshul.blog.service.impl;


import com.hanshul.blog.entities.UserEntity;
import com.hanshul.blog.exceptions.ResourceNotFoundException;
import com.hanshul.blog.payloads.UserDto;
import com.hanshul.blog.repositories.UserRepository;
import com.hanshul.blog.service.UserService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    /////////////////////////////////////////////////
    ///// VARIABLES
    /////////////////////////////////////////////////
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    /////////////////////////////////////////////////
    /////// CONSTRUCTOR
    /////////////////////////////////////////////////

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    /////////////////////////////////////////////////
    /////// METHODS
    /////////////////////////////////////////////////
    @Override
    public ResponseEntity<BlogAppResponse> createUser(UserDto userDto) {
        LOGGER.debug("Inside createUser() method of UserServiceImpl");
        Instant startTime = Instant.now();
        UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        UserEntity createdUserDetails = this.userRepository.save(userEntity);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().request(userDto).build())
                .data(createdUserDetails)
                .build();
//        return this.modelMapper.map(response, UserDto.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BlogAppResponse> updateUser(UserDto userDto, Integer userId) {
        LOGGER.debug("Inside updateUser() method of UserServiceImpl");
        Instant startTime = Instant.now();
        UserEntity userEntity = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setAbout(userDto.getAbout());
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().request(userDto).build())
                .data(this.modelMapper.map(this.userRepository.save(userEntity), UserDto.class))
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getUserById(Integer userId) {
        LOGGER.debug("Inside getUserById() method of UserServiceImpl");
        Instant startTime = Instant.now();
        UserEntity userEntity = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        UserDto userDetails = this.modelMapper.map(userEntity,UserDto.class);
        userDetails.setPassword(null);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .data(userDetails)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getAllUsers() {
        Instant startTime = Instant.now();
        List<UserEntity> userEntity = this.userRepository.findAll();
        List<UserDto> response = new ArrayList<>();
        userEntity.forEach(user->{
            UserDto userDto = new UserDto();
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setAbout(user.getAbout());
            userDto.setId(user.getId());
            response.add(userDto);
        });
//        for (UserEntity user : userEntity) {
//            UserDto userDto = UserDto.builder().name(user.getName()).email(user.getEmail()).about(user.getAbout()).id(user.getId()).build();

//        }
//        Below code can be used to map everything we got from DB to DTO but the only drawback here is that it will also map users passwords and will expose them in response
//        List<UserDto> response = userEntity.stream().map(userEntity1 -> modelMapper.map(userEntity1,UserDto.class)).toList();
        return ResponseEntity.ok(BlogAppResponse.builder().success(true).starTime(startTime)
                .data(response)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build())
                .build());
    }

    @Override
    public void deleteUser(Integer userId) {
        this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepository.deleteById(userId);
    }
}
