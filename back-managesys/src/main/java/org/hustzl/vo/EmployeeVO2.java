package org.hustzl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeVO2 {
    private Integer id;
    private String number;
    private String name;
    private String gerder;
    private Integer apartmentId;
    private Integer jobId;
    private String createDate;
    private String state;
}
