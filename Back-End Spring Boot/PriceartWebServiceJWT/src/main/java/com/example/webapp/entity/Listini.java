package com.example.webapp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="listini")
public class Listini {
	
	@Id
	@Column(name="Id")
	private String id;
	
	@Column(name="Descrizione")
	@Size(min = 10, max = 30, message = "{Size.Listini.descrizione.Validation}")
	@Basic
	private String descrizione;
	
	@Column(name="Obsoleto")
	@Basic
	private String obsoleto;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "listino")
	@JsonManagedReference
	private Set<DettListini> dettListini = new HashSet<>();
	
	public Listini() {
		
	}

	public Listini(String id, String descrizione, String obsoleto) {
		this.id = id;
		this.descrizione = descrizione;
		this.obsoleto = obsoleto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getObsoleto() {
		return obsoleto;
	}

	public void setObsoleto(String obsoleto) {
		this.obsoleto = obsoleto;
	}

	public Set<DettListini> getDettListini() {
		return dettListini;
	}

	public void setDettListini(Set<DettListini> dettListini) {
		this.dettListini = dettListini;
	}

}
