package lt.techin.Movies_studio_2.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String theatersName;
  private LocalDateTime screeningsTime;

  public Screening() {
  }

  public Screening(String name, LocalDateTime screeningsTime) {
    this.theatersName = name;
    this.screeningsTime = screeningsTime;
  }

  public long getId() {
    return id;
  }


  public String getTheatersName() {
    return theatersName;
  }

  public void setTheatersName(String theatersName) {
    this.theatersName = theatersName;
  }

  public LocalDateTime getScreeningsTime() {
    return screeningsTime;
  }

  public void setScreeningsTime(LocalDateTime screeningsTime) {
    this.screeningsTime = screeningsTime;
  }
}


