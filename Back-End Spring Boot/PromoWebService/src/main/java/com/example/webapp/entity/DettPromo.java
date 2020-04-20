package com.example.webapp.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "dettpromo")
public class DettPromo {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false)
	private int riga;
	
	@Basic(optional = false)
	private String codart;
	
	@Transient
	private String descrizione;
	
	@Transient
	private double prezzo;
	
	@Basic
	private String codfid;
		
	@Column(name = "INIZIO")
	@Temporal(TemporalType.DATE)
	private Date inizio;
	
	@Column(name = "FINE")
	@Temporal(TemporalType.DATE)
	private Date fine;
	
	@Basic(optional = false)
	private String oggetto;
	
	@Basic
	private String isfid;
	
	@ManyToOne
	@JoinColumn(name = "IDPROMO", referencedColumnName = "idPromo")
	@JsonBackReference 
	private Promo promo;
	
	@ManyToOne
	@JoinColumn(name = "IDTIPOPROMO", referencedColumnName = "idTipoPromo")
	private TipoPromo tipoPromo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRiga() {
		return riga;
	}

	public void setRiga(int riga) {
		this.riga = riga;
	}

	public String getCodart() {
		return codart;
	}

	public void setCodart(String codart) {
		this.codart = codart;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getCodfid() {
		return codfid;
	}

	public void setCodfid(String codfid) {
		this.codfid = codfid;
	}

	public Date getInizio() {
		return inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	public Date getFine() {
		return fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getIsfid() {
		return isfid;
	}

	public void setIsfid(String isfid) {
		this.isfid = isfid;
	}

	public Promo getPromo() {
		return promo;
	}

	public void setPromo(Promo promo) {
		this.promo = promo;
	}

	public TipoPromo getTipoPromo() {
		return tipoPromo;
	}

	public void setTipoPromo(TipoPromo tipoPromo) {
		this.tipoPromo = tipoPromo;
	}

}
