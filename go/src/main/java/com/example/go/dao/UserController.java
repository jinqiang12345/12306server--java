package com.example.go.dao;

import com.example.go.service.TicketRepository;
import com.example.go.service.TrainRepository;
import com.example.go.service.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    TicketRepository ticketRepository;

    @ApiOperation(value="登陆", notes="")
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public Integer login(@RequestParam Long user, @RequestParam String password) {

        try {
            String pwd = userRepository.findByUser(user).getPassword();
            if(password.equals(pwd)) {
                return 1;
            }
            else {
                return 0;
            }
        }catch (Exception e) {
            return 0;
        }


    }

    @ApiOperation(value="注册", notes="")
    @RequestMapping(value="/signin", method = RequestMethod.POST)
    public Integer signin(@RequestParam Long user, @RequestParam String password) {
        try {
            if(userRepository.findByUser(user) == null) {
                userRepository.save(new User(user, password));
                return 1;
            }
            else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return 0;
        }
    }

    @ApiOperation(value="查询", notes="")
    @RequestMapping(value="/search", method = RequestMethod.POST)
    public List<Train> search(@RequestParam String start, @RequestParam String end) {
        try {
            List<Train> train = trainRepository.findByStartAndEnd(start, end);
            if(train.size() > 0) {
                return train;
            }
            else {
                return new ArrayList<Train>();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return new ArrayList<Train>();
        }
    }

    @ApiOperation(value="购票", notes="")
    @RequestMapping(value="/buy", method = RequestMethod.POST)
    @Transactional
    public Integer buy(@RequestParam String number, @RequestParam Long user) {
//        trainRepository.save(new Train("北京", "上海", "G767", "北京西--上海", "12212", "G"));
        try {

            if(ticketRepository.findByUserAndNumberAndState(user, number, 1) == null) {
                Train buy = trainRepository.findByNumber(number);
                if(buy.getTicket() > 0) {
                    ticketRepository.save(new Ticket(user, buy.getNumber(), buy.getTitle(), buy.getTime(), buy.getType(), 1));
                    trainRepository.updateticketreduce(buy.getId());
                    return 1;
                }
                else {
                    return 0;
                }
            }
            else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return 0;
        }
    }

    @ApiOperation(value="退票", notes="")
    @RequestMapping(value="/refund", method = RequestMethod.POST)
    @Transactional
    public Integer refund(@RequestParam String number, @RequestParam Long user) {
//        trainRepository.save(new Train("北京", "上海", "G767", "北京西--上海", "12212", "G"));
        try {
            Ticket ticket = ticketRepository.findByUserAndNumberAndState(user, number, 1);
            ticketRepository.updatestate(ticket.getId());
            Train t = trainRepository.findByNumber(number);
            trainRepository.updateticketadd(t.getId());
            return 1;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return 0;
        }
    }
}
