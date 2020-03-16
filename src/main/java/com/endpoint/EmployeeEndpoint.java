package com.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.repository.EmployeeRepository;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import employeeCrud.DeleteEmployeeRequest;
import employeeCrud.DeleteEmployeeResponse;
import employeeCrud.EditEmployeeRequest;
import employeeCrud.EditEmployeeResponse;
import employeeCrud.Employee;
import employeeCrud.GetEmployeeRequest;
import employeeCrud.GetEmployeeResponse;
import employeeCrud.SaveEmployeeRequest;
import employeeCrud.SaveEmployeeResponse;

@Endpoint
public class EmployeeEndpoint {

	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	@Autowired
	private EmployeeRepository EmployeeRepository;
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeeRequest")
	@ResponsePayload
	public GetEmployeeResponse getEmployee(@RequestPayload GetEmployeeRequest request) {
		GetEmployeeResponse response = new GetEmployeeResponse();
		
		try {
			com.model.Employee Employee = this.EmployeeRepository.findById(request.getId()).get();
			
			Employee emp = new Employee();
			emp.setId(Employee.getId());
			emp.setFirstname(Employee.getFirstname());
			response.setEmployee(emp);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveEmployeeRequest")
	@ResponsePayload
	public SaveEmployeeResponse saveEmployee(@RequestPayload SaveEmployeeRequest request) {
		SaveEmployeeResponse response = new SaveEmployeeResponse();
		

			Employee Employee1 = request.getEmployee();
			com.model.Employee Employee2 = new com.model.Employee(Employee1.getFirstname(),Employee1.getLastname(),Employee1.getAge());
			
			this.EmployeeRepository.save(Employee2);
			
			Employee emp = new Employee();
			emp.setId(Employee2.getId());
			
			response.setEmployee(emp);
	
		
	
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEmployeeRequest")
	@ResponsePayload
	public DeleteEmployeeResponse deleteEmployee(@RequestPayload DeleteEmployeeRequest request) {
		DeleteEmployeeResponse response = new DeleteEmployeeResponse();
		
		com.model.Employee deleteEmp = this.EmployeeRepository.findById(request.getId()).get();	
		this.EmployeeRepository.delete(deleteEmp);
		
		Employee emp = new Employee();
		emp.setId(deleteEmp.getId());
		
		response.setEmployee(emp);
		
		
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "editEmployeeRequest")
	@ResponsePayload
	public EditEmployeeResponse editEmployee(@RequestPayload EditEmployeeRequest request) {
		
		EditEmployeeResponse response;	response = new EditEmployeeResponse();
		try {
		
			Employee emp = request.getEmployee();
			com.model.Employee Employee2 = this.EmployeeRepository.findById(request.getId()).get();

			Employee2.setFirstname(emp.getFirstname());
			Employee2.setLastname(emp.getLastname());
			Employee2.setAge(emp.getAge());

			com.model.Employee editEmp = this.EmployeeRepository.save(Employee2);
			Employee emp1 = new Employee();
			emp1.setFirstname(editEmp.getFirstname());
			emp1.setLastname(editEmp.getLastname());
			emp1.setAge(editEmp.getAge());

			response.setEmployee(emp);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return response;
	}
	
	
}
