package comjava.repository;

import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import comjava.entity.Employee;

@Repository
public class EmployeeRepository {

	private HashOperations hashOperations;// crud hash
	private ListOperations listOperations;
	private SetOperations setOperations;

	private RedisTemplate redisTemplate;

	public EmployeeRepository(RedisTemplate redisTemplate) {

		this.hashOperations = redisTemplate.opsForHash();
		this.listOperations = redisTemplate.opsForList();
		this.redisTemplate = redisTemplate;

	}

	public void saveEmployee(Employee employee) {

		// hashOperations.put("EMPLOYEE", employee.getId(), employee);
		listOperations.leftPush("EMPLOYEE", employee);
		
	}

	public List<Employee> findAll() {

//        return hashOperations.values("EMPLOYEE");
		return listOperations.range("EMPLOYEE", 0, listOperations.size("EMPLOYEE"));
	}

	public Employee findById(Integer id) {

	
		//return (Employee) hashOperations.get("EMPLOYEE", id);
		
		List<Employee> employees = listOperations.range("EMPLOYEE", 0, listOperations.size("EMPLOYEE"));
		for (Employee employee : employees) {
			if(employee.getId() == id)
				return employee;
		}
		return null;
	}

	public void update(Employee employee) {
		saveEmployee(employee);
	}

	public void delete(Integer id) {
		// hashOperations.delete("EMPLOYEE", id);
		listOperations.rightPopAndLeftPush("EMPLOYEE", id);
	}
}
