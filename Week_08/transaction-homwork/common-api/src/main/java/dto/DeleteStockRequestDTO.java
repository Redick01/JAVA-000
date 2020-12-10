package dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Redick
 * @date 2020/12/9 11:11 下午
 */
@Data
public class DeleteStockRequestDTO implements Serializable {

    /**
     * product id
     */
    private String productId;

    /**
     * stock account
     */
    private Integer stockNum;
}
