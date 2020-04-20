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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "promo")
public class Promo {
	
	@Id
	@Column(name = "IDPROMO")
	private String idPromo;
	
	@Basic(optional = false)
	private int anno;
	
	@Basic(optional = false)
	private String codice;
	
	@Basic
	private String descrizione;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,  mappedBy = "promo",  orphanRemoval = true)
	@OrderBy("riga asc") 
	@JsonManagedReference
	private Set<DettPromo> dettPromo = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,  mappedBy = "promo", orphanRemoval = true)
	@JsonManagedReference
	private Set<DepRifPromo> depRifPromo = new HashSet<>();
	
	public Promo() {
		
	}

	public Promo(String idPromo, int anno, String codice, String descrizione) {
		this.idPromo = idPromo;
		this.anno = anno;
		this.codice = codice;
		this.descrizione = descrizione;
	}


	public String getIdPromo() {
		return idPromo;
	}

	public void setIdPromo(String idPromo) {
		this.idPromo = idPromo;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<DettPromo> getDettPromo() {
		return dettPromo;
	}

	public void setDettPromo(Set<DettPromo> dettPromo) {
		this.dettPromo = dettPromo;
	}

	public Set<DepRifPromo> getDepRifPromo() {
		return depRifPromo;
	}

	public void setDepRifPromo(Set<DepRifPromo> depRifPromo) {
		this.depRifPromo = depRifPromo;
	}
	
}
