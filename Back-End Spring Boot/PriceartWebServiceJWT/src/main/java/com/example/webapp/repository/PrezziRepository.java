package com.example.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.webapp.entity.DettListini;

@Repository
public interface PrezziRepository extends JpaRepository<DettListini,Long>{
	
	@Modifying
	@Query(value = "DELETE FROM dettlistini WHERE CodArt = :codart AND IdList = :idlist", nativeQuery = true)
	void DelRowDettList(@Param("codart") String codArt, @Param("idlist") String idList);
	
	@Query(value = "SELECT b FROM Listini a JOIN a.dettListini b WHERE b.codArt = :codart AND a.id = :idlist")
	DettListini SelByCodArtAndList(@Param("codart") String codArt, @Param("idlist") String idList);
	
}
