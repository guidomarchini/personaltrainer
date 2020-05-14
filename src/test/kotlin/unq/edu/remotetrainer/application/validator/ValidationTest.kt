package unq.edu.remotetrainer.application.validator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import unq.edu.remotetrainer.application.rest.exception.ValidationError

class ValidationTest {
    private val errorMessage: String = "some error message"

    /* ****************************** *
     * **** VALIDATE A CONDITION **** *
     * ****************************** */
    @Test
    fun `validation of false throws a validation exception`() {
        // act
        val error = assertThrows<ValidationError> {
            Validation.validate(false, errorMessage)
        }

        // assert
        assertThat(error.message).isEqualTo(errorMessage)
    }

    @Test
    fun `validation of true doesn't throw an exception`() {
        // act
        Validation.validate(true, errorMessage)

        // assert - no error
    }

    /* ****************************** *
     * ***** VALIDATE NOT NULL** **** *
     * ****************************** */
    @Test
    fun `validation of not null in a null object`() {
        // act
        val error = assertThrows<ValidationError> {
            Validation.validateNotNull<Any>(null, errorMessage)
        }

        // assert
        assertThat(error.message).isEqualTo(errorMessage)
    }

    @Test
    fun `validation of not null in a non null object`() {
        // act
        Validation.validateNotNull(Object(), errorMessage)

        // assert - no error
    }


    /* ****************************** *
     * **** VALIDATE NULLABILITY **** *
     * ****************************** */
    @Test
    fun `validation of null in a null object`() {
        // act
        Validation.validateNull<Any>(null, errorMessage)

        // assert - no error
    }

    @Test
    fun `validation of null in a non null object`() {
        // act
        val error = assertThrows<ValidationError> {
            Validation.validateNull(Object(), errorMessage)
        }

        // assert - no error
        assertThat(error.message).isEqualTo(errorMessage)
    }

}