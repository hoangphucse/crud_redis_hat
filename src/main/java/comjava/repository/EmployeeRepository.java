package comjava.repository;

import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import comjava.entity.Employee;

@Repository
public class EmployeeRepository {

    private HashOperations hashOperations;//crud hash

    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {

        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee){

        hashOperations.put("EMPLOYEE", employee.getId(), employee);
    }
    public List<Employee> findAll(){

        return hashOperations.values("EMPLOYEE");
    }
    public Employee findById(Integer id){

        return (Employee) hashOperations.get("EMPLOYEE", id);
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }
    public void delete(Integer id){
        hashOperations.delete("EMPLOYEE", id);
    }
}
