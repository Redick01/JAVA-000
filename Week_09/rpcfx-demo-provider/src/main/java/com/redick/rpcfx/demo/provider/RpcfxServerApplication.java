package com.redick.rpcfx.demo.provider;

import com.homework.rpc.LocalRegisterCenter;
import com.homework.rpc.ZkRegistry;
import com.homework.rpc.api.RegisterCenter;
import com.homework.rpc.api.RpcfxRequest;
import com.homework.rpc.api.RpcfxResolver;
import com.homework.rpc.api.RpcfxResponse;
import com.homework.rpc.server.RpcfxInvoker;
import io.redick.rpcfx.demo.api.OrderService;
import io.redick.rpcfx.demo.api.UserService;
import io.redick.rpcfx.demo.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RpcfxServerApplication {

	public static void main(String[] args) {
		// 发布服务，注册到本地注册中心
		//LocalRegisterCenter.getInstance().register(UserService.class.getName(), UserServiceImpl.class.getName());
		//LocalRegisterCenter.getInstance().register(OrderService.class.getName(), OrderServiceImpl.class.getName());
		// 注册到zk
		RegisterCenter registry = new ZkRegistry("192.168.3.78:2181", "Rpcfx");
		registry.register("/" + UserService.class.getName(), UserServiceImpl.class.getName());
		registry.register("/" + OrderService.class.getName(), OrderServiceImpl.class.getName());
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
		return new RpcfxInvoker(resolver);
	}

	@Bean
	public RpcfxResolver createResolver(){
		return new DemoResolver();
	}

	// 能否去掉name
	//
	@Bean(name = "io.kimmking.rpcfx.demo.api.UserService")
	public UserService createUserService(){
		return new UserServiceImpl();
	}

	@Bean(name = "io.kimmking.rpcfx.demo.api.OrderService")
	public OrderService createOrderService(){
		return new OrderServiceImpl();
	}

}
