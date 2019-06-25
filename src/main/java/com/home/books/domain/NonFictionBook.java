package com.home.books.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("NF")
@ApiModel(parent = Book.class, discriminator = "type")
public class NonFictionBook extends Book {
    public NonFictionBook() { }

    public NonFictionBook(String title, String author, String bookType) {
        super(title, author, bookType);
    }

    @Override
    @ApiModelProperty(allowableValues = "NF")
    public String getType() {
        return "NF";
    }
}
