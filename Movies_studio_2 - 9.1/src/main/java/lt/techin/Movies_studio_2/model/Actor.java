package lt.techin.Movies_studio_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "actors")
public class Actor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String nameActor;
  private String lastNameActor;
  private long age;

  public Actor(String nameActor, String lastNameActor, long age) {
    this.nameActor = nameActor;
    this.lastNameActor = lastNameActor;
    this.age = age;
  }

  public Actor() {
  }

  public String getNameActor() {
    return nameActor;
  }

  public void setNameActor(String nameActor) {
    this.nameActor = nameActor;
  }

  public String getLastNameActor() {
    return lastNameActor;
  }

  public void setLastNameActor(String lastNameActor) {
    this.lastNameActor = lastNameActor;
  }

  public long getId() {
    return id;
  }

  public long getAge() {
    return age;
  }

  public void setAge(long age) {
    this.age = age;
  }
}

