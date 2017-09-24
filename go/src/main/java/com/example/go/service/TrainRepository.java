package com.example.go.service;

import com.example.go.dao.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {
    List<Train> findByStartAndEnd(String start, String end);
    Train findByNumber(String number);
    @Transactional
    @Modifying
    @Query("update Train t set t.ticket = (t.ticket-1) where t.id=:id ")
    void updateticketreduce(@Param("id") Long id);
    @Transactional
    @Modifying
    @Query("update Train t set t.ticket = (t.ticket+1) where t.id=:id ")
    void updateticketadd(@Param("id") Long id);
}
