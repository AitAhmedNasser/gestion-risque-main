package it.gestionRisque.app.Entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Impaye {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private Date defaultDate;
private Integer dpd;
private Date restructuredDate;
private double overDue;
private String reasonForNpa;
private String reasonForNpaDesc;

/*
 * @OneToOne(cascade = {CascadeType.ALL})
 * 
 * @JoinColumn(name = "engagement_id",referencedColumnName="id") private
 * Engagement engagement;
 */


}
