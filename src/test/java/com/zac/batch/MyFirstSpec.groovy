package com.zac.batch

import static org.junit.Assert.*

import com.zac.batch.entity.Person
import com.zac.batch.repository.PersonRepository
import com.zac.batch.service.PersonService
import org.junit.Test

import spock.lang.Specification

class MyFirstSpec  extends Specification {

	PersonRepository personRepositoryMock = Mock()

	def "let's try this!"() {
		expect:
		Math.max(1, 3) == 3
	}

	def "test where"(){
		given:
		def action2 = new PersonService()
		Person person = new Person()
		person.setId(1)
		person.setFirstName('aaa')
		person.setLastName('bbb')

		when:
		def result = action2.findById(1)

		then:
		1 * personRepositoryMock.findById(1) >> person
		result.getFirstName() == 'aaa'
	}
}
