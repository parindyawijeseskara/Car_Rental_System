package lk.ijse.spring.repo;

import lk.ijse.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByUserNameAndPassword(String userName, String password);
    User findByUserNameAndPasswordAndUserTypeIdUserTypeId(String userName,String password,Integer userTypeId);


    User findByUserId(Integer userId);
    User findByNic(String nic);
    User findByUserName(String userName);

    //User findAllByUserTypeIdUserType
    List <User> findAllByUserTypeIdUserTypeId(Integer userTypeId);

}
