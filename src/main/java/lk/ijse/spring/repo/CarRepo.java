package lk.ijse.spring.repo;

import lk.ijse.spring.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepo extends JpaRepository<Car,Integer> {

    List<Car> findByNoOfPassengers(String noOfPassengers);

    List<Car> findByTransmissionType(String transmissionType);

    List<Car> findByBrand(String brand);

    List<Car> findByType(String type);

    List<Car> findByFuelType(String fuelType);

    Car findByCarId(Integer Id);

    List<Car> findByNoOfPassengersOrTransmissionTypeOrBrandOrTypeOrFuelType(String noOfPassangers,String transmission,String Brand,String type,String fuelType);

}
