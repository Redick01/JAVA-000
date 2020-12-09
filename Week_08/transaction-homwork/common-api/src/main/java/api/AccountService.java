package api;

import dto.PayAccountRequestDTO;
import org.dromara.hmily.annotation.Hmily;

/**
 * @author Redick
 * @date 2020/12/9 10:54 下午
 */
public interface AccountService {

    @Hmily
    boolean pay(PayAccountRequestDTO requestDTO);
}
