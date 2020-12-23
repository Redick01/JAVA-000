package com.redick.rpcfx.demo.provider;

import com.homework.rpc.reigister.ZookeeperRegistry;
import com.homework.rpc.api.RegisterCenter;
import com.homework.rpc.api.RpcfxRequest;
import com.homework.rpc.api.RpcfxResolver;
import com.homework.rpc.api.RpcfxResponse;
import com.homework.rpc.server.RpcfxInvoker;
import io.redick.rpcfx.demo.api.OrderService;
import io.redick.rpcfx.demo.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Redick
 */
@SpringBootApplication
@RestController
public class RpcfxServerApplication {

	public static void main(String[] args) {
		// 注册到zk
		RegisterCenter registry = new ZookeeperRegistry("192.168.58.45:2181", "Rpcfx");
		registry.register(UserService.class.getName(), 8080);
		registry.register(OrderService.class.getName(), 8080);
		System.out.println("System started.");
		SpringApplication.run(RpcfxServerApplication.class, args);
	}

	@Autowired
	RpcfxInvoker invoker;

	@PostMapping("/")
	public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
		return invoker.invoke(request);
	}

	@Bean
	public RpcfxInvoker createInvoker(@Autowired RpcfxResolver resolver){
		return new RpcfxInvoker();
	}

	@Bean
	public RpcfxResolver createResolver(){
		return new DemoResolver();
	}

	@Bean
	public UserService createUserService(){
		return new UserServiceImpl();
	}

	@Bean
	public OrderService createOrderService(){
		return new OrderServiceImpl();
	}

}
