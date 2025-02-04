package lt.techin.MoviesStudio.model;

import jakarta.persistence.*;

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
  @JoinColumn(name = "mocie_id")
  private List<>

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

}
