package org.hustzl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hustzl.entity.Apartment;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO {
    private Object data;
    private Long total;
}
