package com.ecommerce.microcommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.microcommerce.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
Product findById(int id);
List<Product> findByPrixGreaterThan(int prix );
@Query("SELECT p FROM Product p WHERE p.prix > :prixLimit")
List<Product>  chercherUnProduitCher(@Param("prixLimit") int prix);
//public List<Product> findAll();
//public Product ProductFindById(int id);
//public Product ProductSave(Product product);

}
