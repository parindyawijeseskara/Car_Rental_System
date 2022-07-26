package lk.ijse.spring.repo;

import lk.ijse.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByUserNameAndPassword(String userName, String password);
    User findByUserId(Integer userId);
    User findByNic(String nic);
}
