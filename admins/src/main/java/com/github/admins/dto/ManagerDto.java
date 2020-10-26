package com.github.admins.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.admins.payload.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto implements Serializable, Cloneable {

    private static final long serialVersionUID = 2992813738159824262L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "firstName")
    private String firstName;

    @JsonProperty(value = "lastName")
    private String lastName;

}
