package com.example.webapp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tipopromo")
public class TipoPromo {
	
	@Id
	@Column(name = "IDTIPOPROMO")
	private int idTipoPromo;
	
	@Basic
	private String descrizione;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoPromo")
	@JsonBackReference
	private Set<DettPromo> dettPromo = new HashSet<>();
	
	public TipoPromo() {
		
	}

	public TipoPromo(int idTipoPromo) {
		this.idTipoPromo = idTipoPromo;
	}

	public int getIdTipoPromo() {
		return idTipoPromo;
	}

	public void setIdTipoPromo(int idTipoPromo) {
		this.idTipoPromo = idTipoPromo;
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
	
}
