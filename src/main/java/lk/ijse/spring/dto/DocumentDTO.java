package lk.ijse.spring.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class DocumentDTO {
    private int documentId;
    private String name;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date uploadedOn;
    private DocumentTypeDTO documentType;
    private CarDTO car;
    private PaymentDTO payment;
    private UserDTO user;

}
