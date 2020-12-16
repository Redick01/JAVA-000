package io.redick.rpcfx.demo.consumer;

import com.homework.rpc.client.Rpcfx;
import io.redick.rpcfx.demo.api.OrderService;
import io.redick.rpcfx.demo.api.UserService;
import io.redick.rpcfx.demo.dto.Order;
import io.redick.rpcfx.demo.dto.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liupenghui
 * @date 2020/12/15 8:20 下午
 */
@SpringBootApplication
public class RpcfxClientApplication {

    public static void main(String[] args) {
        UserService userService = Rpcfx.create(UserService.class, "http://localhost:8080/");
        User user = userService.findById(1);
        System.out.println("find user id=1 from server: " + user.getName());

        OrderService orderService = Rpcfx.create(OrderService.class, "http://localhost:8080/");
        Order order = orderService.findOrderById(1992129);
        System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));
    }
}