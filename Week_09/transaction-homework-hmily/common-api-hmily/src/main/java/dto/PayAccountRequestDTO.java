package dto;

import enums.ExchangeTypeEnum;
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
     * 兑换类型
     */
    private ExchangeTypeEnum exchangeType;

    /**
     * user actual pay price
     */
    private Integer exchangeAmount;
}
