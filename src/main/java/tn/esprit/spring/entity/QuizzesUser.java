package tn.esprit.spring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QuizzesUser implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sno;

    @Column(nullable = false)
    private long quizId; // multiple quiz-user relations might exist
    private int userId; // relations might exist


    // -3 implies that quiz is unattempted
    // -2 implies that quiz is attempted but not successfully submitted(in case of switch off or app kill)
    // -1 implies that quiz is attempted successfully but not checked
    @ColumnDefault("-3")
    private int marksObtained; // to be updated only after a successful attempt of the test
}
