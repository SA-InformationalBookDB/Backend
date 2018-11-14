package hu.bme.szarch.ibdb.controller.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {

    @Min(value = 1, message = "Points should not be less than 1")
    @Max(value = 5, message = "Points should not be more than 5")
    @NotNull
    private int points;

    @Size(max = 500, message = "Comments should not be longer than 500 characters")
    private String comment;

}
