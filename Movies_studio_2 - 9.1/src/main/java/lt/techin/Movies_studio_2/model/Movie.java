package lt.techin.Movies_studio_2.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lt.techin.Movies_studio_2.validation.Director;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  //  @NotNull
//  @Size(min = 2, max = 100, message = "May not be less than 2 and not more than 100 characters")
  private String title;

  //  @Director
  private String director;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "movie_id")
  private List<Screening> screenings;

  @ManyToMany
  @JoinTable(
          name = "movies_actors",
          joinColumns = @JoinColumn(name = "movie_id"),
          inverseJoinColumns = @JoinColumn(name = "actor_id")
  )
  private List<Actor> actors;

  public Movie() {
  }

  public Movie(String title, String director, List<Screening> screenings, List<Actor> actors) {
    this.title = title;
    this.director = director;
    this.screenings = screenings;
    this.actors = actors;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public long getId() {
    return id;
  }

  public List<Screening> getScreenings() {
    return screenings;
  }

  public void setScreenings(List<Screening> screenings) {
    this.screenings = screenings;
  }

  public List<Actor> getActors() {
    return actors;
  }

  public void setActors(List<Actor> actors) {
    this.actors = actors;
  }

}

