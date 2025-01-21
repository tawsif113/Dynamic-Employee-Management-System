package com.example.dems.repositories;

import com.example.dems.models.Data;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * This Repository is used to interact with the Database
 * It is used to retrieve data from the Database
 * It extends the JpaRepository which provides methods like findAll, findById, save and delete
 * The methods in this interface are used to query the database
 * The methods are annotated with @Query to specify the query to be executed
 */
@Repository
public interface Repo extends JpaRepository<Data, Long> {

    @Query(value = "SELECT * FROM employees WHERE jsonb_exists(skills->'skills', :skill) AND jsonb_exists(skills->'certifications', :certification)", nativeQuery = true)
    List<Data> findEmployeesBySkillAndCertification(@Param("skill") String skill, @Param("certification") String certification);

    @Query(value = "SELECT id, name, designation, department, salary, " +
            "RANK() OVER (ORDER BY salary DESC) AS rank " +
            "FROM employees",
            nativeQuery = true)
    List<Object[]> findAllWithRanks();

    List<Data>findEmployeesByNameOrDepartment(String name, String department);


}
