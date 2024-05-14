package com.epam.course.web;

import com.epam.course.service.CourseRatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping(path = "/courses/{courseId}/ratings")
public class CourseRatingController {
    private final CourseRatingService courseRatingService;

    @Autowired
    public CourseRatingController(CourseRatingService courseRatingService) {
        this.courseRatingService = courseRatingService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_REP')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourseRating(@PathVariable("courseId") Long courseId,
                                   @RequestBody @Validated RatingDto ratingDto) {
        log.info("POST /courses/{}/ratings", courseId);
        courseRatingService.createRating(courseId, ratingDto);
    }

    @PostMapping("/{score}")
    @PreAuthorize("hasRole('ROLE_REP')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMultipleCourseRating(@PathVariable("courseId") Long courseId,
                                           @PathVariable("score") Integer score,
                                           @RequestParam("customers") Long[] customers,
                                           @RequestParam("comment") String comment) {
        log.info("POST /courses/{}/ratings/{}", courseId, score);
        courseRatingService.createMultipleRatings(courseId, score, customers, comment);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RatingDto> getAllCourseRatings(@PathVariable("courseId") Long courseId) {
        log.info("GET /courses/{}/ratings", courseId);
        return courseRatingService.getAllCourseRatings(courseId);
    }

    @GetMapping(path = "/average")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Double> getCourseRatingAverage(@PathVariable("courseId") Long courseId) {
        log.info("GET /courses/{}/ratings/average", courseId);
        return courseRatingService.getCourseRatingAverage(courseId);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_REP')")
    @ResponseStatus(HttpStatus.OK)
    public RatingDto updateAllCourseRating(@PathVariable("courseId") long courseId,
                                                 @RequestBody @Validated RatingDto ratingDto) {
        log.info("PUT /courses/{}/ratings", courseId);
        return courseRatingService.updateAllCourseRating(courseId, ratingDto);
    }

    @PatchMapping
    @PreAuthorize("hasRole('ROLE_REP')")
    @ResponseStatus(HttpStatus.OK)
    public RatingDto updatePartialCourseRating(@PathVariable("courseId") long courseId,
                                                     @RequestBody @Validated RatingDto ratingDto) {
        log.info("PATCH /courses/{}/ratings", courseId);
        return courseRatingService.updatePartialCourseRating(courseId, ratingDto);
    }

    @DeleteMapping(path = "/{customerId}")
    @PreAuthorize("hasRole('ROLE_REP')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseRating(@PathVariable("courseId") long courseId,
                                   @PathVariable("customerId") long customerId) {
        log.info("DELETE /courses/{}/ratings/{}", courseId, customerId);
        courseRatingService.deleteCourseRating(courseId, customerId);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    private String notFound(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
