package lk.ijse.spring.service;

import lk.ijse.spring.dto.CarTypeDTO;
import lk.ijse.spring.dto.RentalRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RentalRequestService {

    public void saveRentalRequest(List<MultipartFile> file,RentalRequestDTO rentalRequestDTO);

    public void deleteRentalRequest(Integer rentalRequestId);

    public void updateRentalRequest(RentalRequestDTO rentalRequestDTO);

    public RentalRequestDTO searchRentalRequest(Integer rentalRequestId);

    List<RentalRequestDTO> getAllRequests();

    List<RentalRequestDTO>getAllPendingRequests();

    public double getLossDamageAmount(CarTypeDTO carTypeDTOS);

    public double getRentalFeeToPay(CarTypeDTO carTypeDTOS);
}
