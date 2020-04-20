package com.example.webapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="dettlistini")
public class DettListini {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="Id")
	private Integer id;
	
	@Column(name="CODART")
	@Size(min = 5, max = 20, message = "{Size.DettListini.codArt.Validation}")
	@NotNull(message = "{NotNull.DettListini.codArt.Validation}")
	private String codArt;
	
	@ManyToOne
	@JoinColumn(name = "IDLIST", referencedColumnName = "Id")
	@JsonBackReference
	private Listini listino;
	
	@Column(name="Prezzo")
	@Min(value = (long) 0.01, message = "{Min.DettListini.prezzo.Validation}")
	private Double prezzo;
	
	public DettListini() {
		
	}

	public DettListini(String codArt, Double prezzo, Listini listino) {
		this.codArt = codArt;
		this.listino = listino;
		this.prezzo = prezzo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodArt() {
		return codArt;
	}

	public void setCodArt(String codArt) {
		this.codArt = codArt;
	}

	public Listini getListino() {
		return listino;
	}

	public void setListino(Listini listino) {
		this.listino = listino;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}
	
}
