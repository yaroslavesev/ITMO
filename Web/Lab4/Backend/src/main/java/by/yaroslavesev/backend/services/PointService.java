package by.yaroslavesev.backend.services;
import by.yaroslavesev.backend.DTO.PointDTO;
import by.yaroslavesev.backend.models.Point;
import by.yaroslavesev.backend.models.User;
import by.yaroslavesev.backend.repositories.PointRepository;
import by.yaroslavesev.backend.repositories.UserRepository;
import by.yaroslavesev.backend.utils.PointUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PointService {

    @Inject
    private PointRepository pointRepository;

    @Inject
    private UserRepository userRepository;

    public Point save(PointDTO pointDTO, String username) {
        User user = userRepository.findUser(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Point point = new Point();
        point.setX(pointDTO.getX());
        point.setY(pointDTO.getY());
        point.setR(pointDTO.getR());
        point.setHitStatus(PointUtils.checkPoint(point.getX(), point.getY(), pointDTO.getR()));
        point.setDate(LocalDateTime.now());
        point.setUser(user);

        return pointRepository.savePoint(point);
    }

    public List<Point> getAllPointsForUser(String username) {
        User user = userRepository.findUser(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return pointRepository.findAllPointsByUser(user);
    }

}
