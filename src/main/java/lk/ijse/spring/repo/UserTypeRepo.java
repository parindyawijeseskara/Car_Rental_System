package lk.ijse.spring.repo;

import lk.ijse.spring.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepo extends JpaRepository<UserType,Integer> {
    UserType findByUserTypeId(Integer userTypeId);

}
