import geb.junit4.GebReportingTest

import pages.*
import org.junit.Test

class PersonCRUDTests extends GebReportingTest {

    @Test
    void doSomeCrud() {
        to ListPage
        assert personRows.size() == 0
        newPersonButton.click()
        
        assert at(CreatePage)
        $("#enabled").click()
        firstName = "Rahul"
        lastName = "Sharma"
        createButton.click()
        
        assert at(ShowPage)
        assert enabled == true
        assert firstName == "Rahul"
        assert lastName == "Sharma"
        editButton.click()
        
        assert at(EditPage)
        $("#enabled").click()
        updateButton.click()
        
        assert at(ShowPage)
        
        to ListPage
        assert personRows.size() == 1
        def row = personRow(0)
        assert row.firstName == "Rahul"
        assert row.lastName == "Sharma"
        row.showLink.click()
        
        assert at(ShowPage)
        def deletedId = id
        withConfirm { deleteButton.click() }
        
        assert at(ListPage)
        assert message == "Person $deletedId deleted"
        assert personRows.size() == 0
    }
}