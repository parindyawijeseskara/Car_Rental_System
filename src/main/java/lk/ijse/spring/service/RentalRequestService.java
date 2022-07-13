package lk.ijse.spring.service;

import lk.ijse.spring.dto.RentalRequestDTO;

import java.util.List;

public interface RentalRequestService {

    public void saveRentalRequest(RentalRequestDTO rentalRequestDTO);

    public void deleteRentalRequest(Integer rentalRequestId);

    public void updateRentalRequest(RentalRequestDTO rentalRequestDTO);

    public RentalRequestDTO searchRentalRequest(Integer rentalRequestId);

    List<RentalRequestDTO> getAllRequests();


}
