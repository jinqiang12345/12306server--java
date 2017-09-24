package com.example.go.service;

import com.example.go.dao.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Transactional
    @Modifying
    @Query("update Ticket t set t.state = 0 where t.id=:id ")
    void updatestate(@Param("id") Long id);

    Ticket findByUserAndNumberAndState(Long user, String number, Integer state);
}
