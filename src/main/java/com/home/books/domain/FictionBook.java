package com.home.books.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("F")
@ApiModel(parent = Book.class, discriminator = "type")
public class FictionBook extends Book {

    public FictionBook() { }

    public FictionBook(String title, String author, String bookType) {
        super(title, author, bookType);
    }

    @Override
    @ApiModelProperty(allowableValues = "F")
    public String getType() {
        return "F";
    }

    //TODO equals() , hashCode()
}
