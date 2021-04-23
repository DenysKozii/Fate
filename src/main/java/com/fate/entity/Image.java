package com.fate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "images")
@EqualsAndHashCode(callSuper = true)
public class Image extends Media {

  @OneToMany(mappedBy = "image", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private List<Question> questions = new ArrayList<>();

}