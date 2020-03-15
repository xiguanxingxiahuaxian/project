package com.shenl.project.controller;

import com.shenl.project.bean.User;
import com.shenl.project.bean.VipUser;
import com.shenl.project.respository.UserRepository;
import com.shenl.project.respository.VipUserRepository;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VipUserRepository vipUserRepository;

    @GetMapping(value = "/getUserList")
    public Map<String, Object> getUserList() {
         return  Global.getMap(userRepository.findAll());
    }

    @PostMapping(value = "/AddUser")
    public Map<String, Object> AddUser(@RequestParam("userName") String userName,
                                       @RequestParam("realName") String realName,
                                       @RequestParam("password") String password,
                                       @RequestParam("eMail") String eMail,
                                       @RequestParam("tel") String tel,
                                       @RequestParam("compose") String compose,
                                       @RequestParam("role") int role,
                                       @RequestParam("address") String address

    ) {
        User UserName = userRepository.findByUserName(userName);
        if (UserName != null) {
            return Global.getMap(null, "用户名不能重复");
        }
        User user = new User();
        user.setUserName(userName);
        user.setRealName(realName);
        user.setPassword(password);
        user.seteMail(eMail);
        user.setTel(tel);
        user.setIsVip("0");
        user.setAddress(address);
        user.setCoupon(compose);
        user.setRole(role);
        return Global.getMap(userRepository.save(user));
    }


    @PostMapping(value = "/setUserInfo")
    public Map<String, Object> setUserInfo(@RequestParam("id") Integer id,
                                           @RequestParam("userName") String userName,
                                           @RequestParam("realName") String realName,
                                           @RequestParam("eMail") String eMail,
                                           @RequestParam("tel") String tel) {
        User user = userRepository.findOne(id);
        user.setUserName(userName);
        user.setRealName(realName);
        user.seteMail(eMail);
        user.setTel(tel);
        return Global.getMap(userRepository.save(user));
    }

    @GetMapping(value = "/getUserInfo")
    public Map<String, Object> getUserInfo(@RequestParam("id") Integer id) {
        User user = userRepository.findOne(id);
        return Global.getMap(user);
    }

    @PostMapping(value = "/login")
    public Map<String, Object> login(@RequestParam("userName") String userName,
                                     @RequestParam("password") String password) {
        User user = userRepository.findByUserName(userName);
        if (user == null){
            return Global.getMap(null, "用户名或密码错误");
        }
        if (password.equals(user.getPassword())) {
            return Global.getMap(user);
        }
        return Global.getMap(null, "用户名或密码错误");
    }

    @GetMapping(value = "/setVip")
    public Map<String, Object> setVip(@RequestParam("userId") Integer userId){
        User user = userRepository.findOne(userId);
        user.setIsVip("1");
        VipUser vipUser = new VipUser();
        vipUser.setUserName(user.getUserName());
        vipUser.setRealName(user.getRealName());
        vipUser.seteMail(user.geteMail());
        vipUser.setPassword("");
        vipUser.setTel(user.getTel());
        vipUser.setIsVip("1");
        vipUserRepository.save(vipUser);
        return Global.getMap(userRepository.save(user));
    }
}
