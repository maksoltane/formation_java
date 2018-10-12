package com.ecommerce.microcommerce.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;

@RestController
public class ProductController {
	@Autowired
	private ProductDao productDao;

	@RequestMapping(value = "/Produits", method = RequestMethod.GET)
	public MappingJacksonValue listeProduits() {
		List<Product> produits = productDao.findAll();
		MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
		return produitsFiltres;
	}

	@RequestMapping(value ="Produits/{id}", method = RequestMethod.GET)
	public Product afficherUnProduit(@PathVariable int id) {
		Product produit =  productDao.findById(id);
		if (produit == null) {
			throw new ProduitIntrouvableException("Le produit " + id + " est introuvable");
		}
		return produit;
	}
	
	@RequestMapping(value ="Produits/filtrePrixCher/{prix}", method = RequestMethod.GET)
	public List<Product> afficherUnProduitCher(@PathVariable int prix) {
		return productDao.chercherUnProduitCher(prix);
	}
	@RequestMapping(value ="Produits/{id}", method = RequestMethod.DELETE)
	public void supprimerUnProduit(@PathVariable int id) {
		productDao.deleteById(id);
	}
	
	@RequestMapping(value ="Produits/filtrePrix/{prix}", method = RequestMethod.GET)
	public List<Product> afficherUnProduitSelonPrix(@PathVariable int prix) {
		return productDao.findByPrixGreaterThan(prix);
	}
	@RequestMapping(value = "Produits/", method = RequestMethod.POST)
	public ResponseEntity<Void> ajouterProduit(@RequestBody Product produit) {
		Product productAdded = productDao.save(produit);
		if (productAdded == null)
			return ResponseEntity.noContent().build();
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productAdded.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	/*
	@PostMapping(value = "Produits")
	public ResponseEntity<Void> ajouterUnProduit(@RequestBody Product product) {
		Product productAdded = productDao.ProductSave(product);

		if (productAdded == null)
			return ResponseEntity.noContent().build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productAdded.getId()).toUri();

		return ResponseEntity.created(location).build();
	}*/
}
