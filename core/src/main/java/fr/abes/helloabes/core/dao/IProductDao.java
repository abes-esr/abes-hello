package fr.abes.helloabes.core.dao;

import fr.abes.helloabes.core.entities.Fournisseur;
import fr.abes.helloabes.core.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Représente un dépôt de Fournisseur du service web.
 * Cette classe est basée sur le framework Spring avec le module Spring Data JPA.
 * @since 0.0.1
 * @author Duy Tran
 */
@Repository
public interface IProductDao extends JpaRepository<Products, Long> {
}
