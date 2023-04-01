package ua.foxminded.university.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="groups")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Group {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
}