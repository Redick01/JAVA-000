package dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Redick
 * @date 2020/12/9 10:55 下午
 */
@Data
public class PayAccountRequestDTO implements Serializable {

    /**
     * user id
     */
    private String userId;

    /**
     * user actual pay price
     */
    private BigDecimal payAmount;
}
