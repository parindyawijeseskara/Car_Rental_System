package lk.ijse.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class DocumentType {
    @Id
    private int documentTypeId;
    private String documentType;
//
//    @OneToMany(mappedBy = "document",cascade = CascadeType.ALL)
//    private List<Document>document;


}
