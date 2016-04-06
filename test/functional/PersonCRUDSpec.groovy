import geb.spock.GebReportingSpec

import spock.lang.*

import pages.*

@Stepwise
class PersonCRUDSpec extends GebReportingSpec {
	
	def "When there are no people added, list should show no rows"() {
		when: "navigate to list page"
		to ListPage
		then: "no rows are shown on the screen"
		personRows.size() == 0
	}
	
	def "When person is created, result should show the details of person added"() {
		given:"navigate to create page"
		newPersonButton.click()
		at CreatePage
		
		when: "create a person with 'Rahul' first name, 'Sharma' last name details"
		$("#enabled").click()
		firstName = "Rahul"
		lastName = "Sharma"
		createButton.click()
		at ShowPage
		
		then: "result should show 'Rahul' as first name and 'Sharma' as last name"    
		firstName == "Rahul"
		lastName == "Sharma"
		enabled == true
	}
	
	def "edit the details"() {
		when:
		editButton.click()
		then:
		at EditPage
		when:
		$("#enabled").click()
		updateButton.click()
		then:
		at ShowPage
	}
	
	def "check in listing"() {
		when:
		to ListPage
		then:
		personRows.size() == 1
		def row = personRow(0)
		row.firstName == "Rahul"
		row.lastName == "Sharma"
	}
	
	def "show person"() {
		when:
		personRow(0).showLink.click()
		then:
		at ShowPage
	}
	
	def "delete user"() {
		given:
		def deletedId = id
		when:
		withConfirm { deleteButton.click() }
		then:
		at ListPage
		message == "Person $deletedId deleted"
		personRows.size() == 0
	}
}