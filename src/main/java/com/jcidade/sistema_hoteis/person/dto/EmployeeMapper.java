package com.jcidade.sistema_hoteis.person.dto;

import com.jcidade.sistema_hoteis.hotel.dto.AddressMapper;
import com.jcidade.sistema_hoteis.person.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface EmployeeMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    EmployeeResponse toResponse(Employee entity);

    List<EmployeeResponse> toResponseList(List<Employee> entities);

    @Mapping(target = "department", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Employee toEntity(EmployeeRequest request);

    @Mapping(target = "department", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(EmployeeRequest request, @MappingTarget Employee entity);
}