package org.pih.warehouse.inventory

import grails.test.GrailsUnitTestCase

import org.pih.warehouse.product.Category;
import org.pih.warehouse.product.Product
import org.pih.warehouse.requisition.RequisitionItem

class InventoryItemTests extends GrailsUnitTestCase {

	protected void setUp() {
		super.setUp()
		mockDomain(Category)
		mockDomain(Product)
		mockDomain(InventoryItem)		
		
		
	}

	protected void tearDown() {
		super.tearDown()
	}
	
    void testToJsonData(){
		def category = new Category(id: "cat1", name: "new category")
        def product = new Product(id: "prod1", name:"aspin", category: category)
        def item = new InventoryItem(
                id: "1234",
                product: product,
                lotNumber: "ABCD", 
				expirationDate: new Date()				
        )
		
		def mockControl = mockFor(InventoryService)
		mockControl.demand.getQuantity(1..2) { Inventory inventory, InventoryItem inventoryItem -> 1 }
		item.inventoryService = mockControl.createMock()
		
        Map json = item.toJson()
		
        assert json.id == item.id
        assert json.productId == item.product.id
        assert json.productName == item.product.name
        assert json.lotNumber == item.lotNumber
		assert json.quantityOnHand == 1
		assert json.quantityATP == 1
		assert json.quantityPicked == 0
		
    }
}