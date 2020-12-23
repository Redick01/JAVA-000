package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liupenghui
 * @date 2020/12/23 9:30 下午
 */
@AllArgsConstructor
@Getter
public enum ExchangeTypeEnum {

    /**
     * 兑换类型
     */
    DOLLAR_TO_REMINBI("00", "美元兑换人民币"),
    REMINBI_TO_DOLLAR("01", "人民币兑换美元");

    private final String type;
    private final String desc;

}
