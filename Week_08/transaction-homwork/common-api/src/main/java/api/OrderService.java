package api;

import dto.AddOrderRequestDTO;
import org.dromara.hmily.annotation.Hmily;

/**
 * @author Redick
 * @date 2020/12/9 11:03 下午
 */
public interface OrderService {

    @Hmily
    boolean addOrder(AddOrderRequestDTO requestDTO);
}
