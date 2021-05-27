package com.sample.problem.parkingrates.repository;

import com.sample.problem.parkingrates.model.Rates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatesRepository extends JpaRepository<Rates, Long> {

  @Query("SELECT r FROM Rates r WHERE days LIKE %:dayOfWeek%")
  List<Rates> findByDaysAndTimes(@Param("dayOfWeek") String dayOfWeek);
}
