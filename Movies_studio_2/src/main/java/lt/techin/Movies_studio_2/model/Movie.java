package lt.techin.Movies_studio_2.model;


import jakarta.persistence.*;

import javax.management.loading.PrivateMLet;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String title;
  private String director;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "movie_id")
  private List<Screenings> screenings;

  public Movie() {
  }

  public Movie(String title, String director) {
    this.title = title;
    this.director = director;
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

  public List<Screenings> getScreenings() {
    return screenings;
  }

  public void setScreenings(List<Screenings> screenings) {
    this.screenings = screenings;
  }
}

