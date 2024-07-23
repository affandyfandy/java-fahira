package com.lecture11.assignment1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReadDepartmentDto {
    private String deptNo;
    private String deptName;
    private List<ReadDepartmentEmployeeDto> listDepartmentEmployee;
    private List<ReadDepartmentManagerDto> listDepartmentManager;
}
