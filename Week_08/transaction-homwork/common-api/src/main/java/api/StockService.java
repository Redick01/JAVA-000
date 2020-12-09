package api;

import dto.DeleteStockRequestDTO;
import org.dromara.hmily.annotation.Hmily;

/**
 * @author Redick
 * @date 2020/12/9 11:10 下午
 */
public interface StockService {

    @Hmily
    boolean deleteStock(DeleteStockRequestDTO requestDTO);
}
