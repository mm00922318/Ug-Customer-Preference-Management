package com.ug.cpm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_CPM_Customerpreference")
public class CustomerPreference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@Column(name = "Customer_Firstname")
	private String customerFirstName;
	@Column(name = "Customer_Lastname")
	private String customerLastName;
	@Column(name = "Customer_Middlename")
	private String customerMiddleName;
	@Column(name = "Customer_Type")
	private String customerType;

	@Column(name = "Preference_Id")
	private int preferenceId;
	@Column(name = "Preference_Type")
	private String preferenceType;
	@Column(name = "Preference_Name")
	private String preferenceName;
	@Column(name = "Preference_Createdon")
	private String preferenceCreatedDate;
	@Column(name = "Preference_Lastupdatedon")
	private String preferenceUpdatedDate;
	
	@Column(name = "Isactive")
	private boolean isActive;
	@Column(name = "Islike")
	private boolean isLike;
	
	@Column(name = "Created_On")
	private String createdDate;
	@Column(name = "Last_Updated_On")
	private String updatedDate;

	@ManyToOne
	@JoinColumn(name = "Customer_Id", referencedColumnName = "Customer_Id")
	private Customer customer;

}
