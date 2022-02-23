package com.maidou.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhanhong
 */
@Data
public class TbCommodityInfo {

    private String id;
    private String commodity_name;
    private String commodity_price;
    private String number;
    private String description;
}
