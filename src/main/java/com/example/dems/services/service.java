package com.example.dems.services;

import com.example.dems.models.Data;
import com.example.dems.exception.NotFound;
import com.example.dems.repositories.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class service {

    private final Repo repo;

    @Autowired
    public service(Repo repo) {
        this.repo = repo;

    }

    public Data createEmployee(Data data){
        return repo.save(data);
    }
    @Cacheable(value = "employees")
    public List<Data> getEmployees(){
        return repo.findAll();
    }

    public List<Data> getEmployeeByNameOrDepartment(String name,String department){
//        System.out.println("Fetching from DB");
//        System.out.println(name+" "+department);
        return repo.findEmployeesByNameOrDepartment(name, department);
    }

    public List<Data> getEmployeesBySkillAndCertification(String skill, String certification){
        return repo.findEmployeesBySkillAndCertification(skill, certification);
    }

    @Caching(evict = {
            @CacheEvict(value = "employees", key = "#id"),
            @CacheEvict(value = "ranks", allEntries = true)
    })
    public void deleteEmployee(Long id){
        if(repo.existsById(id)){
            repo.deleteById(id);
        }else{
            throw new NotFound("Employee not found");
        }
    }
    @Cacheable(value = "employees", key = "#id")
    public Optional<Data> getEmployeeById(Long id){
        return repo.findById(id);
    }
    @CachePut(value = "employees", key = "#id")
    @CacheEvict(value = "ranks", allEntries = true)
    public Data updateEmployee(Long id,Data data){
        return repo.findById(id).map(emp->{
            emp.setName(data.getName());
            emp.setDesignation(data.getDesignation());
            emp.setDepartment(data.getDepartment());
            emp.setJoiningDate(data.getJoiningDate());
            emp.setSalary(data.getSalary());
            emp.setSkills(data.getSkills());
            return repo.save(emp);
        }).orElseThrow(()->new NotFound("Employee not found"));
    }

    @Cacheable(value = "ranks")
    public List<Record> getAllDataWithRanks(){
        List<Object[]> raw = repo.findAllWithRanks();

        return raw.stream().map(
                record -> new Record(
                        (Long) record[0],
                        (String) record[1],
                        (String) record[2],
                        (String) record[3],
                        (Double) record[4],
                        ((Number) record[5]).intValue()
                )).toList();
    }

    public record Record(
            Long id,
            String name,
            String designation,
            String department,
            Double salary,
            Integer rank
    ){}

}
