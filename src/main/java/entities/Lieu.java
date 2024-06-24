package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "lieu")
public class Lieu {

	private String pays;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ville")
	private String ville;

	// Constructeurs
	

	public Lieu(String ville, String pays) {
		this.ville=ville;
		this.pays=pays;
	}

	public Lieu() {

	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	// Equals and HashCode (optional, based on your needs)
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Lieu lieu = (Lieu) o;

		return id != null ? id.equals(lieu.id) : lieu.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	// toString (optional, for debugging)
	@Override
	public String toString() {
		return "Lieu{" +
				"id=" + id +
				", ville='" + ville + '\'' +
				'}';
	}
}
