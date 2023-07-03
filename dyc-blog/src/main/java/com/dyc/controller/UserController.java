package com.dyc.controller;

//import com.dyc.annotiation.SystemLog;
import com.dyc.annotiation.SystemLog;
import com.dyc.domain.ResponseResult;
import com.dyc.domain.entity.User;
import com.dyc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }


    @PutMapping("/userInfo")
    //自定义注解，调用该方法时候打印日志到控制台
    @SystemLog(basinessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")

    public ResponseResult register(@RequestBody User user){

        return userService.register(user);

    }
}
