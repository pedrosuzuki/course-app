package com.epam.course.web;

import com.epam.course.service.CourseRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/courses/{courseId}/ratings")
public class CourseRatingController {
    private final CourseRatingService courseRatingService;

    @Autowired
    public CourseRatingController(CourseRatingService courseRatingService) {
        this.courseRatingService = courseRatingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourseRating(@PathVariable("courseId") Long courseId,
                                   @RequestBody @Validated RatingDto ratingDto) {
        courseRatingService.createRating(courseId, ratingDto);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    private String notFound(NoSuchElementException ex) {
        return ex.getMessage();
    }
}