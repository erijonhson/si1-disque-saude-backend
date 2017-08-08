package com.ufcg.si1.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "H")
public class Hospital extends UnidadeSaude {

}
