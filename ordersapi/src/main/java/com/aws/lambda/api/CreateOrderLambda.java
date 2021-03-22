package com.aws.lambda.api;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.api.dto.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateOrderLambda {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
	
	public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request) throws JsonProcessingException
			  {
		System.out.println(">>>>>>> CreateOrderLambda.createOrder>>>>>>>> ");
	
		Order order = null;
		try {
			order = objectMapper.readValue(request.getBody(), Order.class);
		} catch (JsonProcessingException e) {
			System.out.println(">>>>>>> Error on parsing data  ");
			throw e; 
		}
		System.out.println(">>>>>>> Order created in object>>>>>>>> "+System.getenv("ORDERS_TABLE"));
		
		Table table = dynamoDB.getTable(System.getenv("ORDERS_TABLE"));
		Item item = new Item().withPrimaryKey("id", order.id)
				.withString("itemName", order.itemName)
				.withInt("quantity", order.quantity);
		System.out.println("Before putting items to table");
		table.putItem(item);
		System.out.println("After putting items to table");
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Order ID:" + order.id);

	}
}