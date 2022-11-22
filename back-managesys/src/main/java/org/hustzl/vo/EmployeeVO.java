package org.hustzl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeVO {
    private Integer id;
    private String number;
    private String name;
    private String gerder;
    private String apartmentName;
    private String jobName;
    private String createDate;
    private String state;
}
