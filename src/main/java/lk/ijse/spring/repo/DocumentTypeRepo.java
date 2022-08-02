package lk.ijse.spring.repo;

import lk.ijse.spring.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentTypeRepo extends JpaRepository<DocumentType,Integer> {
    DocumentType findByDocumentTypeId(Integer documentTypeId);

}
