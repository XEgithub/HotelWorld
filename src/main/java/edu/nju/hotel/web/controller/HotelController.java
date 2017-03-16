package edu.nju.hotel.web.controller;

import edu.nju.hotel.data.model.Checkout;
import edu.nju.hotel.logic.service.HotelService;
import edu.nju.hotel.logic.vo.*;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouxiaofan on 2017/1/26.
 */
@Controller
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    HotelService hotelService;
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("hotel");
        return "redirect:/";
    }

    @GetMapping("/account")
    public String account(Model model,HttpSession session) {
        int id= (int) session.getAttribute("hotelid");
        HotelVO h=hotelService.getHotelById(id);
        model.addAttribute("hotelInfo",h);
        return "hotels/account";
    }

    @RequestMapping("/index")
    public String bookList(Model model,HttpSession session) {
        int hotelId= (int) session.getAttribute("hotelid");

        List<BookingVO> bookingVOS=hotelService.getBookingNow(hotelId);
        model.addAttribute("bookingList",bookingVOS);
        return "hotels/bookList";
    }

    @GetMapping("/checkout")
    public String checkoutList(Model model,HttpSession session) {
        int hotelId= (int) session.getAttribute("hotelid");

        List<CheckinVO> checkinVOS=hotelService.getCheckinList(hotelId);
        return "hotels/checkout";
    }

    @PostMapping("/checkoutPost")
    public @ResponseBody Object checkoutPost(){
        ModelMap map=new ModelMap();
        return map;
    }

    @GetMapping("/checkin")
    public String checkinPage(){
        return "hotels/checkin";
    }

    @PostMapping("/checkinPost")
    public @ResponseBody Object checkinPost(@RequestParam("bookingId") int bookingId,
                              @RequestParam("payType") int payType,
                              @RequestParam("idCards") String idCards){
        ModelMap result=hotelService.checkinBooking(bookingId,payType,idCards);

        return result;
    }

    @PostMapping("/newCheckin")
    public @ResponseBody Object handleBooking(@RequestBody CheckinListVO checkinList,
                                              HttpSession session){
        ModelMap result=new ModelMap();

        ModelMap modelMap=hotelService.newCheckin(checkinList);
        return modelMap;
    }

    @PostMapping("/handleCheckin")
    public @ResponseBody String handleCheckin(HttpServletRequest request){
        return "success";
    }

    @RequestMapping("/handleCheckout")
    public @ResponseBody String handleCheckout(){
        return "success";
    }

    @GetMapping("/plan")
    public String plan(Model model,HttpSession session) {
        int hotelid=(Integer) session.getAttribute("hotelid");
        List<RoomTypeVO> roomTypeList=hotelService.getRoomType(hotelid);
        List<PlanVO> planList=hotelService.getPlan(hotelid);
        model.addAttribute("roomTypes",roomTypeList);
        model.addAttribute("planList",planList);

        return "hotels/plan";
    }

    @GetMapping("/getRoom")
    public @ResponseBody Object getRoom(HttpSession session) {
        ModelMap model=new ModelMap();
        int hotelid=(Integer) session.getAttribute("hotelid");

        ModelMap roomList=hotelService.getRoom(hotelid);

        model.addAttribute("roomList",roomList);
        return roomList;
    }

    @RequestMapping("/history")
    public String history(Model model) {
        //所有历史记录
        return "hotels/history";
    }
    @RequestMapping("/summary")
    public String summary(Model model) {
        //所有历史记录
        return "hotels/summary";
    }
}
