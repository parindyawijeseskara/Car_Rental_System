package lk.ijse.spring.repo;

import lk.ijse.spring.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document,Integer> {

}
