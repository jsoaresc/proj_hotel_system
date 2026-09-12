package com.jcidade.sistema_hoteis.person.service;

import com.jcidade.sistema_hoteis.core.exception.BusinessLogicException;
import com.jcidade.sistema_hoteis.core.exception.ResourceNotFoundException;
import com.jcidade.sistema_hoteis.person.dto.EmployeeMapper;
import com.jcidade.sistema_hoteis.person.dto.EmployeeRequest;
import com.jcidade.sistema_hoteis.person.dto.EmployeeResponse;
import com.jcidade.sistema_hoteis.person.entity.Department;
import com.jcidade.sistema_hoteis.person.entity.Employee;
import com.jcidade.sistema_hoteis.person.repository.DepartmentRepository;
import com.jcidade.sistema_hoteis.person.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper mapper;

    public EmployeeResponse create(EmployeeRequest request) {
        if (repository.existsByDocument(request.document())) {
            throw new BusinessLogicException(
                    "Já existe um funcionário com o documento: " + request.document());
        }
        if (repository.existsByEmployeeNumber(request.employeeNumber())) {
            throw new BusinessLogicException(
                    "Já existe um funcionário com a matrícula: " + request.employeeNumber());
        }
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Departamento não encontrado com id: " + request.departmentId()));

        Employee entity = mapper.toEntity(request);
        entity.setDepartment(department);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll(Long departmentId) {
        List<Employee> employees = (departmentId != null)
                ? repository.findByDepartmentId(departmentId)
                : repository.findAll();
        return mapper.toResponseList(employees);
    }

    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Funcionário não encontrado com id: " + id));
    }

    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Funcionário não encontrado com id: " + id));

        if (!entity.getDocument().equals(request.document())
                && repository.existsByDocument(request.document())) {
            throw new BusinessLogicException(
                    "Já existe um funcionário com o documento: " + request.document());
        }
        if (!entity.getEmployeeNumber().equals(request.employeeNumber())
                && repository.existsByEmployeeNumber(request.employeeNumber())) {
            throw new BusinessLogicException(
                    "Já existe um funcionário com a matrícula: " + request.employeeNumber());
        }

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Departamento não encontrado com id: " + request.departmentId()));

        mapper.updateEntityFromRequest(request, entity);
        entity.setDepartment(department);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Funcionário não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}