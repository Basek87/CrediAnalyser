package pl.dawidbasa.crediAnalyser.Credit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("creditRepository")
public interface CreditRepository extends JpaRepository<Credit, Long> {
	
	Credit findByMortgageName(String mortgageName);
	List<Credit> findAll();
}
