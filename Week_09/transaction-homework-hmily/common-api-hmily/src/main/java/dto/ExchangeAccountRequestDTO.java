package dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liupenghui
 * @date 2020/12/16 11:38 下午
 */
@Data
public class ExchangeAccountRequestDTO implements Serializable {

    /**
     * user id
     */
    private String userId;

    /**
     * user actual pay price
     */
    private BigDecimal payAmount;

    /**
     * operate type
     * 00-
     */
    private String operateType;
}
