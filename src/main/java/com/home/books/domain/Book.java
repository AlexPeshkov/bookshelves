package com.home.books.domain;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@ApiModel(subTypes = {NonFictionBook.class, FictionBook.class})
@Entity
@Inheritance @DiscriminatorColumn(name="BOOK_TYPE")
@JsonPropertyOrder({ "id", "type", "title", "author" }) //TODO Jackson annotations semantics: https://www.baeldung.com/jackson-annotations & https://github.com/FasterXML/jackson-annotations
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FictionBook.class, name = "F"),
    @JsonSubTypes.Type(value = NonFictionBook.class, name = "NF")
})
public abstract class Book {
    /** TODO Validation Framework */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @PositiveOrZero private long id;
    @Size(max = 50) @NotEmpty
    private String title;
    @Size(max = 50) @NotEmpty
    private String author;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /** No-arg constructor needed by JPA */
    public Book() { }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @ApiModelProperty(allowableValues = "F,NF")
    @JsonIgnore
    public abstract String getType();

    //TO DO equals() + hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getTitle().equals(book.getTitle()) &&
                getAuthor().equals(book.getAuthor()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getAuthor());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author +
                '}';
    }
}