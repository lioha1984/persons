package telran.java2022.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	
	@Query("select p from Person p where p.name=?1")
	Stream<Person> findByName(String name);

	@Query("select p from Person p where p.address.city=:city")
	Stream<Person> findByAddressCity(@Param("city") String city);

	Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);
	
	@Query("select new telran.java2022.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCitiesPopulation();
	
	@Query("select p from Person p join Child c on p.id = c.id order by p.id")
	Stream<Person> getChild();

	@Query("select p from Person p join Employee e on p.id = e.id where e.salary between ?1 AND ?2 order by p.id")
	Stream<Person> findEmployeeBySalary(int min, int max);

}
