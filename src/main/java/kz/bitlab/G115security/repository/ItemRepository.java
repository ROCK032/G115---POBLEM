package kz.bitlab.G115security.repository;

import kz.bitlab.G115security.entity.Iteam;
import kz.bitlab.G115security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Iteam, Long> {
  @Query("select  i from Iteam i where "
          + "i.product ilike concat('%', :search,'%') "
          + "or i.description ilike concat('%', :search,'%') "
          + "or cast(i.price as string ) = :search ")
  List<Iteam> findFilteredItems(String search);
}
