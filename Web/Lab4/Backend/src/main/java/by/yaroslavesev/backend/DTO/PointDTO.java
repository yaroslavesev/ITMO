package by.yaroslavesev.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointDTO {
    private double x;
    private double y;
    private double r;
    private boolean hitStatus;
    private String date;

    public PointDTO(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
}
