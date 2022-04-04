package com.example.productapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.validation.constraints.Pattern.Flag.CASE_INSENSITIVE;

@Entity(name = "Product")
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku", nullable = false)
    @NaturalId
    @Pattern(
            regexp = "^[A-Z]+-[0-9]{7}$",
            flags = CASE_INSENSITIVE,
            message = "SKU format is incorrect, should be like: FAL-1000000"
    )
    private String sku;

    @Column(name = "name")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @NotBlank
    private String name;

    @Column(name = "brand")
    @Size(min = 3, max = 50, message = "Brand must be between 3 and 50 characters")
    @NotBlank
    private String brand;

    @Column(name = "size")
    @NotBlank(message = "Size must not be blank")
    private String size;

    @Column(name = "price")
    @Min(value = 1, message = "Price should not be less than 1")
    @Max(value = 99999999, message = "Price should not be greater than 99999999")
    private BigDecimal price;

    @Column(name = "principalImage")
    @URL(message = "URL format is incorrect")
    @NotBlank(message = "Principal Image must not be blank")
    private String principalImage;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @Builder.Default
    private List<OtherImage> otherImages = new ArrayList<>();

}


