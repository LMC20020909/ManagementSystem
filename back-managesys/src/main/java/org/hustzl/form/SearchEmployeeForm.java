package org.hustzl.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchEmployeeForm {
    private String apartmentId;
    private String jobId;
    private Integer page;
    private Integer size;
}
