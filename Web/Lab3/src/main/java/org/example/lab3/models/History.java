package org.example.lab3.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "hit_status")
    private boolean hitStatus;

    @Column(name = "radius")
    private double radius;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    public History() {
    }

    public History(double x, double y, double radius, Boolean hitStatus, LocalDateTime date) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.hitStatus = hitStatus;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Boolean getHitStatus() {
        return hitStatus;
    }

    public void setHitStatus(Boolean hitStatus) {
        this.hitStatus = hitStatus;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

