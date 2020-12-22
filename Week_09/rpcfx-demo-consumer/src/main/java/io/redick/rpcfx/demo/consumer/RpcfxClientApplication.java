package io.redick.rpcfx.demo.consumer;

import com.homework.rpc.router.DefaultRouter;
import com.homework.rpc.loadbalancer.RibbonLoadBalancer;
import com.homework.rpc.client.Rpcfx;
import com.homework.rpc.filter.TestFilter;
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

    public static void main(String[] args) throws Exception {
        UserService userService = Rpcfx.createFromRegistry(UserService.class, "192.168.3.78:2181",
                new DefaultRouter(), new RibbonLoadBalancer(), new TestFilter());
        User user = userService.findById(1);
        System.out.println("find user id=1 from server: " + user.getName());

        OrderService orderService = Rpcfx.createFromRegistry(OrderService.class, "192.168.3.78:2181",
                new DefaultRouter(), new RibbonLoadBalancer(), new TestFilter());
        Order order = orderService.findOrderById(1992129);
        System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));
    }
}
