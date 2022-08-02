package lk.ijse.spring.repo;

import lk.ijse.spring.entity.RentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RentalRequestRepo extends JpaRepository<RentalRequest,Integer> {
    RentalRequest findByRentalRequestId(Integer rentalRequestId);

    @Query(value = "SELECT *\n" +
            "FROM   RentalRequest\n" +
            "WHERE  RentalRequest.pickUpDate BETWEEN ? AND ? OR RentalRequest.returnDate BETWEEN ? AND ?", nativeQuery = true)
    List<RentalRequest> findAllByPickUpDateBetweenOrReturnDateBetween(String fromDate1,String toDate1,String fromDate2,String toDate2);

    List<RentalRequest> getAllByStatus(String status);





}
