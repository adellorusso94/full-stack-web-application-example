package com.example.webapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="barcode")
public class Barcode  {

	@Id
	@Column(name = "BARCODE")
	@NotNull(message = "{NotNull.Barcode.barcode.Validation}")
	@Size(min = 8, max = 13, message = "{Size.Barcode.barcode.Validation}")
	private String barcode;
	
	@Column(name = "IDTIPOART")
	@NotNull(message = "{NotNull.Barcode.idTipoArt.Validation}")
	private String idTipoArt;
	
	@ManyToOne
	@JoinColumn(name = "CODART", referencedColumnName = "codArt")
	@JsonBackReference
	private Articoli articolo;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getIdTipoArt() {
		return idTipoArt;
	}

	public void setIdTipoArt(String idTipoArt) {
		this.idTipoArt = idTipoArt;
	}

	public Articoli getArticolo() {
		return articolo;
	}

	public void setArticolo(Articoli articolo) {
		this.articolo = articolo;
	}
	
}
