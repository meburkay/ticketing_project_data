package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserName(String username);


    @Transactional//We use this at derive query and @Modifying annotation at native or jpql query. We use this annotations for important operations that have more than one step like here. We delete first from ui and after that from the db. When we use this annotations if any obstacle happends and the steps doesn't complete altogether is take back the operations to its old version. We can use this annotations at the class level too.
    void deleteByUserName(String username);

    //We create a method for retrieve the users according to their description. IgnoreCase is for ignoring the lower case of the arguments. By this method we can use it everywhere for retrieving according to roles of the users.
    List<User> findByRoleDescriptionIgnoreCase(String description);
}
