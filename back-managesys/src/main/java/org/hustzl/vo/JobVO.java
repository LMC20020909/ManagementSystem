package org.hustzl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobVO {
    private Integer id;
    private String apartmentName;
    private String name;
    private Integer maxnum;
    private Integer available;
}
