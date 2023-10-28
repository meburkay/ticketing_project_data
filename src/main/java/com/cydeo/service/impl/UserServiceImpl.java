package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        //We can sort the data like this.
        List<User> userList = userRepository.findAll(Sort.by("firstName"));

        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());

    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDto(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO user) {

        userRepository.save(userMapper.convertToEntity(user));

    }

    @Override
    public void deleteByUserName(String username) {

        userRepository.deleteByUserName(username);

    }

    @Override
    public UserDTO update(UserDTO user) {

        //Here to update user first we take the user entity from database because we need to know the primary key means id of the user that we will update. Then we convert our updated UserDTO to entity and it still doesn't have any id. If we save like that it will be saved like a new user. Because of that before we save it, we set its id variable and then we save it.
        //Find current user
        User user1 = userRepository.findByUserName(user.getUserName());  //has id
        //Map update user dto to entity object
        User convertedUser = userMapper.convertToEntity(user);   // has id?
        //set id to the converted object
        convertedUser.setId(user1.getId());
        //save the updated user in the db
        userRepository.save(convertedUser);

        return findByUserName(user.getUserName());
    }

    //We create this method for delete operations instead of actual delete method. Here we did not delete but instead we change the user's isDelete part to true then save.
    @Override
    public void delete(String username) {

        User user = userRepository.findByUserName(username);
        user.setIsDeleted(true);
        userRepository.save(user);

    }

}
